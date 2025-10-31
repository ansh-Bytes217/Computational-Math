import java.util.*;
import java.util.function.LongUnaryOperator;

/**
 * MultiplicativeFunctions
 *
 * - Test multiplicativity
 * - Dirichlet convolution
 * - Sample multiplicative functions (phi, mu, sigma) via sieve
 */
public class MultiplicativeFunctions {

    /** test multiplicative: for random coprime pairs up to limit */
    public static boolean testMultiplicative(int N, LongUnaryOperator f) {
        for (int a = 1; a <= N; a++) {
            for (int b = 1; b <= N; b++) {
                if (GCDUtils.gcd(a, b) == 1) {
                    if (f.applyAsLong(a * b) != f.applyAsLong(a) * f.applyAsLong(b)) return false;
                }
            }
        }
        return true;
    }

    /** Dirichlet convolution of f and g up to n */
    public static long[] dirichletConvolution(int n, long[] f, long[] g) {
        long[] h = new long[n + 1];
        for (int i = 1; i <= n; i++) h[i] = 0;
        for (int d = 1; d <= n; d++) {
            for (int k = 1; d * k <= n; k++) {
                h[d * k] += f[d] * g[k];
            }
        }
        return h;
    }

    /** Compute phi[1..n] and sigma (sum of divisors) using sieve-like approach */
    public static long[] phiSieve(int n) {
        long[] phi = new long[n + 1];
        for (int i = 0; i <= n; i++) phi[i] = i;
        for (int p = 2; p <= n; p++) {
            if (phi[p] == p) {
                for (int k = p; k <= n; k += p) {
                    phi[k] -= phi[k] / p;
                }
            }
        }
        return phi;
    }

    public static long[] sigmaSieve(int n) {
        long[] sigma = new long[n + 1];
        for (int i = 0; i <= n; i++) sigma[i] = 0;
        for (int d = 1; d <= n; d++) {
            for (int k = d; k <= n; k += d) sigma[k] += d;
        }
        return sigma;
    }

    public static void main(String[] args) {
        int n = 50;
        long[] phi = phiSieve(n);
        long[] sigma = sigmaSieve(n);

        System.out.print("phi: ");
        for (int i = 1; i <= 10; i++) System.out.print(phi[i] + " ");
        System.out.println();

        System.out.print("sigma: ");
        for (int i = 1; i <= 10; i++) System.out.print(sigma[i] + " ");
        System.out.println();

        // Dirichlet conv example: phi * 1 = id? (phi convolved with 1 gives sum_{d|n} phi(d) = n)
        long[] ones = new long[n + 1];
        Arrays.fill(ones, 1);
        long[] conv = dirichletConvolution(n, phi, ones);
        System.out.print("sum phi(d) over divisors (should equal n): ");
        for (int i = 1; i <= 10; i++) System.out.print(conv[i] + " ");
        System.out.println();
    }
}
