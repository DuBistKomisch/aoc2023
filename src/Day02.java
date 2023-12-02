import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Day02 implements Day {
    static final Map<String, Integer> limits = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14);

    public static void main(String[] args) throws IOException {
        new Day02().run();
    }

    @Override
    public Object part1(String input) {
        var lines = input.split(System.lineSeparator());
        var sum = 0;
        nextGame: for (int game = 0; game < lines.length; game++) {
            for (var pull : lines[game].split(": ")[1].split("; ")) {
                for (var pair : pull.split(", ")) {
                    var parts = pair.split(" ");
                    if (Integer.parseInt(parts[0]) > limits.get(parts[1])) {
                        continue nextGame;
                    }
                }
            }
            sum += game + 1;
        }
        return sum;
    }

    @Override
    public Object part2(String input) {
        var sum = 0;
        for (var line : input.split(System.lineSeparator())) {
            var game = new HashMap<String, Integer>();
            for (var pull : line.split(": ")[1].split("; ")) {
                for (var pair : pull.split(", ")) {
                    var parts = pair.split(" ");
                    var colour = parts[1];
                    game.put(colour, Math.max(game.getOrDefault(colour, 0), Integer.parseInt(parts[0])));
                }
            }
            sum += game.get("red") * game.get("green") * game.get("blue");
        }
        return sum;
    }
}