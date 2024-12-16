import java.util.Scanner;

public class Tres_En_Raya {
    public static void main(String[] args) {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
            welcomeMessage();
            printBoard(board);
            gameLoop(board);
        }
    public static void welcomeMessage() {
        System.out.println("""
                               *********************************************
                               *      TRES  EN  RAYA:  EL  VIDEOJUEGO      *
                               *********************************************
                               """);
    }
    public static void gameLoop(char[][] board) {
        /*
        * Función que maneja todo el bucle del juego.

        * Args:
        * char[][] board: El tablero de juego.

        */
        char user = 'X';
        char computer = 'O';
        Scanner sc = new Scanner(System.in);
        boolean hasEnded = false;
        int turns = 0;

        // Jugabilidad hasta que el juego termine
        while(!hasEnded) {
            int move;

            // Turno del usuario
            do {
                System.out.println("""
                                   * Turno del jugador. Escoja:
                                   1. ARRIBA IZQUIERDA  2. ARRIBA CENTRO    3. ARRIBA DERECHA
                                   4. CENTRO IZQUIERDA  5. CENTRO CENTRO    6. CENTRO CERECHA
                                   7. ABAJO IZQUIERDA   8. ABAJO CENTRO     9. ABAJO DERECHA
                                   """);
                move = sc.nextInt();
                if (!validMove(board, move)) {
                    System.out.println("** MOVIMIENTO INVÁLIDO");
                    printBoard(board);
                } else {
                    System.out.println("** MOVIMIENTO VÁLIDO");
                }
            } while(!validMove(board, move));
            updateBoard(move, user, board);

            printBoard(board);

            // Comprobación de condición de victoria
            if (checkWinner(board, user)) {
                System.out.println("** Enhorabuena, has ganado.");
                hasEnded = true;
            } else {
                // Turno del ordenador
                System.out.println("* Turno de la máquina.");
                do {
                    move = (int) (Math.random() * 9 + 1);
                } while(!validMove(board, move));
                updateBoard(move, computer, board);

                printBoard(board);

                // Comprobación de condición de victoria
                if (checkWinner(board, computer)) {
                    System.out.println("** Lo sentimos, has perdido.");
                    hasEnded = true;
                }
                turns++;

                if (turns == 3) {
                    // Empate
                    System.out.println("** Es un empate.");
                    hasEnded = true;
                }
            }
        }
    }
    public static void printBoard(char[][] board) {
        /*
        * Función encargada de mostrar por pantalla el tablero, en su estado actual.

        * Args:
        * char[][] board: El tablero actual de la partida.
        */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + "      ");
            }
            System.out.println();
        }
    }
    public static boolean validMove (char[][] board, int move){
        /* Función que comprueba si el movimiento que hizo el jugador es válido.

        * Args:
        * - char[][] board: para comprobar las casillas del tablero.
        * - int move: para comprobar que se introduce un número que tenga una casilla asignada.

        * Returns:
        * "false" si se se ha hecho un movimiento en una casilla que ya tiene una ficha o si se introduce un número que no tiene una casilla asignada.
        */
        return switch (move) {
            case 1 -> board[0][0] == '-';
            case 2 -> board[0][1] == '-';
            case 3 -> board[0][2] == '-';
            case 4 -> board[1][0] == '-';
            case 5 -> board[1][1] == '-';
            case 6 -> board[1][2] == '-';
            case 7 -> board[2][0] == '-';
            case 8 -> board[2][1] == '-';
            case 9 -> board[2][2] == '-';
            default -> false;
        };

    }
    public static void updateBoard(int move, char player, char[][] board) {
        /*
         * Esta función se encarga de asignar el caracter del player/ordenador a una posición del tablero.

         * Args:

         * int move: Un entero que es la posición de colocación de la pieza del player.
         * char player: El caracter del jugador en dicha actualización ('X' para usuario / 'O' para el ordenador).
         * char[][] board: Matriz que representa el tablero de juego.

         */
        switch (move) {
            case 1: board[0][0] = player; break;
            case 2: board[0][1] = player; break;
            case 3: board[0][2] = player; break;
            case 4: board[1][0] = player; break;
            case 5: board[1][1] = player; break;
            case 6: board[1][2] = player; break;
            case 7: board[2][0] = player; break;
            case 8: board[2][1] = player; break;
            case 9: board[2][2] = player; break;
        }
    }
    public static boolean checkWinner(char[][] board, char player) {
        /*
         * Función que se encarga de comprobar los índices del tablero para determinar si hay un ganador

         * Args:
         * char[][] board: Matriz que representa el tablero de juego.
         * char player: El caracter del jugador para comprobar las combinaciones.

         */
        return (board[0][0] == player && board[0][1] == player && board[0][2] == player) ||
                (board[1][0] == player && board[1][1] == player && board[1][2] == player) ||
                (board[2][0] == player && board[2][1] == player && board[2][2] == player) ||
                (board[0][0] == player && board[1][0] == player && board[2][0] == player) ||
                (board[0][1] == player && board[1][1] == player && board[2][1] == player) ||
                (board[0][2] == player && board[1][2] == player && board[2][2] == player) ||
                (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }
}

