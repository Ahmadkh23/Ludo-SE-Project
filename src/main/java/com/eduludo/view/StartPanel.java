package com.eduludo.view;

import com.eduludo.model.PlayerColor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

/** Opening screen: new game, game history, question manager (admin only), rules, exit. */
public final class StartPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Implemented by the AppController. */
    public interface Actions {
        void newGame();

        void history();

        void questionManager();

        void rules();

        void exit();
    }

    private final JLabel bankInfo = Theme.centered(" ", Theme.SMALL, Theme.MUTED);

    public StartPanel(Actions actions) {
        super(new GridBagLayout());
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255, 235));
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Theme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(30, 46, 26, 46)));

        JLabel title = Theme.centered("EduLudo", Theme.TITLE, Theme.TEXT);
        JLabel subtitle = Theme.centered("Ludo for software engineering students", Theme.SUBHEADING, Theme.MUTED);
        JLabel subtitle2 = Theme.centered("Agile - MVC - Design Patterns - QA", Theme.BODY, Theme.MUTED);
        center(card, title);
        center(card, subtitle);
        center(card, subtitle2);
        card.add(Box.createVerticalStrut(26));

        RoundButton play = RoundButton.primary("New game");
        RoundButton history = RoundButton.secondary("Game history");
        RoundButton admin = RoundButton.secondary("Question manager (admin)");
        RoundButton rules = RoundButton.secondary("How to play");
        RoundButton exit = RoundButton.secondary("Exit");
        play.setFont(Theme.SUBHEADING);
        play.addActionListener(e -> actions.newGame());
        history.addActionListener(e -> actions.history());
        admin.addActionListener(e -> actions.questionManager());
        rules.addActionListener(e -> actions.rules());
        exit.addActionListener(e -> actions.exit());
        for (RoundButton b : new RoundButton[] {play, history, admin, rules, exit}) {
            b.setMaximumSize(new Dimension(320, 46));
            b.setPreferredSize(new Dimension(320, 46));
            center(card, b);
            card.add(Box.createVerticalStrut(10));
        }
        card.add(Box.createVerticalStrut(6));
        center(card, bankInfo);
        add(card);
    }

    private static void center(JPanel p, javax.swing.JComponent c) {
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(c);
    }

    public void setBankInfo(String text) {
        bankInfo.setText(text);
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(new GradientPaint(0, 0, Theme.BACKGROUND, 0, getHeight(), Theme.mix(Theme.BACKGROUND, Theme.PRIMARY, 0.12)));
        g.fillRect(0, 0, getWidth(), getHeight());
        // decorative pieces in the four corners
        PlayerColor[] colors = PlayerColor.values();
        double r = Math.min(getWidth(), getHeight()) * 0.16;
        double[][] spots = {{0, 0}, {getWidth(), 0}, {getWidth(), getHeight()}, {0, getHeight()}};
        for (int i = 0; i < 4; i++) {
            g.setColor(Theme.mix(Theme.color(colors[i]), Theme.BACKGROUND, 0.55));
            g.fill(new Ellipse2D.Double(spots[i][0] - r, spots[i][1] - r, r * 2, r * 2));
        }
        g.dispose();
    }
}
