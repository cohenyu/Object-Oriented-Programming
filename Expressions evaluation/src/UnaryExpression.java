import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuval Cohen
 * This abstract class holds unary expression that composed of one atomic expression,
 * and extends base expression.
 */
public abstract class UnaryExpression extends BaseExpression {
    //Member
    private Expression exp;

    /**
     * constructor of expression.
     *
     * @param exp - an expression
     */
    public UnaryExpression(Expression exp) {
        this.exp = exp;
    }

    /**
     * constructor of var.
     *
     * @param var a variable
     */
    public UnaryExpression(String var) {
        this.exp = new Var(var);
    }

    /**
     * constructor of num.
     *
     * @param num a number
     */
    public UnaryExpression(double num) {
        this.exp = new Num(num);
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return new list
     */
    public List<String> getVariables() {
        List<String> l = this.exp.getVariables();
        List<String> vars = new ArrayList<>();
        if (l != null) {
            for (String s : l) {
                if (!(vars.contains(s))) {
                    vars.add(s);
                }
            }
        }
        return vars;
    }

    /**
     * returns the member - exp.
     *
     * @return an expression
     */
    public Expression getExp() {
        return exp;
    }
}