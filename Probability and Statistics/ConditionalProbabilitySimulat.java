import java.util.Random;

public class ConditionalProbabilitySimulation {
    public static void main(String[] args) {
        Random rand = new Random();
        int trials = 1_000_000;

        int countB = 0, countAandB = 0;

        for (int i = 0; i < trials; i++) {
            int d1 = rand.nextInt(6) + 1;
            int d2 = rand.nextInt(6) + 1;

            boolean A = (d1 + d2) > 8;
            boolean B = (d1 == 6 || d2 == 6);

            if (B) countB++;
            if (A && B) countAandB++;
        }

        double probA_given_B = (double) countAandB / countB;
        System.out.println("Estimated P(A|B) ≈ " + probA_given_B);
    }
}
