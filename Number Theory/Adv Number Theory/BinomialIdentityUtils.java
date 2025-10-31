
import java.util.*;

public class BinomialIdentityUtils {

    // Simple nCr (no mod) using long, safe for moderate n
    public static long nCr(int n, int r) {
        if (r < 0 || r > n) return 0;
        r = Math.min(r, n - r);
        long num = 1, den = 1;
        for (int i = 1; i <= r; i++) {
            num *= (n - r + i);
            den *= i;
            long g = gcd(num, den);
            if (g > 1) { num /= g; den /= g; }
        }
        return num / den;
    }

    private static long gcd(long a, long b) {
        while (b != 0) { long t = a % b; a = b; b = t; }
        return Math.abs(a);
    }

    // Vandermonde: sum_{k} C(m,k) C(n, r-k) = C(m+n, r)
    public static long vandermonde(int m, int n, int r) {
        long sum = 0;
        for (int k = 0; k <= r; k++) sum += nCr(m, k) * nCr(n, r - k);
        return sum;
    }

    // Hockey-stick identity: sum_{k=0..r} C(k, t) = C(r+1, t+1)
    public static long hockey(int r, int t) {
        long sum = 0;
        for (int k = t; k <= r; k++) sum += nCr(k, t);
        return sum;
    }

    // Binomial transform: given a_n, produce b_n = sum_{k=0..n} C(n,k) a_k
    public static long[] binomialTransform(long[] a) {
        int n = a.length - 1;
        long[] b = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            long s = 0;
            for (int k = 0; k <= i; k++) s += nCr(i, k) * a[k];
            b[i] = s;
        }
        return b;
    }

    public static void main(String[] args) {
        System.out.println("C(10,3) = " + nCr(10,3));
        System.out.println("Vandermonde(5,7,6) = " + vandermonde(5,7,6) + " should equal C(12,6) = " + nCr(12,6));
        System.out.println("Hockey 5,2 = " + hockey(5,2) + " should equal C(6,3)=" + nCr(6,3));
        long[] a = {1,2,3,4};
        System.out.println("Binomial transform of [1,2,3,4] = " + Arrays.toString(binomialTransform(a)));
    }
}
