package com.eduludo.persistence;

import com.eduludo.model.Difficulty;
import com.eduludo.model.GameRecord;
import com.eduludo.model.PlayerColor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Stores the game history in a CSV file (one game per line, opens in Excel).
 * <pre>id,finishedAt,difficulty,durationSeconds,playerCount,ranking</pre>
 * {@code ranking} = "place:name:COLOR:computer" items separated by '|', winner first.
 * Fields are quoted when needed, so names may contain commas or quotes. Damaged lines are skipped.
 */
public final class CsvHistoryRepository implements HistoryRepository {

    static final String HEADER = "id,finishedAt,difficulty,durationSeconds,playerCount,ranking";

    private final Path file;

    public CsvHistoryRepository(Path file) {
        this.file = file;
    }

    @Override
    public synchronized void save(GameRecord r) {
        StringBuilder ranking = new StringBuilder();
        for (GameRecord.RankedPlayer p : r.ranking()) {
            if (!ranking.isEmpty()) {
                ranking.append('|');
            }
            ranking.append(p.place()).append(':').append(escapeRankingName(p.name())).append(':')
                    .append(p.color().name()).append(':').append(p.computer());
        }
        String line = String.join(",",
                quote(r.id()),
                quote(r.finishedAt().toString()),
                quote(r.difficulty().name()),
                Long.toString(r.durationSeconds()),
                Integer.toString(r.ranking().size()),
                quote(ranking.toString()));
        try {
            Files.createDirectories(file.toAbsolutePath().getParent());
            if (!Files.exists(file)) {
                Files.writeString(file, HEADER + System.lineSeparator(), StandardCharsets.UTF_8);
            }
            Files.writeString(file, line + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save the game history", e);
        }
    }

    @Override
    public synchronized List<GameRecord> findAll() {
        List<GameRecord> result = new ArrayList<>();
        if (!Files.exists(file)) {
            return result;
        }
        List<String> lines;
        try {
            lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read the game history", e);
        }
        for (String line : lines) {
            if (line.isBlank() || line.startsWith("id,")) {
                continue;
            }
            try {
                List<String> f = parseCsvLine(line);
                List<GameRecord.RankedPlayer> ranking = new ArrayList<>();
                for (String item : splitRanking(f.get(5))) {
                    String[] parts = item.split(":", -1);
                    int n = parts.length;
                    // name may contain ':' (escaped as \:), so take place first and color/computer last
                    String name = unescapeRankingName(String.join(":", java.util.Arrays.copyOfRange(parts, 1, n - 2)));
                    ranking.add(new GameRecord.RankedPlayer(Integer.parseInt(parts[0]), name,
                            PlayerColor.valueOf(parts[n - 2]), Boolean.parseBoolean(parts[n - 1])));
                }
                result.add(new GameRecord(f.get(0), LocalDateTime.parse(f.get(1)), Difficulty.valueOf(f.get(2)),
                        Long.parseLong(f.get(3)), ranking));
            } catch (RuntimeException ex) {
                // Skip a damaged line instead of losing the whole history.
            }
        }
        result.sort(Comparator.comparing(GameRecord::finishedAt).reversed());
        return result;
    }

    static String quote(String s) {
        if (s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    static List<String> parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        cur.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    cur.append(c);
                }
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                fields.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        fields.add(cur.toString());
        return fields;
    }

    private static String escapeRankingName(String name) {
        return name.replace("\\", "\\\\").replace("|", "\\p").replace(":", "\\c");
    }

    private static String unescapeRankingName(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '\\' && i + 1 < name.length()) {
                char n = name.charAt(++i);
                sb.append(switch (n) {
                    case 'p' -> '|';
                    case 'c' -> ':';
                    default -> n;
                });
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static List<String> splitRanking(String ranking) {
        List<String> items = new ArrayList<>();
        if (ranking.isEmpty()) {
            return items;
        }
        for (String s : ranking.split("\\|", -1)) {
            items.add(s);
        }
        return items;
    }
}
