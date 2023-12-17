class Day08Test implements DayTest {
    static Day day = new Day08();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                LLR
                                
                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ)
                """;
    }

    @Override
    public Object getOutput1() {
        return 6;
    }

    @Override
    public String getSample2() {
        return """
                LR
                                
                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)
                """;
    }

    @Override
    public Object getOutput2() {
        return 6;
    }
}