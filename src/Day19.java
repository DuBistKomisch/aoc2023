import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 implements Day {
    public static void main(String[] args) throws IOException {
        new Day19().run();
    }

    @Override
    public Object part1(String input) {
        var segments = input.split(System.lineSeparator().repeat(2));
        var workflows = toWorkflows(segments[0]);
        var parts = toParts(segments[1]);
        return parts.stream()
                .filter(part -> this.process(workflows, part))
                .mapToLong(Part::rating)
                .sum();
    }

    @Override
    public Object part2(String input) {
        var segments = input.split(System.lineSeparator().repeat(2));
        var workflows = toWorkflows(segments[0]);
        var categoryRanges = new EnumMap<Category, ArrayList<Range>>(Category.class);
        for (var category : Category.values()) {
            if (category == Category.NONE) {
                continue;
            }
            var ranges = new ArrayList<Range>();
            ranges.add(new Range(1, 4000));
            categoryRanges.put(category, ranges);
        }
        for (var rules : workflows.values()) {
            for (var rule : rules) {
                if (rule.category == Category.NONE) {
                    continue;
                }
                var ranges = categoryRanges.get(rule.category);
                var split = rule.condition == Condition.LESS_THAN ? rule.to : rule.to + 1;
                var index = findSplitRange(ranges, split);
                if (index != -1) {
                    var range = ranges.get(index);
                    ranges.remove(index);
                    ranges.add(new Range(range.from, split - range.from));
                    ranges.add(new Range(split, range.from + range.length - split));
                }
            }
        }
        var sum = 0L;
        for (var x : categoryRanges.get(Category.X)) {
            for (var m : categoryRanges.get(Category.M)) {
                for (var a : categoryRanges.get(Category.A)) {
                    for (var s : categoryRanges.get(Category.S)) {
                        if (process(workflows, new Part(x.from, m.from, a.from, s.from))) {
                            sum += x.length * m.length * a.length * s.length;
                        }
                    }
                }
            }
        }
        return sum;
    }

    private Map<String, List<Rule>> toWorkflows(String input) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> {
                    var bracket = line.indexOf("{");
                    var label = line.substring(0, bracket);
                    var rules = Arrays.stream(line.substring(bracket + 1, line.length() - 1).split(","))
                            .map(rule -> {
                                var colon = rule.indexOf(":");
                                if (colon == -1) {
                                    return Rule.unconditional(rule);
                                }
                                var target = rule.substring(colon + 1);
                                var condition = Condition.LESS_THAN;
                                var index = rule.indexOf("<");
                                if (index == -1) {
                                    condition = Condition.GREATER_THAN;
                                    index = rule.indexOf(">");
                                }
                                var category = Category.of(rule.substring(0, index));
                                var to = Long.parseLong(rule.substring(index + 1, colon));
                                return new Rule(category, condition, to, target);
                            })
                            .toList();
                    return new Workflow(label, rules);
                })
                .collect(Collectors.toMap(workflow -> workflow.label, workflow -> workflow.rules));
    }

    private List<Part> toParts(String input) {
        return Arrays.stream(input.split(System.lineSeparator()))
                .map(line -> {
                    var numbers = Pattern.compile("\\d+").matcher(line).results().map(MatchResult::group).toList();
                    return new Part(
                            Long.parseLong(numbers.get(0)),
                            Long.parseLong(numbers.get(1)),
                            Long.parseLong(numbers.get(2)),
                            Long.parseLong(numbers.get(3))
                    );
                })
                .toList();
    }

    private boolean process(Map<String, List<Rule>> workflows, Part part) {
        var workflow = "in";
        while (true) {
            if (workflow.equals("A")) {
                return true;
            }
            if (workflow.equals("R")) {
                return false;
            }
            for (var rule : workflows.get(workflow)) {
                if (rule.satisfiedBy(part)) {
                    workflow = rule.target;
                    break;
                }
            }
        }
    }

    private int findSplitRange(List<Range> ranges, long split) {
        for (int i = 0; i < ranges.size(); i += 1) {
            var range = ranges.get(i);
            if (split > range.from && split < range.from + range.length) {
                return i;
            }
        }
        return -1;
    }

    static class Workflow {
        String label;
        List<Rule> rules;

        Workflow(String label, List<Rule> rules) {
            this.label = label;
            this.rules = rules;
        }
    }

    static class Rule {
        Category category;
        Condition condition;
        long to;
        String target;

        public Rule(Category category, Condition condition, long to, String target) {
            this.category = category;
            this.condition = condition;
            this.to = to;
            this.target = target;
        }

        static Rule unconditional(String target) {
            return new Rule(Category.NONE, Condition.NONE, 0, target);
        }

        boolean satisfiedBy(Part part) {
            var value = part.getValue(category);
            return switch (condition) {
                case NONE -> true;
                case LESS_THAN -> value < to;
                case GREATER_THAN -> value > to;
            };
        }
    }

    enum Category {
        NONE,
        X,
        M,
        A,
        S;

        static Category of(String character) {
            return switch (character) {
                case "x" -> X;
                case "m" -> M;
                case "a" -> A;
                case "s" -> S;
                default -> NONE;
            };
        }
    }

    enum Condition {
        NONE,
        LESS_THAN,
        GREATER_THAN
    }

    static class Part {
        long x;
        long m;
        long a;
        long s;

        public Part(long x, long m, long a, long s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        long getValue(Category category) {
            return switch (category) {
                case X -> x;
                case M -> m;
                case A -> a;
                case S -> s;
                default -> 0;
            };
        }

        long rating() {
            return x + m + a + s;
        }
    }
}