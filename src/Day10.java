import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Objects;

public class Day10 implements Day {
    public static void main(String[] args) throws IOException {
        new Day10().run();
    }

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());
        var pipes = new HashMap<Point, Pipe>();
        Point start = new Point(-1, -1);
        for (var y = 0; y < lines.length; y += 1) {
            for (var x = 0; x < lines[y].length(); x += 1) {
                var point = new Point(x, y);
                var symbol = lines[y].charAt(x);
                if (symbol == 'S') {
                    start = point;
                }
                pipes.put(point, Pipe.fromSymbol(symbol));
            }
        }
        var distances = new HashMap<Point, Integer>();
        distances.put(start, 0);
        var queue = new ArrayDeque<Candidate>();
        if (pipes.get(start.north()).hasSouth()) {
            queue.add(new Candidate(start.north(), start));
        }
        if (pipes.get(start.south()).hasNorth()) {
            queue.add(new Candidate(start.south(), start));
        }
        if (pipes.get(start.east()).hasWest()) {
            queue.add(new Candidate(start.east(), start));
        }
        if (pipes.get(start.west()).hasEast()) {
            queue.add(new Candidate(start.west(), start));
        }
        Candidate candidate;
        while ((candidate = queue.poll()) != null) {
            var next = candidate.next;
            var pipe = pipes.get(next);
            if (pipe == null) {
                continue;
            }
            var distance = distances.get(candidate.previous) + 1;
            if (distances.get(next) != null) {
                if (distances.get(next) == distance) {
                    return distance;
                } else {
                    continue;
                }
            }
            distances.put(next, distance);
            if (pipe.hasNorth() && pipes.get(next.north()).hasSouth()) {
                queue.add(new Candidate(next.north(), next));
            }
            if (pipe.hasSouth() && pipes.get(next.south()).hasNorth()) {
                queue.add(new Candidate(next.south(), next));
            }
            if (pipe.hasEast() && pipes.get(next.east()).hasWest()) {
                queue.add(new Candidate(next.east(), next));
            }
            if (pipe.hasWest() && pipes.get(next.west()).hasEast()) {
                queue.add(new Candidate(next.west(), next));
            }
        }
        return 0;
    }

    @Override
    public Object part2(String input) {
        return 0;
    }

    class Candidate {
        Point next;
        Point previous;

        Candidate(Point next, Point previous) {
            this.next = next;
            this.previous = previous;
        }
    }

    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point north() {
            return new Point(x, y - 1);
        }

        Point south() {
            return new Point(x, y + 1);
        }

        Point east() {
            return new Point(x + 1, y);
        }

        Point west() {
            return new Point(x - 1, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(%d, %d)".formatted(x, y);
        }
    }

    enum Pipe {
        NONE,
        VERTICAL,
        HORIZONTAL,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH_WEST,
        SOUTH_EAST;

        static Pipe fromSymbol(char symbol) {
            return switch (symbol) {
                case '|' -> VERTICAL;
                case '-' -> HORIZONTAL;
                case 'L' -> NORTH_EAST;
                case 'J' -> NORTH_WEST;
                case '7' -> SOUTH_WEST;
                case 'F' -> SOUTH_EAST;
                default -> NONE;
            };
        }

        boolean hasNorth() {
            return this == VERTICAL || this == NORTH_EAST || this == NORTH_WEST;
        }

        boolean hasSouth() {
            return this == VERTICAL || this == SOUTH_EAST || this == SOUTH_WEST;
        }

        boolean hasEast() {
            return this == HORIZONTAL || this == NORTH_EAST || this == SOUTH_EAST;
        }

        boolean hasWest() {
            return this == HORIZONTAL || this == NORTH_WEST || this == SOUTH_WEST;
        }
    }
}