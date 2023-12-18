class Day10Test implements DayTest {
    static Day day = new Day10();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                7-F7-
                .FJ|7
                SJLL7
                |F--J
                LJ.LJ
                """;
    }

    @Override
    public Object getOutput1() {
        return 8;
    }

    @Override
    public String getSample2() {
        return """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L
                """;
    }

    @Override
    public Object getOutput2() {
        return 10;
    }
}