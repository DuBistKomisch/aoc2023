import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface DayTest {
    @Test
    default void part1() {
       assertEquals(getOutput1(), getDay().part1(getSample1()));
    }

    @Test
    default void part2() {
        assertEquals(getOutput2(), getDay().part2(getSample2()));
    }

    Day getDay();
    String getSample1();
    Object getOutput1();
    default String getSample2() {
        return getSample1();
    }
    Object getOutput2();
}
