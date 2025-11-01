import java.util.Random;

public class MonteCarloIntegration {
    public static void main(String[] args) {
        int samples = 1_000_000;
        Random rand = new Random();
        double sum = 0;

        for (int i = 0; i < samples; i++) {
            double x = rand.nextDouble();
            double fx = Math.sqrt(1 - x * x);
            sum += fx;
        }

        double integralEstimate = sum / samples;
        System.out.println("Estimated ∫₀¹ √(1−x²) dx ≈ " + integralEstimate);
        System.out.println("π/4 = " + (Math.PI / 4));
    }
}
