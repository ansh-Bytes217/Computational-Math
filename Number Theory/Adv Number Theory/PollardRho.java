import java.math.BigInteger;
import java.util.*;

/**
 * Pollard's Rho integer factorization (randomized).
 * Returns prime factors (not necessarily sorted). Uses MillerRabin for primality test.
 */
public class PollardRho {

    private static final Random rand = new Random();

    private static long modMul(long a, long b, long mod) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();
    }

    private static long modPow(long a, long e, long mod) {
        return BigInteger.valueOf(a).modPow(BigInteger.valueOf(e), BigInteger.valueOf(mod)).longValue();
    }

    private static long rhoSingle(long n) {
        if (n % 2 == 0) return 2;
        long c = Math.abs(rand.nextLong()) % (n - 1) + 1;
        long x = Math.abs(rand.nextLong()) % n;
        long y = x;
        long d = 1;
        while (d == 1) {
            x = (modMul(x, x, n) + c) % n;
            y = (modMul(y, y, n) + c) % n;
            y = (modMul(y, y, n) + c) % n;
            long diff = x > y ? x - y : y - x;
            d = BigInteger.valueOf(diff).gcd(BigInteger.valueOf(n)).longValue();
            if (d == n) return rhoSingle(n);
        }
        return d;
    }

    public static void factor(long n, List<Long> out) {
        if (n == 1) return;
        if (MillerRabin.isPrime(n)) {
            out.add(n);
            return;
        }
        long d = rhoSingle(n);
        factor(d, out);
        factor(n / d, out);
    }

    public static List<Long> factorize(long n) {
        List<Long> res = new ArrayList<>();
        factor(n, res);
        Collections.sort(res);
        return res;
    }

    public static void main(String[] args) {
        long n = 600851475143L; // example
        System.out.println("Factors of " + n + " = " + factorize(n));
        System.out.println("Factors of 1e12+39 = " + factorize(1_000_000_039L));
    }
}
