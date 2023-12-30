public class Minesweeper {

    public static void main(String[] unused) {
        StdOut.println("Welcome to minesweeper");
        int size = 10, numMines = 10;
        boolean[][] grid = new boolean[size][size];
        boolean[][] revealed = new boolean[size][size];

        // initialize grid: place bombs in random location
        initMinefield(numMines, grid);

        StdDraw.setScale(-0.5, size - 0.5);
        drawMinefield(grid, revealed);

        while (true) {
            handleMouseClick(grid, revealed);

            drawMinefield(grid, revealed);

            if (hasWon(grid, revealed)) {
                StdOut.println("You won!");
                break;
            }
        }
    }

    public static boolean hasWon(boolean[][] grid, boolean[][] revealed) {
        for (int x = 0; x < grid.length; ++x) {
            for (int y = 0; y < grid.length; ++y) {
                if (grid[x][y] && revealed[x][y] || !grid[x][y] && !revealed[x][y]) return false;
            }
        }
        return true;
    }

    public static void handleMouseClick(boolean[][] grid, boolean[][] revealed) {
        // Wait for the user to press on the mouse button
        while (!StdDraw.isMousePressed()) {
            // do nothing! 
        }

        int x = (int)Math.round(StdDraw.mouseX());
        int y = (int)Math.round(StdDraw.mouseY());

        // Wait for the user to release the mouse button
        while (StdDraw.isMousePressed()) {
            // do nothing!  
        }

        uncover(grid, revealed, x, y);

        if (grid[x][y]) {
            StdOut.println("BOOM");
        }
    }

    public static void drawMinefield(boolean[][] grid, boolean[][] revealed) {
        StdDraw.clear();
        int x = 0;
        while (x < grid.length) {
            int y = 0;
            while (y < grid.length) {
                if (!revealed[x][y]) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.filledSquare(x, y, 0.5);
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                else if (grid[x][y]) {
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.filledCircle(x, y, 0.3);
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                else {
                    int count = countNeighboringMines(grid, x, y);
                    if (count != 0) StdDraw.text(x, y, "" + count);
                }
                StdDraw.square(x, y, 0.5);
                y = y + 1;
            }
            x = x + 1;
        }
    }

    public static int countNeighboringMines(boolean[][] grid, int x, int y) {
        int count = 0;
        for (int y2 = y - 1; y2 <= y + 1; ++y2) {
            for (int x2 = x - 1; x2 <= x + 1; ++x2) {
                if (y2 >= 0 && y2 < grid.length && x2 >= 0 && x2 < grid.length) {
                    if (grid[x2][y2]) ++count;
                }
            }
        }
        return count;
    }

    public static void initMinefield(int numMines, boolean[][] grid) {
        for (int n = 0; n < numMines; ++n) {
            int x = StdRandom.uniformInt(grid.length);
            int y = StdRandom.uniformInt(grid.length);
            if (grid[x][y]) {
                --n;
                continue;
            }
            grid[x][y] = true;
        }
    }

    public static void uncover(boolean[][] grid, boolean[][] revealed, int x, int y) {
        if (!revealed[x][y]) {
            revealed[x][y] = true;
            if (countNeighboringMines(grid, x, y) == 0) {
                for (int x1 = x - 1; x1 <= x + 1; ++x1) {
                    for (int y1 = y - 1; y1 <= y + 1; ++y1) {
                        if (x1 >= 0 && x1 < grid.length && y1 >= 0 && y1 < grid.length) {
                            uncover(grid, revealed, x1, y1);
                        }
                    }
                }
            }
        }
    }
}
