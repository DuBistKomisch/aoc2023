class Day12Test implements DayTest {
    static Day day = new Day12();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                ???.### 1,1,3
                .??..??...?##. 1,1,3
                ?#?#?#?#?#?#?#? 1,3,1,6
                ????.#...#... 4,1,1
                ????.######..#####. 1,6,5
                ?###???????? 3,2,1
                """;
    }

    @Override
    public Object getOutput1() {
        return 21L;
    }

    @Override
    public Object getOutput2() {
        return 525152L;
    }
}