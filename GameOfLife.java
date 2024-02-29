public class GameOfLife {

    public static void main(String[] args) {
        String fileName = args[0];
    }

    private static void test1(String fileName) {
        int[][] board = read(fileName);
        print(board);
    }

    private static void test2(String fileName) {
        int[][] board = read(fileName);
    }

    private static void test3(String fileName, int Ngen) {
        int[][] board = read(fileName);
        for (int gen = 0; gen < Ngen; gen++) {
            print(board);
            board = evolve(board);
        }
    }

    public static void play(String fileName) {
        int[][] board = read(fileName);
        while (true) {
            show(board);
            board = evolve(board);
        }
    }

    public static int[][] read(String fileName) {
        In in = new In(fileName);
        int rows = Integer.parseInt(in.readLine());
        int cols = Integer.parseInt(in.readLine());
        int[][] board = new int[rows + 2][cols + 2];
        for (int i = 1; i <= rows; i++) {
            String line = in.readLine();
            for (int j = 1; j <= cols; j++) {
                board[i][j] = line.charAt(j - 1) == '1' ? 1 : 0;
            }
        }
        return board;
    }

    public static int[][] evolve(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int[][] newBoard = new int[rows][cols];
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                int aliveNeighbors = count(board, i, j);
                newBoard[i][j] = cellValue(board[i][j], aliveNeighbors);
            }
        }
        return newBoard;
    }

    public static int cellValue(int cell, int aliveNeighbors) {
        if (cell == 1 && (aliveNeighbors < 2 || aliveNeighbors > 3)) return 0;
        if (cell == 0 && aliveNeighbors == 3) return 1;
        return cell;
    }

    public static int count(int[][] board, int i, int j) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue;
                if (board[i + di][j + dj] == 1) count++;
            }
        }
        return count;
    }

    public static void print(int[][] board) {
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void show(int[][] board) {
        StdDraw.setCanvasSize(900, 900);
        int rows = board.length;
        int cols = board[0].length;
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        StdDraw.enableDoubleBuffering();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int color = 255 * (1 - board[i][j]);
                StdDraw.setPenColor(color, color, color);
                StdDraw.filledRectangle(j + 0.5, rows - i - 0.5, 0.5, 0.5);
            }
        }
        StdDraw.show();
        StdDraw.pause(100);
    }
}
