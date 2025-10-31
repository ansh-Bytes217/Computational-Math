import java.util.*;

/**
 * CombinatoricsUtils - factorials, inverse factorials and nCr under prime modulus.
 *
 * Usage:
 *   CombinatoricsUtils.init(maxN, MOD);
 *   long comb = CombinatoricsUtils.nCr(n, r);
 *
 * Also supports Lucas theorem: nCrLucas(n, r, p) (p must be prime)
 */
public class CombinatoricsUtils {

    private static int MAXN = 0;
    private static long MOD = 1;
    private static long[] fact = null;
    private static long[] invFact = null;

    private CombinatoricsUtils() {}

    /**
     * Initialize factorials up to maxN modulo mod (mod must be prime for inverses to work via Fermat).
     */
    public static void init(int maxN, long mod) {
        MAXN = maxN;
        MOD = mod;
        fact = new long[MAXN + 1];
        invFact = new long[MAXN + 1];
        fact[0] = 1;
        for (int i = 1; i <= MAXN; i++) fact[i] = (fact[i - 1] * i) % MOD;
        invFact[MAXN] = ModularArithmetic.power(fact[MAXN], MOD - 2, MOD); // Fermat inverse
        for (int i = MAXN - 1; i >= 0; i--) invFact[i] = (invFact[i + 1] * (i + 1)) % MOD;
    }

    /**
     * Compute nCr modulo MOD where MOD is prime and 0 <= n, r <= MAXN
     */
    public static long nCr(int n, int r) {
        if (r < 0 || r > n) return 0;
        if (n > MAXN) throw new IllegalArgumentException("n > MAXN: reinit with larger MAXN");
        return (((fact[n] * invFact[r]) % MOD) * invFact[n - r]) % MOD;
    }

    /**
     * Naive nCr (for verification / small n)
     */
    public static long nCrNaive(int n, int r) {
        if (r < 0 || r > n) return 0;
        long num = 1, den = 1;
        for (int i = 0; i < r; i++) {
            num *= (n - i);
            den *= (i + 1);
        }
        return num / den;
    }

    /**
     * Lucas Theorem: compute C(n, r) mod p where p is prime.
     * Splits n and r into base-p digits and multiplies corresponding small binomials.
     * NOTE: requires precomputed factorials modulo p up to p-1 (init with MAXN >= p-1 and MOD = p).
     */
    public static long nCrLucas(long n, long r, int p) {
        if (r < 0 || r > n) return 0;
        long res = 1;
        while (n > 0 || r > 0) {
            int ni = (int) (n % p);
            int ri = (int) (r % p);
            if (ri > ni) return 0;
            res = (res * nCr(ni, ri)) % p; // requires init(MAXN >= p-1, MOD = p)
            n /= p;
            r /= p;
        }
        return res;
    }

    // Example usage
    public static void main(String[] args) {
        // Example: compute C(10,3) mod 1_000_000_007
        int maxN = 1000000;
        long mod = 1_000_000_007L;
        init(1000, mod); // init small for demo; increase as needed
        System.out.println("C(10,3) mod MOD = " + nCr(10, 3)); // 120

        // Lucas example with p = 7 (small prime). Ensure init(MAXN >= p-1, MOD = p)
        init(6, 7); // p-1 = 6
        long bigN = 1000000000L;
        long bigR = 123456789L;
        long lucas = nCrLucas(bigN, bigR, 7);
        System.out.println("Lucas C(n,r) mod 7 = " + lucas);
    }
}
