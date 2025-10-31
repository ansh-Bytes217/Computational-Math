import java.util.*;

public class PrimeUtils {

    // ----------------------------
    // 1️⃣ Basic primality check
    // ----------------------------
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    // ----------------------------
    // 2️⃣ Sieve of Eratosthenes
    // ----------------------------
    public static boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        if (n >= 0) isPrime[0] = false;
        if (n >= 1) isPrime[1] = false;

        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }

    // ----------------------------
    // 3️⃣ Smallest Prime Factor (SPF)
    // ----------------------------
    public static int[] computeSPF(int n) {
        int[] spf = new int[n + 1];
        for (int i = 2; i <= n; i++) spf[i] = i;

        for (int i = 2; i * i <= n; i++) {
            if (spf[i] == i) { // prime
                for (int j = i * i; j <= n; j += i) {
                    if (spf[j] == j) spf[j] = i;
                }
            }
        }
        return spf;
    }

    // ----------------------------
    // 4️⃣ Prime factorization using SPF
    // ----------------------------
    public static Map<Integer, Integer> factorize(int n, int[] spf) {
        Map<Integer, Integer> factors = new LinkedHashMap<>();
        while (n > 1) {
            int p = spf[n];
            factors.put(p, factors.getOrDefault(p, 0) + 1);
            n /= p;
        }
        return factors;
    }

    // ----------------------------
    // 5️⃣ Count & sum of divisors
    // ----------------------------
    public static long countDivisors(Map<Integer, Integer> factors) {
        long count = 1;
        for (int power : factors.values()) count *= (power + 1);
        return count;
    }

    public static long sumDivisors(Map<Integer, Integer> factors) {
        long sum = 1;
        for (Map.Entry<Integer, Integer> e : factors.entrySet()) {
            int p = e.getKey();
            int a = e.getValue();
            long term = (long) ((Math.pow(p, a + 1) - 1) / (p - 1));
            sum *= term;
        }
        return sum;
    }

    // ----------------------------
    // ✅ Testing
    // ----------------------------
    public static void main(String[] args) {
        System.out.println("Is 97 prime? " + isPrime(97));

        boolean[] sieve = sieve(50);
        System.out.print("Primes up to 50: ");
        for (int i = 2; i <= 50; i++) if (sieve[i]) System.out.print(i + " ");
        System.out.println();

        int[] spf = computeSPF(100);
        int n = 84;
        Map<Integer, Integer> factors = factorize(n, spf);
        System.out.println("Prime factorization of " + n + " = " + factors);
        System.out.println("Count of divisors: " + countDivisors(factors));
        System.out.println("Sum of divisors: " + sumDivisors(factors));
    }
}
