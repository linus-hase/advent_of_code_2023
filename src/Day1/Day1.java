package Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1 {
    private static final String PATH = "./src/Day1/input.txt";

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            AtomicInteger result2 = new AtomicInteger();
            int result = br.lines().mapToInt((value) -> {
                result2.set(result2.get() + part2(value));
                return Integer.parseInt(numberAsString(value));
            }).sum();
            System.out.println("Part 1: " + result);
            System.out.println("Part 2: " + result2);
        }
    }

    private static int part2(String line) {
        String[] numbers = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (String s : numbers) {
            line = line.replaceAll(s, replaceNum(s));
        }
        return Integer.parseInt(numberAsString(line));
    }

    private static String replaceNum(String num) {
        return switch (num) {
            case "one" -> "o1e";
            case "two" -> "t2o";
            case "three" -> "th3ee";
            case "four" -> "f4ur";
            case "five" -> "f5ve";
            case "six" -> "s6x";
            case "seven" -> "se7en";
            case "eight" -> "ei8ht";
            case "nine" -> "n9ne";
            default -> "";
        };
    }

    private static String numberAsString(String line) {
        Integer first = null;
        Integer last = null;
        for(int i = 0; i < line.length(); ++i) {
            String c = String.valueOf(line.charAt(i));
            try {
                int num = Integer.parseInt(c);
                if (first == null) {
                    first = num;
                }

                last = num;
            } catch (NumberFormatException ignored) {
            }
        }
        return first + String.valueOf(last);
    }
}
