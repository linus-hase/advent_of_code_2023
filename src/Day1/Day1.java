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
                System.out.println(part2(value));
                return Integer.parseInt(numberAsString(value));
            }).sum();
            System.out.println(result);
            System.out.println(result2);
        }
    }

    private static int part2(String line) {
        if (line.equals("twone")) {
            System.out.println();
        }
        String[] numbers = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        int maxIdx = -1;
        int minIdx;
        String first  = null;
//        String last = null;
//        for (String num : numbers) {
//            if (line.contains(num)) {
//                int currFirstIdx  = line.indexOf(num);
//                int currLastIndex = line.lastIndexOf(num);
//                if (currLastIndex > maxIdx) {
//                    maxIdx = currLastIndex;
//                    last = num;
//                }
//                if (currFirstIdx < minIdx) {
//                    minIdx = currFirstIdx;
//                    first = num;
//                }
//            }
//        }
        do {
            first = null;
            minIdx = Integer.MAX_VALUE;
            for (String num : numbers) {
                if (line.contains(num)) {
                    int currFirstIdx  = line.indexOf(num);
                    if (currFirstIdx < minIdx) {
                        minIdx = currFirstIdx;
                        first = num;
                    }
                }
            }
            if (first != null) {
                line = line.replaceFirst(first, replaceNum(first));
            }
            System.out.println(line);
        } while (first != null);
        String modLine = line;
//        System.out.println(modLine);
//        if (first != null) {
//            modLine = modLine.substring(0, minIdx) + modLine.substring(minIdx).replaceFirst(first, replaceNum(first));
//            maxIdx -= first.length();
//        }
//        if (last != null && modLine.contains(last)) {
//            modLine = modLine.substring(0, maxIdx) + modLine.substring(maxIdx).replace(last, replaceNum(last));
//        }
        return Integer.parseInt(numberAsString(modLine));
    }

    private static String replaceNum(String num) {
        return switch (num) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
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
            } catch (NumberFormatException var6) {
            }
        }

        return first + String.valueOf(last);
    }
}
