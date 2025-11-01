import java.util.Random;

public class MonteCarloPi {
    public static void main(String[] args) {
        int trials = 10_000_000;
        Random rand = new Random();
        int insideCircle = 0;

        for (int i = 0; i < trials; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();

            if (x * x + y * y <= 1) insideCircle++;
        }

        double piEstimate = 4.0 * insideCircle / trials;
        System.out.println("Estimated π ≈ " + piEstimate);
    }
}
