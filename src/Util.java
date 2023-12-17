import java.util.Arrays;

public class Util {
    // Borrowed from https://stackoverflow.com/a/40531215
    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }
    public static long lcm(long ...numbers) {
        return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }
}
