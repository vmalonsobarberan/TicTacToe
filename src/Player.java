import java.util.Scanner;

public class Player {
    private String name;
    private boolean isAi;
    private CellType symbol;

    public Player(String name, boolean isAi, CellType symbol) {
        this.name = name;
        this.isAi = isAi;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public boolean isAi() {
        return isAi;
    }

    public CellType getSymbol() {
        return symbol;
    }

    public static Player readPlayerFromKeyboard(Scanner input, int numPlayer) {
        System.out.println("Enter name: ");
        String name = input.next();
        System.out.println("Are you an AI? (y/n)");
        String aiStr = input.next();
        boolean ai = false;
        if (aiStr.equalsIgnoreCase("y") ||
                aiStr.equalsIgnoreCase("yes")) {
            ai = true;
        }
        CellType symbol = numPlayer == 1 ?
                CellType.PLAYER1 : CellType.PLAYER2;
        return new Player(name, ai, symbol);
    }

    @Override
    public String toString() {
        return name + " (" + (isAi ? "AI" : "human") + ") " + symbol;
    }
}
