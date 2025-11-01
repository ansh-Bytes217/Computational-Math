import java.util.Random;

public class MonteCarloExpectation {
    public static void main(String[] args) {
        Random rand = new Random();
        int trials = 1_000_000;
        double sum = 0;

        for (int i = 0; i < trials; i++) {
            double a = rand.nextDouble();
            double b = rand.nextDouble();
            sum += Math.max(a, b);
        }

        double expectedValue = sum / trials;
        System.out.println("Estimated E[max(A,B)] ≈ " + expectedValue);
        System.out.println("Theoretical E[X] = 2/3 = " + (2.0 / 3));
    }
}
