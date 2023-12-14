import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class Day06 implements Day {
    public static void main(String[] args) throws IOException {
        new Day06().run();
    }

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());
        var times = toNumbers(lines[0].split(":")[1]).collect(Collectors.toList());
        var distances = toNumbers(lines[1].split(":")[1]).collect(Collectors.toList());
        return IntStream.range(0, times.size())
                .mapToLong(i -> waysToBeat(times.get(i), distances.get(i)))
                .reduce(1, (x, y) -> x * y);
    }

    @Override
    public Object part2(String input) {
        var lines = input.split(System.lineSeparator());
        var time = Long.valueOf(lines[0].replaceAll("[^0-9]", ""));
        var distance = Long.valueOf(lines[1].replaceAll("[^0-9]", ""));
        return waysToBeat(time, distance);
    }

    private Stream<Long> toNumbers(String list) {
        return Arrays.stream(list.split(" "))
                .filter(not(String::isEmpty))
                .map(Long::valueOf);
    }

    private long waysToBeat(long time, long record) {
        return LongStream.range(1, time)
            .map(hold -> hold * (time - hold))
            .filter(distance -> distance > record)
            .count();
    }
}