import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day20 implements Day {
    public static void main(String[] args) throws IOException {
        new Day20().run();
    }

    @Override
    public Object part1(String input) {
        var modules = buildModules(input);
        var lowCount = 0;
        var highCount = 0;
        var queue = new ArrayDeque<Pulse>();
        Pulse pulse;
        for (int i = 0; i < 1000; i += 1) {
            queue.add(new Pulse("button", false, "broadcaster"));
            while ((pulse = queue.poll()) != null) {
                if (pulse.high) {
                    highCount += 1;
                } else {
                    lowCount += 1;
                }
                var toModule = modules.get(pulse.to);
                if (toModule != null) {
                    queue.addAll(toModule.receive(pulse.from, pulse.high));
                }
            }
        }
        return lowCount * highCount;
    }

    @Override
    public Object part2(String input) {
        var modules = buildModules(input);
        var buttonCount = 0;
        var queue = new ArrayDeque<Pulse>();
        Pulse pulse;
        // find inputs to conjunction module before output,
        // and then cycle length for each input
        var inputs = modules.values().stream()
                .filter(module -> !modules.containsKey(module.getDestinations()[0]))
                .filter(module -> module instanceof Conjunction)
                .map(module -> (Conjunction) module)
                .findFirst().orElseThrow()
                .inputs.keySet().stream()
                .collect(Collectors.toMap(key -> key, key -> 0));
        while (inputs.containsValue(0)) {
            buttonCount += 1;
            queue.add(new Pulse("button", false, "broadcaster"));
            while ((pulse = queue.poll()) != null) {
                if (inputs.containsKey(pulse.from) && pulse.high && inputs.get(pulse.from) == 0) {
                    inputs.put(pulse.from, buttonCount);
                }
                var toModule = modules.get(pulse.to);
                if (toModule != null) {
                    queue.addAll(toModule.receive(pulse.from, pulse.high));
                }
            }
        }
        return Util.lcm(inputs.values().stream().mapToLong(Integer::longValue).toArray());
    }

    private Map<String, Module> buildModules(String input) {
        var lines = input.split(System.lineSeparator());
        Map<String, Module> modules = Arrays.stream(lines)
                .map(line -> {
                    var parts = line.split(" -> ");
                    var name = parts[0].substring(1);
                    var destinations = parts[1].split(", ");
                    if (parts[0].startsWith("%")) {
                        return new FlipFlop(name, destinations);
                    } else if (parts[0].startsWith("&")) {
                        return new Conjunction(name, destinations);
                    } else {
                        return new Broadcaster(parts[0], destinations);
                    }
                })
                .collect(Collectors.toMap(Module::getName, Function.identity()));
        modules.values().forEach(module -> {
            for (var destination : module.getDestinations()) {
                var destinationModule = modules.get(destination);
                if (destinationModule != null) {
                    destinationModule.addSource(module.getName());
                }
            }
        });
        return modules;
    }

    interface Module {
        String getName();
        String[] getDestinations();
        void addSource(String from);
        List<Pulse> receive(String from, boolean high);
    }

    static class Broadcaster implements Module {
        private final String name;
        private final String[] destinations;

        Broadcaster(String name, String[] destinations) {
            this.name = name;
            this.destinations = destinations;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String[] getDestinations() {
            return destinations;
        }

        public void addSource(String from) {
            // do nothing
        }

        public List<Pulse> receive(String from, boolean high) {
            return Arrays.stream(destinations)
                    .map(destination -> new Pulse(name, high, destination))
                    .toList();
        }
    }

    static class FlipFlop extends Broadcaster {
        boolean on;

        FlipFlop(String name, String[] destinations) {
            super(name, destinations);
        }

        @Override
        public List<Pulse> receive(String from, boolean high) {
            if (!high) {
                on = !on;
                return super.receive(null, on);
            }
            return Collections.emptyList();
        }
    }

    static class Conjunction extends Broadcaster {
        HashMap<String, Boolean> inputs = new HashMap<>();

        Conjunction(String name, String[] destinations) {
            super(name, destinations);
        }

        @Override
        public void addSource(String from) {
            inputs.put(from, false);
        }

        @Override
        public List<Pulse> receive(String from, boolean high) {
            inputs.put(from, high);
            boolean output = !inputs.values().stream().allMatch(input -> input);
            return super.receive(from, output);
        }
    }

    static class Pulse {
        String from;
        boolean high;
        String to;

        public Pulse(String from, boolean high, String to) {
            this.from = from;
            this.high = high;
            this.to = to;
        }
    }
}