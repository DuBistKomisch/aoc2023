import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Day01 implements Day {
    public static void main(String[] args) throws IOException {
        new Day01().run();
    }

    @Override
    public Object part1(String input) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> {
                    var digits = line.chars()
                            .filter(Character::isDigit)
                            .map(Character::getNumericValue)
                            .toArray();
                    return digits[0] * 10 + digits[digits.length - 1];
                })
                .mapToInt(Integer::intValue).sum();
    }

    @Override
    public Object part2(String input) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> {
                    // Normally Matcher will skip to after the result to start searching again,
                    // wrapping in (?=(   )). uses a lookahead and then match any one character,
                    // which forces it to proceed one at a time and find overlaps.
                    var digits = Pattern.compile("(?=(\\d|one|two|three|four|five|six|seven|eight|nine)).")
                            .matcher(line)
                            .results()
                            .map(result -> result.group(1))
                            .mapToInt(this::tokenToDigit)
                            .toArray();
                    return digits[0] * 10 + digits[digits.length - 1];
                })
                .mapToInt(Integer::intValue).sum();
    }

    private int tokenToDigit(String token) {
        return switch (token) {
            case "1", "one" -> 1;
            case "2", "two" -> 2;
            case "3", "three" -> 3;
            case "4", "four" -> 4;
            case "5", "five" -> 5;
            case "6", "six" -> 6;
            case "7", "seven" -> 7;
            case "8", "eight" -> 8;
            case "9", "nine" -> 9;
            default -> throw new IllegalArgumentException("Invalid token: " + token);
        };
    }
}