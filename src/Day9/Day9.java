package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {

    private static final String PATH = "./src/Day9/input.txt";
    private static final String EXAMPLE = "./src/Day9/example.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("Part 1: " + part1(PATH));
        System.out.println("Part 2: " + part2(PATH));
    }

    private record Pair<T>(T left, T right, T child, Day9.Day9.Pair<T> leftParent, Day9.Day9.Pair<T> rightParent) {
        @Override
        public String toString() {
            return left.toString() + " - " + right.toString();
        }
    }

    private static int searchValue(List<Integer> l, int part) {
        List<Pair<Integer>> ints = new ArrayList<>();
        for (int i = 0; i < l.size() - 1; i++) {
            ints.add(new Pair<>(l.get(i), l.get(i + 1), l.get(i + 1) - l.get(i), null, null));
        }

        List<Pair<Integer>> temp = new ArrayList<>();
        while (!isZeroRow(ints)) {
            for (int i = 0; i < ints.size() - 1; i++) {
                temp.add(new Pair<>(ints.get(i).child(), ints.get(i + 1).child(),
                                    ints.get(i + 1).child() - ints.get(i).child(),
                                    ints.get(i), ints.get(i + 1)));
            }
            ints = temp;
            temp = new ArrayList<>();
        }

        return part == 1 ? addRightSide(ints) : addLeftSide(ints);
    }

    private static boolean isZeroRow(List<Pair<Integer>> pairs) {
        return pairs.stream().allMatch(p -> p.child() == 0);
    }

    private static int addRightSide(List<Pair<Integer>> ints) {
        Pair<Integer> rightLine = ints.get(ints.size() - 1);
        int rightPath = 0;
        while (rightLine.rightParent != null) {
            rightPath += (rightPath == 0 ? rightLine.right() : 0) + rightLine.rightParent.right();
            rightLine = rightLine.rightParent;
        }
        return rightPath;
    }

    private static int addLeftSide(List<Pair<Integer>> ints) {
        Pair<Integer> leftLine = ints.get(0);
        int leftPath = 0;
        boolean started = false;
        while (leftLine.leftParent != null) {
            leftPath = leftLine.leftParent.left() - (!started ? leftLine.left() : leftPath);
            leftLine = leftLine.leftParent;
            started = true;
        }
        return leftPath;
    }

    private static int part1(String line) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(line))) {
            return br.lines().mapToInt(l -> {
                List<Integer> numbers = Arrays.stream(l.split(" "))
                                              .map(Integer::parseInt).toList();
                return searchValue(numbers, 1);
            }).sum();
        }
    }

    private static int part2(String line) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(line))) {
            return br.lines().mapToInt(l -> {
                List<Integer> numbers = Arrays.stream(l.split(" "))
                                              .map(Integer::parseInt).toList();
                return searchValue(numbers, 2);
            }).sum();
        }
    }
}
