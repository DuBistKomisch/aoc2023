import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day03 implements Day {
    public static void main(String[] args) throws IOException {
        new Day03().run();
    }

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());
        var symbols = new HashSet<Point>();
        for (int row = 0; row < lines.length; row++) {
            var line = lines[row];
            for (int column = 0; column < line.length(); column++) {
                var character = line.charAt(column);
                if (!Character.isDigit(character) && character != '.') {
                    symbols.add(new Point(column, row));
                }
            }
        }
        AtomicInteger sum = new AtomicInteger();
        findNumbers(lines, (number, row, start, end) -> {
            if (hasAdjacentSymbol(symbols, row, start, end)) {
                sum.addAndGet(number);
            }
        });
        return sum.get();
    }

    @Override
    public Object part2(String input) {
        var lines = input.split(System.lineSeparator());
        var gears = new HashMap<Point, ArrayList<Integer>>();
        for (int row = 0; row < lines.length; row++) {
            var line = lines[row];
            for (int column = 0; column < line.length(); column++) {
                if (line.charAt(column) == '*') {
                    gears.put(new Point(column, row), new ArrayList<>());
                }
            }
        }
        findNumbers(lines, (number, row, start, end) -> {
            var adjacentGears = getAdjacentSymbols(gears.keySet(), row, start, end);
            for (var adjacentGear : adjacentGears) {
                gears.get(adjacentGear).add(number);
            }
        });
        var sum = 0;
        for (var numbers : gears.values()) {
            if (numbers.size() == 2) {
                sum += numbers.get(0) * numbers.get(1);
            }
        }
        return sum;
    }

    private void findNumbers(String[] lines, NumberCallback callback) {
        for (int row = 0; row < lines.length; row++) {
            var line = lines[row];
            int start = 0;
            while (start < line.length()) {
                int end = start;
                while (end < line.length() && Character.isDigit(line.charAt(end))) {
                    end += 1;
                }
                if (end == start) {
                    start += 1;
                    continue;
                }
                var number = Integer.valueOf(line.substring(start, end));
                callback.call(number, row, start, end);
                start = end + 1;
            }
        }
    }

    private boolean hasAdjacentSymbol(Set<Point> symbols, int row, int start, int end) {
        return adjacentPoints(row, start, end).stream().anyMatch(symbols::contains);
    }

    private Set<Point> getAdjacentSymbols(Set<Point> symbols, int row, int start, int end) {
        return adjacentPoints(row, start, end).stream().filter(symbols::contains).collect(Collectors.toSet());
    }

    private Set<Point> adjacentPoints(int row, int start, int end) {
        var adjacentPoints = new HashSet<Point>();
        adjacentPoints.add(new Point(start - 1, row));
        adjacentPoints.add(new Point(end, row));
        for (int column = start - 1; column <= end; column++) {
            adjacentPoints.add(new Point(column, row - 1));
            adjacentPoints.add(new Point(column, row + 1));
        }
        return adjacentPoints;
    }

    private interface NumberCallback {
        void call(int number, int row, int start, int end);
    }
}