class Day22Test implements DayTest {
    static Day day = new Day22();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                1,0,1~1,2,1
                0,0,2~2,0,2
                0,2,3~2,2,3
                0,0,4~0,2,4
                2,0,5~2,2,5
                0,1,6~2,1,6
                1,1,8~1,1,9
                """;
    }

    @Override
    public Object getOutput1() {
        return 5L;
    }

    @Override
    public Object getOutput2() {
        return 7;
    }
}