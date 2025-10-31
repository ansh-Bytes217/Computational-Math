import java.util.*;

public class InclusionExclusionUtils {

    /** Count numbers <= N divisible by d */
    public static long countDivisible(long N, long d) {
        if (d == 0) return 0;
        return N / d;
    }

    /** Count numbers <= N divisible by at least one of divisors[] using PIE
     * divisors length should be small (<= 60 unrealistic; practical <= 20)
     */
    public static long countAtLeastOne(long N, long[] divisors) {
        int m = divisors.length;
        long ans = 0;
        for (int mask = 1; mask < (1 << m); mask++) {
            long lcm = 1;
            int bits = Integer.bitCount(mask);
            boolean ok = true;
            for (int i = 0; i < m; i++) {
                if ((mask & (1 << i)) != 0) {
                    long g = gcd(lcm, divisors[i]);
                    // check overflow of lcm * (divisors[i]/g) using double
                    long next = lcm / g;
                    if (next > Long.MAX_VALUE / divisors[i]) { ok = false; break; }
                    lcm = next * divisors[i];
                    if (lcm > N) { ok = false; break; } // no contribution
                }
            }
            if (!ok) continue;
            long cnt = N / lcm;
            if ((bits & 1) == 1) ans += cnt; else ans -= cnt;
        }
        return ans;
    }

    private static long gcd(long a, long b) {
        a = Math.abs(a); b = Math.abs(b);
        while (b != 0) {
            long t = a % b; a = b; b = t;
        }
        return a;
    }

    /** Example: count numbers <= N divisible by a or b or c */
    public static void main(String[] args) {
        long N = 100;
        long[] divisors = {2, 3, 5};
        System.out.println("Count <= " + N + " divisible by 2 or 3 or 5 = " + countAtLeastOne(N, divisors));
        // direct check
        long brute = 0;
        for (int i = 1; i <= N; i++) if (i % 2 == 0 || i % 3 == 0 || i % 5 == 0) brute++;
        System.out.println("Brute = " + brute);
    }
}
