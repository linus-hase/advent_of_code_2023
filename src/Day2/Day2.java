package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day2 {

    private static final String PATH = "./src/Day2/input.txt";
    private static final int RED = 12;
    private static final int GREEN = 13;
    private static final int BLUE = 14;

    public static void main(String[] args) throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            AtomicInteger gameIdx = new AtomicInteger();
            AtomicInteger result2 = new AtomicInteger();
            int result1 = br.lines().flatMapToInt(l -> {
                String[] draws = l.substring(l.indexOf(":") + 1).split(";");
                boolean isPossible = true;
                String[] colours;
                List<String> coloursList = new ArrayList<>();
                for (String draw : draws) {
                    colours = draw.split(",");
                    coloursList.addAll(List.of(colours));
                }
                result2.addAndGet(Day2.part2(coloursList));
                for (int i = 0; i < coloursList.size() && isPossible; i++) {
                    String currColour = coloursList.get(i).trim();
                    int parse = Integer.parseInt(currColour.substring(0, currColour.indexOf(" ")));
                    switch (currColour.substring(currColour.indexOf(" ") + 1)) {
                        case "red" -> isPossible = parse <= RED;
                        case "green" -> isPossible = parse <= GREEN;
                        case "blue" -> isPossible = parse <= BLUE;
                    }
                }
                gameIdx.getAndIncrement();
                return IntStream.of(isPossible ? gameIdx.get() : 0);
            }).sum();

            System.out.println("Part 1: " + result1);
            System.out.println("Part 2: " + result2);
        }
    }

    private static int part2(List<String> colours) {
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;
        for (String colour : colours) {
            String curr = colour.trim();
            int parse = Integer.parseInt(curr.substring(0, curr.indexOf(" ")));
            switch (curr.substring(curr.indexOf(" ") + 1)) {
                case "red" -> maxRed = Math.max(maxRed, parse);
                case "green" -> maxGreen = Math.max(maxGreen, parse);
                case "blue" -> maxBlue = Math.max(maxBlue, parse);
            }
        }
        return maxRed * maxGreen * maxBlue;
    }
}
