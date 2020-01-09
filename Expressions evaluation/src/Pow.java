import java.util.Map;

/**
 * @author Yuval Cohen
 * Pow class calculates the power and derivative of power between two function
 * while extending UnaryExpression and implementing Expression.
 */
public class Pow extends BinaryExpression implements Expression {

    /**
     * constructor of 2 expressions.
     *
     * @param exp1 expression 1
     * @param exp2 expression 2
     */
    public Pow(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    /**
     * constructor of number and expression.
     *
     * @param num number
     * @param exp expression
     */
    public Pow(double num, Expression exp) {
        super(num, exp);
    }

    /**
     * constructor of expression and number.
     *
     * @param exp expression
     * @param num number
     */
    public Pow(Expression exp, double num) {
        super(exp, num);
    }

    /**
     * constructor of 2 numbers.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Pow(double num1, double num2) {
        super(num1, num2);
    }

    /**
     * constructor of expression and variable.
     *
     * @param var a variable
     * @param exp an expression
     */
    public Pow(String var, Expression exp) {
        super(var, exp);
    }

    /**
     * constructor of expression and variable.
     *
     * @param exp an expression
     * @param var a variable
     */
    public Pow(Expression exp, String var) {
        super(exp, var);
    }

    /**
     * constructor of 2 variables.
     *
     * @param var1 variable 1
     * @param var2 variable 2
     */
    public Pow(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * constructor of number and variable.
     *
     * @param var a variable
     * @param num a number
     */
    public Pow(String var, double num) {
        super(var, num);
    }

    /**
     * constructor of number and variable.
     *
     * @param num a number
     * @param var a variable
     */
    public Pow(double num, String var) {
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
        double value = Math.pow(this.getExp1().evaluate(assignment), this.getExp2().evaluate(assignment));
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new Exception("ERROR - invalid operation");
        }
        return value;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return "(" + this.getExp1() + "^" + this.getExp2() + ")";
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
        return new Pow(this.getExp1().assign(var, expression), this.getExp2().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression differentiate(String var) {
        return new Mult(
                new Pow(this.getExp1(), this.getExp2()),
                new Plus(new Mult(this.getExp1().differentiate(var), new Div(this.getExp2(), this.getExp1()))
                        , new Mult(this.getExp2().differentiate(var), new Log(new Var("e"), this.getExp1()))));
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
            return new Num(new Pow(e1, e2).evaluate());
        } catch (Exception c) {
            return new Pow(e1, e2);
        }
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
            return new Num(new Pow(e1, e2).evaluate());
        } catch (Exception c) {
            //x^0 = 1
            if (e2.toString().equals("0.0")) {
                return new Num(1);
            }
            //0^x = 0
            if (e1.toString().equals("0.0")) {
                return new Num(0);
            }
            //x^1 = x
            if (e2.toString().equals("1.0")) {
                return e1.simplifyBonus();
            }
            //1^x = 1
            if (e1.toString().equals("1.0")) {
                return new Num(1);
            }
            // (x ^ 2) ^ 3 = x ^ 6
            if (e1 instanceof Pow) {
                return new Pow(((Pow) e1).getExp1(), new Mult(((Pow) e1).getExp2(), e2)).simplifyBonus();
            }
            if (e2.toString().equals("-1.0")) {
                return new Div(new Num(1), e1).simplifyBonus();
            }
            return new Pow(e1, e2);
        }
    }
}