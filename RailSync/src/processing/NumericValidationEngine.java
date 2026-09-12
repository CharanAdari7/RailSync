package processing;

public class NumericValidationEngine {

    public boolean isProbablyPrime(long number) {

        if (number < 2) {
            return false;
        }

        if (number == 2 || number == 3) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        long d = number - 1;
        int power = 0;

        while ((d & 1) == 0) {
            d >>= 1;
            power++;
        }

        long[] bases = {2, 3, 5, 7, 11};

        for (long base : bases) {

            if (base >= number) {
                continue;
            }

            long x = modularPower(base, d, number);

            if (x == 1 || x == number - 1) {
                continue;
            }

            boolean passed = false;

            for (int r = 1; r < power; r++) {

                x = multiplyMod(x, x, number);

                if (x == number - 1) {
                    passed = true;
                    break;
                }
            }

            if (!passed) {
                return false;
            }
        }

        return true;
    }

    private long modularPower(
            long base,
            long exponent,
            long modulus) {

        long result = 1;
        base %= modulus;

        while (exponent > 0) {

            if ((exponent & 1) == 1) {
                result = multiplyMod(result, base, modulus);
            }

            base = multiplyMod(base, base, modulus);
            exponent >>= 1;
        }

        return result;
    }

    private long multiplyMod(
            long a,
            long b,
            long modulus) {

        long result = 0;

        while (b > 0) {

            if ((b & 1) == 1) {
                result = (result + a) % modulus;
            }

            a = (a + a) % modulus;
            b >>= 1;
        }

        return result;
    }
}