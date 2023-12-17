package Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day10 {

    private static final String PATH = "./src/Day10/input.txt";
    private static final String EXAMPLE = "./src/Day10/example.txt";
    private static final int[][] start = new int[1][2];
    private static final List<Pipe> path = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            LineNumberReader reader = new LineNumberReader(new FileReader(PATH));
            reader.skip(Long.MAX_VALUE);
            String l1 = br.readLine();
            Pipe[][] grid = new Pipe[reader.getLineNumber()][l1.length()];
            createGrid(grid, l1, 0);
            AtomicInteger row = new AtomicInteger(1);
            br.lines().forEach(l -> createGrid(grid, l, row.getAndIncrement()));
            System.out.println("Part 1: " + countCircuitSteps(grid, determineStartPipe(grid)) / 2);
            System.out.println("Part 2: " + determineEnclosedTiles(grid));
        }
    }

    private static class Pipe {
        private final char pipe;
        private final int x, y;
        public enum DIR {NORTH, EAST, SOUTH, WEST}
        private final List<DIR> openings = new ArrayList<>();
        public Pipe(char c, int x, int y) {
            this.pipe = c;
            this.x = x;
            this.y = y;
            createOpenings();
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public char getSymbol() {
            return pipe;
        }

        public List<DIR> getOpenings() {
            return new ArrayList<>(openings);
        }

        private void createOpenings() {
            switch (this.pipe) {
                case '|' -> openings.addAll(List.of(DIR.NORTH, DIR.SOUTH));
                case '-' -> openings.addAll(List.of(DIR.WEST, DIR.EAST));
                case 'L' -> openings.addAll(List.of(DIR.NORTH, DIR.EAST));
                case 'J' -> openings.addAll(List.of(DIR.NORTH, DIR.WEST));
                case '7' -> openings.addAll(List.of(DIR.WEST, DIR.SOUTH));
                case 'F' -> openings.addAll(List.of(DIR.EAST, DIR.SOUTH));
            }
        }

        @Override
        public boolean equals(Object pipe) {
            Pipe other = (Pipe) pipe;
            return this.getClass() == pipe.getClass() && this.x == other.getX() && this.y == other.getY();
        }
    }

    private static void createGrid(Pipe[][] grid, String line, int row) {
        for (int i = 0; i < line.length(); i++) {
            grid[row][i] = new Pipe(line.charAt(i), row, i);
            if (line.charAt(i) == 'S') {
                start[0][0] = row;
                start[0][1] = i;
            }
        }
    }

    private static Pipe determineStartPipe(Pipe[][] grid) {
        List<Pipe.DIR> openings = new ArrayList<>();
        Pipe.DIR curr = Pipe.DIR.NORTH;
        while (curr != null) {
            switch (curr) {
                case NORTH -> {
                    if (start[0][0] - 1 > 0 &&grid[start[0][0] - 1][start[0][1]].getOpenings().contains(Pipe.DIR.SOUTH)) {
                        openings.add(curr);
                    }
                    curr = Pipe.DIR.EAST;
                }
                case EAST -> {
                    if (start[0][1] + 1 < grid[0].length && grid[start[0][0]][start[0][1] + 1].getOpenings().contains(Pipe.DIR.WEST)) {
                        openings.add(curr);
                    }
                    curr = Pipe.DIR.SOUTH;
                }
                case SOUTH -> {
                    if (start[0][0] + 1 < grid.length && grid[start[0][0] + 1][start[0][1]].getOpenings().contains(Pipe.DIR.NORTH)) {
                        openings.add(curr);
                    }
                    curr = Pipe.DIR.WEST;
                }
                case WEST -> {
                    if (start[0][1] - 1 > 0 && grid[start[0][0]][start[0][1] - 1].getOpenings().contains(Pipe.DIR.EAST)) {
                        openings.add(curr);
                    }
                    curr = null;
                }
            }
        }
        if (openings.contains(Pipe.DIR.NORTH) && openings.contains(Pipe.DIR.SOUTH)) {
            return new Pipe('|', start[0][0], start[0][1]);
        } else if (openings.contains(Pipe.DIR.EAST) && openings.contains(Pipe.DIR.WEST)) {
            return new Pipe('-', start[0][0], start[0][1]);
        } else if (openings.contains(Pipe.DIR.NORTH) && openings.contains(Pipe.DIR.EAST)) {
            return new Pipe('L', start[0][0], start[0][1]);
        } else if (openings.contains(Pipe.DIR.NORTH) && openings.contains(Pipe.DIR.WEST)) {
            return new Pipe('J', start[0][0], start[0][1]);
        } else if (openings.contains(Pipe.DIR.SOUTH) && openings.contains(Pipe.DIR.WEST)) {
            return new Pipe('7', start[0][0], start[0][1]);
        } else if (openings.contains(Pipe.DIR.SOUTH) && openings.contains(Pipe.DIR.EAST)) {
            return new Pipe('F', start[0][0], start[0][1]);
        }
        return new Pipe('.', start[0][0], start[0][1]);
    }

    private static int countCircuitSteps(Pipe[][] grid, Pipe startPipe) {
        int steps = 0;
        Pipe.DIR opening = startPipe.getOpenings().get(0);
        Pipe next = startPipe;
        int x = start[0][0];
        int y = start[0][1];
        while (next.getSymbol() != 'S') {
            path.add(next);
            switch (opening) {
                case NORTH -> x--;
                case EAST -> y++;
                case SOUTH -> x++;
                case WEST -> y--;
            }
            next = grid[x][y];
            switch (opening) {
                case NORTH -> {
                    if (next.getSymbol() == 'F') {
                        opening = Pipe.DIR.EAST;
                    } else if (next.getSymbol() == '7') {
                        opening = Pipe.DIR.WEST;
                    }
                }
                case EAST -> {
                    if (next.getSymbol() == 'J') {
                        opening = Pipe.DIR.NORTH;
                    } else if (next.getSymbol() == '7') {
                        opening = Pipe.DIR.SOUTH;
                    }
                }
                case SOUTH -> {
                    if (next.getSymbol() == 'J') {
                        opening = Pipe.DIR.WEST;
                    } else if (next.getSymbol() == 'L') {
                        opening = Pipe.DIR.EAST;
                    }
                }
                case WEST -> {
                    if (next.getSymbol() == 'L') {
                        opening = Pipe.DIR.NORTH;
                    } else if (next.getSymbol() == 'F') {
                        opening = Pipe.DIR.SOUTH;
                    }
                }
            }
            steps++;
        }

        return steps;
    }

    private static int determineEnclosedTiles(Pipe[][] grid) {
        int result = 0;
        for (Pipe[] pipes : grid) {
            boolean insidePath = false;
            char previous = '.';
            for (int j = 0; j < grid[0].length; j++) {
                result = insidePath && !path.contains(pipes[j]) ? result + 1 : result;
                char c = pipes[j].getSymbol();
                if (c == '|' && path.contains(pipes[j]))
                    insidePath = !insidePath;
                if (c == 'J' && previous == 'F' && path.contains(pipes[j]))
                    insidePath = !insidePath;
                if (c == '7' && previous == 'L' && path.contains(pipes[j]))
                    insidePath = !insidePath;
                if (c != '-' && path.contains(pipes[j]))
                    previous = c;
            }
        }
        return result;
    }

}
