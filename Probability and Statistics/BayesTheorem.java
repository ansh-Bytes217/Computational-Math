import java.util.Random;

public class BayesTheoremSimulation {
    public static void main(String[] args) {
        Random rand = new Random();
        int trials = 1_000_000;

        double diseaseProb = 0.01;
        double truePositiveRate = 0.99;
        double trueNegativeRate = 0.95;

        int testPositive = 0, diseaseAndPositive = 0;

        for (int i = 0; i < trials; i++) {
            boolean hasDisease = rand.nextDouble() < diseaseProb;
            boolean testPositiveNow;

            if (hasDisease)
                testPositiveNow = rand.nextDouble() < truePositiveRate;
            else
                testPositiveNow = rand.nextDouble() > trueNegativeRate;

            if (testPositiveNow) {
                testPositive++;
                if (hasDisease) diseaseAndPositive++;
            }
        }

        double bayesEstimate = (double) diseaseAndPositive / testPositive;
        System.out.println("Estimated P(Disease | TestPositive) ≈ " + bayesEstimate);
    }
}
