import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Day21 implements Day {
    public static void main(String[] args) throws IOException {
        new Day21().run();
    }

    @Override
    public Object part1(String input) {
        return part1(input, 64);
    }

    public Object part1(String input, int steps) {
        var garden = buildGarden(input);
        var locations = new HashSet<Point>();
        locations.add(garden.start);
        for (int i = 0; i < steps; i += 1) {
            var nextLocations = new HashSet<Point>();
            for (var point : locations) {
                for (var dir : Dir.values()) {
                    var nextPoint = point.step(dir);
                    if (garden.grid.get(nextPoint) == Content.PLOT) {
                        nextLocations.add(nextPoint);
                    }
                }
            }
            locations = nextLocations;
        }
        return locations.size();
    }

    @Override
    public Object part2(String input) {
        var garden = buildGarden(input);
        var locations = new HashSet<Point>();
        locations.add(garden.start);
        for (int i = 0; i < 26501365; i += 1) {
            if (i % 100 == 0) {
                System.out.println("%d: %d".formatted(i, locations.size()));
            }
            var nextLocations = new HashSet<Point>();
            for (var point : locations) {
                for (var dir : Dir.values()) {
                    var nextPoint = point.step(dir);
                    var wrappedPoint = new Point(nextPoint.x % garden.width, nextPoint.y % garden.height);
                    if (garden.grid.get(wrappedPoint) == Content.PLOT) {
                        nextLocations.add(nextPoint);
                    }
                }
            }
            locations = nextLocations;
        }
        return locations.size();
    }

    private Garden buildGarden(String input) {
        var lines = input.split(System.lineSeparator());
        var grid = new HashMap<Point, Content>();
        var height = lines.length;
        var width = lines[0].length();
        Point start = new Point(0, 0);
        for (int y = 0; y < height; y += 1) {
            for (int x = 0; x < width; x += 1) {
                var point = new Point(x, y);
                var character = lines[y].charAt(x);
                var content = Content.PLOT;
                if (character == '#') {
                    content = Content.ROCK;
                } else if (character == 'S') {
                    start = point;
                }
                grid.put(point, content);
            }
        }
        return new Garden(grid, width, height, start);
    }

    static class Garden {
        Map<Point, Content> grid;
        int width;
        int height;
        Point start;

        public Garden(Map<Point, Content> grid, int width, int height, Point start) {
            this.grid = grid;
            this.width = width;
            this.height = height;
            this.start = start;
        }
    }

    enum Content {
        PLOT,
        ROCK
    }
}