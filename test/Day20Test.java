class Day20Test implements DayTest {
    static Day day = new Day20();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                broadcaster -> a
                %a -> inv, con
                &inv -> b
                %b -> con
                &con -> output
                """;
    }

    @Override
    public Object getOutput1() {
        return 11687500;
    }

    @Override
    public Object getOutput2() {
        return 1L;
    }
}