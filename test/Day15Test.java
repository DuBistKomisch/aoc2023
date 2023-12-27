class Day15Test implements DayTest {
    static Day day = new Day15();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
                """;
    }

    @Override
    public Object getOutput1() {
        return 1320;
    }

    @Override
    public Object getOutput2() {
        return 145;
    }
}