package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day7 {

    private static final String PATH = "./src/Day7/input.txt";
    private static final String EXAMPLE = "./src/Day7/example.txt";

    public static void main(String[] args) throws IOException{
        System.out.println("Part 1: " + Day7.part1(PATH));
        System.out.println("Part 2: " + Day7.part2(PATH));
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

    private record HandComparator(int part) implements Comparator<String> {

        @Override
            public int compare(String o1, String o2) {
                String hand1 = o1.substring(0, o1.indexOf(" "));
                String hand2 = o2.substring(0, o2.indexOf(" "));
                int    rank1 = calculateRank(hand1);
                int    rank2 = calculateRank(hand2);
                if (rank1 == rank2) {
                    return compareByCardStrength(hand1, hand2);
                }
                return Integer.compare(rank1, rank2);
            }

            private int calculateRank(String s) {
                Map<Character, Integer> occurrences = new TreeMap<>();
                for (char c : s.toCharArray()) {
                    occurrences.put(c, occurrences.getOrDefault(c, 0) + 1);
                }

                int jokers = 0;
                if (this.part == 2) {
                    jokers = getJokerCount(occurrences);
                    if (jokers == 5) {
                        occurrences.put('A', 0);
                    }
                }

                Pair<Integer, Integer> pair = new Pair<>(Integer.MIN_VALUE, Integer.MIN_VALUE);
                occurrences.forEach((key, value) -> {
                    if (value > pair.getLeft()) {
                        pair.setRight(pair.getLeft());
                        pair.setLeft(value);
                    } else if (value > pair.getRight()) {
                        pair.setRight(value);
                    }
                });
                int left  = pair.getLeft() + jokers;
                int right = pair.getRight();

                if (left == 5) {
                    return 7; // Five of a kind
                } else if (left == 4) {
                    return 6; // Four of a kind
                } else if (left == 3 && right == 2) {
                    return 5; // Full house
                } else if (left == 3) {
                    return 4; // Three of a kind
                } else if (left == 2 && right == 2) {
                    return 3; // Two pair
                } else if (left == 2) {
                    return 2; // One pair
                } else {
                    return 1; // High card
                }
            }

            private int compareByCardStrength(String hand1, String hand2) {
                for (int i = 0; i < hand1.length(); i++) {
                    char card1 = hand1.charAt(i);
                    char card2 = hand2.charAt(i);

                    String cardStrength = this.part == 1 ? "23456789TJQKA" : "J23456789TQKA";
                    if (card1 != card2) {
                        return Integer.compare(cardStrength.indexOf(card1), cardStrength.indexOf(card2));
                    }
                }
                return 0;
            }

            private int getJokerCount(Map<Character, Integer> m) {
                return m.get('J') != null ? m.remove('J') : 0;
            }
        }

    private static int part1(String line) throws IOException {
        int result = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(line))) {
            List<String> l = new ArrayList<>(br.lines().toList());
            l.sort(new HandComparator(1));
            for (int i = 0; i < l.size(); i++) {
                result += (i + 1) * Integer.parseInt(l.get(i).substring(l.get(i).indexOf(" ") + 1));
            }
        }
        return result;
    }

    private static int part2(String line) throws IOException{
        int result = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(line))) {
            List<String> l = new ArrayList<>(br.lines().toList());
            l.sort(new HandComparator(2));
            System.out.println(l);
            for (int i = 0; i < l.size(); i++) {
                result += (i + 1) * Integer.parseInt(l.get(i).substring(l.get(i).indexOf(" ") + 1));
            }
        }
        return result;
    }
}
