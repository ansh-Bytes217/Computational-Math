package mathlib;

import java.util.*;

/**
 * DiophantineUtils — tools for linear Diophantine and modular equations.
 *
 * Features:
 *  1. Extended Euclidean algorithm (finds x, y such that ax + by = gcd(a, b))
 *  2. Solves ax + by = c for integer (x, y)
 *  3. Finds all solutions of ax + by = c within given bounds
 *  4. Modular inverse using Extended Euclid
 *  5. Solves a * x ≡ b (mod m)
 *  6. Chinese Remainder Theorem (CRT)
 */
public class DiophantineUtils {

    private DiophantineUtils() {}

    // ---------- EXTENDED EUCLIDEAN ALGORITHM ----------

    /** Result container for extended GCD */
    public static class GcdTriplet {
        public long gcd, x, y;
        public GcdTriplet(long gcd, long x, long y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "gcd=" + gcd + ", x=" + x + ", y=" + y;
        }
    }

    /**
     * Extended Euclidean algorithm.
     * Returns (gcd, x, y) satisfying a*x + b*y = gcd(a, b)
     * Time: O(log min(a, b))
     */
    public static GcdTriplet extendedGcd(long a, long b) {
        if (b == 0)
            return new GcdTriplet(a, 1, 0);
        GcdTriplet next = extendedGcd(b, a % b);
        return new GcdTriplet(
                next.gcd,
                next.y,
                next.x - (a / b) * next.y
        );
    }

    // ---------- LINEAR DIOPHANTINE EQUATION a*x + b*y = c ----------

    /**
     * Solves ax + by = c for one integer solution.
     * Returns (x0, y0) if possible, else null if no solution.
     * Condition: c % gcd(a, b) == 0
     */
    public static long[] solveLinearDiophantine(long a, long b, long c) {
        GcdTriplet g = extendedGcd(a, b);
        if (c % g.gcd != 0)
            return null; // no integer solution
        long scale = c / g.gcd;
        return new long[]{g.x * scale, g.y * scale};
    }

    /**
     * Returns general solution form:
     * x = x0 + (b/g)*t
     * y = y0 - (a/g)*t
     */
    public static String generalSolutionForm(long a, long b, long c) {
        long[] sol = solveLinearDiophantine(a, b, c);
        if (sol == null) return "No integer solution";
        long g = GCDUtils.gcd(a, b);
        long x0 = sol[0], y0 = sol[1];
        return String.format("x = %d + (%d/%d)*t, y = %d - (%d/%d)*t", x0, b, g, y0, a, g);
    }

    /**
     * Returns all integer pairs (x, y) satisfying ax + by = c within range limits.
     * Complexity: O((maxX - minX)/(b/g))
     */
    public static List<long[]> allSolutionsInRange(long a, long b, long c,
                                                   long minX, long maxX,
                                                   long minY, long maxY) {
        List<long[]> solutions = new ArrayList<>();
        long[] base = solveLinearDiophantine(a, b, c);
        if (base == null) return solutions;
        long g = GCDUtils.gcd(a, b);
        long x0 = base[0], y0 = base[1];

        long stepX = b / g;
        long stepY = -a / g;

        // Adjust x0, y0 into range
        for (long t = -1000000; t <= 1000000; t++) {
            long x = x0 + t * stepX;
            long y = y0 + t * stepY;
            if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                solutions.add(new long[]{x, y});
            }
        }
        return solutions;
    }

    // ---------- MODULAR INVERSE ----------

    /**
     * Finds modular inverse of a modulo m using Extended Euclid.
     * Returns (a^-1 mod m), or -1 if it doesn't exist.
     */
    public static long modInverse(long a, long m) {
        GcdTriplet g = extendedGcd(a, m);
        if (g.gcd != 1) return -1; // inverse doesn't exist
        long inv = g.x % m;
        if (inv < 0) inv += m;
        return inv;
    }

    // ---------- SOLVE a * x ≡ b (mod m) ----------

    /**
     * Solves a*x ≡ b (mod m).
     * Returns all possible x in [0, m-1], or empty list if no solution.
     */
    public static List<Long> solveLinearCongruence(long a, long b, long m) {
        GcdTriplet g = extendedGcd(a, m);
        List<Long> res = new ArrayList<>();
        if (b % g.gcd != 0) return res; // no solution

        long x0 = (g.x * (b / g.gcd)) % m;
        if (x0 < 0) x0 += m;
        long step = m / g.gcd;
        for (long i = 0; i < g.gcd; i++) {
            long sol = (x0 + i * step) % m;
            res.add(sol);
        }
        return res;
    }

    // ---------- CHINESE REMAINDER THEOREM (CRT) ----------

    /**
     * Solves system:
     * x ≡ a1 (mod m1)
     * x ≡ a2 (mod m2)
     * ...
     * Returns smallest non-negative x or -1 if no solution exists.
     */
    public static long chineseRemainderTheorem(long[] a, long[] m) {
        if (a.length != m.length) throw new IllegalArgumentException("Arrays must have same length");
        long x = a[0];
        long mod = m[0];
        for (int i = 1; i < a.length; i++) {
            long a2 = a[i], m2 = m[i];
            long[] sol = solveLinearDiophantine(mod, -m2, a2 - x);
            if (sol == null) return -1;
            long t = sol[0];
            x = (x + mod * t) % (mod * m2 / GCDUtils.gcd(mod, m2));
            if (x < 0) x += (mod * m2 / GCDUtils.gcd(mod, m2));
            mod = mod * m2 / GCDUtils.gcd(mod, m2);
        }
        return x;
    }

    // ---------- MAIN TESTING ----------

    public static void main(String[] args) {
        System.out.println("Extended GCD of (30, 20): " + extendedGcd(30, 20));
        System.out.println("Solve 15x + 25y = 5 → " + Arrays.toString(solveLinearDiophantine(15, 25, 5)));
        System.out.println("General form: " + generalSolutionForm(15, 25, 5));
        System.out.println("Mod inverse of 3 mod 11 = " + modInverse(3, 11));
        System.out.println("Solve 6x ≡ 8 (mod 14): " + solveLinearCongruence(6, 8, 14));
        System.out.println("CRT for x ≡ [2, 3, 2], mod [3, 5, 7]: " +
                chineseRemainderTheorem(new long[]{2, 3, 2}, new long[]{3, 5, 7}));
    }
}


