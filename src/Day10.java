import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class Day10 implements Day {
    public static void main(String[] args) throws IOException {
        new Day10().run();
    }

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());
        return findLoop(lines).distance;
    }

    @Override
    public Object part2(String input) {
        var lines = input.split(System.lineSeparator());
        var loop = findLoop(lines);
        // flood fill outside the points marked in distances
        var fill = new HashMap<Point, Boolean>();
        var queue = new ArrayDeque<Point>();
        queue.add(new Point(0, 0));
        Point candidate;
        while ((candidate = queue.poll()) != null) {
            if (fill.containsKey(candidate) || loop.distances.containsKey(candidate)) {
                continue;
            }
            if (candidate.x < 0 || candidate.y < 0 || candidate.x > loop.width * 2 || candidate.y > loop.height * 2) {
                continue;
            }
            fill.put(candidate, true);
            queue.add(candidate.north());
            queue.add(candidate.south());
            queue.add(candidate.east());
            queue.add(candidate.west());
        }
        // now any point scaled back to 1x resolution not marked is internal
        var area = 0;
        for (var y = 0; y < loop.height; y += 1) {
            for (var x = 0; x < loop.width; x += 1) {
                var point = new Point(2 * x + 1, 2 * y + 1);
                if (!fill.containsKey(point) && !loop.distances.containsKey(point)) {
                    area += 1;
                }
            }
        }
        return area;
    }

    class Loop {
        int width;
        int height;
        int distance;
        Map<Point, Integer> distances;

        Loop(int width, int height, int distance, Map<Point, Integer> distances) {
            this.width = width;
            this.height = height;
            this.distance = distance;
            this.distances = distances;
        }
    }

    private Loop findLoop(String[] lines) {
        var pipes = new HashMap<Point, Pipe>();
        var grid = new HashMap<Point, Boolean>();
        Point start = new Point(-1, -1);
        var height = lines.length;
        var width = lines[0].length();
        for (var y = 0; y < height; y += 1) {
            for (var x = 0; x < width; x += 1) {
                var point = new Point(x, y);
                var symbol = lines[y].charAt(x);
                var pipe = Pipe.fromSymbol(symbol);
                pipes.put(point, pipe);
                // also fill in 2x resolution grid with connections
                var point2 = new Point(2 * x + 1, 2 * y + 1);
                grid.put(point2, true);
                if (pipe.hasWest() && x > 0 && pipes.get(point.west()).hasEast()) {
                    grid.put(point2.west(), true);
                }
                if (pipe.hasNorth() && y > 0 && pipes.get(point.north()).hasSouth()) {
                    grid.put(point2.north(), true);
                }
                // also mark start
                if (symbol == 'S') {
                    start = point2;
                }
            }
        }
        // bfs along pipes tracking distance
        var distances = new HashMap<Point, Integer>();
        distances.put(start, 0);
        var queue = new ArrayDeque<Candidate>();
        queue.add(new Candidate(start.north(), start));
        queue.add(new Candidate(start.south(), start));
        queue.add(new Candidate(start.east(), start));
        queue.add(new Candidate(start.west(), start));
        Candidate candidate;
        while ((candidate = queue.poll()) != null) {
            var next = candidate.next;
            if (grid.get(next) == null) {
                continue;
            }
            var distance = distances.get(candidate.previous) + 1;
            if (distances.get(next) != null) {
                if (distances.get(next) == distance) {
                    // stop once we reach the point where distances are equal
                    // answer is distances scaled back down to 1x resolution
                    return new Loop(width, height, distance / 2, distances);
                } else {
                    continue;
                }
            }
            distances.put(next, distance);
            queue.add(new Candidate(next.north(), next));
            queue.add(new Candidate(next.south(), next));
            queue.add(new Candidate(next.east(), next));
            queue.add(new Candidate(next.west(), next));
        }
        throw new RuntimeException("no loop");
    }

    class Candidate {
        Point next;
        Point previous;

        Candidate(Point next, Point previous) {
            this.next = next;
            this.previous = previous;
        }
    }

    enum Pipe {
        NONE,
        VERTICAL,
        HORIZONTAL,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH_WEST,
        SOUTH_EAST,
        ALL;

        static Pipe fromSymbol(char symbol) {
            return switch (symbol) {
                case '|' -> VERTICAL;
                case '-' -> HORIZONTAL;
                case 'L' -> NORTH_EAST;
                case 'J' -> NORTH_WEST;
                case '7' -> SOUTH_WEST;
                case 'F' -> SOUTH_EAST;
                case 'S' -> ALL;
                default -> NONE;
            };
        }

        boolean hasNorth() {
            return this == VERTICAL || this == NORTH_EAST || this == NORTH_WEST || this == ALL;
        }

        boolean hasSouth() {
            return this == VERTICAL || this == SOUTH_EAST || this == SOUTH_WEST || this == ALL;
        }

        boolean hasEast() {
            return this == HORIZONTAL || this == NORTH_EAST || this == SOUTH_EAST || this == ALL;
        }

        boolean hasWest() {
            return this == HORIZONTAL || this == NORTH_WEST || this == SOUTH_WEST || this == ALL;
        }
    }
}