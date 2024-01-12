import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 implements Day {
    public static void main(String[] args) throws IOException {
        new Day22().run();
    }

    @Override
    public Object part1(String input) {
        var blocks = buildBlocks(input);
        buildSupportGraph(blocks);
        return blocks.stream()
                .filter(block -> block.supports.stream()
                        .noneMatch(supported -> supported.supportedBy.size() == 1))
                .count();
    }

    @Override
    public Object part2(String input) {
        var blocks = buildBlocks(input);
        buildSupportGraph(blocks);
        return blocks.stream()
                .mapToInt(block -> {
                    var disintegrated = new HashSet<Block>();
                    disintegrated.add(block);
                    var queue = new ArrayDeque<Block>();
                    queue.addAll(block.supports);
                    Block next;
                    while ((next = queue.poll()) != null) {
                        if (disintegrated.containsAll(next.supportedBy)) {
                            disintegrated.add(next);
                            queue.addAll(next.supports);
                        }
                    }
                    return disintegrated.size() - 1;
                })
                .sum();
    }

    private List<Block> buildBlocks(String input) {
        var lines = input.split(System.lineSeparator());
        return IntStream.range(0, lines.length)
                .mapToObj(i -> {
                    var parts = lines[i].split("[,~]");
                    return new Block(
                            i,
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),
                            Integer.parseInt(parts[5])
                    );
                })
                .sorted(Comparator.comparingInt(block -> block.minZ))
                .collect(Collectors.toList());
    }

    private Map<Block, Set<Block>> buildIntersects(List<Block> blocks) {
        var intersects = new HashMap<Block, Set<Block>>();
        for (int i = 0; i < blocks.size(); i += 1) {
            for (int j = i + 1; j < blocks.size(); j += 1) {
                var a = blocks.get(i);
                var b = blocks.get(j);
                if (a.intersectsPlane(b)) {
                    intersects.computeIfAbsent(a, key -> new HashSet<>()).add(b);
                    intersects.computeIfAbsent(b, key -> new HashSet<>()).add(a);
                }
            }
        }
        return intersects;
    }

    private void buildSupportGraph(List<Block> blocks) {
        var intersects = buildIntersects(blocks);
        for (var block : blocks) {
            while (block.minZ > 1) {
                var supporters = intersects.getOrDefault(block, Collections.emptySet()).stream()
                        .filter(other -> other.maxZ == block.minZ - 1)
                        .collect(Collectors.toSet());
                if (!supporters.isEmpty()) {
                    block.supportedBy.addAll(supporters);
                    for (var supporter : supporters) {
                        supporter.supports.add(block);
                    }
                    break;
                }
                block.maxZ -= 1;
                block.minZ -= 1;
            }
        }
    }

    static class Block {
        int id;
        int minX;
        int minY;
        int minZ;
        int maxX;
        int maxY;
        int maxZ;
        Set<Block> supports = new HashSet<>();
        Set<Block> supportedBy = new HashSet<>();

        Block(int id, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.id = id;
            this.minX = Math.min(x1, x2);
            this.minY = Math.min(y1, y2);
            this.minZ = Math.min(z1, z2);
            this.maxX = Math.max(x1, x2);
            this.maxY = Math.max(y1, y2);
            this.maxZ = Math.max(z1, z2);
        }

        private static boolean intersects(int minA, int maxA, int minB, int maxB) {
            return !(maxA < minB || maxB < minA);
        }

        boolean intersectsPlane(Block other) {
            return intersects(this.minX, this.maxX, other.minX, other.maxX)
                    && intersects(this.minY, this.maxY, other.minY, other.maxY);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Block block = (Block) o;
            return id == block.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return id +
                    "{" + minX +
                    "," + minY +
                    "," + minZ +
                    "~" + maxX +
                    "," + maxY +
                    "," + maxZ +
                    '}';
        }
    }
}