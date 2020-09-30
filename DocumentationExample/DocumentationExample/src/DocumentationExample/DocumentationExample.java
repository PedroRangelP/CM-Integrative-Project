package DocumentationExample;
import java.text.DecimalFormat;

/**
 * @author Brandom Grasley
 */

public class DocumentationExample {

    /** 
     * @param args Unused.
     */
    public static void main(String[] args) {
        double x,y,z; //Coordinates of a point.

        x=5.4;
        y=10.2;
        z=3.17;

        System.out.println("Result: " + format(distance(x, y, z)));
    }


    /** 
     * Calculates the distance from the origin to the point (x,y,z).
     * @param x The distance of the point along the x-axis.
     * @param y The distance of the point along the y-axis.
     * @param z The distance of the point along the z-axis.
     * @return the distance from the origin to the point (x,y,z). The value will allways be
     * non-negative.
     */
    public static double distance(double x, double y, double z) {
         return Math.sqrt(x*x + y*y + z*z);   
    }

    
    /** 
     * Formats a double value so that it has a single digit after the decimal place. If the
     * digit after the decimal place is 0, only a String representation of an integer is 
     * returned.
     * @param input The value to be formated.
     * @return String representation of input with up to 1 decimal place.
     */
    public static String format(double input) {
        DecimalFormat df = new DecimalFormat("0.#");
        return df.format(input);
    }
}