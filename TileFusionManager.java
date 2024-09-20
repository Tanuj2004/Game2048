/**
 * A simple implementation of the 2048 game.
 * The game starts with an empty board, and the user can move tiles
 * using the arrow keys. When two tiles of the same number collide,
 * they merge into one with their values added together.
 * Controls:
 * W / UP - Move tiles up
 * A / LEFT - Move tiles left
 * S / DOWN - Move tiles down
 * D / RIGHT - Move tiles right
 * The game continues until no moves are possible. The user is then prompted
 * to either restart the game or exit.
 * Owner -> TANUJ YADAV
 * Date -> 17/09/2024
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;
import java.util.Scanner;
public class TileFusionManager {
    private int gridSize;
    private int[][] gameBoard;
    private boolean hasMoved = false;
    private int currentScore = 0;
    private TileCanvas tileCanvas;
    public static void main(String[] args) {
        TileFusionManager game = new TileFusionManager();
        game.start();
    }
    /*
     * Starts the game by initializing the game board, setting the grid size,
     * and creating the JFrame window.
     */
    public void start() {
        Scanner gridSizeInput = new Scanner(System.in);
        gridSize = getValidGridSize(gridSizeInput);
        gameBoard = new int[gridSize][gridSize];
        gameBoard[0][0] = 2;
        gameBoard[1][1] = 2;
        JFrame frame = new JFrame("2048 Game");
        tileCanvas = new TileCanvas(gameBoard, gridSize, currentScore);
        frame.add(tileCanvas);
        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                hasMoved = false;
                switch (keyCode) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        moveUp();
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        moveDown();
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        moveRight();
                        break;
                }
                if (hasMoved) {
                    addNewTile();
                    if (isGameOver()) {
                        int response = JOptionPane.showConfirmDialog(frame,
                                Constants.DIALOG + currentScore + Constants.PLAY_AGAIN,
                                Constants.GAME_OVER,
                                JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            resetGame();
                        } else {
                            System.exit(0);
                        }
                    }
                }
                tileCanvas.updateBoard(gameBoard, currentScore);
            }
        });
    }
    /*
     * Prompts the user for a valid grid size between 2 and 8.
     * If the user provides an invalid input (non-integer or out of range),
     * it will repeatedly ask until a valid input is provided.
     * @param gridSizeInput The Scanner object to get user input
     * @return The valid grid size (between 2 and 8)
     */
    private int getValidGridSize(Scanner gridSizeInput) {
        int gridSize = 0;
        while (true) {
            try {
                System.out.println(Constants.GRID_SIZE);
                gridSize = gridSizeInput.nextInt();
                if (gridSize >= 2 && gridSize <= 8) {
                    break;
                } else {
                    System.out.println(Constants.INPUT_ERROR_NUMER);
                }
            } catch (InputMismatchException e) {
                System.out.println(Constants.INPUT_ERROR);
                gridSizeInput.next();
            }
        }
        return gridSize;
    }
    /*
     * Checks if the game is over. The game ends when no more moves are possible.
     * A move is possible if there are empty cells or adjacent tiles with the same value.
     * @return true if the game is over, false otherwise.
     */
    private boolean isGameOver() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gameBoard[i][j] == 0) {
                    return false;
                }
                if (i > 0 && gameBoard[i][j] == gameBoard[i - 1][j]) {
                    return false;
                }
                if (i < gridSize - 1 && gameBoard[i][j] == gameBoard[i + 1][j]) {
                    return false;
                }
                if (j > 0 && gameBoard[i][j] == gameBoard[i][j - 1]) {
                    return false;
                }
                if (j < gridSize - 1 && gameBoard[i][j] == gameBoard[i][j + 1]) {
                    return false;
                }
            }
        }
        return true;
    }
     // Moves all tiles up and merges adjacent tiles with the same value.
    private void moveUp() {
        for (int col = 0; col < gridSize; col++) {
            for (int row = 1; row < gridSize; row++) {
                if (gameBoard[row][col] != 0) {
                    int newRow = row;
                    while (newRow > 0 && gameBoard[newRow - 1][col] == 0) {
                        gameBoard[newRow - 1][col] = gameBoard[newRow][col];
                        gameBoard[newRow][col] = 0;
                        newRow--;
                        hasMoved = true;
                    }
                    if (newRow > 0 && gameBoard[newRow - 1][col] == gameBoard[newRow][col]) {
                        gameBoard[newRow - 1][col] *= 2;
                        currentScore += gameBoard[newRow - 1][col];
                        gameBoard[newRow][col] = 0;
                        hasMoved = true;
                    }
                }
            }
        }
    }
      //Moves all tiles down and merges adjacent tiles with the same value.
    private void moveDown() {
        for (int col = 0; col < gridSize; col++) {
            for (int row = gridSize - 2; row >= 0; row--) {
                if (gameBoard[row][col] != 0) {
                    int newRow = row;
                    while (newRow < gridSize - 1 && gameBoard[newRow + 1][col] == 0) {
                        gameBoard[newRow + 1][col] = gameBoard[newRow][col];
                        gameBoard[newRow][col] = 0;
                        newRow++;
                        hasMoved = true;
                    }
                    if (newRow < gridSize - 1 && gameBoard[newRow + 1][col] == gameBoard[newRow][col]) {
                        gameBoard[newRow + 1][col] *= 2;
                        currentScore += gameBoard[newRow + 1][col];
                        gameBoard[newRow][col] = 0;
                        hasMoved = true;
                    }
                }
            }
        }
    }
     // Moves all tiles left and merges adjacent tiles with the same value.
    private void moveLeft() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 1; col < gridSize; col++) {
                if (gameBoard[row][col] != 0) {
                    int newCol = col;
                    while (newCol > 0 && gameBoard[row][newCol - 1] == 0) {
                        gameBoard[row][newCol - 1] = gameBoard[row][newCol];
                        gameBoard[row][newCol] = 0;
                        newCol--;
                        hasMoved = true;
                    }
                    if (newCol > 0 && gameBoard[row][newCol - 1] == gameBoard[row][newCol]) {
                        gameBoard[row][newCol - 1] *= 2;
                        currentScore += gameBoard[row][newCol - 1];
                        gameBoard[row][newCol] = 0;
                        hasMoved = true;
                    }
                }
            }
        }
    }
     // Moves all tiles right and merges adjacent tiles with the same value.
    private void moveRight() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = gridSize - 2; col >= 0; col--) {
                if (gameBoard[row][col] != 0) {
                    int newCol = col;
                    while (newCol < gridSize - 1 && gameBoard[row][newCol + 1] == 0) {
                        gameBoard[row][newCol + 1] = gameBoard[row][newCol];
                        gameBoard[row][newCol] = 0;
                        newCol++;
                        hasMoved = true;
                    }
                    if (newCol < gridSize - 1 && gameBoard[row][newCol + 1] == gameBoard[row][newCol]) {
                        gameBoard[row][newCol + 1] *= 2;
                        currentScore += gameBoard[row][newCol + 1];
                        gameBoard[row][newCol] = 0;
                        hasMoved = true;
                    }
                }
            }
        }
    }
     // Adds a new tile (with value 2) to a random empty cell.
    private void addNewTile() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gameBoard[i][j] == 0) {
                    gameBoard[i][j] = 2;
                    return;
                }
            }
        }
    }
     // Resets the game state to start a new game.
    private void resetGame() {
        gameBoard = new int[gridSize][gridSize];
        currentScore = 0;
        gameBoard[0][0] = 2;
        gameBoard[1][1] = 2;
        tileCanvas.updateBoard(gameBoard, currentScore);
    }
}
