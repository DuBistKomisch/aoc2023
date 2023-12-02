import java.io.IOException;

public interface Day {
    default void run() throws IOException {
        var input = new String(System.in.readAllBytes());
        System.out.println("Part 1:");
        System.out.println(part1(input));
        System.out.println("Part 2:");
        System.out.println(part2(input));
    }
    Object part1(String input);
    Object part2(String input);
}
