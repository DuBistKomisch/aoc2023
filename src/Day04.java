import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class Day04 implements Day {
    public static void main(String[] args) throws IOException {
        new Day04().run();
    }

    @Override
    public Object part1(String input) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .mapToInt(card -> (int) Math.pow(2, matches(card) - 1))
                .sum();
    }

    @Override
    public Object part2(String input) {
        var cards = input.split(System.lineSeparator());
        var instances = new int[cards.length];
        Arrays.fill(instances, 1);
        for (var card = 0; card < cards.length; card++) {
            var matches = matches(cards[card]);
            for (var match = 0; match < matches ; match++) {
                instances[card + match + 1] += instances[card];
            }
        }
        return Arrays.stream(instances).sum();
    }

    private long matches(String card) {
        var lists = card.split(": ")[1].split(" \\| ");
        var winning = toNumbers(lists[0]).collect(Collectors.toSet());
        return toNumbers(lists[1]).filter(winning::contains).count();
    }

    private Stream<Integer> toNumbers(String list) {
        return Arrays.stream(list.split(" "))
                .filter(not(String::isEmpty))
                .map(Integer::valueOf);
    }
}