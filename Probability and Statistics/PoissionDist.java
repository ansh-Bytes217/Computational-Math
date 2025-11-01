import java.util.Random;

public class PoissonDistribution {
    public static void main(String[] args) {
        int trials = 1_000_000;
        double lambda = 4.0;
        Random rand = new Random();

        double sum = 0, sumSq = 0;

        for (int i = 0; i < trials; i++) {
            double L = Math.exp(-lambda);
            int k = 0;
            double p = 1.0;
            do {
                k++;
                p *= rand.nextDouble();
            } while (p > L);
            int value = k - 1;
            sum += value;
            sumSq += value * value;
        }

        double mean = sum / trials;
        double variance = (sumSq / trials) - (mean * mean);

        System.out.println("E[X] ≈ " + mean + " (expected λ=" + lambda + ")");
        System.out.println("Var[X] ≈ " + variance + " (expected λ=" + lambda + ")");
    }
}
