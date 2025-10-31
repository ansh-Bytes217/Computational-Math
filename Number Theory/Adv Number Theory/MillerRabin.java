import java.util.*;

/**
 * Deterministic Miller-Rabin for 64-bit integers based on tested bases.
 * Use MillerRabin.isPrime(n) to test primality for n < 2^64.
 */
public class MillerRabin {

    private static long modMul(long a, long b, long mod) {
        // Use long with builtin to avoid overflow: use unsigned multiply via BigInteger-like trick.
        // But simplest safe: use Java's built-in BigInteger when needed - however for speed, do this:
        long res = 0;
        a %= mod;
        b %= mod;
        while (b > 0) {
            if ((b & 1) == 1) res = (res + a) % mod;
            a = (a << 1) % mod;
            b >>= 1;
        }
        return res;
    }

    private static long modPow(long a, long e, long mod) {
        long res = 1;
        a %= mod;
        while (e > 0) {
            if ((e & 1) == 1) res = modMul(res, a, mod);
            a = modMul(a, a, mod);
            e >>= 1;
        }
        return res;
    }

    // Deterministic bases for testing 64-bit numbers
    private static final long[] bases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};

    public static boolean isPrime(long n) {
        if (n < 2) return false;
        for (long p : new long[]{2,3,5,7,11,13,17,19,23,29,31,37}) {
            if (n == p) return true;
            if (n % p == 0) return n == p;
        }
        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) {
            d >>= 1; s++;
        }
        for (long a : bases) {
            if (a % n == 0) continue;
            long x = modPow(a, d, n);
            if (x == 1 || x == n - 1) continue;
            boolean composite = true;
            for (int r = 1; r < s; r++) {
                x = modMul(x, x, n);
                if (x == n - 1) { composite = false; break; }
            }
            if (composite) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        long[] tests = {2,3,5,7,11,13,17,19,23,29,31,37, 61, 1_000_000_007L, 1_000_000_009L, 4_294_967_297L};
        for (long t : tests) System.out.println(t + " prime? " + isPrime(t));
    }
}
