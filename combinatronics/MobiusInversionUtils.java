import java.util.*;

/**
 * MobiusInversionUtils
 *
 * - mobiusSieve(n) -> int[] mu for 1..n
 * - mobiusPrefix(n) -> prefix sums if needed
 * - mobiusInvertOnDivisors: given F(n)=sum_{d|n} f(d), computes f(n)
 *
 * Complexity: sieve O(n), inversion per n uses O(divisors(n)).
 */
public class MobiusInversionUtils {

    /** Linear sieve to compute mu[1..n] and primes */
    public static int[] mobiusSieve(int n) {
        int[] mu = new int[n + 1];
        boolean[] isComposite = new boolean[n + 1];
        int[] primes = new int[n + 1];
        int pc = 0;
        mu[1] = 1;
        for (int i = 2; i <= n; i++) mu[i] = 0;

        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes[pc++] = i;
                mu[i] = -1;
            }
            for (int j = 0; j < pc; j++) {
                int p = primes[j];
                long v = 1L * p * i;
                if (v > n) break;
                isComposite[(int) v] = true;
                if (i % p == 0) {
                    mu[(int) v] = 0;
                    break;
                } else {
                    mu[(int) v] = -mu[i];
                }
            }
        }
        // set mu[1] already 1
        return mu;
    }

    /** Given F(n) = sum_{d|n} f(d), compute f(n) by Möbius inversion:
     * f(n) = sum_{d|n} mu[d] * F(n/d)
     */
    public static long mobiusInvertSingle(int n, long[] F, int[] mu) {
        long res = 0;
        for (int d = 1; d * d <= n; d++) {
            if (n % d == 0) {
                int d1 = d;
                int d2 = n / d;
                res += mu[d1] * F[n / d1];
                if (d1 != d2) res += mu[d2] * F[n / d2];
            }
        }
        return res;
    }

    /** transform arrays: compute f[1..n] from F[1..n] using mu array */
    public static long[] mobiusInvertAll(int n, long[] F) {
        int[] mu = mobiusSieve(n);
        long[] f = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            long sum = 0;
            for (int d = 1; d * d <= i; d++) {
                if (i % d == 0) {
                    int d1 = d;
                    int d2 = i / d;
                    sum += mu[d1] * F[i / d1];
                    if (d1 != d2) sum += mu[d2] * F[i / d2];
                }
            }
            f[i] = sum;
        }
        return f;
    }

    public static void main(String[] args) {
        int n = 20;
        int[] mu = mobiusSieve(n);
        System.out.print("mu: ");
        for (int i = 1; i <= n; i++) System.out.print(mu[i] + " ");
        System.out.println();

        // Example: let f(n) = 1 for all n, then F(n)=sum_{d|n}1 = tau(n)
        long[] F = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            long cnt = 0;
            for (int d = 1; d * d <= i; d++) {
                if (i % d == 0) {
                    cnt++;
                    if (d * d != i) cnt++;
                }
            }
            F[i] = cnt; // tau(i)
        }
        long[] recovered = mobiusInvertAll(n, F);
        System.out.println("Recovered f (should be all ones):");
        for (int i = 1; i <= n; i++) System.out.print(recovered[i] + " ");
        System.out.println();
    }
}
