import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day09 implements Day {
    public static void main(String[] args) throws IOException {
        new Day09().run();
    }

    @Override
    public Object part1(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).mapToInt(line -> {
            var sequences = toSequences(line);
            return sequences.stream().mapToInt(sequence -> sequence[sequence.length - 1]).sum();
        }).sum();
    }

    @Override
    public Object part2(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).mapToInt(line -> {
            var sequences = toSequences(line);
            var result = 0;
            for (var i = sequences.size() - 2; i >= 0; i -= 1) {
                result = sequences.get(i)[0] - result;
            }
            return result;
        }).sum();
    }

    private int[] toNumbers(String list) {
        return Arrays.stream(list.split(" "))
                .mapToInt(Integer::valueOf)
                .toArray();
    }

    private List<int[]> toSequences(String line) {
        var sequences = new ArrayList<int[]>();
        sequences.add(toNumbers(line));
        while (true) {
            var previous = sequences.get(sequences.size() - 1);
            var next = IntStream.range(0, previous.length - 1).map(i -> previous[i + 1] - previous[i]).toArray();
            sequences.add(next);
            if (Arrays.stream(next).allMatch(x -> x == 0)) {
                break;
            }
        }
        return sequences;
    }
}