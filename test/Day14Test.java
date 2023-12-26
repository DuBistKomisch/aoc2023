class Day14Test implements DayTest {
    static Day day = new Day14();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#....
                """;
    }

    @Override
    public Object getOutput1() {
        return 136;
    }

    @Override
    public Object getOutput2() {
        return 64;
    }
}