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

    public static boolean validMovement (char board[][], int movement){
        /* Función: comprueba si el movimiento que hizo el jugador es válido.

        Args:
        - char board[][]: para comprobar las casillas del tablero.
        - int movement: para comprobar que se introduce un número que tenga una casilla asignada.

        Devuelve "false" si se se ha hecho un movimiento en una casilla que ya tiene una ficha o si se introduce un número que no tiene una casilla asignada.
         */
        switch (movement) {
            case 1: return board[0][0] == '-';
            case 2: return board[0][1] == '-';
            case 3: return board[0][2] == '-';
            case 4: return board[1][0] == '-';
            case 5: return board[1][1] == '-';
            case 6: return board[1][2] == '-';
            case 7: return board[2][0] == '-';
            case 8: return board[2][1] == '-';
            case 9: return board[2][2] == '-';
        }

        if (movement > 0 || movement < 10) {
            return true;
        }

        return false;
    }
}