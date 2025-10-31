package mathlib;

import java.util.*;

/**
 * MatrixNumberTheoryUtils
 *
 * Implements number-theoretic operations on matrices under a modulus:
 *  - Determinant (mod M)
 *  - Adjoint and modular inverse
 *  - Trace and transpose
 *
 * Works over integers mod M (ℤ_M).
 * Assumes M > 1. For modular inverses, det(A) must be coprime to M.
 */
public class MatrixNumberTheoryUtils {

    private MatrixNumberTheoryUtils() {}

    /** Transpose of a matrix */
    public static long[][] transpose(long[][] A) {
        int n = A.length, m = A[0].length;
        long[][] T = new long[m][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                T[j][i] = A[i][j];
        return T;
    }

    /** Trace of a square matrix */
    public static long trace(long[][] A, long mod) {
        if (A.length != A[0].length)
            throw new IllegalArgumentException("Trace only defined for square matrices");
        long tr = 0;
        for (int i = 0; i < A.length; i++) {
            tr = (tr + A[i][i]) % mod;
        }
        return tr;
    }

    /** Computes determinant of a matrix under modulus using modular Gaussian elimination */
    public static long determinant(long[][] A, long mod) {
        int n = A.length;
        if (n != A[0].length)
            throw new IllegalArgumentException("Determinant only defined for square matrices");

        long[][] mat = Arrays.stream(A).map(long[]::clone).toArray(long[][]::new);
        long det = 1;
        for (int i = 0; i < n; i++) {
            // Find pivot
            int pivot = i;
            for (int j = i; j < n; j++) {
                if (mat[j][i] % mod != 0) {
                    pivot = j;
                    break;
                }
            }
            if (mat[pivot][i] % mod == 0)
                return 0; // determinant is 0 mod M

            // Swap rows if needed
            if (pivot != i) {
                long[] temp = mat[i];
                mat[i] = mat[pivot];
                mat[pivot] = temp;
                det = (mod - det) % mod; // swap flips sign
            }

            det = (det * mat[i][i]) % mod;
            long invPivot = DiophantineUtils.modInverse(mat[i][i], mod);
            if (invPivot == -1)
                throw new ArithmeticException("Matrix not invertible modulo " + mod);

            // Eliminate below
            for (int j = i + 1; j < n; j++) {
                long factor = (mat[j][i] * invPivot) % mod;
                for (int k = i; k < n; k++) {
                    mat[j][k] = (mat[j][k] - factor * mat[i][k]) % mod;
                    if (mat[j][k] < 0) mat[j][k] += mod;
                }
            }
        }
        return (det % mod + mod) % mod;
    }

    /** Cofactor matrix (mod M) */
    public static long[][] cofactorMatrix(long[][] A, long mod) {
        int n = A.length;
        long[][] cof = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long[][] minor = minor(A, i, j);
                long sign = ((i + j) % 2 == 0) ? 1 : -1;
                long detMinor = determinant(minor, mod);
                cof[i][j] = ((sign * detMinor) % mod + mod) % mod;
            }
        }
        return cof;
    }

    /** Adjoint matrix (mod M) */
    public static long[][] adjoint(long[][] A, long mod) {
        return transpose(cofactorMatrix(A, mod));
    }

    /** Inverse of matrix under modulus (if exists) */
    public static long[][] inverse(long[][] A, long mod) {
        int n = A.length;
        long det = determinant(A, mod);
        if (det == 0)
            throw new ArithmeticException("Matrix not invertible: determinant ≡ 0 (mod " + mod + ")");
        long invDet = DiophantineUtils.modInverse(det, mod);
        if (invDet == -1)
            throw new ArithmeticException("Determinant not coprime with modulus; inverse doesn't exist");

        long[][] adj = adjoint(A, mod);
        long[][] inv = new long[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                inv[i][j] = (adj[i][j] * invDet) % mod;
        return inv;
    }

    /** Helper: creates minor matrix after removing row r and column c */
    private static long[][] minor(long[][] A, int r, int c) {
        int n = A.length;
        long[][] res = new long[n - 1][n - 1];
        int row = 0;
        for (int i = 0; i < n; i++) {
            if (i == r) continue;
            int col = 0;
            for (int j = 0; j < n; j++) {
                if (j == c) continue;
                res[row][col++] = A[i][j];
            }
            row++;
        }
        return res;
    }

    /** Print matrix */
    public static void print(long[][] A) {
        for (long[] row : A)
            System.out.println(Arrays.toString(row));
        System.out.println();
    }

    // ---------- Example Main ----------
    public static void main(String[] args) {
        long mod = 13;
        long[][] A = {
                {3, 5, 7},
                {2, 6, 4},
                {1, 0, 2}
        };

        System.out.println("Matrix A:");
        print(A);

        System.out.println("Det(A) mod " + mod + " = " + determinant(A, mod));
        System.out.println("Trace(A) mod " + mod + " = " + trace(A, mod));

        long[][] inv = inverse(A, mod);
        System.out.println("Inverse(A) mod " + mod + ":");
        print(inv);
    }
}
