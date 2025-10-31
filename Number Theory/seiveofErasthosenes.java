import java.util.*;

public class SieveOfEratosthenes {

    /**
     * Generates all primes up to a given limit using the Sieve of Eratosthenes.
     * @param n Upper limit (inclusive)
     * @return A boolean array where isPrime[i] is true if i is prime
     */
    public static boolean[] generateSieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);

        if (n < 2) return isPrime;
        isPrime[0] = false;
        isPrime[1] = false;

        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        return isPrime;
    }

    /**
     * Returns a list of all primes up to n using the sieve.
     * @param n Upper limit
     * @return List of primes ≤ n
     */
    public static List<Integer> getPrimes(int n) {
        boolean[] sieve = generateSieve(n);
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (sieve[i]) primes.add(i);
        }
        return primes;
    }

    public static void main(String[] args) {
        int n = 100;
        List<Integer> primes = getPrimes(n);
        System.out.println("Primes up to " + n + ": " + primes);
    }
}
