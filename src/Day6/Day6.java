package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day6 {

    private static final String PATH = "./src/Day6/input.txt";
    private static final String EXAMPLE = "./src/Day6/example.txt";

    public static void main(String[] args) throws IOException{
        System.out.println("Part 1: " + Day6.part1(PATH));
        System.out.println("Part 2: " + Day6.part2(PATH));
    }

    private static List<Integer> toIntegerList(String line) {
        return Arrays.stream(line.split(" ")).filter(t -> {
            try {
                Integer.parseInt(t.trim());
            }catch (NumberFormatException e) {
                return false;
            }
            return true;
        }).map(Integer::parseInt).toList();
    }

    private static int part1(String path) throws IOException {

        int result = 1;
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Integer> times = Day6.toIntegerList(br.readLine());
            List<Integer> distances = Day6.toIntegerList(br.readLine());
            for (int i = 0; i < distances.size(); i++) {
                int time = times.get(i);
                int counter = 0;
                for (int j = 1; j < times.get(i) - 1; j++) {
                    if ((time - j) * j > distances.get(i)) {
                        counter++;
                    }
                }
                result *= counter;
            }
        }
        return result;
    }

    private static int part2(String path) throws IOException {

        int result = 1;
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            long time     = Long.parseLong(br.readLine().replaceAll(" ", "").substring(5));
            long distance = Long.parseLong(br.readLine().replaceAll(" ", "").substring(9));
                int counter = 0;
                for (int j = 1; j < time - 1; j++) {
                    if ((time - j) * j > distance) {
                        counter++;
                    }
                }
                result *= counter;
        }
        return result;
    }
}
