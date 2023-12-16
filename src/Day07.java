import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day07 implements Day {
    public static void main(String[] args) throws IOException {
        new Day07().run();
    }

    @Override
    public Object part1(String input) {
        var hands = Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> new Hand(line, false))
                .sorted()
                .toList();
        return IntStream.range(0, hands.size())
                .map(i -> (i + 1) * hands.get(i).bid)
                .sum();
    }

    @Override
    public Object part2(String input) {
        var hands = Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> new Hand(line, true))
                .sorted()
                .toList();
        return IntStream.range(0, hands.size())
                .map(i -> (i + 1) * hands.get(i).bid)
                .sum();
    }

    enum Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }

    class Hand implements Comparable<Hand> {
        String hand;
        String comparableHand;
        int bid;
        Type type;

        Hand(String line, boolean jokers) {
            var parts = line.split(" ");
            this.hand = parts[0];
            this.comparableHand = this.hand.chars().mapToObj(card -> cardValue(card, jokers)).collect(Collectors.joining());
            this.bid = Integer.parseInt(parts[1]);
            this.type = getType(hand, jokers);
        }

        static String cardValue(int card, boolean jokers) {
            return switch (card) {
                case 'A' -> "n";
                case 'K' -> "m";
                case 'Q' -> "l";
                case 'J' -> jokers ? "a" : "k";
                case 'T' -> "j";
                case '9' -> "i";
                case '8' -> "h";
                case '7' -> "g";
                case '6' -> "f";
                case '5' -> "e";
                case '4' -> "d";
                case '3' -> "c";
                case '2' -> "b";
                default -> " ";
            };
        }

        static Type getType(String hand, boolean jokers) {
            var frequencies = hand.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            if (jokers) {
                frequencies.remove(Integer.valueOf('J'));
            }
            var counts = frequencies.values().stream().sorted().mapToLong(Long::valueOf).toArray();
            // zero jokers
            if (Arrays.equals(counts, new long[]{5})) {
                return Type.FIVE_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{1, 4})) {
                return Type.FOUR_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{2, 3})) {
                return Type.FULL_HOUSE;
            } else if (Arrays.equals(counts, new long[]{1, 1, 3})) {
                return Type.THREE_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{1, 2, 2})) {
                return Type.TWO_PAIR;
            } else if (Arrays.equals(counts, new long[]{1, 1, 1, 2})) {
                return Type.ONE_PAIR;
            } else if (Arrays.equals(counts, new long[]{1, 1, 1, 1, 1})) {
                return Type.HIGH_CARD;
            }
            // one joker
            if (Arrays.equals(counts, new long[]{4})) {
                return Type.FIVE_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{1, 3})) {
                return Type.FOUR_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{2, 2})) {
                return Type.FULL_HOUSE;
            } else if (Arrays.equals(counts, new long[]{1, 1, 2})) {
                return Type.THREE_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{1, 1, 1, 1})) {
                return Type.ONE_PAIR;
            }
            // two jokers
            if (Arrays.equals(counts, new long[]{3})) {
                return Type.FIVE_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{1, 2})) {
                return Type.FOUR_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{1, 1, 1})) {
                return Type.THREE_OF_A_KIND;
            }
            // three jokers
            if (Arrays.equals(counts, new long[]{2})) {
                return Type.FIVE_OF_A_KIND;
            } else if (Arrays.equals(counts, new long[]{1, 1})) {
                return Type.FOUR_OF_A_KIND;
            }
            // four or five jokers
            return Type.FIVE_OF_A_KIND;
        }

        @Override
        public int compareTo(Hand other) {
            int compareType = this.type.compareTo(other.type);
            if (compareType != 0) {
                return compareType;
            }
            return this.comparableHand.compareTo(other.comparableHand);
        }

        @Override
        public String toString() {
            return "Hand(%s, %d, %d)".formatted(this.hand, this.type, this.bid);
        }
    }
}