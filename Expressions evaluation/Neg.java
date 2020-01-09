import java.util.Map;

/**
 * @author Yuval Cohen
 * Neg class calculates the negative value and derivative of a certain function
 * while extending UnaryExpression and implementing Expression.
 */
public class Neg extends UnaryExpression implements Expression {

    /**
     * constructor of expression.
     *
     * @param exp - an expression
     */
    public Neg(Expression exp) {
        super(exp);
    }

    /**
     * constructor of var.
     *
     * @param var a variable
     */
    public Neg(String var) {
        super(var);
    }

    /**
     * constructor of num.
     *
     * @param num a number
     */
    public Neg(double num) {
        super(num);
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
        return -this.getExp().evaluate(assignment);
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return "(-" + this.getExp() + ")";
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
        return new Neg(this.getExp().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression differentiate(String var) {
        return new Neg(this.getExp().differentiate(var));
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return new expression
     */
    public Expression simplify() {
        Expression e = this.getExp().simplify();
        //Attempt to calculate the expression to a number
        try {
            return new Num(new Neg(e).evaluate());
        } catch (Exception c) {
            return new Neg(e);
        }
    }

    /**
     * Returned a simpler version than the usual simplification of the current expression.
     *
     * @return new expression
     */
    public Expression simplifyBonus() {
        Expression e = this.getExp().simplifyBonus();
        //Attempt to calculate the expression to a number
        try {
            return new Num(new Neg(e).evaluate());
        } catch (Exception c) {
            // --x = x
            if (e instanceof Neg) {
                return ((Neg) e).getExp().simplifyBonus();
            }
            return new Neg(e);
        }
    }
}