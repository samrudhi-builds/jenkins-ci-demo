import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class NumberAnalysis {
    private static final int MAX_FACTORIAL_INPUT = 1_000;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage: java NumberAnalysis <num> [outputFile]");
            System.exit(2);
            return;
        }

        int num;
        try {
            num = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number: " + args[0]);
            System.exit(2);
            return;
        }

        if (num > MAX_FACTORIAL_INPUT) {
            System.err.println("Maximum factorial input is " + MAX_FACTORIAL_INPUT
                    + ". Received: " + num);
            System.exit(2);
            return;
        }

        String primeResult = num + (isPrime(num)
                ? " is a Prime number\n"
                : " is NOT a Prime number\n");

        StringBuilder report = new StringBuilder();
        report.append("Input Number: ").append(num).append('\n');

        if (num < 0) {
            report.append("Factorial: Not defined for negative numbers\n");
            report.append(primeResult);
        } else {
            BigInteger factorial = BigInteger.ONE;
            for (int i = 2; i <= num; i++) {
                factorial = factorial.multiply(BigInteger.valueOf(i));
            }

            String factorialDigits = factorial.toString();
            int digitSum = 0;
            for (int i = 0; i < factorialDigits.length(); i++) {
                digitSum += factorialDigits.charAt(i) - '0';
            }

            report.append("Factorial = ").append(factorialDigits).append('\n');
            report.append("SumOfDigitsOfFactorial = ").append(digitSum).append('\n');
            report.append(primeResult);
            report.append("DigitsInFactorial = ").append(factorialDigits.length()).append('\n');
        }

        String output = report.toString();
        System.out.println(output);

        if (args.length >= 2) {
            String outputFile = args[1];
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(output);
            }
            System.out.println("Results saved to " + outputFile);
        }
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }

        if (num % 2 == 0) {
            return false;
        }

        int limit = (int) Math.sqrt(num);
        for (int divisor = 3; divisor <= limit; divisor += 2) {
            if (num % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
