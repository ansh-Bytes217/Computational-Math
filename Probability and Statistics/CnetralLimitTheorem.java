import java.util.Random;

public class CLTDemo {
    public static void main(String[] args) {
        Random rand = new Random();
        int trials = 1_000_000;

        double sum = 0, sumSq = 0;

        for (int i = 0; i < trials; i++) {
            double sampleMean = 0;
            for (int j = 0; j < 12; j++) {
                sampleMean += rand.nextDouble(); // Uniform(0,1)
            }
            sampleMean -= 6; // mean-center
            sum += sampleMean;
            sumSq += sampleMean * sampleMean;
        }

        double mean = sum / trials;
        double variance = (sumSq / trials) - (mean * mean);

        System.out.println("Empirical Mean ≈ " + mean);
        System.out.println("Empirical Variance ≈ " + variance);
        System.out.println("Distribution ≈ Normal(0,1)");
    }
}
