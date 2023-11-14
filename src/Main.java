import java.awt.desktop.ScreenSleepListener;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Player player1;
    private static Player player2;
    private static Board board;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean end = false;

        player1 = Player.readPlayerFromKeyboard(input, 1);
        player2 = Player.readPlayerFromKeyboard(input, 2);
        while (!end) {
            playGame();
            System.out.println("Another game (y/n)");
            String answer = input.next();
            if (answer.equalsIgnoreCase("n") ||
                    answer.equalsIgnoreCase("no")) {
                end = true;
            }
        }
    }

    private static void playGame() {
        Player currentPlayer = chooseRandomPlayer();
        board = new Board();
        boolean gameOver = false;
        boolean incorrectCell = false;
        while (!gameOver) {
            if (!incorrectCell) {
                System.out.println(board);
            }
            Shot shot = getShot(currentPlayer);
            incorrectCell = !board.shoot(shot.row, shot.col, currentPlayer);
            if (incorrectCell) {
                System.out.println("Incorrect cell. Shoot again.");
            } else {
                if (board.wins(currentPlayer)) {
                    processWin(currentPlayer);
                    gameOver = true;
                } else {
                    if (board.isTie()) {
                        processTie();
                        gameOver = true;
                    }
                }
                // Change turn
                currentPlayer = currentPlayer == player1 ? player2 : player1;
            }
        }
    }

    private static void processTie() {
        System.out.println(board);
        System.out.println("It is a TIE !!!");
    }

    private static void processWin(Player currentPlayer) {
        System.out.println(board);
        System.out.println(currentPlayer.getName() + " YOU WIN !!!!");
    }

    private static Shot getShot(Player currentPlayer) {
        if (currentPlayer.isAi()) {
            Player enemy = currentPlayer == player1 ? player2 : player1;
            return AIEngine.getAIShoot(currentPlayer, enemy, board);
        } else {
            return getHumanShoot(currentPlayer);
        }

    }

    private static Shot getHumanShoot(Player currentPlayer) {
        System.out.println(currentPlayer.getName() +
                " (" + currentPlayer.getSymbol() + ")");
        Shot shot = new Shot();

        while (shot.row < 0 || shot.row >= Board.SIZE) {
            System.out.print("Enter row: ");
            try {
                int x = 0;
                int y = 2;
                int z = y / x;
                shot.row = input.nextInt() - 1;

            } catch (InputMismatchException ex) {
                input.nextLine();
            } finally {
                System.out.println("FINALLY");
            }
        }
        while (shot.col < 0 || shot.col >= Board.SIZE) {
            System.out.print("Enter column: ");
            try {
                shot.col = input.nextInt() - 1;
            } catch (InputMismatchException ex) {
                input.nextLine();
            }
        }

        return shot;
    }

    private static Player chooseRandomPlayer() {
        int num = (int) (Math.random() * 2);
        return num == 0 ? player1 : player2;
    }


}
