import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public class Day17 implements Day {
    public static void main(String[] args) throws IOException {
        new Day17().run();
    }

    @Override
    public Object part1(String input) {
        return solve(input,
                state -> state.consecutive < 3,
                state -> true);
    }

    @Override
    public Object part2(String input) {
        return solve(input,
                state -> state.consecutive < 10,
                state -> state.consecutive >= 4);
    }

    private int solve(String input, Predicate<State> canContinue, Predicate<State> canTurn) {
        var lines = input.split(System.lineSeparator());
        var losses = new HashMap<Point, Integer>();
        var height = lines.length;
        var width = lines[0].length();
        for (int y = 0; y < height; y += 1) {
            for (int x = 0; x < width; x += 1) {
                losses.put(new Point(x, y), Integer.parseInt(lines[y].substring(x, x + 1)));
            }
        }
        var start = new Point(0, 0);
        var target = new Point(width - 1, height - 1);
        var distances = new HashMap<State, Integer>();
        var queue = new ArrayDeque<Candidate>();
        queue.add(new Candidate(0, new State(start.east(), Dir.EAST, 1)));
        queue.add(new Candidate(0, new State(start.south(), Dir.SOUTH, 1)));
        Candidate candidate;
        while ((candidate = queue.poll()) != null) {
            var state = candidate.state;
            var loss = losses.get(state.point);
            if (loss == null) {
                continue;
            }
            var distance = candidate.distance + loss;
            var oldDistance = distances.get(state);
            if (oldDistance != null && distance >= oldDistance) {
                continue;
            }
            distances.put(state, distance);
            if (canContinue.test(state)) {
                queue.add(new Candidate(distance, state.step()));
            }
            if (canTurn.test(state)) {
                queue.add(new Candidate(distance, state.clockwise()));
                queue.add(new Candidate(distance, state.anticlockwise()));
            }
        }
        return distances.entrySet().stream()
                .filter(entry -> entry.getKey().point.equals(target) && canTurn.test(entry.getKey()))
                .mapToInt(Map.Entry::getValue)
                .min().orElseThrow();
    }

    static class State {
        Point point;
        Dir dir;
        int consecutive;

        public State(Point point, Dir dir, int consecutive) {
            this.point = point;
            this.dir = dir;
            this.consecutive = consecutive;
        }

        State step() {
            return new State(point.step(dir), dir, consecutive + 1);
        }

        State clockwise() {
            return new State(point.step(dir.clockwise()), dir.clockwise(), 1);
        }

        State anticlockwise() {
            return new State(point.step(dir.anticlockwise()), dir.anticlockwise(), 1);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return consecutive == state.consecutive && Objects.equals(point, state.point) && dir == state.dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, dir, consecutive);
        }
    }

    static class Candidate {
        int distance;
        State state;

        public Candidate(int distance, State state) {
            this.distance = distance;
            this.state = state;
        }
    }
}