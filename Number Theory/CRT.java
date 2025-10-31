import java.util.*;

/**
 * CRTUtils - utilities for solving systems of modular congruences.
 *
 * Provides:
 *  - pairwiseCombine(a1, m1, a2, m2) -> long[] {x, lcmMod} or null if no solution
 *  - solveCRT(a[], m[]) -> long[] {x, M} or null if no solution
 *
 * Note: Uses extendedGCD from GCDUtils (expects extendedGCD(long a, long b) returning [g, x, y]).
 */
public class CRTUtils {

    /**
     * Combine two congruences:
     * x ≡ a1 (mod m1)
     * x ≡ a2 (mod m2)
     *
     * Returns smallest non-negative solution x and modulus l = lcm(m1, m2) as {x, l}
     * or null if incompatible.
     */
    public static long[] pairwiseCombine(long a1, long m1, long a2, long m2) {
        // Normalize
        a1 = mod(a1, m1);
        a2 = mod(a2, m2);

        // Solve: m1 * s + m2 * t = g
        long[] eg = GCDUtils.extendedGCD(m1, m2); // returns [g, x, y] such that m1*x + m2*y = g
        long g = eg[0];
        long xCoeff = eg[1]; // coefficient for m1 in the representation (unused directly)
        // long yCoeff = eg[2];

        if ((a2 - a1) % g != 0) return null; // no solution

        long l = (m1 / g) * m2; // lcm
        // Compute solution:
        long diff = a2 - a1;
        long mult = (diff / g) % (m2 / g);
        long k = ( (xCoeff % (m2 / g)) * mult ) % (m2 / g); // multiplier
        long result = a1 + m1 * k;
        result = mod(result, l);
        return new long[]{result, l};
    }

    /**
     * Solve arrays of congruences:
     * x ≡ a[i] (mod m[i]) for i = 0..k-1
     * Returns {x, M} where M is the final modulus, or null if no solution.
     */
    public static long[] solveCRT(long[] a, long[] m) {
        if (a.length != m.length) throw new IllegalArgumentException("Lengths must match");
        long x = mod(a[0], m[0]);
        long mod = m[0];
        for (int i = 1; i < a.length; i++) {
            long[] comb = pairwiseCombine(x, mod, a[i], m[i]);
            if (comb == null) return null;
            x = comb[0];
            mod = comb[1];
        }
        return new long[]{x, mod};
    }

    private static long mod(long a, long m) {
        if (m < 0) m = -m;
        long res = a % m;
        if (res < 0) res += m;
        return res;
    }

    // Example usage / quick test
    public static void main(String[] args) {
        // Simple pair
        long[] r = pairwiseCombine(2, 3, 3, 5); // x ≡ 2 (mod 3), x ≡ 3 (mod 5) -> x = 8 mod 15
        if (r != null) System.out.println("x = " + r[0] + " (mod " + r[1] + ")");

        // Non-coprime example
        // x ≡ 2 (mod 6)
        // x ≡ 8 (mod 14)
        // gcd(6,14)=2, check compatibility: 2 ≡ 8 (mod 2) -> both ≡0 mod 2? 2%2=0,8%2=0 so compatible
        long[] r2 = pairwiseCombine(2, 6, 8, 14);
        if (r2 != null) System.out.println("x = " + r2[0] + " (mod " + r2[1] + ")");
        else System.out.println("No solution");

        // Multiple congruences
        long[] a = {2, 3, 2};
        long[] m = {3, 5, 7};
        long[] sol = solveCRT(a, m);
        if (sol != null) System.out.println("CRT solution: x = " + sol[0] + " (mod " + sol[1] + ")");
        else System.out.println("No solution for these congruences");
    }
}
