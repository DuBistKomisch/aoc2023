import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day18 implements Day {
    public static void main(String[] args) throws IOException {
        new Day18().run();
    }

    @Override
    public Object part1(String input) {
        var instructions = Arrays.stream(input.split(System.lineSeparator())).map(line -> {
            var parts = line.split(" ");
            var dir = Dir.of(parts[0].charAt(0));
            var steps = Integer.parseInt(parts[1]);
            return new Instruction(dir, steps);
        }).toList();
        return solve(instructions);
    }

    @Override
    public Object part2(String input) {
        var instructions = Arrays.stream(input.split(System.lineSeparator())).map(line -> {
            var parts = line.split("#");
            var dir = toDir(parts[1].charAt(5));
            var steps = Integer.parseInt(parts[1].substring(0, 5), 16);
            return new Instruction(dir, steps);
        }).toList();
        return solve(instructions);
    }

    private Dir toDir(int digit) {
        return switch (digit) {
            case '0' -> Dir.EAST;
            case '1' -> Dir.SOUTH;
            case '2' -> Dir.WEST;
            case '3' -> Dir.NORTH;
            default -> throw new IllegalArgumentException();
        };
    }

    private long solve(List<Instruction> instructions) {
        var current = new Point(0, 0);
        // collect each corner visited
        var grid = new HashSet<Point>();
        grid.add(current);
        for (var instruction : instructions) {
            current = current.step(instruction.dir, instruction.steps);
            grid.add(current);
        }
        // collect sorted unique x and y values of corners as a simplified coordinate system
        var columns = grid.stream().map(point -> point.x).collect(Collectors.toSet()).stream().sorted().toList();
        var rows = grid.stream().map(point -> point.y).collect(Collectors.toSet()).stream().sorted().toList();
        // collect points along trench in 2x scaled coordinates
        current = new Point(columns.indexOf(0) * 2, rows.indexOf(0) * 2);
        var trench = new HashSet<Point>();
        trench.add(current);
        for (var instruction : instructions) {
            // convert to real coordinates to determine next corner more easily
            var next = new Point(columns.get(current.x / 2), rows.get(current.y / 2)).step(instruction.dir, instruction.steps);
            // then convert back to 2x scaled coordinates
            next = new Point(columns.indexOf(next.x) * 2, rows.indexOf(next.y) * 2);
            // then step 1 by 1 to next corner to build trench
            while (!current.equals(next)) {
                current = current.step(instruction.dir);
                trench.add(current);
            }
        }
        // flood fill in 2x scaled coordinates to determine inside vs outside
        var outside = new HashSet<Point>();
        var queue = new ArrayDeque<Point>();
        queue.add(new Point(-1, -1));
        while ((current = queue.poll()) != null) {
            if (trench.contains(current) || outside.contains(current)) {
                continue;
            }
            outside.add(current);
            if (current.x >= 0) {
                queue.add(current.west());
            }
            if (current.x <= columns.size() * 2) {
                queue.add(current.east());
            }
            if (current.y >= 0) {
                queue.add(current.north());
            }
            if (current.y <= rows.size() * 2) {
                queue.add(current.south());
            }
        }
        // calculate area
        var area = 0L;
        for (int y = 0; y < rows.size(); y += 1) {
            for (int x = 0; x < columns.size(); x += 1) {
                // for each 1x scaled coordinate, use following intermediate points to determine how much area to include
                current = new Point(x * 2, y * 2);
                if (outside.contains(current)) {
                    continue;
                }
                var right = !outside.contains(current.east());
                var down = !outside.contains(current.south());
                var downRight = !outside.contains(current.south().east());
                if (right) {
                    var width = (long) (columns.get(x + 1) - columns.get(x));
                    if (down) {
                        var height = (long) (rows.get(y + 1) - rows.get(y));
                        if (downRight) {
                            // ## -> count full area
                            // ##
                            area += width * height;
                        } else {
                            // ## -> count only top and left edge, minus one to avoid counting corner twice
                            // #.
                            area += width + height - 1;
                        }
                    } else {
                        // ## -> count only top edge
                        // .?
                        area += width;
                    }
                } else {
                    if (down) {
                        // #. -> count only left edge
                        // #?
                        var height = (long) (rows.get(y + 1) - rows.get(y));
                        area += height;
                    } else {
                        // #. -> count only corner
                        // .?
                        area += 1;
                    }
                }
            }
        }
        return area;
    }

    static class Instruction {
        Dir dir;
        int steps;

        public Instruction(Dir dir, int steps) {
            this.dir = dir;
            this.steps = steps;
        }
    }
}