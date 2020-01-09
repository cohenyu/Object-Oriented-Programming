import java.util.Map;

/**
 * @author Yuval Cohen
 * Cos class calculates the cosinus and derivative of a certain function
 * while extending UnaryExpression and implementing Expression.
 */
public class Cos extends UnaryExpression implements Expression {

    /**
     * constructor of expression.
     *
     * @param exp - an expression
     */
    public Cos(Expression exp) {
        super(exp);
    }

    /**
     * constructor of var.
     *
     * @param var a variable
     */
    public Cos(String var) {
        super(var);
    }

    /**
     * constructor of num.
     *
     * @param num a number
     */
    public Cos(double num) {
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
        return Math.cos(Math.toRadians(this.getExp().evaluate(assignment)));
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return "cos(" + this.getExp() + ")";
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
        return new Cos(this.getExp().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression differentiate(String var) {
        return new Mult(new Neg(new Sin(this.getExp())), this.getExp().differentiate(var));
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
            return new Num(new Cos(e).evaluate());
        } catch (Exception c) {
            return new Cos(e);
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
            return new Num(new Cos(e).evaluate());
        } catch (Exception c) {
            // cos(-x) = cos(x)
            if (e instanceof Neg) {
                return new Cos(((Neg) e).getExp()).simplifyBonus();
            }
            if (e instanceof Minus) {
                //cos(90 - a) = sin(a).
                if (((Minus) e).getExp1().toString().equals("90.0")) {
                    return new Sin(((Minus) e).getExp2()).simplifyBonus();
                }
                //cos(180 - a) = -cos(a).
                if (((Minus) e).getExp1().toString().equals("180.0")) {
                    return new Neg(new Cos(((Minus) e).getExp2())).simplifyBonus();
                }
            }
            return new Cos(e);
        }
    }
}