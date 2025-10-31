import java.util.*;

/**
 * TotientSumUtils
 *
 * - phiSieve(n) + prefix sum to compute sum_{i=1..n} phi(i)
 * - For very large n, mention & outline the "Harmonic / divide-and-conquer" method (not implemented here).
 */
public class TotientSumUtils {

    public static long[] phiSieve(int n) {
        long[] phi = new long[n + 1];
        for (int i = 0; i <= n; i++) phi[i] = i;
        for (int p = 2; p <= n; p++) {
            if (phi[p] == p) {
                for (int k = p; k <= n; k += p) phi[k] -= phi[k] / p;
            }
        }
        return phi;
    }

    public static long totientSum(int n) {
        long[] phi = phiSieve(n);
        long sum = 0;
        for (int i = 1; i <= n; i++) sum += phi[i];
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("Sum phi(1..10) = " + totientSum(10)); // 1+1+2+2+4+2+6+4+6+4 = 32
        System.out.println("Sum phi(1..100) = " + totientSum(100));
    }
}
