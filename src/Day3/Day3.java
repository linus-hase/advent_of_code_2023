package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (Character.isDigit(line.charAt(i))) {
                        int startIdx = i;
                        int endIdx   = startIdx;
                        try {
                            while (Character.isDigit(line.charAt((i += 1)))) {
                                endIdx++;
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
        }

    }

}
