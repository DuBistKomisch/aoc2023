import java.io.IOException;
import java.util.Arrays;

public class Day13 implements Day {
    public static void main(String[] args) throws IOException {
        new Day13().run();
    }

    @Override
    public Object part1(String input) {
        return solve(input, 0);
    }

    @Override
    public Object part2(String input) {
        return solve(input, 1);
    }

    private int solve(String input, int target) {
        return Arrays.stream(input.split(System.lineSeparator().repeat(2)))
                .mapToInt(block -> {
                    var grid = block.split(System.lineSeparator());
                    var width = grid[0].length();
                    var height = grid.length;
                    // vertical
                    for (int x = 1; x < width; x += 1) {
                        var diff = 0;
                        for (int y = 0; y < height; y += 1) {
                            for (int i = 0; x - i - 1 >= 0 && x + i < width; i += 1) {
                                if (grid[y].charAt(x - i - 1) != grid[y].charAt(x + i)) {
                                    diff += 1;
                                }
                            }
                        }
                        if (diff == target) {
                            return x;
                        }
                    }
                    // vertical
                    for (int y = 1; y < height; y += 1) {
                        var diff = 0;
                        for (int x = 0; x < width; x += 1) {
                            for (int i = 0; y - i - 1 >= 0 && y + i < height; i += 1) {
                                if (grid[y - i - 1].charAt(x) != grid[y + i].charAt(x)) {
                                    diff += 1;
                                }
                            }
                        }
                        if (diff == target) {
                            return y * 100;
                        }
                    }
                    return 0;
                })
                .sum();
    }
}