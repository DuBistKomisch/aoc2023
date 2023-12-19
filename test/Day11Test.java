class Day11Test implements DayTest {
    static Day day = new Day11();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                ...#......
                .......#..
                #.........
                ..........
                ......#...
                .#........
                .........#
                ..........
                .......#..
                #...#.....
                """;
    }

    @Override
    public Object getOutput1() {
        return 374L;
    }

    @Override
    public Object getOutput2() {
        return 82000210L;
    }
}