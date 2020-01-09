import java.util.Map;

/**
 * @author Yuval Cohen
 * Minus class calculates the difference and derivative of division between
 * two functions while extending UnaryExpression and implementing Expression.
 */
public class Minus extends BinaryExpression implements Expression {

    /**
     * constructor of 2 expressions.
     *
     * @param exp1 expression 1
     * @param exp2 expression 2
     */
    public Minus(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    /**
     * constructor of number and expression.
     *
     * @param num number
     * @param exp expression
     */
    public Minus(double num, Expression exp) {
        super(num, exp);
    }

    /**
     * constructor of expression and number.
     *
     * @param exp expression
     * @param num number
     */
    public Minus(Expression exp, double num) {
        super(exp, num);
    }

    /**
     * constructor of 2 numbers.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Minus(double num1, double num2) {
        super(num1, num2);
    }

    /**
     * constructor of expression and variable.
     *
     * @param var a variable
     * @param exp an expression
     */
    public Minus(String var, Expression exp) {
        super(var, exp);
    }

    /**
     * constructor of expression and variable.
     *
     * @param exp an expression
     * @param var a variable
     */
    public Minus(Expression exp, String var) {
        super(exp, var);
    }

    /**
     * constructor of 2 variables.
     *
     * @param var1 variable 1
     * @param var2 variable 2
     */
    public Minus(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * constructor of number and variable.
     *
     * @param var a variable
     * @param num a number
     */
    public Minus(String var, double num) {
        super(var, num);
    }

    /**
     * constructor of number and variable.
     *
     * @param num a number
     * @param var a variable
     */
    public Minus(double num, String var) {
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
        return this.getExp1().evaluate(assignment) - this.getExp2().evaluate(assignment);
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return a string
     */
    public String toString() {
        return "(" + this.getExp1() + " - " + this.getExp2() + ")";
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
        return new Minus(this.getExp1().assign(var, expression), this.getExp2().assign(var, expression));
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var differentiate relative to variable `var`
     * @return derivative
     */
    public Expression differentiate(String var) {
        return new Minus(this.getExp1().differentiate(var), this.getExp2().differentiate(var));
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
            return new Num(new Minus(e1, e2).evaluate());
        } catch (Exception c) {
            if (e1.toString().equals(e2.toString())) {
                return new Num(0);
            }
            if (e1.toString().equals("0.0")) {
                return new Neg(e2);
            }
            if (e2.toString().equals("0.0")) {
                return e1;
            }
            return new Minus(e1, e2);
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
            return new Num(new Minus(e1, e2).evaluate());
        } catch (Exception c) {
            // x - x = 0
            if (e1.toString().equals(e2.toString())) {
                return new Num(0);
            }
            // 0 - x = -x
            if (e1.simplify().toString().equals("0.0")) {
                return new Neg(e2).simplifyBonus();
            }
            // x - 0 = x
            if (e2.simplify().toString().equals("0.0")) {
                return e1.simplifyBonus();
            }
            if (e2 instanceof Pow && e1.toString().equals("1.0") && ((Pow) e2).getExp2().toString().equals("2.0")) {
                // 1 - (sin(x))^2 = (cos(x))^2
                if (((Pow) e2).getExp1() instanceof Sin) {
                    return new Pow(new Cos(((Sin) ((Pow) e2).getExp1()).getExp()), new Num(2));
                    //  1 - (cos(x))^2 = (sin(x))^2
                } else if (((Pow) e2).getExp1() instanceof Cos) {
                    return new Pow(new Sin(((Cos) ((Pow) e2).getExp1()).getExp()), new Num(2));
                }
            }
            if (e1 instanceof Mult && e2 instanceof Mult) {
                // sin(a - b) = sin(a)cos(b) -  sin(b)cos(a)
                if (((Mult) e1).getExp1() instanceof Sin && ((Mult) e1).getExp2() instanceof Cos
                        && ((Mult) e2).getExp1() instanceof Sin && ((Mult) e2).getExp2() instanceof Cos
                        && ((Sin) ((Mult) e1).getExp1()).getExp().toString().equals(
                        ((Cos) ((Mult) e2).getExp2()).getExp().toString())
                        && ((Cos) ((Mult) e1).getExp2()).getExp().toString().equals(
                        ((Sin) ((Mult) e2).getExp1()).getExp().toString())) {
                    return new Sin(new Minus(((Sin) ((Mult) e1).getExp1()).getExp()
                            , ((Cos) ((Mult) e1).getExp2()).getExp())).simplifyBonus();
                }
                //cos(a + b) = cos(a)cos(b) - sin(a)sin(b)
                if (((Mult) e1).getExp1() instanceof Cos && ((Mult) e1).getExp2() instanceof Cos
                        && ((Mult) e2).getExp1() instanceof Sin && ((Mult) e2).getExp2() instanceof Sin
                        && ((Cos) ((Mult) e1).getExp1()).getExp().toString().
                        equals(((Sin) ((Mult) e2).getExp1()).getExp().toString())
                        && ((Cos) ((Mult) e1).getExp2()).getExp().toString().
                        equals(((Sin) ((Mult) e2).getExp2()).getExp().toString())) {
                    return new Cos(new Plus(((Cos) ((Mult) e1).getExp1()).getExp()
                            , ((Cos) ((Mult) e1).getExp2()).getExp())).simplifyBonus();
                }
            }
            // (cos(a))^2 - (sin(a))^2 = cos(2a)
            if (e1 instanceof Pow && e2 instanceof Pow && ((Pow) e1).getExp1() instanceof Cos
                    && ((Pow) e2).getExp1() instanceof Sin && ((Pow) e1).getExp2().toString().equals("2.0")
                    && ((Pow) e2).getExp2().toString().equals("2.0")
                    && ((Cos) ((Pow) e1).getExp1()).getExp().toString().
                    equals(((Sin) ((Pow) e2).getExp1()).getExp().toString())) {
                return new Cos(new Mult(new Num(2), ((Cos) ((Pow) e1).getExp1()).getExp())).simplifyBonus();
            }
            return new Minus(e1, e2);
        }
    }
}