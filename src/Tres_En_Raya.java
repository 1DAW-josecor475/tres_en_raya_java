import java.util.Scanner;

public class Tres_En_Raya {
    public static void main(String[] args) {
        welcomeMessage();
        playGame();
    }

    public static void welcomeMessage() {
        System.out.println("""
                               *********************************************
                               *      TRES  EN  RAYA:  EL  VIDEOJUEGO      *
                               *********************************************
                               """);
    }

    public static void playGame() {
        Scanner sc = new Scanner(System.in);
        int userWins = 0, computerWins = 0;
        boolean playAgain;

        do {
            char[][] board = generateBoard();
            System.out.println("\nNueva partida: \n");
            printBoard(board);

            String difficulty = chooseDifficulty(sc);
            boolean userWon = gameLoop(board, difficulty, sc);

            if (userWon) {
                userWins++;
            } else {
                computerWins++;
            }

            System.out.println("\nPuntuaciones:");
            System.out.println("Jugador: " + userWins);
            System.out.println("Ordenador: " + computerWins);

            playAgain = userWins < 2 && computerWins < 2;
            if (playAgain) {
                System.out.println("\nSiguiente partida al mejor de 3...\n");
            }
        } while (playAgain);

        System.out.println(userWins > computerWins ? "\n** Enhorabuena, ganaste la serie al mejor de 3! **" : "\n** Lo sentimos, perdiste la serie al mejor de 3. **");
    }

    public static char[][] generateBoard() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
        return board;
    }

    public static boolean gameLoop(char[][] board, String difficulty, Scanner sc) {
        char user = 'X';
        char computer = 'O';
        boolean hasEnded = false;
        int turns = 0;

        while (!hasEnded) {
            int movement;

            // Turno del jugador
            do {
                System.out.println("""
                                   * Turno del jugador. Escoja:
                                   1. ARRIBA IZQUIERDA  2. ARRIBA CENTRO    3. ARRIBA DERECHA
                                   4. CENTRO IZQUIERDA  5. CENTRO CENTRO    6. CENTRO DERECHA
                                   7. ABAJO IZQUIERDA   8. ABAJO CENTRO     9. ABAJO DERECHA
                                   """);
                movement = sc.nextInt();
                if (!validMovement(board, movement)) {
                    System.out.println("** MOVIMIENTO INVÁLIDO");
                    printBoard(board);
                }
            } while (!validMovement(board, movement));

            updateBoard(movement, user, board);
            printBoard(board);

            if (checkWinner(board, user)) {
                System.out.println("** Resultado de la partida: VICTORIA.");
                return true;
            }

            if (++turns == 9) {
                System.out.println("** Resultado de la partida: EMPATE.");
                return false;
            }

            // Turno del ordenador
            System.out.println("* Turno del ordenador...");
            do {
                movement = switch (difficulty) {
                    case "dificil" -> getBestMove(board, computer, user);
                    case "medio" -> getBlockingMove(board, computer, user);
                    default -> getRandomMove(board);
                };
            } while (!validMovement(board, movement));

            updateBoard(movement, computer, board);
            printBoard(board);

            if (checkWinner(board, computer)) {
                System.out.println("** Resultado de la partida: DERROTA.");
                return false;
            }
        }

        return false;
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
    
    public static String chooseDifficulty(Scanner sc) {
      
        System.out.println("\nSeleccione la dificultad:");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");

        int choice;
        do {
            choice = sc.nextInt();
        } while (choice < 1 || choice > 3);

        return switch (choice) {
            case 1 -> "facil";
            case 2 -> "medio";
            case 3 -> "dificil";
            default -> "facil";
        };
    }

    public static int getRandomMove(char[][] board) {
      
        return (int) (Math.random() * 9 + 1);
    }

    public static int getBestMove(char[][] board, char computer, char user) {
      
        // Verifica si puede ganar en el siguiente movimiento
        for (int i = 1; i <= 9; i++) {
            if (validMovement(board, i)) {
                updateBoard(i, computer, board);
                if (checkWinner(board, computer)) {
                    undoMove(i, board);
                    return i;
                }
                undoMove(i, board);
            }
        }

        // Bloquea al jugador si está a punto de ganar
        for (int i = 1; i <= 9; i++) {
            if (validMovement(board, i)) {
                updateBoard(i, user, board);
                if (checkWinner(board, user)) {
                    undoMovement(i, board);
                    return i;
                }
                undoMovement(i, board);
            }
        }

        // Si no hay ningún movimiento ganador, elige uno al azar
        return getRandomMove(board);
    }

    public static int getBlockingMove(char[][] board, char computer, char user) {
        
        // Bloquea al jugador si está a punto de ganar
        for (int i = 1; i <= 9; i++) {
            if (validMovement(board, i)) {
                updateBoard(i, user, board);
                if (checkWinner(board, user)) {
                    undoMovement(i, board);
                    return i;
                }
                undoMovement(i, board);
            }
        }

        // Si no hay peligro, mueve aleatoriamente
        return getRandomMove(board);
    }

    public static void undoMovement(int movement, char[][] board) {
    
        switch (movement) {
            case 1 -> board[0][0] = '-';
            case 2 -> board[0][1] = '-';
            case 3 -> board[0][2] = '-';
            case 4 -> board[1][0] = '-';
            case 5 -> board[1][1] = '-';
            case 6 -> board[1][2] = '-';
            case 7 -> board[2][0] = '-';
            case 8 -> board[2][1] = '-';
            case 9 -> board[2][2] = '-';
        }
    }

    public static boolean validMovement(char[][] board, int movement) {
        /* Función que comprueba si el movimiento que hizo el jugador es válido.

        * Args:
        * - char[][] board: para comprobar las casillas del tablero.
        * - int movement: para comprobar que se introduce un número que tenga una casilla asignada.

        * Returns:
        * "false" si se se ha hecho un movimiento en una casilla que ya tiene una ficha o si se introduce un número que no tiene una casilla asignada.
        */
        return switch (movement) {
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

    public static void updateBoard(int movement, char player, char[][] board) {
         /*
         * Esta función se encarga de asignar el caracter del player/ordenador a una posición del tablero.

         * Args:

         * int movement: Un entero que es la posición de colocación de la pieza del player.
         * char player: El caracter del jugador en dicha actualización ('X' para usuario / 'O' para el ordenador).
         * char[][] board: Matriz que representa el tablero de juego.

         */
        switch (movement) {
            case 1 -> board[0][0] = player;
            case 2 -> board[0][1] = player;
            case 3 -> board[0][2] = player;
            case 4 -> board[1][0] = player;
            case 5 -> board[1][1] = player;
            case 6 -> board[1][2] = player;
            case 7 -> board[2][0] = player;
            case 8 -> board[2][1] = player;
            case 9 -> board[2][2] = player;
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
