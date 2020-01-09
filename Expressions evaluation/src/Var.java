import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yuval Cohen
 * Var class holds a variable object that represents an unknown value.
 * A variable is a String type and can be simplified, placed in a
 * number or expression, differentiated and more.
 * This class is implements Expression.
 */
public class Var implements Expression {
    //member
    private String variable;

    /**
     * constructor.
     *
     * @param var a variable
     */
    public Var(String var) {
        this.variable = var;
    }

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
    public double evaluate(Map<String, Double> assignment) throws Exception {
        if (assignment.containsKey(this.variable)) {
            return assignment.get(this.variable);
        }
        throw new Exception("ERROR - var's value not found");
    }

    /**
     * Evaluate the expression and return the result.
     *
     * @return the result
     * @throws Exception - un exception
     */
    public double evaluate() throws Exception {
        throw new Exception("ERROR - var's value not found");
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return new list
     */
    public List<String> getVariables() {
        List<String> list = new ArrayList<String>();
        list.add(this.variable);
        return list;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return this.variable;
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
        if (this.variable.equals(var)) {
            return expression;
        }
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
        if (this.variable.equals(var)) {
            return new Num(1);
        }
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