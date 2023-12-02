import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface DayTest {
    @Test
    default void part1() {
       assertEquals(getDay().part2(getSample1()), getOutput1());
    }

    @Test
    default void part2() {
        assertEquals(getDay().part2(getSample2()), getOutput2());
    }

    Day getDay();
    String getSample1();
    Object getOutput1();
    default String getSample2() {
        return getSample1();
    }
    Object getOutput2();
}
