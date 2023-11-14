import java.util.SimpleTimeZone;

public class AIEngine {

    public static Shot getAIShoot(Player aiPlayer, Player enemy ,Board board) {
        Shot shot;
        shot = winIfYouCan(aiPlayer, board);
        if (shot != null) return shot;
        shot = blockIfYouCan(aiPlayer, enemy, board);
        if (shot != null) return shot;
        shot = chooseCenter(aiPlayer, board);
        if (shot != null) return shot;
        return chooseRandom(aiPlayer, board);
    }

    private static Shot chooseRandom(Player aiPlayer, Board board) {
        while (true) {
            int row = (int) (Math.random() * Board.SIZE);
            int col = (int) (Math.random() * Board.SIZE);
            if (board.getCell(row, col) == CellType.EMPTY) {
                return new Shot(row, col);
            }
        }
    }

    private static Shot chooseCenter(Player aiPlayer, Board board) {
        if (board.getCell(Board.SIZE / 2, Board.SIZE / 2) == CellType.EMPTY) {
            return new Shot(Board.SIZE / 2, Board.SIZE / 2);
        }
        return null;
    }

    private static Shot blockIfYouCan(Player aiPlayer, Player enemy, Board board) {
        for (int row = 0; row < Board.SIZE; row ++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.getCell(row, col) == CellType.EMPTY) {
                    board.shoot(row, col, enemy);
                    if (board.wins(enemy)) {
                        board.unshoot(row, col);
                        return new Shot(row, col);
                    } else {
                        board.unshoot(row, col);
                    }
                }
            }
        }
        return null;
    }

    public static Shot winIfYouCan(Player aiPlayer, Board board) {
        for (int row = 0; row < Board.SIZE; row ++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.getCell(row, col) == CellType.EMPTY) {
                    board.shoot(row, col, aiPlayer);
                    if (board.wins(aiPlayer)) {
                        board.unshoot(row, col);
                        return new Shot(row, col);
                    } else {
                        board.unshoot(row, col);
                    }
                }
            }
        }
        return null;
    }
}
