package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8 {

    private static final String PATH = "./src/Day8/input.txt";
    private static final String EXAMPLE = "./src/Day8/example.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("Part 1: " + Day8.part1(PATH));
        System.out.println("Part 2: " + Day8.part2(PATH));
    }

    private static class Pair<L,R> {
        private L left;

        private R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        private L getLeft() {
            return left;
        }

        private void setLeft(L left) {
            this.left = left;
        }

        private R getRight() {
            return right;
        }

        private void setRight(R right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return left.toString() + " / " + right.toString();
        }
    }

    private static int countSteps(String path, Map<String, Pair<String,String>> m) {
        int result = 0;
        Pair<String,String> curr = m.get("AAA");
        for (int c = 0; c < path.length(); c++) {
            result++;
            String newStr;
            if (path.charAt(c) == 'L') {
                newStr = curr.getLeft();
            } else {
                newStr = curr.getRight();
            }
            if (newStr.equals("ZZZ")) {
                return result;
            }
            curr = m.get(newStr);
            if (c == path.length() - 1) {
                c = -1;
            }
        }
        return result;
    }

    private static long countSimultaneousSteps(String path, Map<String, Pair<String,String>> m) {
        long result;
        Map<String,Pair<String,Pair<Integer,Boolean>>> starts = new HashMap<>();
        for (String s : m.keySet()) {
            if (s.endsWith("A")) {
                starts.put(s, new Pair<>(s, new Pair<>(0, false)));
            }
        }
        boolean foundAll = false;
        for (int c = 0; c < path.length() && !foundAll; c++) {
            for (String s : starts.keySet()) {
                if (!starts.get(s).getRight().getRight()) {
                    starts.get(s).getRight().setLeft(starts.get(s).getRight().getLeft() + 1);
                    if (path.charAt(c) == 'L') {
                        starts.get(s).setLeft(m.get(starts.get(s).getLeft()).getLeft());
                    } else {
                        starts.get(s).setLeft(m.get(starts.get(s).getLeft()).getRight());
                    }
                    if (starts.get(s).getLeft().endsWith("Z")) {
                        starts.get(s).getRight().setRight(true);
                    }
                }
            }
            foundAll = starts.values().stream().allMatch(v -> v.getRight().getRight());
            if (c == path.length() - 1) {
                c = -1;
            }
        }
        result = findLCM(starts.values().stream().map(v -> v.getRight().getLeft()).collect(Collectors.toList()));

        return result;
    }

    private static long findLCM(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty.");
        }
        long lcm = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            lcm = findLCM(lcm, numbers.get(i));
        }
        return lcm;
    }

    private static long findLCM(long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(a);
        long absNumber2 = Math.abs(b);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    private static int part1(String line) throws IOException {
        int result;
        try(BufferedReader br = new BufferedReader(new FileReader(line))) {
            String path = br.readLine() + br.readLine();
            Map<String, Pair<String,String>> m = br.lines().collect(
                    Collectors.toMap(l -> l.substring(0, l.indexOf(" ")), l ->
                            new Pair<>(l.substring(l.indexOf("(") + 1, l.indexOf(",")),
                                       l.substring(l.indexOf(",") + 2, l.indexOf(")")))));
            result = countSteps(path, m);
        }
        return result;
    }

    private static long part2(String line) throws IOException {
        long result;
        try(BufferedReader br = new BufferedReader(new FileReader(line))) {
            String path = br.readLine() + br.readLine();
            Map<String, Pair<String,String>> m = br.lines().collect(
                    Collectors.toMap(l -> l.substring(0, l.indexOf(" ")), l ->
                            new Pair<>(l.substring(l.indexOf("(") + 1, l.indexOf(",")),
                                       l.substring(l.indexOf(",") + 2, l.indexOf(")")))));
            result = countSimultaneousSteps(path, m);
        }
        return result;
    }

}
