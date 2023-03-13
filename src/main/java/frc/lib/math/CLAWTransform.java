package frc.lib.math;

/**
 * Represents a functional mapping of a real number onto another real number. Plucked from 1711's own CLAW framework because I couldn't get the vendor dep to work (lol)
 */
@FunctionalInterface
public interface CLAWTransform {
    /**
     * f(x) = x
     */
    public static final CLAWTransform NONE = x -> x;
    
    /**
     * f(x) = -x
     */
    public static final CLAWTransform NEGATE = x -> -x;
        
    /**
     * The mathematical sign (sgn) function.
     */
    public static final CLAWTransform SIGN = x -> x == 0 ? 0 : (x > 0 ? 1 : -1);
    
    /**
     * Gets a {@link CLAWTransform} which clamps a value to a closed interval.
     * @param low   The minimum output value.
     * @param high  The maximum output value.
     * @return      The described clamp transform.
     */
    public static CLAWTransform clamp (double low, double high) {
        return input -> Math.min(Math.max(input, low), high);
    }
    
    /**
     * Turns a transform into an even function by reflecting all x values less than zero across the y-axis.
     * @param transform The base {@link CLAWTransform} to turn into an even function.
     * @return          The symmetrical (even function) version of the given transform. 
     */
    public static CLAWTransform toEven (CLAWTransform transform) {
        return x -> transform.apply(x >= 0 ? x : -x);
    }
    
    /**
     * Turns a transform into an odd function by reflecting all x values less than zero across the y-axis
     * and negating the output for those reflected values. Note that {@code Transform.toOdd(f).apply(0)} will always
     * yield 0 for any transform function {@code f}.
     * @param transform The base {@link CLAWTransform} to turn into an odd function.
     * @return          The symmetrical (odd function) version of the given transform. 
     */
    public static CLAWTransform toOdd (CLAWTransform transform) {
        return x -> toEven(transform).apply(x) * SIGN.apply(x);
    }
    
    /**
     * Composes this {@link CLAWTransform} with another. That is,
     * {@code g.then(f)} is equivalent to the mathematical notation
     * "f(g(x))".
     * @param transform The transform to be applied after this one finishes.
     * @return          The composition of the two transforms.
     */
    public default CLAWTransform then (CLAWTransform transform) {
        return x -> transform.apply(this.apply(x));
    }
    
    /**
     * Apply the functional mapping described by this transformation.
     * @param x The {@code double} input to the function.
     * @return  The {@code double} output from the function.
     */
    public double apply (double x);
    
}