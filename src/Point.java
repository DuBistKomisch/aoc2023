import java.util.Objects;

public class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point north() {
        return north(1);
    }

    Point north(int distance) {
        return new Point(x, y - distance);
    }

    Point south() {
        return south(1);
    }

    Point south(int distance) {
        return new Point(x, y + distance);
    }

    Point east() {
        return east(1);
    }

    Point east(int distance) {
        return new Point(x + distance, y);
    }

    Point west() {
        return west(1);
    }

    Point west(int distance) {
        return new Point(x - distance, y);
    }

    Point step(Dir dir) {
        return step(dir, 1);
    }

    Point step(Dir dir, int distance) {
        return switch (dir) {
            case NORTH -> north(distance);
            case SOUTH -> south(distance);
            case EAST -> east(distance);
            case WEST -> west(distance);
        };
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
