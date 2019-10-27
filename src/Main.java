import java.util.Scanner;

/**
 * Matt Jankowski
 * AI (CS 411) Hw 7
 * 15 puzzle IDA* - Main Class
 * To God be the Glory
 */


public class Main {

    public static long startingTime;
    public static int heuristic; //static var keeps track of which heuristic to use

    public static void main(String[] args) {
        startingTime = System.currentTimeMillis();//get time at start of run
        //System.out.println("Starting 15 puzzle.");

        int initialBoard[] = new int[]{
//                1, 0, 3, 4, 5, 2, 6, 8, 9, 10, 7, 11, 13, 14, 15, 12
//                1, 2, 3, 4, 5, 6, 8, 0, 9, 11, 7, 12, 13, 10, 14, 15
//                1, 0, 2, 4, 5, 7, 3, 8, 9, 6, 11, 12, 13, 10, 14, 15
//                1, 2, 0, 4, 6, 7, 3, 8, 5, 9, 10, 12, 13, 14, 11, 15
//                1, 3, 4, 8, 5, 2, 0, 6, 9, 10, 7, 11, 13, 14, 15, 12

//                2, 0, 3, 4, 1, 5, 6, 8, 9, 13, 7, 11, 14, 10, 15, 12  //13
//                1, 6, 0, 4, 9, 3, 2, 8, 10, 5, 7, 11, 15, 13, 14, 12  //20
//                1, 3, 6, 8, 9, 0, 4, 2, 10, 5, 7, 11, 15, 13, 14, 12  //26
                1, 3, 4, 11, 5, 2, 6, 7, 0, 8, 9, 10, 13, 15, 12, 14  //32



        };

        //    ^^^ REPLACE INPUT HERE.


        System.out.println("1 = Misplaced Tiles\n2 = Manhattan Distance");
        System.out.println("Enter heuristic you want to use:");
        Scanner scanner = new Scanner(System.in);
        heuristic = scanner.nextInt();

        if (heuristic == 1) {
            System.out.println("Starting IDA* Search with heuristic: MISPLACED TILES\n");
            new IDAStar(initialBoard);  //run AStar:  Misplaced tiles
        }
        else if (heuristic == 2) {
            System.out.println("Starting IDA* Search with heuristic: MANHATTAN DISTANCE\n");
            new IDAStar(initialBoard);  //run AStar:  Manhattan distance
        }
        else
            System.out.println("Invalid Input. Please run again.");
    }


}