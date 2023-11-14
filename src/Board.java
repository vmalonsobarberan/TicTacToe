public class Board {
    public static final char SYMBOL_PLAYER_1 = 'X';
    public static final char SYMBOL_PLAYER_2 = 'O';
    public static final char SYMBOL_EMPTY = '·';
    public static final int SIZE = 3;

    private CellType[][] matrix;

    public Board() {
        matrix = new CellType[SIZE][SIZE];
        for(int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = CellType.EMPTY;
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        s += getHeaderStr();
        for (int row = 0; row < matrix.length; row++) {
            s += getRowStr(row);
        }
        return s;
    }

    private char getSymbolForCellType(CellType cellType) {
        switch (cellType) {
            case EMPTY: return SYMBOL_EMPTY;
            case PLAYER1: return SYMBOL_PLAYER_1;
            case PLAYER2: return SYMBOL_PLAYER_2;
        }
        return SYMBOL_EMPTY;
    }

    private String getRowStr(int row) { // row parameter starts with 0
        String s = (row + 1) + " ";
        for (int col = 0; col < matrix[0].length; col++) {
            s += getSymbolForCellType(matrix[row][col]) + " ";
        }
        s += "\n";
        return s;
    }

    private static String getHeaderStr() {
        String s = "  ";
        for (int i = 1; i <= SIZE; i++) {
            s += i + " ";
        }
        s += "\n";
        return s;
    }

    public void unshoot(int row, int col) {
        matrix[row][col] = CellType.EMPTY;
    }
    public boolean shoot(int row, int col, Player player) {
        if (matrix[row][col] == CellType.EMPTY) {
            matrix[row][col] = player.getSymbol();
            return true;
        } else {
            return false;
        }
    }

    public boolean isTie() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (matrix[row][col] == CellType.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean wins(Player player) {
        CellType symbol = player.getSymbol();
        if (winsRows(symbol)) {
            return true;
        }
        if (winsColumns(symbol)) {
            return true;
        }
        if (winsDiagonals(symbol)) {
            return true;
        }
        return false;
    }

    private boolean winsDiagonals(CellType symbol) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][i] == symbol) {
                count++;
            }
        }
        if (count == SIZE) {
            return true;
        }
        count = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][SIZE - i - 1] == symbol) {
                count++;
            }
        }
        return count == SIZE;
    }

    private boolean winsColumns(CellType symbol) {
        for (int col = 0; col < matrix[0].length; col++) {
            if (winsColumn(col, symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean winsColumn(int col, CellType symbol) {
        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row][col] != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean winsRows(CellType symbol) {
        for (int row = 0; row < matrix.length; row++) {
            if (winsRow (row, symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean winsRow(int row, CellType symbol) {
        for (int col = 0; col < matrix[0].length; col++) {
            if (matrix[row][col] != symbol) {
                return false;
            }
        }
        return true;
    }

    public CellType getCell(int row, int col) {
        return matrix[row][col];
    }
}
