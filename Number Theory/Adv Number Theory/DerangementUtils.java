import java.util.*;

public class DerangementUtils {
    // exact derangement (may overflow for large n) using long; use BigInteger if needed
    public static long derangement(int n) {
        if (n == 0) return 1;
        if (n == 1) return 0;
        long a = 1, b = 0; // D0=1, D1=0
        for (int i = 2; i <= n; i++) {
            long c = (i - 1) * (a + b);
            a = b;
            b = c;
        }
        return b;
    }

    // derangement modulo mod
    public static long derangementMod(int n, long mod) {
        if (n == 0) return 1 % mod;
        if (n == 1) return 0;
        long a = 1 % mod, b = 0;
        for (int i = 2; i <= n; i++) {
            long c = ((i - 1) * ((a + b) % mod)) % mod;
            a = b; b = c;
        }
        return b;
    }

    // Generate one derangement of [1..n] using simple algorithm (random shuffle until derangement found) - practical for small n
    public static int[] generateDerangement(int n) {
        Random rnd = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        for (;;) {
            // Fisher-Yates shuffle
            for (int i = n - 1; i > 0; i--) {
                int j = rnd.nextInt(i + 1);
                int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            }
            boolean ok = true;
            for (int i = 0; i < n; i++) if (arr[i] == i + 1) { ok = false; break; }
            if (ok) return arr;
        }
    }

    public static void main(String[] args) {
        for (int n = 0; n <= 10; n++) System.out.println("!"+n+" = "+derangement(n));
        System.out.println("Derangement mod 1e9+7 for 20 = " + derangementMod(20, 1_000_000_007L));
        int[] d = generateDerangement(6);
        System.out.println("A derangement of 1..6: " + Arrays.toString(d));
    }
}
