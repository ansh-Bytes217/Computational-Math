public class StarsAndBarsUtils {
    private static final int MOD = 1_000_000_007;

    public static long modPow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            b >>= 1;
        }
        return res;
    }

    public static long modInverse(long a) {
        return modPow(a, MOD - 2);
    }

    public static long nCr(long n, long r) {
        if (r < 0 || r > n) return 0;
        long num = 1, denom = 1;
        for (long i = 0; i < r; i++) {
            num = (num * (n - i)) % MOD;
            denom = (denom * (i + 1)) % MOD;
        }
        return (num * modInverse(denom)) % MOD;
    }

    public static long starsAndBars(int n, int k) {
        return nCr(n + k - 1, k - 1);
    }

    public static void main(String[] args) {
        System.out.println("Ways to distribute 10 identical balls into 4 boxes: " + starsAndBars(10, 4));
    }
}
