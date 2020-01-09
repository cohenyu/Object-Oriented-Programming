import java.util.Map;
import java.util.TreeMap;

/**
 * @author Yuval Cohen
 * This is the main of the program.
 */
public class ExpressionsTest {
    /**
     * Main method.
     *
     * @param args - from the command line
     */
    public static void main(String[] args) {
        Map<String, Double> assignment = new TreeMap<String, Double>();
        assignment.put("e", 2.71);
        assignment.put("x", 2.0);
        assignment.put("y", 0.25);
        Expression e = new Plus(new Plus(new Mult(new Num(2), new Var("x"))
                , new Sin(new Mult(new Num(4), new Var("y")))), new Pow(new Var("e"), new Var("x")));

        System.out.println(e);

        try {
            System.out.println(e.evaluate(assignment));
        } catch (Exception c) {
            System.out.println(c);
        }

        System.out.println(e.differentiate("x"));

        try {
            System.out.println(e.differentiate("x").evaluate(assignment));
        } catch (Exception c) {
            System.out.println(c);
        }

        System.out.println(e.differentiate("x").simplify());
    }
}

