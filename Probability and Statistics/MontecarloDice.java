import java.util.Random;

public class MonteCarloDiceEvent {
    public static void main(String[] args) {
        Random rand = new Random();
        int trials = 1_000_000;
        int count = 0;

        for (int i = 0; i < trials; i++) {
            int d1 = rand.nextInt(6) + 1;
            int d2 = rand.nextInt(6) + 1;
            int d3 = rand.nextInt(6) + 1;

            if (d1 != d2 && d2 != d3 && d1 != d3 && (d1 + d2 + d3) >= 15) {
                count++;
            }
        }

        double probability = (double) count / trials;
        System.out.println("Estimated P(sum≥15 and all distinct) ≈ " + probability);
    }
}
