/********************************************************
 * Checking code of Ass4
 * @author Ori Kopel <okopel@gmail.com>
 * @version 2.0
 * @since 02-05-2018.
 ******************************************************/

import java.util.Map;
import java.util.TreeMap;

/**
 * Test Class.
 */
public class Main {
    /**
     * jbjbjb.
     *
     * @param args hbhbh
     * @throws Exception hghggghv
     */
    public static void main(String[] args) throws Exception {
        Expression e = new Div("x",0);


        System.out.println(e);
        System.out.println(e.simplifyBonus());
        Map<String, Double> ass = new TreeMap<String, Double>();
        ass.put("x", 2.0);
        ass.put("y", 0.25);
        ass.put("e", 2.71);
        try {
            System.out.println(e.evaluate(ass));
        } catch (Exception exception) {
            System.out.println(exception);
        }
        System.out.println(e.differentiate("x"));
        try {
            System.out.println(e.differentiate("x").evaluate(ass));
        } catch (Exception e1) {
            System.out.println(e1);
        }
        System.out.println(e.differentiate("x").simplify());
    }


}