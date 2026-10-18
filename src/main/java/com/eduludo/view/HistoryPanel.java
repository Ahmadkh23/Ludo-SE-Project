package com.eduludo.view;

import com.eduludo.model.Difficulty;
import com.eduludo.model.GameRecord;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Game history: date, level, duration, winner and full ranking of every finished game, with a level filter. */
public final class HistoryPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final transient List<GameRecord> all = new ArrayList<>();
    private final Model model = new Model();
    private final JComboBox<String> filter = new JComboBox<>(new String[] {"All levels", "Easy", "Medium", "Hard"});
    private final JLabel summary = Theme.label(" ", Theme.BODY, Theme.MUTED);

    public HistoryPanel(Runnable back) {
        super(new BorderLayout(0, 12));
        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(Theme.label("Game history", Theme.HEADING, Theme.TEXT), BorderLayout.WEST);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);
        right.add(Theme.label("Show:", Theme.BODY, Theme.MUTED));
        filter.addActionListener(e -> apply());
        right.add(filter);
        top.add(right, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(Theme.BODY);
        table.getTableHeader().setFont(Theme.BODY_BOLD);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(130);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(420);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(summary, BorderLayout.CENTER);
        RoundButton backButton = RoundButton.secondary("Back");
        backButton.addActionListener(e -> back.run());
        bottom.add(backButton, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }

    public void setRecords(List<GameRecord> records) {
        all.clear();
        all.addAll(records);
        apply();
    }

    private void apply() {
        int sel = filter.getSelectedIndex();
        List<GameRecord> shown = sel == 0 ? all
                : all.stream().filter(r -> r.difficulty() == Difficulty.values()[sel - 1]).toList();
        model.rows = new ArrayList<>(shown);
        model.fireTableDataChanged();
        if (shown.isEmpty()) {
            summary.setText(all.isEmpty() ? "No games played yet." : "No games on this level yet.");
        } else {
            long avg = (long) shown.stream().mapToLong(GameRecord::durationSeconds).average().orElse(0);
            summary.setText(shown.size() + " game(s)    Average time: " + GameRecord.formatSeconds(avg));
        }
    }

    private static final class Model extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private static final String[] COLUMNS = {"Date", "Level", "Time", "Winner", "Ranking"};
        private transient List<GameRecord> rows = new ArrayList<>();

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Object getValueAt(int row, int col) {
            GameRecord r = rows.get(row);
            return switch (col) {
                case 0 -> r.finishedAt().format(FMT);
                case 1 -> r.difficulty().displayName();
                case 2 -> r.formattedDuration();
                case 3 -> r.winnerName();
                default -> r.ranking().stream()
                        .map(p -> p.place() + ". " + p.name() + " (" + p.color().displayName() + (p.computer() ? ", computer" : "") + ")")
                        .collect(Collectors.joining("   "));
            };
        }
    }
}
