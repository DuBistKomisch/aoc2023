import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Day12 implements Day {
    public static void main(String[] args) throws IOException {
        new Day12().run();
    }

    @Override
    public Object part1(String input) {
        return solve(input, 1);
    }

    @Override
    public Object part2(String input) {
        return solve(input, 5);
    }

    private long solve(String input, int repeats) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> {
                    var parts = line.split(" ");
                    var conditions = String.join("?", Collections.nCopies(repeats, parts[0]))
                            .chars()
                            .mapToObj(Condition::of)
                            .toArray(Condition[]::new);
                    var groups = Arrays.stream(String.join(",", Collections.nCopies(repeats, parts[1])).split(","))
                            .mapToInt(Integer::valueOf)
                            .toArray();
                    return new Row(conditions, groups);
                })
                .mapToLong(row -> dfs(row, 0, 0))
                .sum();
    }

    private long dfs(Row row, int position, int group) {
        var memoKey = position * 100 + group;
        if (row.memo.containsKey(memoKey)) {
            return row.memo.get(memoKey);
        }
        if (group >= row.groups.length) {
            for (int i = position; i < row.conditions.length; i += 1) {
                if (row.conditions[i] == Condition.DAMAGED) {
                    return 0;
                }
            }
            return 1;
        }
        var size = row.groups[group];
        long sum = 0;
        next: for (int i = position; i <= row.conditions.length - row.minSizes[group]; i += 1) {
            if (i > 0 && row.conditions[i - 1] == Condition.DAMAGED) {
                break;
            }
            for (int j = i; j < i + size; j += 1) {
                if (row.conditions[j] == Condition.OPERATIONAL) {
                    continue next;
                }
            }
            if (i + size < row.conditions.length && row.conditions[i + size] == Condition.DAMAGED) {
                continue;
            }
            sum += dfs(row, i + size + 1, group + 1);
        }
        row.memo.put(memoKey, sum);
        return sum;
    }

    static class Row {
        Condition[] conditions;
        int[] groups;
        int[] minSizes;
        Map<Integer, Long> memo = new HashMap<>();

        public Row(Condition[] conditions, int[] groups) {
            this.conditions = conditions;
            this.groups = groups;
            this.minSizes = new int[this.groups.length];
            this.minSizes[this.minSizes.length - 1] = this.groups[this.groups.length - 1];
            for (int i = this.minSizes.length - 2; i >= 0; i -= 1) {
                this.minSizes[i] = this.minSizes[i + 1] + this.groups[i] + 1;
            }
        }
    }

    enum Condition {
        OPERATIONAL,
        DAMAGED,
        UNKNOWN;

        static Condition of(int character) {
            return switch (character) {
                case '.' -> OPERATIONAL;
                case '#' -> DAMAGED;
                default -> UNKNOWN;
            };
        }

        @Override
        public String toString() {
            return switch (this) {
                case OPERATIONAL -> ".";
                case DAMAGED -> "#";
                case UNKNOWN -> "?";
            };
        }
    }
}