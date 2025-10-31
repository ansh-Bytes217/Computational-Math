import java.math.BigInteger;

/**
 * FermatQuotientUtils
 *
 * Computes Fermat quotient q_p(a) = (a^(p-1) - 1) / p.
 * Uses BigInteger to avoid overflow and compute pow mod p^2.
 *
 * Notes:
 *  - p should be prime and a not divisible by p.
 *  - Returns the quotient as BigInteger (exact integer).
 *  - Also provides q mod p (small result).
 */
public class FermatQuotientUtils {

    /** compute a^(exp) mod mod using BigInteger */
    private static BigInteger modPow(BigInteger a, BigInteger exp, BigInteger mod) {
        return a.modPow(exp, mod);
    }

    /**
     * Exact Fermat quotient as BigInteger: q = (a^(p-1) - 1) / p
     * Requires p prime and gcd(a, p) = 1.
     */
    public static BigInteger fermatQuotient(BigInteger a, BigInteger p) {
        if (p.compareTo(BigInteger.TWO) < 0) throw new IllegalArgumentException("p must be >= 2");
        if (a.mod(p).equals(BigInteger.ZERO)) throw new IllegalArgumentException("a divisible by p");

        BigInteger p2 = p.multiply(p);
        BigInteger pow = modPow(a, p.subtract(BigInteger.ONE), p2); // a^(p-1) mod p^2
        BigInteger diff = pow.subtract(BigInteger.ONE);
        if (diff.signum() < 0) diff = diff.add(p2); // safe wrap (shouldn't be negative)
        BigInteger quotient = diff.divide(p); // exact integer
        return quotient;
    }

    /** return q_p(a) mod p (as long) */
    public static long fermatQuotientModP(long a, long p) {
        BigInteger q = fermatQuotient(BigInteger.valueOf(a), BigInteger.valueOf(p));
        return q.mod(BigInteger.valueOf(p)).longValue();
    }

    public static void main(String[] args) {
        // Example: p=7, a=3 => q = (3^6 - 1)/7 = (729 - 1)/7 = 728/7 = 104
        BigInteger q = fermatQuotient(BigInteger.valueOf(3), BigInteger.valueOf(7));
        System.out.println("Fermat quotient q_7(3) = " + q); // 104
        System.out.println("q mod 7 = " + q.mod(BigInteger.valueOf(7))); // 104 mod 7 = 6
    }
}
