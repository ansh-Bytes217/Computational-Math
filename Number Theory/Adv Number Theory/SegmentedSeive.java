import java.util.*;

/**
 * Segmented sieve to generate primes in range [L, R] where R can be large (≤ 1e12 typically).
 */
public class SegmentedSieve {

    public static List<Long> getPrimesInRange(long L, long R) {
        int limit = (int)Math.sqrt(R) + 1;
        boolean[] mark = new boolean[limit + 1];
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) if (!mark[i]) {
            primes.add(i);
            for (long j = 1L*i*i; j <= limit; j += i) mark[(int)j] = true;
        }
        int sz = (int)(R - L + 1);
        boolean[] isPrime = new boolean[sz];
        Arrays.fill(isPrime, true);
        for (int p : primes) {
            long start = Math.max(1L*p*p, ((L + p - 1)/p) * 1L * p);
            for (long j = start; j <= R; j += p) isPrime[(int)(j - L)] = false;
        }
        List<Long> res = new ArrayList<>();
        for (int i = 0; i < sz; i++) {
            long v = L + i;
            if (isPrime[i] && v >= 2) res.add(v);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(getPrimesInRange(1_000_000_000L, 1_000_000_100L));
        System.out.println(getPrimesInRange(100, 200));
    }
}
