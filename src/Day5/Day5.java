package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day5 {

    private static final String PATH = "./src/Day5/input.txt";

    public static void main(String[] args) throws IOException{
        System.out.println("Part 1: " + Day5.part1());
    }

    private static Map<Long,Long> initMap (List<Long> seeds) {
        Map<Long,Long> m = new HashMap<>();
        for (Long l : seeds) {
            m.put(l, l);
        }
        return m;
    }

    private static Long part1() throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            List<Long> seeds = new ArrayList<>(
                    Arrays.stream((br.readLine() + br.readLine()).split(" ")).map(s -> {
                        try {
                            return Long.parseLong(s);
                        } catch (NumberFormatException ignored) {
                            return -1L;
                        }
                    }).toList());
            seeds.remove(0);

            Map<Long,Long> seedMap = Day5.initMap(seeds);
            String line = br.readLine();
            while (line != null) {
                line = br.readLine();
                List<Long> v = new ArrayList<>(seedMap.values());
                while (line != null && !line.isEmpty()) {
                    int i = 0;
                    Long[] lArr = new Long[3];
                    int idx = 0;
                    for (String s : line.split(" ")) {
                        lArr[idx] = Long.parseLong(s);
                        idx++;
                    }
                    for (Long l : seedMap.keySet()) {
                        Long currVal = seedMap.get(l);
                        if (v.get(i) >= lArr[1] && v.get(i) < lArr[1] + lArr[2]) {
                            long l1 = (lArr[0] - lArr[1]) + v.get(i);
                            if (currVal > l1 || Objects.equals(v.get(i), currVal)) {
                                seedMap.put(l, l1);
                            }
                        }
                        i++;
                    }
                    line = br.readLine();
                }
                line = br.readLine();
            }
            return seedMap.values().stream().min(Long::compareTo).orElse(-1L);
        }
    }

}
