class Day10Test implements DayTest {
    static Day day = new Day10();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...
                """;
    }

    @Override
    public Object getOutput1() {
        return 8;
    }

    @Override
    public Object getOutput2() {
        return 0;
    }
}