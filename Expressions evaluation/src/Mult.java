import java.util.Map;

/**
 * @author Yuval Cohen
 * Mult class calculates the multiplication and derivative of power between
 * two functions while extending UnaryExpression and implementing Expression.
 */
public class Mult extends BinaryExpression implements Expression {

    /**
     * constructor of 2 expressions.
     *
     * @param exp1 expression 1
     * @param exp2 expression 2
     */
    public Mult(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    /**
     * constructor of number and expression.
     *
     * @param num number
     * @param exp expression
     */
    public Mult(double num, Expression exp) {
        super(num, exp);
    }

    /**
     * constructor of expression and number.
     *
     * @param exp expression
     * @param num number
     */
    public Mult(Expression exp, double num) {
        super(exp, num);
    }

    /**
     * constructor of 2 numbers.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Mult(double num1, double num2) {
        super(num1, num2);
    }

    /**
     * constructor of expression and variable.
     *
     * @param var a variable
     * @param exp an expression
     */
    public Mult(String var, Expression exp) {
        super(var, exp);
    }

    /**
     * constructor of expression and variable.
     *
     * @param exp an expression
     * @param var a variable
     */
    public Mult(Expression exp, String var) {
        super(exp, var);
    }

    /**
     * constructor of 2 variables.
     *
     * @param var1 variable 1
     * @param var2 variable 2
     */
    public Mult(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * constructor of number and variable.
     *
     * @param var a variable
     * @param num a number
     */
    public Mult(String var, double num) {
        super(var, num);
    }

    /**
     * constructor of number and variable.
     *
     * @param num a number
     * @param var a variable
     */
    public Mult(double num, String var) {
        super(num, var);
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
        return this.getExp1().evaluate(assignment) * this.getExp2().evaluate(assignment);
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return "(" + this.getExp1() + " * " + this.getExp2() + ")";
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
        return new Mult(this.getExp1().assign(var, expression), this.getExp2().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression differentiate(String var) {
        return new Plus(new Mult(this.getExp1().differentiate(var), this.getExp2()),
                new Mult(this.getExp1(), this.getExp2().differentiate(var)));
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return new expression
     */
    public Expression simplify() {
        Expression e1 = this.getExp1().simplify();
        Expression e2 = this.getExp2().simplify();
        //Attempt to calculate the expression to a number
        try {
            return new Num(new Mult(e1, e2).evaluate());
        } catch (Exception c) {
            // 0 * x = 0
            if (e1.toString().equals("0.0")) {
                return new Num(0);
            }
            // 1 * x = x
            if (e1.toString().equals("1.0")) {
                return e2;
            }
            // x * 0 = 0
            if (e2.toString().equals("0.0")) {
                return new Num(0);
            }
            // x * 1 = x
            if (e2.toString().equals("1.0")) {
                return e1;
            }
        }
        return new Mult(e1, e2);
    }

    /**
     * Returned a simpler version than the usual simplification of the current expression.
     *
     * @return new expression
     */
    public Expression simplifyBonus() {
        Expression e1 = this.getExp1().simplifyBonus();
        Expression e2 = this.getExp2().simplifyBonus();
        //Attempt to calculate the expression to a number
        try {
            return new Num(new Mult(e1, e2).evaluate());
        } catch (Exception c) {
            // 0 * x = 0
            if (e1.toString().equals("0.0")) {
                return new Num(0);
            }
            // 1 * x = x
            if (e1.toString().equals("1.0")) {
                return e2.simplifyBonus();
            }
            // x * 0 = 0
            if (e2.toString().equals("0.0")) {
                return new Num(0);
            }
            // x * 1 = x
            if (e2.toString().equals("1.0")) {
                return e1.simplifyBonus();
            }
            // X * x = x^2
            if (e1.toString().equals(e2.toString())) {
                return new Pow(e1, new Num(2)).simplifyBonus();
            }
            // x * (y / x) = y
            if (e2 instanceof Div && e1.toString().equals(((Div) e2).getExp2().toString())) {
                return ((Div) e2).getExp1().simplifyBonus();
            }
            // (x^2) * (x^3) = x ^ 5
            if (e1 instanceof Pow && e2 instanceof Pow && ((Pow) e1).getExp1().toString().
                    equals(((Pow) e2).getExp1().toString())) {
                return new Pow(((Pow) e1).getExp1(), new Plus(((Pow) e1).getExp2()
                        , ((Pow) e2).getExp2())).simplifyBonus();
            }
        }
        return new Mult(e1, e2);
    }
}