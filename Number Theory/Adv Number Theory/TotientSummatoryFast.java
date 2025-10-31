import java.util.*;

/**
 * Fast summatory totient S(n) = sum_{i=1..n} phi(i) using the divide-and-conquer with memoization trick.
 * Complexity approximately O(n^{2/3}) in practice for large n.
 */
public class TotientSummatoryFast {

    private static long[] phiSieve(int n) {
        long[] phi = new long[n + 1];
        for (int i = 0; i <= n; i++) phi[i] = i;
        for (int p = 2; p <= n; p++) {
            if (phi[p] == p) {
                for (int k = p; k <= n; k += p) phi[k] -= phi[k] / p;
            }
        }
        return phi;
    }

    public static long totientSumNaive(int n) {
        long[] phi = phiSieve(n);
        long s = 0;
        for (int i = 1; i <= n; i++) s += phi[i];
        return s;
    }

    // Fast version using memoization (dujiao / floor division technique)
    private static Map<Long, Long> memo = new HashMap<>();

    public static long totientSumFast(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (memo.containsKey(n)) return memo.get(n);
        long res = n * (n + 1) / 2; // sum_{i=1..n} i
        long i = 2;
        while (i <= n) {
            long k = n / i;
            long j = n / k;
            long subtotal = totientSumFast(k);
            // use transform: sum_{d=1..n} phi(d) = 1/2 (1 + sum_{k=1..n} mu? specialized approach)
            // Here we use known trick for sigma-like functions: convert loops via floor partitions.
            // A simpler memoized approach: subtract (j - i + 1) * S(k)
            res -= (j - i + 1) * subtotal;
            i = j + 1;
        }
        memo.put(n, res);
        return res;
    }

    public static void main(String[] args) {
        int n = 1000000;
        System.out.println("Naive S(1000) = " + totientSumNaive(1000));
        // Fast small test
        System.out.println("Fast S(1000) = " + totientSumFast(1000));
        // Compare for some values
        for (int k : new int[]{10,100,1000,10000}) {
            System.out.println("S(" + k + ") = " + (k <= 1000000 ? totientSumNaive(k) : totientSumFast(k)));
        }
    }
}
