import java.util.Map;

/**
 * @author Yuval Cohen
 * log class calculates the log and derivative of a certain function
 * while extending UnaryExpression and implementing Expression.
 */
public class Log extends BinaryExpression implements Expression {

    /**
     * constructor of 2 expressions.
     *
     * @param exp1 expression 1
     * @param exp2 expression 2
     */
    public Log(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    /**
     * constructor of number and expression.
     *
     * @param num number
     * @param exp expression
     */
    public Log(double num, Expression exp) {
        super(num, exp);
    }

    /**
     * constructor of expression and number.
     *
     * @param exp expression
     * @param num number
     */
    public Log(Expression exp, double num) {
        super(exp, num);
    }

    /**
     * constructor of 2 numbers.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Log(double num1, double num2) {
        super(num1, num2);
    }

    /**
     * constructor of expression and variable.
     *
     * @param var a variable
     * @param exp an expression
     */
    public Log(String var, Expression exp) {
        super(var, exp);
    }

    /**
     * constructor of expression and variable.
     *
     * @param exp an expression
     * @param var a variable
     */
    public Log(Expression exp, String var) {
        super(exp, var);
    }

    /**
     * constructor of 2 variables.
     *
     * @param var1 variable 1
     * @param var2 variable 2
     */
    public Log(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * constructor of number and variable.
     *
     * @param var a variable
     * @param num a number
     */
    public Log(String var, double num) {
        super(var, num);
    }

    /**
     * constructor of number and variable.
     *
     * @param num a number
     * @param var a variable
     */
    public Log(double num, String var) {
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
        if (this.getExp2().evaluate(assignment) <= 0) {
            throw new Exception("ERROR - the exponent of the log not within the domain.");
        }
        if (this.getExp1().evaluate(assignment) == 1 || this.getExp1().evaluate(assignment) <= 0) {
            throw new Exception("ERROR - the base of the log not within the domain.");
        }
        return Math.log(this.getExp2().evaluate(assignment)) / Math.log(this.getExp1().evaluate(assignment));
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return "log(" + this.getExp1() + ", " + this.getExp2() + ")";
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
        return new Log(this.getExp1().assign(var, expression), this.getExp2().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression differentiate(String var) {
        Log numer = new Log(new Var("e"), this.getExp2());
        Log deno = new Log(new Var("e"), this.getExp1());
        Expression a = new Minus(new Mult(numer.lnDifferentiate(var), deno)
                , new Mult(numer, deno.lnDifferentiate(var)));
        Expression b = new Pow(deno, new Num(2));
        return new Div(a, b);
    }

    /**
     * Returns the expression tree resulting from differentiating log with
     * base of e relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression lnDifferentiate(String var) {
        return new Div(this.getExp2().differentiate(var), this.getExp2());
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
            return new Num(new Log(e1, e2).evaluate());
        } catch (Exception c) {
            if (e1.toString().equals(e2.toString())) {
                return new Num(1);
            }
            return new Log(e1, e2);
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
            return new Num(new Log(e1, e2).evaluate());
        } catch (Exception c) {
            // log (x,x) = 1
            if (e1.toString().equals(e2.toString())) {
                return new Num(1);
            }
            //log(x,1) = 0
            if (e2.toString().equals("1.0")) {
                return new Num(0);
            }
            return new Log(e1, e2);
        }
    }
}