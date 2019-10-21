/**
 * Matt Jankowski
 * AI (CS 411) Hw 7
 * 15 puzzle IDA* - Node Class
 * To God be the Glory
 */
//git exp

public class Node implements Comparable<Node> {
    private Node parent; //each node has a parent except root
    private int[] board = new int[16];
    private int depth;
    private int childCount = 0;
    private char direction;
    private int nodeID;
    private int F;
    private int H;

    public Node(int[] in_board, Node in_parent, char in_direction) {
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        parent = in_parent;
        if (parent == null) {
            depth = 0; //root
        } else {
            depth = parent.depth + 1;
            parent.childCount++;
        }
        board = in_board;
        direction = in_direction; //slide move to get to this node from previous node
        nodeID = DLS.nodeCount++;

        //A* things:
        if (Main.heuristic == 1)                      //heuristic: 1 -> Misplaced Tiles.
            H = misplacedTiles();
        else if (Main.heuristic == 2)                //           2 ->  Manhattan Dist.
            H = manhattanDistance();

        F = depth + H;  //F = G + H

/*      //Uncomment for debugging:
        System.out.println("F = G + H\t" + F + " = " + depth + " + " + H);
        try {
            System.out.println("Creating a new node.\t\t" + direction + "\t\t" + depth + "\t\t" + nodeID + "\t\t" + parent.nodeID);
        } catch (NullPointerException e) {
            System.out.println("Creating a new node.\t\t" + direction + "\t\t" + depth + "\t\t" + nodeID + "\t\troot");
        }
        printBoard();
*/
    }

    //getters:
    public int getF() {
        return F;
    }

    public int getChildCount() {
        return childCount;
    }

    public int getDepth() {
        return depth;
    }

    public int getNodeID() {
        return nodeID;
    }

    public int[] getBoard() {
        return board;
    }

    public Node getParent() {
        return parent;
    }

    public char getDirection() {
        return direction;
    }


    /* Calculate the Manhattan Distance of a 4 x 4 board */
    public int manhattanDistance() {
        //position arrays = arrays holding index values of various digits
        int goalPosArray[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}; //holds positions of goal board 1..0
        int boardPosArray[] = new int[16];                                                     //holds positions of current board
        int MDArray[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};            //holds individual MD's
        int manhattanDistance = 0;

        //build boardPosArray:
        for (int i = 0; i < 15; i++) { // <15 since [15] has special case below
            boardPosArray[i] = getPosition(i + 1, board);
        }
        boardPosArray[15] = getPosition(0, board); //special case: 0 is at pos [15]

//        Functions.printArray("boardPosArray", boardPosArray);
//        Functions.printArray("goalPosArray ", goalPosArray);

        //calculate position differences between board and goal:
        int boardVal;
        int goalVal;
        int rowDifference;
        int colDifference;

        for (int i = 0; i < 16; i++) {
            boardVal = boardPosArray[i];
            goalVal = goalPosArray[i];
            //
            //calculate individual manhattan distances:
            rowDifference = Math.abs(boardVal / 4 - goalVal / 4); //get row difference
            colDifference = Math.abs(boardVal % 4 - goalVal % 4); //get col difference
            MDArray[i] = rowDifference + colDifference; // add the 2 to get MD
        }
        //Functions.printArray("MDArray ", MDArray);

        //add up individual md's
        for (int i = 0; i < 15; i++) { //don't include the blank (0)
            manhattanDistance += MDArray[i];
        }
        return manhattanDistance;
    }


    /* Calculate the Misplaced Tiles on a 4 x 4 board */
    public int misplacedTiles() {

        //position arrays = arrays holding index values of various digits
        int goalPosArray[] = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}; //holds positions of goal board 1..0
        int boardPosArray[] = new int[16];                                                     //holds positions of current board
        int misplacedTiles = 0;  //init

        //build boardPosArray:
        for (int i = 0; i < 15; i++) { // <15 since [15] has special case below
            boardPosArray[i] = getPosition(i + 1, board);
        }
        boardPosArray[15] = getPosition(0, board); //special case: 0 is at pos [15]

//        Functions.printArray("boardPosArray", boardPosArray);
//        Functions.printArray("goalPosArray ", goalPosArray);

        //calculate # of tiles that differ between board and goal:
        int boardVal;
        int goalVal;
        for (int i = 0; i < 16; i++) {
            boardVal = boardPosArray[i];
            goalVal = goalPosArray[i];
            if (boardVal != goalVal) {
                misplacedTiles++;
            }
        }
        return misplacedTiles;
    }


    //return position of a certain digit 0..15 on the board
    public int getPosition(int digit, int[] pattern) {
        for (int i = 0; i < 16; i++) {
            if (pattern[i] == digit) {
                return i; //should find it since indexing is 0..15 as well.
            }
        }
        return -1; //if this happens: something went wrong
    }

    //move 0 left if possible
    public Node moveLeft() {
        int[] pattern = board.clone(); //new board
        if (pattern[0] == 0 || pattern[4] == 0 || pattern[8] == 0 || pattern[12] == 0) {
            //System.out.println("Cannot move left! Blank is in left column.");
            return null;
        } else { //move left
            int zeroPosition = getPosition(0, pattern);
            pattern[zeroPosition] = pattern[zeroPosition - 1]; //swap (slide)
            pattern[zeroPosition - 1] = 0;
            return new Node(pattern, this, 'L'); //create new node
        }
    }

    //move 0 right if possible
    public Node moveRight() {
        int[] pattern = board.clone(); //new board
        if (pattern[3] == 0 || pattern[7] == 0 || pattern[11] == 0 || pattern[15] == 0) {
            //System.out.println("Cannot move right! Blank is in right column.");
            return null;
        } else { //move right
            int zeroPosition = getPosition(0, pattern);
            pattern[zeroPosition] = pattern[zeroPosition + 1]; //swap (slide)
            pattern[zeroPosition + 1] = 0;
            return new Node(pattern, this, 'R'); //create new node
        }
    }

    //move 0 up if possible
    public Node moveUp() {
        int[] pattern = board.clone(); //new board
        if (pattern[0] == 0 || pattern[1] == 0 || pattern[2] == 0 || pattern[3] == 0) {
            //System.out.println("Cannot move up! Blank is in top row.");
            return null;
        } else { //move up
            int zeroPosition = getPosition(0, pattern);
            pattern[zeroPosition] = pattern[zeroPosition - 4]; //swap (slide)
            pattern[zeroPosition - 4] = 0;
            return new Node(pattern, this, 'U'); //create new node
        }
    }

    //move 0 down if possible
    public Node moveDown() {
        int[] pattern = board.clone(); //new board
        if (pattern[12] == 0 || pattern[13] == 0 || pattern[14] == 0 || pattern[15] == 0) {
            //System.out.println("Cannot move down! Blank is in bottom row.");
            return null;
        } else { //move down
            int zeroPosition = getPosition(0, pattern);
            pattern[zeroPosition] = pattern[zeroPosition + 4]; //swap (slide)
            pattern[zeroPosition + 4] = 0;
            return new Node(pattern, this, 'D'); //create new node
        }
    }

    //print data about the node
    public void printNodeData() {
        System.out.println("   depth = " + this.depth);
        try {
            Functions.printArray("   parent board", this.parent.board);
        } catch (NullPointerException e) {
            System.out.println("   parent board = This is the root and there is no parent.");
        }
        //Functions.printArray("   board", this.board);
        printBoard();
    }

    //print just the board
    public void printBoard() {
        for (int i = 0; i < 16; i++) {
            if (i % 4 == 3) { //if last character
                System.out.print("\t" + this.board[i] + "\n");
            } else {
                System.out.print("\t" + this.board[i]);
            }
        }
    }

    // Compare Two Nodes based on their f value: Comparator function for priority queue

    /**
     * //holds individual MD's
     *
     * @param anotherNode - The Node to be compared.
     * @return A negative integer, zero, or a positive integer as this node
     * is less than, equal to, or greater than the supplied node object.
     */
    @Override
    public int compareTo(Node anotherNode) {
        return this.getF() - anotherNode.getF();
    }

}