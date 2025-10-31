import java.math.BigInteger;
import java.util.*;

/**
 * Tonelli-Shanks algorithm to solve x^2 ≡ n (mod p) for odd prime p.
 * Returns a solution x (0 <= x < p) or -1 if none exists.
 */
public class TonelliShanks {

    private static long modPow(long a, long e, long mod) {
        return BigInteger.valueOf(a).modPow(BigInteger.valueOf(e), BigInteger.valueOf(mod)).longValue();
    }

    // Legendre symbol (a|p)
    private static int legendre(long a, long p) {
        long ls = modPow(a, (p - 1) / 2, p);
        if (ls == 1) return 1;
        if (ls == p - 1) return -1;
        return 0;
    }

    public static long tonelliShanks(long n, long p) {
        n %= p;
        if (n == 0) return 0;
        if (p == 2) return n;
        if (legendre(n, p) != 1) return -1; // no solution

        if (p % 4 == 3) {
            return modPow(n, (p + 1) / 4, p);
        }

        long q = p - 1;
        int s = 0;
        while ((q & 1) == 0) { q >>= 1; s++; }

        long z = 2;
        while (legendre(z, p) != -1) z++;

        long c = modPow(z, q, p);
        long x = modPow(n, (q + 1) / 2, p);
        long t = modPow(n, q, p);
        int m = s;

        while (t != 1) {
            int i = 1;
            long tt = (t * t) % p;
            while (tt != 1) {
                tt = (tt * tt) % p;
                i++;
                if (i == m) return -1;
            }
            long b = modPow(c, 1L << (m - i - 1), p);
            x = (x * b) % p;
            c = (b * b) % p;
            t = (t * c) % p;
            m = i;
        }
        return x;
    }

    public static void main(String[] args) {
        long p = 23;
        for (long a = 0; a < p; a++) {
            long x = tonelliShanks(a, p);
            if (x != -1) System.out.println("sqrt(" + a + ") mod " + p + " = " + x);
        }
    }
}
