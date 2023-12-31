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
}
