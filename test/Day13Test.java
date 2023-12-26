class Day13Test implements DayTest {
    static Day day = new Day13();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
                                
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
                """;
    }

    @Override
    public Object getOutput1() {
        return 405;
    }

    @Override
    public Object getOutput2() {
        return 400;
    }
}