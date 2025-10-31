import java.math.BigInteger;

/**
 * WilsonTheoremUtils
 *
 * Implements:
 *  - factorialMod(n, p): returns n! mod p (BigInteger safe)
 *  - isPrimeByWilson(p): checks Wilson's theorem (O(p) operations) - only practical for small p
 *
 * Complexity: O(p) for factorial mod p; not practical for large p (use other primality tests).
 */
public class WilsonTheoremUtils {

    /** computes n! mod p using BigInteger */
    public static BigInteger factorialMod(long n, long p) {
        BigInteger mod = BigInteger.valueOf(p);
        BigInteger res = BigInteger.ONE;
        for (long i = 1; i <= n; i++) {
            res = res.multiply(BigInteger.valueOf(i)).mod(mod);
        }
        return res;
    }

    /** check Wilson: (p-1)! ≡ -1 (mod p) */
    public static boolean isPrimeByWilson(long p) {
        if (p < 2) return false;
        BigInteger fact = factorialMod(p - 1, p);
        BigInteger mod = BigInteger.valueOf(p);
        BigInteger target = mod.subtract(BigInteger.ONE); // -1 mod p
        return fact.equals(target);
    }

    public static void main(String[] args) {
        System.out.println("Is 7 prime by Wilson? " + isPrimeByWilson(7)); // true
        System.out.println("Is 11 prime by Wilson? " + isPrimeByWilson(11)); // true
        System.out.println("Is 21 prime by Wilson? " + isPrimeByWilson(21)); // probably false
    }
}
