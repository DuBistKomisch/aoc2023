import java.io.IOException;
import java.util.*;

public class Day16 implements Day {
    public static void main(String[] args) throws IOException {
        new Day16().run();
    }

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());
        var height = lines.length;
        var width = lines[0].length();
        var grid = buildGrid(lines, height, width);
        return getEnergisedTiles(grid, new Move(0, 0, Dir.EAST));
    }

    @Override
    public Object part2(String input) {
        var lines = input.split(System.lineSeparator());
        var height = lines.length;
        var width = lines[0].length();
        var grid = buildGrid(lines, height, width);
        var candidates = new ArrayList<Integer>();
        for (int x = 0; x < width; x += 1) {
            candidates.add(getEnergisedTiles(grid, new Move(x, 0, Dir.SOUTH)));
            candidates.add(getEnergisedTiles(grid, new Move(x, height - 1, Dir.NORTH)));
        }
        for (int y = 0; y < height; y += 1) {
            candidates.add(getEnergisedTiles(grid, new Move(0, y, Dir.EAST)));
            candidates.add(getEnergisedTiles(grid, new Move(width - 1, y, Dir.WEST)));
        }
        return candidates.stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    private Map<Point, Contents> buildGrid(String[] lines, int height, int width) {
        var grid = new HashMap<Point, Contents>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid.put(new Point(x, y), Contents.of(lines[y].charAt(x)));
            }
        }
        return grid;
    }

    private int getEnergisedTiles(Map<Point, Contents> grid, Move start) {
        var energised = new HashSet<Point>();
        var seen = new HashSet<Move>();
        var queue = new ArrayDeque<Move>();
        queue.add(start);
        Move move;
        while ((move = queue.poll()) != null) {
            var contents = grid.get(move.point);
            if (contents == null) {
                continue;
            }
            if (seen.contains(move)) {
                continue;
            }
            seen.add(move);
            energised.add(move.point);
            switch (contents) {
                case EMPTY -> queue.add(move.step());
                case SW_NE_MIRROR -> {
                    var dir = switch (move.dir) {
                        case NORTH -> Dir.EAST;
                        case SOUTH -> Dir.WEST;
                        case EAST -> Dir.NORTH;
                        case WEST -> Dir.SOUTH;
                    };
                    queue.add(move.turn(dir));
                }
                case NW_SE_MIRROR -> {
                    var dir = switch (move.dir) {
                        case NORTH -> Dir.WEST;
                        case SOUTH -> Dir.EAST;
                        case EAST -> Dir.SOUTH;
                        case WEST -> Dir.NORTH;
                    };
                    queue.add(move.turn(dir));
                }
                case VERTICAL_SPLITTER -> {
                    if (move.dir == Dir.EAST || move.dir == Dir.WEST) {
                        queue.add(move.turn(Dir.NORTH));
                        queue.add(move.turn(Dir.SOUTH));
                    } else {
                        queue.add(move.step());
                    }
                }
                case HORIZONTAL_SPLITTER -> {
                    if (move.dir == Dir.NORTH || move.dir == Dir.SOUTH) {
                        queue.add(move.turn(Dir.EAST));
                        queue.add(move.turn(Dir.WEST));
                    } else {
                        queue.add(move.step());
                    }
                }
            }
        }
        return energised.size();
    }

    static class Move {
        Point point;
        Dir dir;

        public Move(Point point, Dir dir) {
            this.point = point;
            this.dir = dir;
        }

        public Move(int x, int y, Dir dir) {
            this.point = new Point(x, y);
            this.dir = dir;
        }

        Move step() {
            return new Move(point.step(dir), dir);
        }

        Move turn(Dir dir) {
            return new Move(point.step(dir), dir);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Move move = (Move) o;
            return Objects.equals(point, move.point) && dir == move.dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, dir);
        }
    }

    enum Contents {
        EMPTY,
        SW_NE_MIRROR,
        NW_SE_MIRROR,
        VERTICAL_SPLITTER,
        HORIZONTAL_SPLITTER;

        static Contents of(int character) {
            return switch (character) {
                case '/' -> SW_NE_MIRROR;
                case '\\' -> NW_SE_MIRROR;
                case '|' -> VERTICAL_SPLITTER;
                case '-' -> HORIZONTAL_SPLITTER;
                default -> EMPTY;
            };
        }
    }
}