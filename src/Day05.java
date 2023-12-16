import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Day05 implements Day {
    public static void main(String[] args) throws IOException {
        new Day05().run();
    }

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());

        List<Long> seeds = toNumbers(lines[0].split(": ")[1]);
        for (var mappings : toMappingsList(lines)) {
            seeds = seeds.stream().map(seed -> {
                var source = mappings.floorKey(seed);
                if (source == null) {
                    return seed;
                }
                var destination = mappings.get(source);
                if (seed >= source + destination.length) {
                    return seed;
                }
                return seed - source + destination.from;
            }).collect(Collectors.toList());
        }
        return seeds.stream().mapToLong(Long::longValue).min().getAsLong();
    }

    @Override
    public Object part2(String input) {
        var lines = input.split(System.lineSeparator());

        List<Range> seeds = toRanges(lines[0].split(": ")[1]);
        for (var mappings : toMappingsList(lines)) {
            seeds = seeds.stream().flatMap(seed -> {
                var newSeeds = new ArrayList<Range>();
                var source = mappings.floorKey(seed.from);
                while (source != null && seed.length != 0) {
                    if (source > seed.from && source < seed.from + seed.length) {
                        // gap, keep range for next mapping
                        newSeeds.add(new Range(
                                seed.from,
                                Math.min(source - seed.from, seed.length)));
                        // any leftover as next seed
                        seed = new Range(
                                source,
                                Math.max(seed.from + seed.length - source, 0));
                    }
                    var destination = mappings.get(source);
                    if (seed.from < source + destination.length) {
                        // overlap, map to destination range
                        var newSeed = new Range(
                                seed.from - source + destination.from,
                                Math.min(source + destination.length - seed.from, seed.length));
                        newSeeds.add(newSeed);
                        // any leftover as next seed
                        seed = new Range(
                                seed.from + newSeed.length,
                                seed.length - newSeed.length);
                    }
                    source = mappings.higherKey(source);
                }
                if (seed.length != 0) {
                    newSeeds.add(seed);
                }
                return newSeeds.stream();
            }).collect(Collectors.toList());
        }
        return seeds.stream().mapToLong(range -> range.from).min().getAsLong();
    }

    class Range {
        long from;
        long length;

        Range(long from, long length) {
            this.from = from;
            this.length = length;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "from=" + from +
                    ", length=" + length +
                    '}';
        }
    }

    private List<TreeMap<Long, Range>> toMappingsList(String[] lines) {
        var mappingsList = new ArrayList<TreeMap<Long, Range>>();
        int line = 3;
        while (line < lines.length) {
            var mappings = new TreeMap<Long, Range>();
            while (line < lines.length && !lines[line].isEmpty()) {
                var mapping = toNumbers(lines[line]);
                mappings.put(mapping.get(1), new Range(mapping.get(0), mapping.get(2)));
                line += 1;
            }
            mappingsList.add(mappings);
            line += 2;
        }
        return mappingsList;
    }

    private List<Long> toNumbers(String list) {
        return Arrays.stream(list.split(" "))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    private List<Range> toRanges(String list) {
        var numbers = toNumbers(list);
        var ranges = new ArrayList<Range>();
        for (var i = 0; i < numbers.size(); i += 2) {
            ranges.add(new Range(numbers.get(i), numbers.get(i + 1)));
        }
        return ranges;
    }
}