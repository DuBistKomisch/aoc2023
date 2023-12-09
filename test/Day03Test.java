class Day03Test implements DayTest {
    static Day day = new Day03();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..
                """;
    }

    @Override
    public Object getOutput1() {
        return 4361;
    }

    @Override
    public Object getOutput2() {
        return 467835;
    }
}