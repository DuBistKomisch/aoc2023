import java.io.IOException;
import java.util.*;

public class Day25 implements Day {
    public static void main(String[] args) throws IOException {
        new Day25().run();
    }

    @Override
    public Object part1(String input) {
        var nodes = new HashSet<String>();
        var pairs = new HashSet<Pair>();
        var edges = new HashMap<String, HashSet<String>>();
        for (var line : input.split(System.lineSeparator())) {
            var left = line.substring(0, 3);
            var rights = line.substring(5).split(" ");
            nodes.add(left);
            for (var right : rights) {
                nodes.add(right);
                pairs.add(new Pair(left, right));
                edges.computeIfAbsent(left, key -> new HashSet<>()).add(right);
                edges.computeIfAbsent(right, key -> new HashSet<>()).add(left);
            }
        }
        for (int i = 0; i < 3; i += 1) {
            var flows = findFlows(nodes, edges);
            var highestFlowPair = flows.entrySet().stream().max(Comparator.comparingInt(Map.Entry<Pair, Integer>::getValue)).orElseThrow().getKey();
            pairs.remove(highestFlowPair);
            edges.get(highestFlowPair.a).remove(highestFlowPair.b);
            edges.get(highestFlowPair.b).remove(highestFlowPair.a);
        }
        var part = flood(nodes, edges);
        return part * (nodes.size() - part);
    }

    @Override
    public Object part2(String input) {
        return 0;
    }

    private Map<Pair, Integer> findFlows(Set<String> nodes, Map<String, HashSet<String>> edges) {
        var flows = new HashMap<Pair, Integer>();
        for (var node : nodes) {
            var seen = new HashSet<String>();
            var queue = new ArrayDeque<Candidate>();
            queue.add(new Candidate(node, Collections.emptyList()));
            Candidate candidate;
            while ((candidate = queue.poll()) != null) {
                if (seen.contains(candidate.node)) {
                    continue;
                }
                seen.add(candidate.node);
                var path = new ArrayList<>(candidate.path);
                path.add(candidate.node);
                for (var next : edges.get(candidate.node)) {
                    queue.add(new Candidate(next, path));
                }
                for (var i = 0; i < path.size() - 1; i += 1) {
                    var pair = new Pair(path.get(i), path.get(i + 1));
                    flows.put(pair, flows.getOrDefault(pair, 0) + 1);
                }
            }
        }
        return flows;
    }

    private int flood(Set<String> nodes, Map<String, HashSet<String>> edges) {
        var seen = new HashSet<String>();
        var queue = new ArrayDeque<String>();
        queue.add(nodes.stream().findFirst().orElseThrow());
        String node;
        while ((node = queue.poll()) != null) {
            if (seen.contains(node)) {
                continue;
            }
            seen.add(node);
            queue.addAll(edges.get(node));
        }
        return seen.size();
    }

    static class Candidate {
        String node;
        List<String> path;

        public Candidate(String node, List<String> path) {
            this.node = node;
            this.path = path;
        }
    }

    static class Pair {
        String a;
        String b;

        public Pair(String a, String b) {
            if (a.compareTo(b) < 0) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

        @Override
        public String toString() {
            return a + "/" + b;
        }
    }
}