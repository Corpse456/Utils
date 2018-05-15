package constant;

/**
 *  
 * @author Neznaev_ai
 *
 */
public class Factorial {

    private final static long[] FACTORIALS = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    
    /**
     * @param number for which it is necessary to obtain the factorial (from 0 to 20 only)
     * @return factorial of the number
     */
    public static long of (int number) {
        if (number < 0 || number > 20) return -1;
        
        return FACTORIALS[number];
    }
}
