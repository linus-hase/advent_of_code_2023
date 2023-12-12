package Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day4 {

    private static final String PATH = "./src/Day4/input.txt";

    public static void main(String[] args) throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            Map<Integer,Integer> part2 = new HashMap<>();
            AtomicInteger cardNum = new AtomicInteger(1);
            int result1 = br.lines()
             .map(l -> l.substring(l.indexOf(":") + 2))
             .flatMapToInt(l -> {
                 part2.put(cardNum.get(), part2.get(cardNum.getAndIncrement()));
                  AtomicInteger i = new AtomicInteger(0);
                  Map<Integer,Integer> m = new HashMap<>();
                        for (String s : l.split("\\|")) {
                            String[] temp = s.trim().split(" ");
                            if (i.getAndIncrement() % 2 == 0) {
                                for (String t : temp) {
                                    try {
                                        m.put(Integer.parseInt(t.trim()), null);
                                    } catch (NumberFormatException ignored) {
                                    }
                                }
                            } else {
                                for (String t : temp) {
                                    try {
                                        Integer key = Integer.parseInt(t.trim());
                                        if (m.containsKey(key)) {
                                            m.replace(key, null, key);
                                        }
                                    } catch (NumberFormatException ignored) {
                                    }
                                }
                            }
                        }
                  int count = 0;
                  for (Integer key : m.keySet()) {
                      count = m.get(key) != null ? count + 1 : count;
                  }
                 int finalCount = count;
                 Day4.part2(part2, finalCount, cardNum.get());
                  return IntStream.of(IntStream.rangeClosed(1, count).reduce((x,y) -> x * 2).orElse(0));
             }).sum();
            System.out.println("Part 1: " + result1);
            Integer result2 = part2.values().stream().reduce(Integer::sum).orElse(0);
            System.out.println("Part 2: " + result2);
        }
    }

    private static void part2(Map<Integer, Integer> m, int hits, int curr) {
        curr--;
        if (m.get(curr) == null) {
            m.put(curr, 1);
        } else {
            m.put(curr, m.get(curr) + 1);
        }
        Integer addend = m.get(curr);

        for (int i = 1; i <= hits; i++) {
            if (m.get(curr + i) != null) {
                m.put(curr + i, m.get(curr + i) + addend);
            } else {
                m.put(curr + i, addend);
            }
        }
    }

}
