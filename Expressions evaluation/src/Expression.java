import java.util.List;
import java.util.Map;

/**
 * @author Yuval Cohen
 * Expression is composed of atomic expressions which are either binary or unary,
 * arranged in a tree structure. The expression itself is the root of the tree.
 */
public interface Expression {

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment map that holds the variable values
     * @return the result
     * @throws Exception If the expression contains a variable which is not in the map
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Evaluate the expression and return the result.
     *
     * @return the result
     * @throws Exception - un exception
     */
    double evaluate() throws Exception;

    /**
     * Returns a list of the variables in the expression.
     *
     * @return new list
     */
    List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        The replaced var
     * @param expression the expression we assign into var
     * @return new expression
     */
    Expression assign(String var, Expression expression);

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    Expression differentiate(String var);

    /**
     * Returned a simplified version of the current expression.
     *
     * @return new expression
     */
    Expression simplify();

    /**
     * Returned a simpler version than the usual simplification of the current expression.
     *
     * @return new expression
     */
    Expression simplifyBonus();
}
