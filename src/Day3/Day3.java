package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {

    private static final String PATH = "./src/Day3/input.txt";

    public static void main(String[] args) throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String precedingLine = null;
            String followingLine;
            String line;
            line          = br.readLine();
            followingLine = br.readLine();
            int result = 0;
            int result2 = 0;
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '*') {
                        result2 += Day3.part2(precedingLine, line, followingLine, i);
                    }
                    if (Character.isDigit(line.charAt(i))) {
                        int startIdx = i;
                        int endIdx   = startIdx;
                        try {
                            while (Character.isDigit(line.charAt((i += 1)))) {
                                endIdx++;
                                if (line.charAt(i) == '*') {
                                    result2 += Day3.part2(precedingLine, line, followingLine, i);
                                }
                            }
                            if (line.charAt(i) == '*') {
                                result2 += Day3.part2(precedingLine, line, followingLine, i);
                            }
                        } catch (StringIndexOutOfBoundsException ignored) {
                        }

                        boolean hasAdjacent = false;
                        int     i1          = endIdx < line.length() - 1 ? endIdx + 1 : line.length() - 1;
                        if (precedingLine != null) {
                            for (int j = startIdx > 0 ? startIdx - 1 : 0; j <= i1 && !hasAdjacent; j++) {
                                hasAdjacent = !Character.isDigit(precedingLine.charAt(j)) && !(precedingLine.charAt(
                                        j) == '.');
                            }
                        }
                        char c = line.charAt(startIdx > 0 ? startIdx - 1 : 0);
                        hasAdjacent = hasAdjacent || (c != '.' && !Character.isDigit(c))
                                || (line.charAt(i1) != '.' && !Character.isDigit(line.charAt(i1)));

                        if (followingLine != null && !hasAdjacent) {
                            for (int j = startIdx > 0 ? startIdx - 1 : 0; j <= i1 && !hasAdjacent; j++) {
                                hasAdjacent = !Character.isDigit(followingLine.charAt(j)) && !(followingLine.charAt(
                                        j) == '.');
                            }
                        }
                        if (hasAdjacent) {
                            result += Integer.parseInt(line.substring(startIdx, endIdx + 1));
                        }
                    }
                }
                precedingLine = line;
                line          = followingLine;
                followingLine = br.readLine();
            }

            System.out.println("Part 1: " + result);
            System.out.println("Part 2: " + result2);
        }

    }

    private static int part2(String precedingLine, String line, String followingLine, int idx) {

        List<Integer> numbers       = new ArrayList<>();

        for (String surrounding : new String[]{precedingLine, followingLine}) {
            int min = Math.min(idx + 1, surrounding.length() - 1);
            for (int i = Math.max(idx - 1, 0); i <= min; i++) {
                if (i == Math.max(idx - 1, 0) && Character.isDigit(surrounding.charAt(i))) {
                    int tempIdx = i;
                    while (tempIdx >= 0 && Character.isDigit(surrounding.charAt(tempIdx))) {
                        tempIdx--;
                    }
                    while (Character.isDigit(surrounding.charAt(Math.min(i + 1, surrounding.length() - 1)))) {
                        i++;
                    }
                    numbers.add(Integer.parseInt(surrounding.substring(tempIdx + 1, Math.min(i + 1, surrounding.length()))));
                } else if (i == idx && Character.isDigit(surrounding.charAt(i))) {
                    while (Character.isDigit(surrounding.charAt(Math.min(i + 1, surrounding.length() - 1)))) {
                        i++;
                    }
                    numbers.add(Integer.parseInt(surrounding.substring(idx, Math.min(i + 1, surrounding.length() - 1))));
                } else if (i == min && Character.isDigit(surrounding.charAt(i))) {
                    int tempIdx = i;
                    while (tempIdx < surrounding.length() && Character.isDigit(surrounding.charAt(Math.min(tempIdx + 1, surrounding.length() - 1)))) {
                        tempIdx++;
                    }
                    numbers.add(Integer.parseInt(surrounding.substring(i, Math.min(tempIdx + 1, surrounding.length()))));
                }
            }
        }

        // left
        int tempIdx = idx - 1;
        while(tempIdx >= 0 && Character.isDigit(line.charAt(tempIdx))) {
            tempIdx--;
        }
        if (tempIdx != idx - 1) {
            numbers.add(Integer.parseInt(line.substring(tempIdx + 1, idx)));
        }

        // right
        tempIdx = idx + 1;
        while(tempIdx <= line.length() - 1 && Character.isDigit(line.charAt(tempIdx))) {
            tempIdx++;
        }
        if (tempIdx != idx + 1) {
            numbers.add(Integer.parseInt(line.substring(idx + 1, tempIdx)));
        }

        return numbers.size() == 2 ? numbers.get(0) * numbers.get(1) : 0;
    }

}
