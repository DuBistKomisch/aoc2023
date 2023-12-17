import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Day08 implements Day {
    public static void main(String[] args) throws IOException {
        new Day08().run();
    }

    static Pattern pattern = Pattern.compile("\\w{3}");

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());
        var instructions = lines[0].chars().mapToObj(c -> c == 'R').toList();
        var graph = toGraph(lines);

        var current = "AAA";
        var step = 0;
        while (!current.equals("ZZZ")) {
            var instruction = instructions.get(step % instructions.size());
            current = graph.get(current).getNext(instruction);
            step += 1;
        }
        return step;
    }

    @Override
    public Object part2(String input) {
        var lines = input.split(System.lineSeparator());
        var instructions = lines[0].chars().mapToObj(c -> c == 'R').toList();
        var graph = toGraph(lines);

        var starts = graph.keySet().stream().filter(node -> node.endsWith("A")).toList();
        var lengths = starts.stream().mapToLong(current -> {
            var step = 0;
            while (!current.endsWith("Z")) {
                var instruction = instructions.get(step % instructions.size());
                current = graph.get(current).getNext(instruction);
                step += 1;
            }
            return step;
        }).toArray();
        return Util.lcm(lengths);
    }

    class Node {
        String left;
        String right;

        Node(String left, String right) {
            this.left = left;
            this.right = right;
        }

        String getNext(boolean isRight) {
            return isRight ? right : left;
        }

        @Override
        public String toString() {
            return "(%s, %s)".formatted(left, right);
        }
    }

    Map<String, Node> toGraph(String[] lines) {
        var graph = new HashMap<String, Node>();
        Arrays.stream(lines).skip(2).forEach(line -> {
            var nodes = pattern.matcher(line).results()
                    .map(MatchResult::group)
                    .toList();
            graph.put(nodes.get(0), new Node(nodes.get(1), nodes.get(2)));
        });
        return graph;
    }
}