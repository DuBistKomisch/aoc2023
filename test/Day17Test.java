class Day17Test implements DayTest {
    static Day day = new Day17();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                2413432311323
                3215453535623
                3255245654254
                3446585845452
                4546657867536
                1438598798454
                4457876987766
                3637877979653
                4654967986887
                4564679986453
                1224686865563
                2546548887735
                4322674655533
                """;
    }

    @Override
    public Object getOutput1() {
        return 102;
    }

    @Override
    public Object getOutput2() {
        return 94;
    }
}