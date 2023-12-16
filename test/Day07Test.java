class Day07Test implements DayTest {
    static Day day = new Day07();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483
                """;
    }

    @Override
    public Object getOutput1() {
        return 6440;
    }

    @Override
    public Object getOutput2() {
        return 5905;
    }
}