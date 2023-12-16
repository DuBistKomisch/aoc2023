import java.io.IOException;

public interface Day {
    default void run() throws IOException {
        var input = new String(System.in.readAllBytes());

        long part1start = System.nanoTime();
        var part1 = part1(input);
        System.out.println("Part 1 (in %.3fms):".formatted((System.nanoTime() - part1start) / 1_000_000f));
        System.out.println(part1);

        long part2start = System.nanoTime();
        var part2 = part2(input);
        System.out.println("Part 2 (in %.3fms):".formatted((System.nanoTime() - part2start) / 1_000_000f));
        System.out.println(part2);
    }

    Object part1(String input);

    Object part2(String input);
}
