public enum Dir {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    static Dir of(int character) {
        return switch (character) {
            case 'U' -> NORTH;
            case 'D' -> SOUTH;
            case 'R' -> EAST;
            case 'L' -> WEST;
            default -> throw new IllegalArgumentException();
        };
    }

    Dir clockwise() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    Dir anticlockwise() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }
}
