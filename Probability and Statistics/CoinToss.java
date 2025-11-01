import java.util.Random;

public class CoinTossSimulation {
    public static void main(String[] args) {
        int trials = 1_000_000;
        int heads = 0;

        Random rand = new Random();

        for (int i = 0; i < trials; i++) {
            if (rand.nextBoolean()) heads++;
        }

        double probabilityOfHeads = (double) heads / trials;

        System.out.println("Simulated P(Heads) ≈ " + probabilityOfHeads);
        System.out.println("Expected P(Heads) = 0.5");
    }
}
