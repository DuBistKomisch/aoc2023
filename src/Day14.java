import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day14 implements Day {
    public static void main(String[] args) throws IOException {
        new Day14().run();
    }

    @Override
    public Object part1(String input) {
        var platform = Platform.of(input);
        platform.shiftNorth();
        return platform.load();
    }

    @Override
    public Object part2(String input) {
        var platform = Platform.of(input);
        var loads = new HashMap<Integer, Integer>();
        var cycle = new HashMap<Integer, Integer>();
        var cycle2 = new HashMap<Integer, Integer>();
        for (int i = 1; i <= 1000; i += 1) {
            platform.shiftNorth();
            platform.shiftWest();
            platform.shiftSouth();
            platform.shiftEast();
            var load = platform.load();
            System.out.println("%d = %d".formatted(i, load));
            loads.put(i, load);
            if (i > 1 && load == loads.get(i - 1)) {
                continue;
            } if (!cycle.containsKey(load)) {
                cycle.put(load, i);
            } else if (!cycle2.containsKey(load)) {
                cycle2.put(load, i);
            } else {
                var prev = cycle.get(load);
                var start = cycle2.get(load);
                var prevLength = start - prev;
                var length = i - start;
                if (prevLength != length) {
                    continue;
                }
                var offset = (1_000_000_000 - i) % length;
                return loads.get(start + offset);
            }
        }
        return 0;
    }

    static class Platform {
        Map<Point, Rock> grid;
        int height;
        int width;

        public Platform(Map<Point, Rock> grid, int height, int width) {
            this.grid = grid;
            this.height = height;
            this.width = width;
        }

        static Platform of(String input) {
            var lines = input.split(System.lineSeparator());
            var grid = new HashMap<Point, Rock>();
            var height = lines.length;
            var width = lines[0].length();
            for (var y = 0; y < height; y += 1) {
                for (var x = 0; x < width; x += 1) {
                    var point = new Point(x, y);
                    var character = lines[y].charAt(x);
                    if (character == '#') {
                        grid.put(point, Rock.SQUARE);
                    } else if (character == 'O') {
                        grid.put(point, Rock.ROUND);
                    }
                }
            }
            return new Platform(grid, height, width);
        }

        void shiftNorth() {
            for (var y = 0; y < height; y += 1) {
                for (var x = 0; x < width; x += 1) {
                    var point = new Point(x, y);
                    if (grid.get(point) == Rock.ROUND) {
                        grid.remove(point);
                        while (point.y > 0 && !grid.containsKey(point.north())) {
                            point = point.north();
                        }
                        grid.put(point, Rock.ROUND);
                    }
                }
            }
        }

        void shiftWest() {
            for (var x = 0; x < width; x += 1) {
                for (var y = 0; y < height; y += 1) {
                    var point = new Point(x, y);
                    if (grid.get(point) == Rock.ROUND) {
                        grid.remove(point);
                        while (point.x > 0 && !grid.containsKey(point.west())) {
                            point = point.west();
                        }
                        grid.put(point, Rock.ROUND);
                    }
                }
            }
        }

        void shiftSouth() {
            for (var y = height - 1; y >= 0; y -= 1) {
                for (var x = 0; x < width; x += 1) {
                    var point = new Point(x, y);
                    if (grid.get(point) == Rock.ROUND) {
                        grid.remove(point);
                        while (point.y < height - 1 && !grid.containsKey(point.south())) {
                            point = point.south();
                        }
                        grid.put(point, Rock.ROUND);
                    }
                }
            }
        }

        void shiftEast() {
            for (var x = width - 1; x >= 0; x -= 1) {
                for (var y = 0; y < height; y += 1) {
                    var point = new Point(x, y);
                    if (grid.get(point) == Rock.ROUND) {
                        grid.remove(point);
                        while (point.x < width - 1 && !grid.containsKey(point.east())) {
                            point = point.east();
                        }
                        grid.put(point, Rock.ROUND);
                    }
                }
            }
        }

        int load() {
            var load = 0;
            for (var y = 0; y < height; y += 1) {
                for (var x = 0; x < width; x += 1) {
                    if (grid.get(new Point(x, y)) == Rock.ROUND) {
                        load += height - y;
                    }
                }
            }
            return load;
        }
    }

    enum Rock {
        SQUARE,
        ROUND
    }
}