import java.util.*;

/**
 * Linear sieve (Euler sieve) computing primes and smallest prime factor (spf).
 * O(n) time.
 */
public class LinearSieveWithSPF {
    public static int[] spf(int n) {
        int[] spf = new int[n + 1];
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (spf[i] == 0) {
                spf[i] = i;
                primes.add(i);
            }
            for (int p : primes) {
                long v = 1L * p * i;
                if (v > n) break;
                spf[(int)v] = p;
                if (i % p == 0) break;
            }
        }
        return spf;
    }

    public static List<Integer> primesUpTo(int n) {
        int[] spf = spf(n);
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) if (spf[i] == i) primes.add(i);
        return primes;
    }

    public static Map<Integer,Integer> factorFromSPF(int x, int[] spf) {
        Map<Integer,Integer> map = new LinkedHashMap<>();
        while (x > 1) {
            int p = spf[x];
            map.put(p, map.getOrDefault(p,0)+1);
            x /= p;
        }
        return map;
    }

    public static void main(String[] args) {
        int N = 100;
        System.out.println("Primes up to " + N + ": " + primesUpTo(N));
        int[] s = spf(N);
        System.out.println("SPF factorization of 84: " + factorFromSPF(84, s));
    }
}
