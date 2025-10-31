import java.util.*;

public class MultinomialUtils {
    private static final int MOD = 1_000_000_007;
    private static final int MAX = 1_000_000;
    private static long[] fact = new long[MAX + 1];
    private static long[] invFact = new long[MAX + 1];
    private static boolean precomputed = false;

    private static long modPow(long base, long exp) {
        long res = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (res * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return res;
    }

    private static void precompute() {
        if (precomputed) return;
        fact[0] = 1;
        for (int i = 1; i <= MAX; i++) fact[i] = (fact[i - 1] * i) % MOD;
        invFact[MAX] = modPow(fact[MAX], MOD - 2);
        for (int i = MAX - 1; i >= 0; i--) invFact[i] = (invFact[i + 1] * (i + 1)) % MOD;
        precomputed = true;
    }

    public static long multinomial(int[] ks) {
        if (!precomputed) precompute();
        int sum = 0;
        for (int k : ks) sum += k;
        long res = fact[sum];
        for (int k : ks) res = (res * invFact[k]) % MOD;
        return res;
    }

    public static void main(String[] args) {
        int[] ks = {2, 3, 4}; // n = 9
        System.out.println("Multinomial(9;2,3,4) mod p = " + multinomial(ks));
    }
}
