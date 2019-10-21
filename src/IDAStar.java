/**
 * Matt Jankowski
 * AI (CS 411) Hw 7
 * 15 puzzle IDA* - IDAStar Class
 * To God be the Glory
 */
//git exp

//IDAStar => "Iterative Deepening Depth First Search".
public class IDAStar{


    private double infinity = Double.POSITIVE_INFINITY;

    public IDAStar(int[] initial_board) {


        for (int limit = 0; limit < infinity; limit++){
            //System.out.println("LIMIT: " + limit);
            new DLS(initial_board, limit); //conduct DLS on increasing limit levels
        }
    }

}