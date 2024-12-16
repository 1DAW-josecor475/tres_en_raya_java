import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
    }
    public static void printBoard() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
                System.out.print("-         ");
            }
            System.out.println("");
        }
    }
}