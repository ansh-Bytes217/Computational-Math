package mathlib;

import java.util.*;

/**
 * NumberTheoryUtils — advanced derived functions built on top of Euler, GCD, and ModularArithmetic.
 * Covers:
 *  - divisor count & sum
 *  - totatives list (coprime numbers ≤ n)
 *  - Möbius function μ(n)
 *  - primitive root finder (for prime moduli)
 *  - power tower modulo computation
 *  - multiplicative function checker
 */
public class NumberTheoryUtils {

    private NumberTheoryUtils() {}

    /**
     * Returns all divisors of n in ascending order.
     * Time: O(√n)
     */
    public static List<Long> getDivisors(long n) {
        List<Long> divisors = new ArrayList<>();
        for (long i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i != n / i) divisors.add(n / i);
            }
        }
        Collections.sort(divisors);
        return divisors;
    }

    /**
     * Returns number of divisors (d(n)).
     * Formula: if n = p1^a1 * p2^a2 * ... then d(n) = (a1+1)(a2+1)...
     */
    public static long divisorCount(long n) {
        long count = 1;
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                int exp = 0;
                while (n % p == 0) {
                    n /= p;
                    exp++;
                }
                count *= (exp + 1);
            }
        }
        if (n > 1) count *= 2;
        return count;
    }

    /**
     * Returns sum of divisors σ(n)
     * Formula: for prime factorization n = p1^a1 * ... σ(n) = Π((p^(a+1) - 1)/(p - 1))
     */
    public static long divisorSum(long n) {
        long sum = 1;
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                long term = 1;
                long pow = 1;
                while (n % p == 0) {
                    n /= p;
                    pow *= p;
                    term += pow;
                }
                sum *= term;
            }
        }
        if (n > 1) sum *= (1 + n);
        return sum;
    }

    /**
     * Möbius function μ(n)
     * μ(n) = 1 if n = 1
     * μ(n) = (-1)^k if n is product of k distinct primes
     * μ(n) = 0 if n has squared prime factor
     */
    public static int mobius(long n) {
        if (n == 1) return 1;
        int primes = 0;
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                n /= p;
                primes++;
                if (n % p == 0) return 0; // squared factor
            }
        }
        if (n > 1) primes++;
        return (primes % 2 == 0) ? 1 : -1;
    }

    /**
     * Returns totatives of n: numbers ≤ n that are coprime with n
     */
    public static List<Integer> getTotatives(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (GCDUtils.gcd(i, n) == 1)
                list.add(i);
        }
        return list;
    }

    /**
     * Checks if n is a primitive root modulo mod (mod should be prime)
     * Condition: for each prime factor q of φ(mod), a^(φ(mod)/q) ≠ 1 (mod mod)
     */
    public static boolean isPrimitiveRoot(long a, long mod) {
        if (a <= 1 || a >= mod) return false;
        long phi = EulerFunctions.phi(mod);
        List<Long> factors = getPrimeFactors(phi);
        for (long f : factors) {
            if (ModularArithmetic.power(a, phi / f, mod) == 1) return false;
        }
        return true;
    }

    /**
     * Returns one primitive root modulo mod (mod should be prime)
     */
    public static long findPrimitiveRoot(long mod) {
        if (mod == 2) return 1;
        long phi = EulerFunctions.phi(mod);
        List<Long> factors = getPrimeFactors(phi);
        for (long r = 2; r <= mod; r++) {
            boolean ok = true;
            for (long f : factors) {
                if (ModularArithmetic.power(r, phi / f, mod) == 1) {
                    ok = false;
                    break;
                }
            }
            if (ok) return r;
        }
        return -1; // no primitive root (should not happen for primes)
    }

    /**
     * Helper: get prime factors of n (unique)
     */
    public static List<Long> getPrimeFactors(long n) {
        List<Long> list = new ArrayList<>();
        for (long p = 2; p * p <= n; p++) {
            if (n % p == 0) {
                list.add(p);
                while (n % p == 0) n /= p;
            }
        }
        if (n > 1) list.add(n);
        return list;
    }

    /**
     * Compute power tower: a^(b^(c)) mod m
     * Efficiently handles huge exponents using Euler reduction.
     * (Only valid if a and m are coprime)
     */
    public static long powerTower(long a, long b, long c, long m) {
        long expMod = EulerFunctions.phi(m);
        long reducedExp = ModularArithmetic.power(b, c, expMod);
        return ModularArithmetic.power(a, reducedExp, m);
    }

    /**
     * Checks if f(n * m) = f(n) * f(m) when gcd(n, m) = 1 (multiplicative property)
     * Useful for verifying custom arithmetic functions
     */
    public static boolean isMultiplicative(FunctionInterface func, int n, int m) {
        if (GCDUtils.gcd(n, m) != 1) return false;
        return func.apply(n * m) == func.apply(n) * func.apply(m);
    }

    /** Functional interface for testing multiplicative property */
    public interface FunctionInterface {
        long apply(long n);
    }

    // ---- Quick test ----
    public static void main(String[] args) {
        System.out.println("Divisors of 36: " + getDivisors(36));
        System.out.println("divisorCount(36) = " + divisorCount(36));
        System.out.println("divisorSum(36) = " + divisorSum(36));
        System.out.println("μ(30) = " + mobius(30));   // -> -1 (3 distinct primes)
        System.out.println("μ(12) = " + mobius(12));   // -> 0 (square factor 2^2)
        System.out.println("Totatives(10): " + getTotatives(10));
        System.out.println("Primitive root of 17: " + findPrimitiveRoot(17));
        System.out.println("Power tower 3^(4^5) mod 7 = " + powerTower(3, 4, 5, 7));
    }
}
