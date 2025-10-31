import java.util.*;

public class GCDUtils {

    // ----------------------------
    // 1️⃣ Basic Euclidean Algorithm (iterative)
    // ----------------------------
    public static int gcdIterative(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    // ----------------------------
    // 2️⃣ Recursive Euclid
    // ----------------------------
    public static int gcdRecursive(int a, int b) {
        if (b == 0) return Math.abs(a);
        return gcdRecursive(b, a % b);
    }

    // ----------------------------
    // 3️⃣ Least Common Multiple
    // ----------------------------
    public static long lcm(long a, long b) {
        return (a / gcdIterative((int) a, (int) b)) * b;
    }

    // ----------------------------
    // 4️⃣ Extended Euclidean Algorithm
    // Returns array [gcd, x, y] where ax + by = gcd
    // ----------------------------
    public static long[] extendedGCD(long a, long b) {
        if (b == 0) return new long[]{a, 1, 0};

        long[] vals = extendedGCD(b, a % b);
        long g = vals[0];
        long x1 = vals[2];
        long y1 = vals[1] - (a / b) * vals[2];

        return new long[]{g, x1, y1};
    }

    // ----------------------------
    // 5️⃣ Modular Inverse
    // (works only if gcd(a, m) = 1)
    // ----------------------------
    public static long modInverse(long a, long m) {
        long[] res = extendedGCD(a, m);
        long g = res[0];
        long x = res[1];
        if (g != 1) throw new ArithmeticException("Inverse doesn't exist");
        return (x % m + m) % m; // ensure positive
    }

    // ----------------------------
    // 6️⃣ GCD of Array
    // ----------------------------
    public static int gcdArray(int[] arr) {
        int g = arr[0];
        for (int i = 1; i < arr.length; i++) g = gcdIterative(g, arr[i]);
        return g;
    }

    // ----------------------------
    // ✅ Testing
    // ----------------------------
    public static void main(String[] args) {
        System.out.println("gcd(36, 60) iterative = " + gcdIterative(36, 60));
        System.out.println("gcd(36, 60) recursive = " + gcdRecursive(36, 60));
        System.out.println("lcm(12, 18) = " + lcm(12, 18));

        long[] ext = extendedGCD(30, 50);
        System.out.println("extendedGCD(30,50): gcd=" + ext[0] + ", x=" + ext[1] + ", y=" + ext[2]);
        System.out.println("Verification: " + (30 * ext[1] + 50 * ext[2]));

        System.out.println("Modular inverse of 3 mod 11 = " + modInverse(3, 11));

        int[] arr = {24, 60, 36};
        System.out.println("GCD of array = " + gcdArray(arr));
    }
}
