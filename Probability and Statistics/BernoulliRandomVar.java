import java.util.Random;

public class BernoulliRV {
    public static void main(String[] args) {
        int trials = 1_000_000;
        double p = 0.3;
        Random rand = new Random();

        double sum = 0, sumSq = 0;

        for (int i = 0; i < trials; i++) {
            int x = rand.nextDouble() < p ? 1 : 0;
            sum += x;
            sumSq += x * x;
        }

        double mean = sum / trials;
        double variance = (sumSq / trials) - (mean * mean);

        System.out.println("Empirical E[X] ≈ " + mean);
        System.out.println("Empirical Var[X] ≈ " + variance);
        System.out.println("Theoretical E[X] = " + p);
        System.out.println("Theoretical Var[X] = " + (p * (1 - p)));
    }
}
