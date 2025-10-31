package mathlib;

import java.util.*;

public class EulerFunctions {

    private EulerFunctions() {}

    /**
     * Computes Euler's Totient Function φ(n)
     * Definition: Count of integers in [1, n] that are coprime with n
     * Formula: φ(n) = n * Π(1 - 1/p) for every prime divisor p of n
     * Time Complexity: O(√n)
     */
    public static long phi(long n) {
        long result = n;
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                while (n % p == 0)
                    n /= p;
                result -= result / p;
            }
        }
        if (n > 1)
            result -= result / n;
        return result;
    }

    /**
     * Precomputes φ(i) for all i ≤ n using a modified sieve
     * Time Complexity: O(n log log n)
     */
    public static int[] phiSieve(int n) {
        int[] phi = new int[n + 1];
        for (int i = 0; i <= n; i++)
            phi[i] = i;

        for (int p = 2; p <= n; p++) {
            if (phi[p] == p) { // p is prime
                for (int k = p; k <= n; k += p)
                    phi[k] -= phi[k] / p;
            }
        }
        return phi;
    }

    /**
     * Returns list of numbers ≤ n that are coprime with n
     * (Useful for verifying φ(n) correctness or mathematical experiments)
     */
    public static List<Integer> coprimeList(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (GCDUtils.gcd(i, n) == 1)
                list.add(i);
        }
        return list;
    }

    /**
     * Computes sum of φ(i) for i in [1, n]
     * (Appears in certain number theory identities)
     */
    public static long phiPrefixSum(int n) {
        int[] phi = phiSieve(n);
        long sum = 0;
        for (int i = 1; i <= n; i++)
            sum += phi[i];
        return sum;
    }
}
