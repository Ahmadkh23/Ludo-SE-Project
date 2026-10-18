package com.eduludo.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A finished game, as stored in the history: when it ended, the difficulty, how long it took,
 * and the players in finishing order (winner first).
 *
 * @param id              unique id
 * @param finishedAt      date and time the game ended
 * @param difficulty      game level
 * @param durationSeconds play time in seconds
 * @param ranking         players in finishing order
 */
public record GameRecord(String id, LocalDateTime finishedAt, Difficulty difficulty, long durationSeconds,
                         List<RankedPlayer> ranking) {

    public GameRecord {
        ranking = List.copyOf(ranking);
    }

    /** One line of the ranking. */
    public record RankedPlayer(int place, String name, PlayerColor color, boolean computer) {
    }

    public String winnerName() {
        return ranking.isEmpty() ? "" : ranking.get(0).name();
    }

    /** Duration formatted as mm:ss or h:mm:ss. */
    public String formattedDuration() {
        return formatSeconds(durationSeconds);
    }

    public static String formatSeconds(long seconds) {
        long h = seconds / 3600;
        long m = (seconds % 3600) / 60;
        long s = seconds % 60;
        return h > 0 ? String.format("%d:%02d:%02d", h, m, s) : String.format("%02d:%02d", m, s);
    }
}
