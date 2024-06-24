package theGoodCode;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Get number of players
        System.out.print("Enter number of players: ");
        int numPlayers = scnr.nextInt();
        scnr.nextLine(); // Consume newline left-over

        // Create array to hold players
        Player[] ps = new Player[numPlayers];

        // Fill player details
        fillPlayers(ps, scnr);

        // Get window dimensions
        System.out.print("Enter Window Width: ");
        int width = scnr.nextInt();

        System.out.print("Enter Window Height: ");
        int height = scnr.nextInt();

        // Launch page with collected data
        PageLauncher pageLaunch = new PageLauncher(ps, width, height);
        
        System.out.println("Another Round? ");
     // Close the scanner after reading all input
        scnr.close();
    }

    private static void fillPlayers(Player[] ps, Scanner scnr) {
        for (int i = 0; i < ps.length; i++) {
            System.out.print("Enter Name for Player " + (i + 1) + ": ");
            String name = scnr.nextLine();

            System.out.print("X Position Guess for Player " + (i + 1) + ": ");
            int xGuess = scnr.nextInt();

            System.out.print("Y Position Guess for Player " + (i + 1) + ": ");
            int yGuess = scnr.nextInt();

            // Consume newline left-over
            scnr.nextLine();

            ps[i] = new Player(name, xGuess, yGuess);
        }
    }
}
