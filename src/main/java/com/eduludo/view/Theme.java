package com.eduludo.view;

import com.eduludo.model.PlayerColor;
import com.eduludo.model.QuestionLevel;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;

/** Colours and fonts used by every screen, so the whole UI looks consistent. */
public final class Theme {

    public static final Color BACKGROUND = new Color(0xF4F1EA);
    public static final Color PANEL = Color.WHITE;
    public static final Color TEXT = new Color(0x22262E);
    public static final Color MUTED = new Color(0x6B7280);
    public static final Color PRIMARY = new Color(0x3B5BDB);
    public static final Color PRIMARY_DARK = new Color(0x2F4AB8);
    public static final Color DANGER = new Color(0xC92A2A);
    public static final Color SUCCESS = new Color(0x2B8A3E);
    public static final Color BORDER = new Color(0xD9D4C7);
    public static final Color BOARD_LINE = new Color(0x8A8172);
    public static final Color TRACK = new Color(0xFFFDF8);
    public static final Color RING = new Color(0xF1ECE0);
    public static final Color SAFE = new Color(0xE9ECEF);
    public static final Color SURPRISE = new Color(0x9C36B5);

    public static final Font TITLE = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    public static final Font HEADING = new Font(Font.SANS_SERIF, Font.BOLD, 22);
    public static final Font SUBHEADING = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    public static final Font BODY = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    public static final Font BODY_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    public static final Font SMALL = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    public static final Font MONO = new Font(Font.MONOSPACED, Font.BOLD, 20);

    private Theme() {
    }

    public static Color color(PlayerColor c) {
        return switch (c) {
            case RED -> new Color(0xE03131);
            case GREEN -> new Color(0x2F9E44);
            case YELLOW -> new Color(0xF2B705);
            case BLUE -> new Color(0x1C7ED6);
        };
    }

    public static Color light(PlayerColor c) {
        return mix(color(c), Color.WHITE, 0.62);
    }

    public static Color dark(PlayerColor c) {
        return mix(color(c), Color.BLACK, 0.30);
    }

    public static Color color(QuestionLevel level) {
        return switch (level) {
            case EASY -> new Color(0x40C057);
            case MEDIUM -> new Color(0xFAB005);
            case HARD -> new Color(0xF03E3E);
        };
    }

    /** Mixes two colours: amount 0 = a, 1 = b. */
    public static Color mix(Color a, Color b, double amount) {
        double k = 1 - amount;
        return new Color(
                (int) Math.round(a.getRed() * k + b.getRed() * amount),
                (int) Math.round(a.getGreen() * k + b.getGreen() * amount),
                (int) Math.round(a.getBlue() * k + b.getBlue() * amount));
    }

    public static JLabel label(String text, Font font, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(color);
        return l;
    }

    public static JLabel centered(String text, Font font, Color color) {
        JLabel l = label(text, font, color);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }

    public static Border card() {
        return BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(14, 16, 14, 16));
    }

    public static <T extends JComponent> T padded(T c, int top, int left, int bottom, int right) {
        c.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return c;
    }
}
