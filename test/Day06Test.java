class Day06Test implements DayTest {
    static Day day = new Day06();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                Time:      7  15   30
                Distance:  9  40  200
                """;
    }

    @Override
    public Object getOutput1() {
        return 288L;
    }

    @Override
    public Object getOutput2() {
        return 71503L;
    }
}