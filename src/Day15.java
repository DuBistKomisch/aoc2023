import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day15 implements Day {
    public static void main(String[] args) throws IOException {
        new Day15().run();
    }

    @Override
    public Object part1(String input) {
        return Arrays.stream(input.trim().split(","))
                .mapToInt(this::hash)
                .sum();
    }

    @Override
    public Object part2(String input) {
        var boxes = new HashMap<Integer, ArrayList<Lens>>();
        for (var command : input.trim().split(",")) {
            if (command.endsWith("-")) {
                var label = command.substring(0, command.length() - 1);
                var box = boxes.get(hash(label));
                if (box == null) {
                    continue;
                }
                var index = findLens(box, label);
                if (index != -1) {
                    box.remove(index);
                }
            } else {
                var parts = command.split("=");
                var label = parts[0];
                var focalLength = Integer.parseInt(parts[1]);
                var box = boxes.computeIfAbsent(hash(label), key -> new ArrayList<>());
                var index = findLens(box, label);
                if (index == -1) {
                    box.add(new Lens(label, focalLength));
                } else {
                    box.get(index).focalLength = focalLength;
                }
            }
        }
        var sum = 0;
        for (var entry : boxes.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i += 1) {
                sum += (entry.getKey() + 1) * (i + 1) * entry.getValue().get(i).focalLength;
            }
        }
        return sum;
    }

    private int hash(String input) {
        int current = 0;
        for (var i = 0; i < input.length(); i += 1) {
            current = ((current + input.charAt(i)) * 17) % 256;
        }
        return current;
    }

    private int findLens(List<Lens> box, String label) {
        for (int i = 0; i < box.size(); i += 1) {
            if (box.get(i).label.equals(label)) {
                return i;
            }
        }
        return -1;
    }

    class Lens {
        String label;
        int focalLength;

        public Lens(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }
    }
}