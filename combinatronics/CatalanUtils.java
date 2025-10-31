public class CatalanUtils {
    private static final int MOD = 1_000_000_007;

    private static long modPow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            b >>= 1;
        }
        return res;
    }

    private static long modInverse(long a) {
        return modPow(a, MOD - 2);
    }

    public static long nCr(long n, long r) {
        if (r > n) return 0;
        long num = 1, denom = 1;
        for (long i = 0; i < r; i++) {
            num = (num * (n - i)) % MOD;
            denom = (denom * (i + 1)) % MOD;
        }
        return (num * modInverse(denom)) % MOD;
    }

    public static long catalan(int n) {
        long res = nCr(2L * n, n);
        res = (res * modInverse(n + 1)) % MOD;
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            System.out.println("Catalan(" + i + ") = " + catalan(i));
        }
    }
}
