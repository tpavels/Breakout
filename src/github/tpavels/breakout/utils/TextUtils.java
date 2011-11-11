package github.tpavels.breakout.utils;

import github.tpavels.breakout.Score;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TextUtils {

    private static final String FONT = "Arial";

    /**
     * Displays pause text
     * @param gfx2d Graphics2D from image
     * @param d Dimension of the screen
     */
    public void displayPause(Graphics2D gfx2d, Dimension d) {
        Font f = new Font(FONT, Font.BOLD, 44);
        gfx2d.setFont(f);
        gfx2d.setColor(new Color(255, 64, 60));
        drawCenteredString("PAUSE", d.width, d.height, gfx2d);
    }

    /**
     * Displays game over text and final score in the center of the screen
     * @param gfx2d Graphics2D from image
     * @param score object with score data
     * @param d Dimension of the screen
     */
    public void displayGameOver(Graphics2D gfx2d, Score score, Dimension d) {
        Font f = new Font(FONT, Font.BOLD, 44);
        gfx2d.setFont(f);
//        gfx2d.setColor(new Color(255, 64, 64)); 
        gfx2d.setColor(Color.RED);
        drawCenteredString("GAME OVER", d.width, d.height, gfx2d);
        f = new Font(FONT, Font.BOLD, 28);
        gfx2d.setFont(f);
        drawCenteredString("(Your total score is " + score.getScore() + " points)", d.width, d.height+100, gfx2d);
    }

    /**
     * Displays win text and final score in the center of the screen
     * @param gfx2d Graphics2D from image
     * @param score object with score data
     * @param d Dimension of the screen
     */
    public void displayWin(Graphics2D gfx2d, Score score, Dimension d) {
        Font f = new Font(FONT, Font.BOLD, 44);
        gfx2d.setFont(f);
        gfx2d.setColor(new Color(255, 64, 60));
        drawCenteredString("Congratulations, you have finished the Game", d.width, d.height, gfx2d);
        f = new Font(FONT, Font.BOLD, 28);
        gfx2d.setFont(f);
        drawCenteredString("(Your total score is " + score.getScore() + " points)", d.width, d.height+100, gfx2d);
    }


    /**
     * Display score information in the topmost right corner of the screen.
     * @param gfx2d Graphics2D from image
     * @param score object with score data
     */
    public void displayScore(Graphics2D gfx2d, Score score) {
        gfx2d.setColor(Color.WHITE);
        Font font = new Font(FONT, Font.BOLD, 16);
        gfx2d.setFont(font);
        gfx2d.drawString("Score: "+ score.getScore(), 5, 20);
    }

    /**
     * Draws string in the center of the screen
     * @param s String to draw
     * @param w screen width
     * @param h screen height
     * @param g Graphics2D from image
     */
    private void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

}
