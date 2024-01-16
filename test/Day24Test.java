import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24Test implements DayTest {
    static Day24 day = new Day24();

    @Override
    public Day24 getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                19, 13, 30 @ -2,  1, -2
                18, 19, 22 @ -1, -1, -2
                20, 25, 34 @ -2, -2, -4
                12, 31, 28 @ -1, -2, -1
                20, 19, 15 @  1, -5, -3
                """;
    }

    @Override
    public Object getOutput1() {
        return 2;
    }

    @Override
    public Object getOutput2() {
        return 0;
    }

    @Test
    @Override
    public void part1() {
        assertEquals(getOutput1(), getDay().part1(getSample1(), 7.0, 27.0));
    }
}