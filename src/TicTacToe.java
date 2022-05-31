import java.util.Scanner;

public class TicTacToe {
    static final int EMPTY = 0;
    static final int NAUGHT = 1;
    static final int CROSS = 2;
    static int[] grid = new int[9];

    static int turn = CROSS;
    static boolean running = true;

    public static void main(String[] args) {
        System.out.print(getBoardString());
        while (running) {
            getUserInput();
        }
    }

    public static int checkWinner() { // Returns the turn who won if there is a winner
        // Rows and columns
        for (int i = 0; i < 3; i++) {
            if (grid[i * 3] == turn && grid[i * 3 + 1] == turn && grid[i * 3 + 2] == turn)
                return turn;
            if (grid[i] == turn && grid[3 + i] == turn && grid[6 + i] == turn)
                return turn;
        }
        // Diagonals
        if (grid[0] == turn && grid[4] == turn && grid[8] == turn)
            return turn;
        if (grid[2] == turn && grid[4] == turn && grid[6] == turn)
            return turn;

        boolean gridIsFull = true;
        for (int i = 0; i < 9; i++) {
            if (grid[i] == EMPTY) {
                gridIsFull = false;
                break;
            }
        }
        if (gridIsFull)
            return -1; // Draw

        return EMPTY; // No winner
    }

    public static void restart() {
        for (int i = 0; i < 9; i++)
            grid[i] = EMPTY;
        turn = CROSS;
        System.out.println("####################################");
        System.out.print(getBoardString());
    }

    public static void endGame() {
        System.out.println("Ending game...");
        // Sleep for 1 second
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        running = false;
    }

    public static void makeMove(int square) {
        grid[square] = turn;
        System.out.print(getBoardString());

        int winner = checkWinner();
        if (winner != EMPTY) {
            Scanner scanner = new Scanner(System.in);

            if (winner == NAUGHT)
                System.out.println("Naughts win!");
            else if (winner == -1)
                System.out.println("Draw!");
            else
                System.out.println("Crosses win!");

            boolean userValidInput = false;
            while (!userValidInput) {
                System.out.print("Play again? (y/n) ");
                String userWant = scanner.nextLine().toLowerCase();
                if (userWant.equals("y")) {
                    restart();
                    userValidInput = true;
                }
                else if (userWant.equals("n")) {
                    endGame();
                    userValidInput = true;
                }
                else
                    System.out.println("Put in 'y' or 'n'");
            }
        }
        else {
            // Update turns
            if (turn == CROSS)
                turn = NAUGHT;
            else
                turn = CROSS;
        }
    }

    public static void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input position (1 - 9): ");
        // Validate position
        if (scanner.hasNextInt()) {
            int inputSquare = scanner.nextInt();
            if (inputSquare >= 1 && inputSquare <= 9) {
                if (grid[inputSquare - 1] == EMPTY) // Valid spot
                    makeMove(inputSquare - 1);
                else
                    System.out.println("Square is occupied");
            }
            else
                System.out.println("Put a number between 1 - 9");
        }
        else
            System.out.println("Put in a number");
    }

    static String getBoardString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int gridItem = grid[i];
            if (gridItem == EMPTY)
                string.append(".");
            else if (gridItem == NAUGHT)
                string.append("O");
            else if (gridItem == CROSS)
                string.append("X");

            if ((i + 1) % 3 == 0)
                string.append("\n");
        }

        return string.toString();
    }
}
