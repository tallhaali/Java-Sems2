import java.util.Random;

public class Tracer {
    // Maze dimensions
    static final int SIZE = 4;
    // 0 = open, 1 = wall
    static int[][] maze = new int[SIZE][SIZE];

    // Fill the maze with random walls (except start and end)
    static void generateMaze() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i == 0 && j == 0) || (i == SIZE-1 && j == SIZE-1)) {
                    maze[i][j] = 0; // Start and end are open
                } else {
                    maze[i][j] = rand.nextInt(3) == 0 ? 1 : 0; // Some walls
                }
            }
        }
    }

    // Recursive function to find a path from (x, y) to the end
    static boolean solveMaze(int x, int y, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "At position (" + x + ", " + y + ")");
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            System.out.println(indent + "Out of bounds. Backtracking.");
            return false;
        }
        if (maze[x][y] == 1) {
            System.out.println(indent + "Hit a wall at (" + x + ", " + y + "). Backtracking.");
            return false;
        }
        if (x == SIZE - 1 && y == SIZE - 1) {
            System.out.println(indent + "Reached the goal at (" + x + ", " + y + ")!");
            return true;
        }
        // Mark as visited
        maze[x][y] = 1;
        System.out.println(indent + "Exploring neighbors of (" + x + ", " + y + ")...");

        // Try moving right
        System.out.println(indent + "Trying to move right...");
        if (solveMaze(x, y + 1, depth + 1)) {
            System.out.println(indent + "Path found through right from (" + x + ", " + y + ")");
            return true;
        }
        // Try moving down
        System.out.println(indent + "Trying to move down...");
        if (solveMaze(x + 1, y, depth + 1)) {
            System.out.println(indent + "Path found through down from (" + x + ", " + y + ")");
            return true;
        }
        // Try moving left
        System.out.println(indent + "Trying to move left...");
        if (solveMaze(x, y - 1, depth + 1)) {
            System.out.println(indent + "Path found through left from (" + x + ", " + y + ")");
            return true;
        }
        // Try moving up
        System.out.println(indent + "Trying to move up...");
        if (solveMaze(x - 1, y, depth + 1)) {
            System.out.println(indent + "Path found through up from (" + x + ", " + y + ")");
            return true;
        }

        System.out.println(indent + "No path from (" + x + ", " + y + "). Backtracking.");
        return false;
    }

    public static void main(String[] args) {
        generateMaze();
        System.out.println("Maze (0=open, 1=wall):");
        for (int[] row : maze) {
            for (int cell : row) System.out.print(cell + " ");
            System.out.println();
        }
        System.out.println("\nTracing recursive maze solving from (0, 0):\n");
        boolean found = solveMaze(0, 0, 0);
        if (found) {
            System.out.println("\nA path was found!");
        } else {
            System.out.println("\nNo path exists in this maze.");
        }
    }
}