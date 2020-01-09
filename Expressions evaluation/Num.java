import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yuval Cohen
 * Num class holds a number object.
 * A number is a double type and can be simplified, differentiated and more.
 * This class is implements Expression.
 */
public class Num implements Expression {
    //Member
    private double num;

    /**
     * constructor of number.
     *
     * @param num a number
     */
    public Num(double num) {
        this.num = num;
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment map that holds the variable values
     * @return the result
     * @throws Exception If the expression contains a variable which is not in the assignment
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.num;
    }

    /**
     * Evaluate the expression and return the result.
     *
     * @return the result
     * @throws Exception - un exception
     */
    public double evaluate() throws Exception {
        return this.num;
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return new list
     */
    public List<String> getVariables() {
        return new ArrayList<String>();
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return Double.toString(this.num);
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        The replaced var
     * @param expression the expression we assign into var
     * @return new expression
     */
    public Expression assign(String var, Expression expression) {
        return this;
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return new expression
     */
    public Expression simplify() {
        return this;
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return new expression
     */
    public Expression simplifyBonus() {
        return this;
    }
}