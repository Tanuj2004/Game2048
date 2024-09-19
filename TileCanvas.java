/**
 * GamePanel is responsible for rendering the game board and the current score.
 * It handles drawing the tiles with their corresponding colors and values.
 * Owner -> TANUJ YADAV
 * Date -> 17/09/2024
 */

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class TileCanvas extends JPanel {
    private int[][] gameBoard;
    private int gridSize;
    private int currentScore;
    private boolean isAnimating;
    private int animationStep;
    private Timer animationTimer;
    public TileCanvas(int[][] gameBoard, int gridSize, int currentScore) {
        this.gameBoard = gameBoard;
        this.gridSize = gridSize;
        this.currentScore = currentScore;
        this.isAnimating = false;
        this.animationStep = 0;
        animationTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                if (animationStep > 20) {
                    isAnimating = false;
                    animationTimer.stop();
                }
                repaint();
            }
        });
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        drawGameBoard(graphics2D);
        drawScore(graphics2D);
    }
    /**
     * Draws the game board and the tiles on it.
     */
    private void drawGameBoard(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, getWidth(), getHeight() - 50);
        int tileSize = (getWidth() - 20) / gridSize;
        graphics2D.setFont(new Font("Arial", Font.BOLD, tileSize / 3));
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                drawTile(graphics2D, row, col, tileSize);
            }
        }
    }
    /**
     * Draws an individual tile on the board.
     */
    private void drawTile(Graphics2D graphics2D, int row, int col, int tileSize) {
        int tileValue = gameBoard[row][col];
        int x = col * tileSize + 10;
        int y = row * tileSize + 10;
        if (isAnimating) {
            double scale = 1.0 + 0.1 * Math.sin(Math.PI * animationStep / 20);
            int scaledSize = (int) (tileSize * scale);
            int offset = (tileSize - scaledSize) / 2;
            x += offset;
            y += offset;
            tileSize = scaledSize;
        }
        graphics2D.setColor(getTileColor(tileValue));
        graphics2D.fillRect(x, y, tileSize, tileSize);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(x, y, tileSize, tileSize);
        if (tileValue != 0) {
            String text = String.valueOf(tileValue);
            int textWidth = graphics2D.getFontMetrics().stringWidth(text);
            int textHeight = graphics2D.getFontMetrics().getAscent();
            graphics2D.drawString(text, x + (tileSize - textWidth) / 2, y + (tileSize + textHeight) / 2);
        }
    }
    /**
     * Draws the current score at the bottom of the window.
     */
    private void drawScore(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, getHeight() - 50, getWidth(), 50); // Background for score
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        graphics2D.drawString("Score: " + currentScore, 10, getHeight() - 20);
    }
    /**
     * Returns the color corresponding to the tile's value.
     */
    private Color getTileColor(int tileValue) {
        switch (tileValue) {
            case 2:
                return new Color(0xeee4da);
            case 4:
                return new Color(0xede0c8);
            case 8:
                return new Color(0xf2b179);
            case 16:
                return new Color(0xf59563);
            case 32:
                return new Color(0xf67c5f);
            case 64:
                return new Color(0xf65e3b);
            case 128:
                return new Color(0xedcf72);
            case 256:
                return new Color(0xedcc61);
            case 512:
                return new Color(0xedc850);
            case 1024:
                return new Color(0xedc53f);
            case 2048:
                return new Color(0xedc22e);
            default:
                return new Color(0xcdc1b4);
        }
    }
    /**
     * Updates the board and the score and triggers an animation.
     */
    public void updateBoard(int[][] gameBoard, int currentScore) {
        this.gameBoard = gameBoard;
        this.currentScore = currentScore;
        this.isAnimating = true;
        this.animationStep = 0;
        animationTimer.start();
    }
}
