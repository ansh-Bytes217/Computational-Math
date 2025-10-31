package mathlib;

import java.util.Arrays;

/**
 * ModuloMatrix.java
 * 
 * Utility for modular matrix operations:
 *  - addition, subtraction, multiplication (mod M)
 *  - matrix exponentiation (A^n mod M)
 *  - identity matrix generation
 * 
 *  These operations are critical for:
 *  - Linear recurrences (Fibonacci, Tribonacci, etc.)
 *  - Modular combinatorics
 *  - Cryptographic matrix systems
 */
public class ModuloMatrix {

    private ModuloMatrix() {}

    /** Adds two matrices mod M */
    public static long[][] add(long[][] A, long[][] B, long mod) {
        int n = A.length, m = A[0].length;
        long[][] res = new long[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                res[i][j] = (A[i][j] + B[i][j]) % mod;
        return res;
    }

    /** Subtracts two matrices mod M */
    public static long[][] subtract(long[][] A, long[][] B, long mod) {
        int n = A.length, m = A[0].length;
        long[][] res = new long[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                res[i][j] = (A[i][j] - B[i][j]) % mod;
                if (res[i][j] < 0) res[i][j] += mod;
            }
        return res;
    }

    /** Multiplies two matrices mod M */
    public static long[][] multiply(long[][] A, long[][] B, long mod) {
        int n = A.length, m = A[0].length, p = B[0].length;
        if (A[0].length != B.length)
            throw new IllegalArgumentException("Invalid matrix dimensions for multiplication");

        long[][] res = new long[n][p];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                long sum = 0;
                for (int k = 0; k < m; k++) {
                    sum = (sum + A[i][k] * B[k][j]) % mod;
                }
                res[i][j] = sum;
            }
        }
        return res;
    }

    /** Generates an identity matrix of size n */
    public static long[][] identity(int n) {
        long[][] I = new long[n][n];
        for (int i = 0; i < n; i++) I[i][i] = 1;
        return I;
    }

    /** Exponentiates matrix A^power mod M */
    public static long[][] power(long[][] A, long power, long mod) {
        int n = A.length;
        if (A.length != A[0].length)
            throw new IllegalArgumentException("Matrix must be square for exponentiation");

        long[][] result = identity(n);
        long[][] base = Arrays.stream(A).map(long[]::clone).toArray(long[][]::new);

        while (power > 0) {
            if ((power & 1) == 1)
                result = multiply(result, base, mod);
            base = multiply(base, base, mod);
            power >>= 1;
        }
        return result;
    }

    /** Pretty-prints a matrix */
    public static void printMatrix(long[][] A) {
        for (long[] row : A)
            System.out.println(Arrays.toString(row));
        System.out.println();
    }

    // ---------- Example test ----------
    public static void main(String[] args) {
        long mod = 1000000007L;

        // Example: Fibonacci using matrix exponentiation
        long[][] F = { {1, 1}, {1, 0} };
        long n = 10; // F(10)
        long[][] Fn = power(F, n - 1, mod);
        long fibN = Fn[0][0];
        System.out.println("F(" + n + ") = " + fibN + " mod " + mod);

        // Check matrix multiplication
        long[][] A = {{1, 2}, {3, 4}};
        long[][] B = {{5, 6}, {7, 8}};
        long[][] C = multiply(A, B, mod);
        printMatrix(C);

        // Check exponentiation
        long[][] PowA = power(A, 5, mod);
        printMatrix(PowA);
    }
}
