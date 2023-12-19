import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day11 implements Day {
    public static void main(String[] args) throws IOException {
        new Day11().run();
    }

    @Override
    public Object part1(String input) {
        return solve(input, 2);
    }

    @Override
    public Object part2(String input) {
        return solve(input, 1_000_000);
    }

    private long solve(String input, int expansion) {
        var lines = input.split(System.lineSeparator());
        var width = lines[0].length();
        var height = lines.length;
        var columns = new boolean[width];
        var rows = new boolean[height];
        var galaxies = new ArrayList<Point>();
        for (var y = 0; y < height; y += 1) {
            for (var x = 0; x < width; x += 1) {
                if (lines[y].charAt(x) == '#') {
                    galaxies.add(new Point(x, y));
                    columns[x] = true;
                    rows[y] = true;
                }
            }
        }
        var emptyColumns = IntStream.range(0, width).filter(x -> !columns[x]).toArray();
        var emptyRows = IntStream.range(0, height).filter(y -> !rows[y]).toArray();
        long sum = 0;
        for (var i = 0; i < galaxies.size(); i++) {
            for (var j = i + 1; j < galaxies.size(); j++) {
                var a = galaxies.get(i);
                var b = galaxies.get(j);
                sum += Math.abs(a.x - b.x) + Math.abs(a.y - b.y)
                        + Arrays.stream(emptyColumns).filter(x -> x > Math.min(a.x, b.x) && x < Math.max(a.x, b.x)).count() * (expansion - 1)
                        + Arrays.stream(emptyRows).filter(y -> y > Math.min(a.y, b.y) && y < Math.max(a.y, b.y)).count() * (expansion - 1);
            }
        }
        return sum;
    }
}