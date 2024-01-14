import java.io.IOException;
import java.util.*;

public class Day23 implements Day {
    public static void main(String[] args) throws IOException {
        new Day23().run();
    }

    @Override
    public Object part1(String input) {
        var hike = buildHike(input, true);
        dfs(hike, hike.start, 0);
        return hike.longest;
    }

    @Override
    public Object part2(String input) {
        var hike = buildHike(input, false);
        dfs(hike, hike.start, 0);
        return hike.longest;
    }

    private Hike buildHike(String input, boolean slippery) {
        var lines = input.split(System.lineSeparator());
        var grid = new HashMap<Point, Content>();
        for (int y = 0; y < lines.length; y += 1) {
            for (int x = 0; x < lines[y].length(); x += 1) {
                grid.put(new Point(x, y), Content.of(lines[y].charAt(x), slippery));
            }
        }
        var start = new Point(1, 0);
        var end = new Point(lines[0].length() - 2, lines.length - 1);
        return new Hike(grid, start, end);
    }

    private void dfs(Hike hike, Point point, int distance) {
        if (hike.path.contains(point)) {
            return;
        }
        var content = hike.grid.get(point);
        if (content == null || content == Content.FOREST) {
            return;
        }
        if (point.equals(hike.end)) {
            if (distance > hike.longest) {
                System.out.println(" !!! %d".formatted(distance));
                hike.longest = distance;
            }
            return;
        }
        hike.path.add(point);
        if (content == Content.PATH || content == Content.SLOPE_NORTH) {
            dfs(hike, point.north(), distance + 1);
        }
        if (content == Content.PATH || content == Content.SLOPE_SOUTH) {
            dfs(hike, point.south(), distance + 1);
        }
        if (content == Content.PATH || content == Content.SLOPE_EAST) {
            dfs(hike, point.east(), distance + 1);
        }
        if (content == Content.PATH || content == Content.SLOPE_WEST) {
            dfs(hike, point.west(), distance + 1);
        }
        hike.path.remove(point);
    }

    static class Hike {
        Map<Point, Content> grid;
        Set<Point> path = new HashSet<>();
        Point start;
        Point end;
        int longest = 0;

        public Hike(Map<Point, Content> grid, Point start, Point end) {
            this.grid = grid;
            this.start = start;
            this.end = end;
        }
    }

    enum Content {
        PATH,
        FOREST,
        SLOPE_NORTH,
        SLOPE_SOUTH,
        SLOPE_EAST,
        SLOPE_WEST;

        static Content of(int character, boolean slippery) {
            return switch (character) {
                case '.' -> PATH;
                case '#' -> FOREST;
                case '^' -> slippery ? SLOPE_NORTH : PATH;
                case 'v' -> slippery ? SLOPE_SOUTH : PATH;
                case '>' -> slippery ? SLOPE_EAST : PATH;
                case '<' -> slippery ? SLOPE_WEST : PATH;
                default -> throw new IllegalArgumentException();
            };
        }
    }
}