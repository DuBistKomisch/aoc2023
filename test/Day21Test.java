import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Test implements DayTest {
    static Day21 day = new Day21();

    @Override
    public Day21 getDay() {
        return day;
    }

    @Override
    public String getSample1() {
        return """
                ...........
                .....###.#.
                .###.##..#.
                ..#.#...#..
                ....#.#....
                .##..S####.
                .##..#...#.
                .......##..
                .##.#.####.
                .##..##.##.
                ...........
                """;
    }

    @Override
    public Object getOutput1() {
        return 16;
    }

    @Override
    public Object getOutput2() {
        return 0;
    }

    @Test
    @Override
    public void part1() {
        assertEquals(getOutput1(), getDay().part1(getSample1(), 6));
    }
}