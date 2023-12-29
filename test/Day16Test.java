class Day16Test implements DayTest {
    static Day day = new Day16();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                .|...\\....
                |.-.\\.....
                .....|-...
                ........|.
                ..........
                .........\\
                ..../.\\\\..
                .-.-/..|..
                .|....-|.\\
                ..//.|....
                """;
    }

    @Override
    public Object getOutput1() {
        return 46;
    }

    @Override
    public Object getOutput2() {
        return 51;
    }
}