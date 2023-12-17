class Day09Test implements DayTest {
    static Day day = new Day09();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
                """;
    }

    @Override
    public Object getOutput1() {
        return 114;
    }

    @Override
    public Object getOutput2() {
        return 2;
    }
}