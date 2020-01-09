import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuval Cohen
 * This abstract class holds binary expression that composed of two atomic expressions,
 * and extends base expression.
 */
public abstract class BinaryExpression extends BaseExpression {
    //Members
    private Expression exp1;
    private Expression exp2;

    /**
     * constructor of 2 expressions.
     *
     * @param exp1 expression 1
     * @param exp2 expression 2
     */
    public BinaryExpression(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * constructor of number and expression.
     *
     * @param num number
     * @param exp expression
     */
    public BinaryExpression(double num, Expression exp) {
        this.exp1 = new Num(num);
        this.exp2 = exp;
    }

    /**
     * constructor of expression and number.
     *
     * @param exp expression
     * @param num number
     */
    public BinaryExpression(Expression exp, double num) {
        this.exp1 = exp;
        this.exp2 = new Num(num);
    }

    /**
     * constructor of 2 numbers.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public BinaryExpression(double num1, double num2) {
        this.exp1 = new Num(num1);
        this.exp2 = new Num(num2);
    }

    /**
     * constructor of expression and variable.
     *
     * @param var a variable
     * @param exp an expression
     */
    public BinaryExpression(String var, Expression exp) {
        this.exp1 = new Var(var);
        this.exp2 = exp;
    }

    /**
     * constructor of expression and variable.
     *
     * @param exp an expression
     * @param var a variable
     */
    public BinaryExpression(Expression exp, String var) {
        this.exp1 = exp;
        this.exp2 = new Var(var);
    }

    /**
     * constructor of 2 variables.
     *
     * @param var1 variable 1
     * @param var2 variable 2
     */
    public BinaryExpression(String var1, String var2) {
        this.exp1 = new Var(var1);
        this.exp2 = new Var(var2);
    }

    /**
     * constructor of number and variable.
     *
     * @param var a variable
     * @param num a number
     */
    public BinaryExpression(String var, double num) {
        this.exp1 = new Var(var);
        this.exp2 = new Num(num);
    }

    /**
     * constructor of number and variable.
     *
     * @param num a number
     * @param var a variable
     */
    public BinaryExpression(double num, String var) {
        this.exp1 = new Num(num);
        this.exp2 = new Var(var);
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return new list
     */
    public List<String> getVariables() {
        List<String> l1 = this.exp1.getVariables();
        List<String> l2 = this.exp2.getVariables();
        List<String> vars = new ArrayList<>();
        if (l1 != null) {
            for (String s : l1) {
                if (!(vars.contains(s))) {
                    vars.add(s);
                }
            }
        }
        if (l2 != null) {
            for (String s : l2) {
                if (!(vars.contains(s))) {
                    vars.add(s);
                }
            }
        }
        return vars;
    }

    /**
     * returns the member - exp1.
     *
     * @return Expression 1
     */
    protected Expression getExp1() {
        return this.exp1;
    }

    /**
     * returns the member - exp2.
     *
     * @return Expression 2
     */
    protected Expression getExp2() {
        return exp2;
    }
}