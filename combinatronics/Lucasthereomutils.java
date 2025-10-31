public class LucasTheoremUtils {
    private static final int MOD = 1_000_000_007;
    private static long[] fact = new long[MOD]; // Not practical for MOD=1e9+7, so use smaller primes
    private static boolean precomputed = false;
    private static int P = 1000003; // use a smaller prime for demonstration

    private static long modPow(long base, long exp, long mod) {
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (res * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return res;
    }

    private static long modInverse(long a, long mod) {
        return modPow(a, mod - 2, mod);
    }

    public static void precompute(int p) {
        if (precomputed) return;
        fact[0] = 1;
        for (int i = 1; i < p; i++) {
            fact[i] = (fact[i - 1] * i) % p;
        }
        precomputed = true;
    }

    private static long nCrModPsmall(long n, long r, int p) {
        if (r > n) return 0;
        long num = fact[(int) n];
        long denom = (fact[(int) r] * fact[(int) (n - r)]) % p;
        return (num * modInverse(denom, p)) % p;
    }

    public static long nCrLucas(long n, long r, int p) {
        if (!precomputed) precompute(p);
        if (r == 0) return 1;
        long ni = n % p;
        long ri = r % p;
        return (nCrLucas(n / p, r / p, p) * nCrModPsmall(ni, ri, p)) % p;
    }

    public static void main(String[] args) {
        int p = 1000003;
        precompute(p);
        long n = 1_000_000_000_000L, r = 1234567L;
        System.out.println("nCr mod p (Lucas) = " + nCrLucas(n, r, p));
    }
}
