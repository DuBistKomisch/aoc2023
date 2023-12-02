class Day01Test implements DayTest {
    static Day day = new Day01();

    @Override
    public Day getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """;
    }

    @Override
    public Object getOutput1() {
        return 142;
    }

    @Override
    public String getSample2() {
        return """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                """;
    }

    @Override
    public Object getOutput2() {
        return 281;
    }
}