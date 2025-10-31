import java.math.BigInteger;
import java.util.*;

/**
 * Baby-step Giant-step algorithm to solve discrete log:
 * find x such that a^x ≡ b (mod mod). Returns x or -1 if none.
 * Works when gcd(a, mod) = 1 (a is generator of multiplicative subgroup).
 */
public class BabyStepGiantStep {

    private static long modPow(long a, long e, long mod) {
        return BigInteger.valueOf(a).modPow(BigInteger.valueOf(e), BigInteger.valueOf(mod)).longValue();
    }

    public static long discreteLog(long a, long b, long mod) {
        a %= mod; b %= mod;
        if (b == 1) return 0;
        long cnt = 0;
        long t = 1;
        long g;
        while ((g = BigInteger.valueOf(a).gcd(BigInteger.valueOf(mod)).longValue()) > 1) {
            if (b % g != 0) return -1;
            mod /= g;
            b /= g;
            t = (t * (a / g)) % mod;
            cnt++;
            if (t == b) return cnt;
        }
        long m = (long)Math.ceil(Math.sqrt(mod));
        Map<Long, Long> vals = new HashMap<>();
        long base = 1;
        for (long j = 0; j < m; j++) {
            vals.put(base * b % mod, j);
            base = modPow(a, j + 1, mod);
        }

        long factor = modPow(a, m, mod);
        long gamma = t;
        for (long i = 1; i <= m + 1; i++) {
            gamma = (gamma * factor) % mod;
            if (vals.containsKey(gamma)) {
                long ans = i * m - vals.get(gamma) + cnt;
                return ans;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        // Example: find x s.t. 2^x ≡ 5 (mod 13). Solution x=4 since 2^4=16≡3 not 5 — ok different example:
        System.out.println("Discrete log 2^? ≡ 5 mod 13 -> " + discreteLog(2,5,13));
    }
}
