package com.eduludo.view;

import com.eduludo.model.PlayerColor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/** Draws the window icon (four coloured quarters with a die dot), so no image file is needed. */
public final class AppIcon {

    private AppIcon() {
    }

    public static BufferedImage create(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int h = size / 2;
        PlayerColor[] c = PlayerColor.values();
        int[][] pos = {{0, 0}, {h, 0}, {h, h}, {0, h}};
        for (int i = 0; i < 4; i++) {
            g.setColor(Theme.color(c[i]));
            g.fillRect(pos[i][0], pos[i][1], h, h);
        }
        g.setColor(Color.WHITE);
        int d = size / 2;
        g.fillRoundRect(size / 4, size / 4, d, d, size / 6, size / 6);
        g.setColor(Theme.TEXT);
        int p = Math.max(3, size / 10);
        g.fillOval(size / 2 - p / 2, size / 2 - p / 2, p, p);
        g.dispose();
        return img;
    }
}
