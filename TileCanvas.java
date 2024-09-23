/**
 * The TileCanvas class is responsible for rendering the game board and
 * displaying the current score in a 2048 game with rounded corner tiles.
 * It extends JPanel and overrides the paintComponent method to handle
 * custom drawing of tiles and the score.
 * Owner -> TANUJ YADAV
 * Date -> 17/09/2024
 */
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class TileCanvas extends JPanel {
    private int[][] gameBoard;
    private int gridSize;
    private int Score;

    /*
     * Constructor to initialize the TileCanvas with the game board, grid size, and score.
     * @param gameBoard 2D array representing the current state of the game board.
     * @param gridSize The size of the grid (e.g., 4 for a 4x4 grid).
     * @param currentScore The current score of the player.
     */
    public TileCanvas(int[][] gameBoard, int gridSize, int currentScore) {
        this.gameBoard = gameBoard;
        this.gridSize = gridSize;
        this.Score = currentScore;
    }

    /*
     * Overridden method that is called whenever the component needs to be repainted.
     * It calls helper methods to draw the game board and the score.
     * @param g The Graphics object used to draw on the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        // Ensure gameBoard is not null before trying to draw it
        if (gameBoard != null) {
            drawGameBoard(graphics2D);
        }
        drawScore(graphics2D);
    }

    /*
     * Draws the entire game board by iterating over the grid and calling the drawTile method
     * for each tile. The background is filled first, then tiles are drawn.
     * @param graphics2D The Graphics2D object used to draw on the canvas.
     */
    private void drawGameBoard(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, getWidth(), getHeight() - 50);  // Draw background

        int tileSize = (getWidth() - 20) / gridSize;
        graphics2D.setFont(new Font("Arial", Font.BOLD, tileSize / 3));  // Set font size relative to tile size

        // Loop over each tile and draw it
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                drawTile(graphics2D, row, col, tileSize);
            }
        }
    }

    /*
     * Draws an individual tile at the specified row and column with rounded corners.
     * The tile is filled with a color based on its value, and the number is drawn in the center.
     * @param graphics2D The Graphics2D object used to draw on the canvas.
     * @param row The row index of the tile.
     * @param col The column index of the tile.
     * @param tileSize The size of the tile.
     */
    private void drawTile(Graphics2D graphics2D, int row, int col, int tileSize) {
        int tileValue = gameBoard[row][col];
        int x = col * tileSize + 10;
        int y = row * tileSize + 10;
        graphics2D.setColor(getTileColor(tileValue));  // Get the color based on the tile value
        int arcSize = 20;
        graphics2D.fillRoundRect(x, y, tileSize, tileSize, arcSize, arcSize);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRoundRect(x, y, tileSize, tileSize, arcSize, arcSize);

        if (tileValue != 0) {  // Draw the value if it's not 0
            String text = String.valueOf(tileValue);
            int textWidth = graphics2D.getFontMetrics().stringWidth(text);
            int textHeight = graphics2D.getFontMetrics().getAscent();
            graphics2D.drawString(text, x + (tileSize - textWidth) / 2, y + (tileSize + textHeight) / 2);
        }
    }

    /*
     * Draws the current score at the bottom of the window.
     * The score is displayed with a white background and black text.
     * @param graphics2D The Graphics2D object used to draw on the canvas.
     */
    private void drawScore(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, getHeight() - 50, getWidth(), 50);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 24));
        graphics2D.drawString("Score: " + Score, 10, getHeight() - 20);
    }

    /*
     * Returns the color corresponding to a tile's value. The higher the value, the
     * darker and more intense the color.
     * @param tileValue The value of the tile (e.g., 2, 4, 8, etc.)
     * @return The Color object representing the tile's background color.
     */
    private Color getTileColor(int tileValue) {
        try {
            switch (tileValue) {
                case 2: return Color.decode(Constants.COLOR_2);
                case 4: return Color.decode(Constants.COLOR_4);
                case 8: return Color.decode(Constants.COLOR_8);
                case 16: return Color.decode(Constants.COLOR_16);
                case 32: return Color.decode(Constants.COLOR_32);
                case 64: return Color.decode(Constants.COLOR_64);
                case 128: return Color.decode(Constants.COLOR_128);
                case 256: return Color.decode(Constants.COLOR_256);
                case 512: return Color.decode(Constants.COLOR_512);
                case 1024: return Color.decode(Constants.COLOR_1024);
                case 2048: return Color.decode(Constants.COLOR_2048);
                default: return Color.decode(Constants.COLOR_DEFAULT);
            }
        } catch (NumberFormatException e) {
            return Color.GRAY;
        }
    }

    /*
     * Updates the game board and the score, then triggers a repaint of the canvas.
     * This method should be called from an instance of TileCanvas to ensure repainting.
     * @param gameBoard 2D array representing the current state of the game board.
     * @param currentScore The updated score of the player.
     */
    public void updateBoard(int[][] gameBoard, int currentScore) {
        this.gameBoard = gameBoard;
        this.Score = currentScore;
        this.repaint();
    }
}
