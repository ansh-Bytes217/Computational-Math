import java.util.*;

public class CombinatoricsUtils {
    private static final int MOD = 1_000_000_007;
    private static final int MAX = 1_000_000; // adjust as needed
    private static long[] fact = new long[MAX + 1];
    private static long[] invFact = new long[MAX + 1];
    private static boolean precomputed = false;

    // --- Modular exponentiation ---
    private static long modPow(long base, long exp, long mod) {
        long res = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                res = (res * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return res;
    }

    // --- Precompute factorials and inverse factorials ---
    public static void precompute() {
        if (precomputed) return;
        fact[0] = 1;
        for (int i = 1; i <= MAX; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        invFact[MAX] = modPow(fact[MAX], MOD - 2, MOD);
        for (int i = MAX - 1; i >= 0; i--) {
            invFact[i] = (invFact[i + 1] * (i + 1)) % MOD;
        }

        precomputed = true;
    }

    // --- nCr modulo MOD ---
    public static long nCrMod(int n, int r) {
        if (!precomputed) precompute();
        if (r < 0 || r > n) return 0;
        return (((fact[n] * invFact[r]) % MOD) * invFact[n - r]) % MOD;
    }

    // --- Simple nCr (no mod, for small n) ---
    public static long nCr(int n, int r) {
        if (r > n) return 0;
        if (r == 0 || r == n) return 1;
        r = Math.min(r, n - r); // symmetry
        long res = 1;
        for (int i = 0; i < r; i++) {
            res = res * (n - i) / (i + 1);
        }
        return res;
    }

    // --- Pascal’s Triangle construction ---
    public static long[][] buildPascalsTriangle(int n) {
        long[][] C = new long[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j < i; j++) {
                C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) % MOD;
            }
        }
        return C;
    }

    // --- Example Test ---
    public static void main(String[] args) {
        precompute();
        System.out.println("10C3 mod p = " + nCrMod(10, 3));
        System.out.println("10C3 exact = " + nCr(10, 3));

        long[][] pascal = buildPascalsTriangle(5);
        System.out.println("Pascal Triangle up to 5:");
        for (long[] row : pascal)
            System.out.println(Arrays.toString(row));
    }
}
