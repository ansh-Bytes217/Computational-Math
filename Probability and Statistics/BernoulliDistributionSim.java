import java.util.Random;

public class BinomialDistribution {
    public static void main(String[] args) {
        int trials = 1_000_000;
        int n = 10;
        double p = 0.5;

        Random rand = new Random();

        double sum = 0, sumSq = 0;

        for (int i = 0; i < trials; i++) {
            int successes = 0;
            for (int j = 0; j < n; j++) {
                if (rand.nextDouble() < p) successes++;
            }
            sum += successes;
            sumSq += successes * successes;
        }

        double mean = sum / trials;
        double variance = (sumSq / trials) - (mean * mean);

        System.out.println("E[X] ≈ " + mean + " (expected " + (n * p) + ")");
        System.out.println("Var[X] ≈ " + variance + " (expected " + (n * p * (1 - p)) + ")");
    }
}
