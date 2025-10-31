package mathlib;

public class ModularArithmetic {

    // Prevent instantiation
    private ModularArithmetic() {}

    /** 
     * Performs modular addition (a + b) % mod safely
     */
    public static long add(long a, long b, long mod) {
        a %= mod;
        b %= mod;
        long res = a + b;
        if (res >= mod) res -= mod;
        return res;
    }

    /**
     * Performs modular subtraction (a - b) % mod safely
     */
    public static long subtract(long a, long b, long mod) {
        a %= mod;
        b %= mod;
        long res = a - b;
        if (res < 0) res += mod;
        return res;
    }

    /**
     * Performs modular multiplication (a * b) % mod safely
     */
    public static long multiply(long a, long b, long mod) {
        a %= mod;
        b %= mod;
        long res = (a * b) % mod;
        if (res < 0) res += mod;
        return res;
    }

    /**
     * Fast modular exponentiation (a^b) % mod
     * Time: O(log b)
     */
    public static long power(long a, long b, long mod) {
        a %= mod;
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1)
                res = multiply(res, a, mod);
            a = multiply(a, a, mod);
            b >>= 1;
        }
        return res;
    }

    /**
     * Computes modular inverse of 'a' under modulo 'mod'
     * Works only if mod is prime and a is coprime with mod
     * Uses Fermat's Little Theorem: a^(mod-2) % mod
     */
    public static long modInverse(long a, long mod) {
        if (GCDUtils.gcd(a, mod) != 1)
            throw new IllegalArgumentException("Inverse doesn't exist when gcd(a, mod) != 1");
        return power(a, mod - 2, mod);
    }

    /**
     * Performs modular division: (a / b) % mod
     * Uses modular inverse of b.
     */
    public static long divide(long a, long b, long mod) {
        return multiply(a, modInverse(b, mod), mod);
    }
}
