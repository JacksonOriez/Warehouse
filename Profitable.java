/**
 * <h1>Profitable</h1>
 * <p>
 * This interface represents something that can be used to make a profit. Along
 * with returning total profits it must also be able to provide a report.
 *
 * @author Jackson Oriez and Alexmaxfield
 * @version 12/4/2018
 */
public interface Profitable {

    double getProfit();

    String report();

}