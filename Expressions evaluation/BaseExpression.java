import java.util.TreeMap;

/**
 * @author Yuval Cohen
 * abstract BaseExpression class that implements Expression.
 * This class is the root of all the other classes that represent an expression.
 */
public abstract class BaseExpression implements Expression {

    /**
     * Evaluate the expression and return the result.
     *
     * @return the result
     * @throws Exception - un exception
     */
    public double evaluate() throws Exception {
        return evaluate(new TreeMap<>());
    }
}