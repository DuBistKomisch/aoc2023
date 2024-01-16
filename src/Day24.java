import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day24 implements Day {
    public static void main(String[] args) throws IOException {
        new Day24().run();
    }

    @Override
    public Object part1(String input) {
        return part1(input, 200000000000000.0, 400000000000000.0);
    }

    public Object part1(String input, double min, double max) {
        var hailstones = buildHailstones(input);
        var count = 0;
        for (int i = 0; i < hailstones.size(); i += 1) {
            for (int j = i + 1; j < hailstones.size(); j += 1) {
                var a = hailstones.get(i);
                var b = hailstones.get(j);
                var ma = a.dy / a.dx;
                var ca = a.y - ma * a.x;
                var mb = b.dy / b.dx;
                var cb = b.y - mb * b.x;
                var x = (cb - ca) / (ma - mb);
                var y = ma * x + ca;
                if ((x - a.x) / a.dx > 0 && (x - b.x) / b.dx > 0 && x >= min && x <= max && y >= min && y <= max) {
                    count += 1;
                }
            }
        }
        return count;
    }

    @Override
    public Object part2(String input) {
        return 0;
    }

    List<Hailstone> buildHailstones(String input) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> {
                    var parts = Pattern.compile("-?\\d+").matcher(line).results()
                            .map(MatchResult::group).toList();
                    return new Hailstone(
                            Double.parseDouble(parts.get(0)),
                            Double.parseDouble(parts.get(1)),
                            Double.parseDouble(parts.get(2)),
                            Double.parseDouble(parts.get(3)),
                            Double.parseDouble(parts.get(4)),
                            Double.parseDouble(parts.get(5))
                    );
                })
                .toList();
    }

    static class Hailstone {
        double x;
        double y;
        double z;
        double dx;
        double dy;
        double dz;

        public Hailstone(double x, double y, double z, double dx, double dy, double dz) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }

        @Override
        public String toString() {
            return "Hailstone{" + x + "," + y + "," + z + " @ " + dx + "," + dy + "," + dz + '}';
        }
    }
}