import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuval Cohen
 * This class is the main of the Bonus part.
 * I usually presented the simplest expressions, but the result would be equal
 * even if instead of variables there would be more complex expressions.
 */
public class SimplificationDemo {

    /**
     * The main function.
     *
     * @param args - from the command line.
     */
    public static void main(String[] args) {
        regularExpression();
        trigonometry();
    }

    /**
     * This method prints for each expression - the expression and the simplified expression.
     *
     * @param list list of Expressions
     */
    public static void prints(List<Expression> list) {
        for (Expression l : list) {
            System.out.println(l);
            System.out.println(l.simplifyBonus());
            System.out.println("________________________________________");
        }
    }

    /**
     * This method creates trigonometric expressions and prints them and their simplification.
     */
    public static void trigonometry() {
        List<Expression> list = new ArrayList<>();

        //cos(180 - a) = -cos(a)
        Expression e1 = new Cos(new Minus(new Num(180), new Var("a")));
        list.add(e1);

        // cos(-x) = cos(x)
        Expression e2 = new Cos(new Neg("x"));
        list.add(e2);

        //cos(90 - a) = sin(a)
        Expression e3 = new Cos(new Minus(new Num(90), new Var("a")));
        list.add(e3);

        //sin(-x) = -sin(x)
        Expression e4 = new Sin(new Neg("x"));
        list.add(e4);

        //sin(90 - a) = cos(a).
        Expression e5 = new Sin(new Minus(new Num(90), new Var("a")));
        list.add(e5);

        // sin((180 - a) = sin(a)
        Expression e6 = new Sin(new Minus(new Num(180), new Var("x")));
        list.add(e6);

        // (cos(a))^2 - (sin(a))^2 = cos(2a)
        Expression e9 = new Minus(new Pow(new Cos(new Var("x")), new Num(2))
                , new Pow(new Sin(new Var("x")), new Num(2)));
        list.add(e9);

        //cos(a + b) = cos(a)cos(b) - sin(a)sin(b)
        Expression e10 = new Minus(new Mult(new Cos(new Var("a")), new Cos(new Var("b")))
                , new Mult(new Sin(new Var("a")), new Sin(new Var("b"))));
        list.add(e10);

        //  1 - (cos(x))^2 = (sin(x))^ 2
        Expression e11 = new Minus(new Num(1), new Pow(new Cos(new Var("x")), new Num(2)));
        list.add(e11);

        // 1 - (sin(x))^2 = (cos(x))^2
        Expression e12 = new Minus(new Num(1), new Pow(new Sin(new Var("x")), new Num(2)));
        list.add(e12);

        // sin(x)^2 + cos(x)^2 = 1
        Expression e16 = new Plus(new Pow(new Sin(new Var("x"))
                , new Num(2)), new Pow(new Cos(new Var("x")), new Num(2)));
        list.add(e16);

        // cos(x)^2 + sin(x)^2 = 1
        Expression e17 = new Plus(new Pow(new Cos(new Var("x"))
                , new Num(2)), new Pow(new Sin(new Var("x")), new Num(2)));
        list.add(e17);

        // sin(2.5a + b) = sin(2.5a)cos(b) +  sin(b)cos(2.5a)
        Expression e18 = new Plus(new Mult(new Sin(new Mult(new Num(2.5), new Var("a"))), new Cos(new Var("b"))),
                new Mult(new Sin(new Var("b")), new Cos(new Mult(new Num(2.5), new Var("a")))));
        list.add(e18);

        // sin(a - b) = sin(a)cos(b) -  sin(b)cos(a)
        Expression e19 = new Minus(new Mult(new Sin(new Var("a")), new Cos(new Var("b")))
                , new Mult(new Sin(new Var("b")), new Cos(new Var("a"))));
        list.add(e19);

        // cos(a - b) = cos(a)cos(b) + sin(a)sin(b)
        Expression e20 = new Plus(new Mult(new Cos(new Var("a")), new Cos(new Var("b")))
                , new Mult(new Sin(new Var("a")), new Sin(new Var("b"))));
        list.add(e20);

        // cos(a + b) = cos(a)cos(b) - sin(a)sin(b)
        Expression e21 = new Minus(new Mult(new Cos("a"), new Cos("b")), new Mult(new Sin("a"), new Sin("b")));
        list.add(e21);

        prints(list);
    }

    /**
     * This method creates regular expressions and prints them and their simplification.
     */
    public static void regularExpression() {
        List<Expression> list = new ArrayList<>();
        //log(x,1) = 0
        Expression e7 = new Log(new Var("x"), new Num(1));
        list.add(e7);

        // log (x,x) = 1
        Expression e8 = new Log(new Var("x"), new Var("x"));
        list.add(e8);

        // x * x = x^2
        Expression e14 = new Mult(new Var("x"), new Var("x"));
        list.add(e14);

        // x *(y / x) = y
        Expression e15 = new Mult(new Var("x"), new Div(new Var("y"), new Var("x")));
        list.add(e15);

        // (3*x) ^ 0 = 1
        Expression e22 = new Pow(new Mult(new Num(3), new Var("x")), new Num(0));
        list.add(e22);

        // 0 ^ (2x) = 0
        Expression e23 = new Pow(new Num(0), new Mult(new Num(2), new Var("x")));
        list.add(e23);

        //x^1 = x
        Expression e24 = new Pow(new Var("x"), new Num(1));
        list.add(e24);

        //1^(2 * x) = 1
        Expression e25 = new Pow(new Num(1), new Mult(new Num(2), new Var("x")));
        list.add(e25);

        // 2x + 3x = 5x
        Expression e28 = new Plus(new Mult(new Num(2), new Var("x")), new Mult(new Num(3), new Var("x")));
        list.add(e28);

        // (2(3x + y)) + (3(3x + y)) = 5(3x+y)
        Expression e40 = new Plus(new Mult(new Num(2), new Plus(new Mult(new Num(3), new Var("x"))
                , new Var("y"))), new Mult(new Num(3), new Plus(new Mult(new Num(3), new Var("x")), new Var("y"))));
        list.add(e40);

        // x + x = 2x
        Expression e29 = new Plus(new Var("x"), new Var("x"));
        list.add(e29);

        // 1 / (x / y) = y / x
        Expression e30 = new Div(new Num(1), new Div(new Var("x"), new Var("y")));
        list.add(e30);

        // (x^2.5)^3 = x^7.5
        Expression e31 = new Pow(new Pow(new Var("x"), new Num(2.5)), new Num(3));
        list.add(e31);

        // (x^2y)^4z = x^(2y*4z)
        Expression e32 = new Pow(new Pow(new Var("x"), new Mult(new Num(2), new Var("y"))),
                new Mult(new Num(4), new Var("z")));
        list.add(e32);

        // (x * y) / x = y
        Expression e33 = new Div(new Mult(new Var("x"), new Var("y")), new Var("x"));
        list.add(e33);

        // x / (x * y) = 1 / y
        Expression e35 = new Div(new Var("x"), new Mult(new Var("x"), new Var("y")));
        list.add(e35);

        // --x = x
        Expression e36 = new Neg(new Neg("x"));
        list.add(e36);

        // (x^2) / (y^2) = ((x / y)^2.0)
        Expression e37 = new Div(new Pow("x", 2), new Pow("y", 2));
        list.add(e37);

        // (x^2) * (x^3) = x ^ 5
        Expression e38 = new Mult(new Pow("x", 2), new Pow("x", 3));
        list.add(e38);

        // (x^5) / (x^3) = x^2
        Expression e39 = new Div(new Pow("x", 5), new Pow("x", 3));
        list.add(e39);

        // (x^2) / (x^3) = 1 / x
        Expression e41 = new Div(new Pow("x", 2), new Pow("x", 3));
        list.add(e41);

        // x ^ -1 = 1 / x
        Expression e42 = new Pow(new Var("x"), new Num(-1));
        list.add(e42);


        prints(list);
    }
}