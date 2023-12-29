import java.util.Objects;

public class Point {
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

    Point step(Dir dir) {
        return switch (dir) {
            case NORTH -> north();
            case SOUTH -> south();
            case EAST -> east();
            case WEST -> west();
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
