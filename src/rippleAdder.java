/*
FileName: rippleAdder.java
Author: Daniel St Andrews
Purpose: Implement a subtraction and addition function for longwords.
ICSI404
Fall 2022
Professor Phipps
 */

public class rippleAdder {

    public static longword add(longword a, longword b){
        //Adds two longwords together one bit at a time making use of the add function from longword.
        longword answer = new longword();
        //Temporary longword only used for accessing add method.
        longword temp = new longword();
        bit carry = new bit(false);
        bit[] adder;

        for(int i = answer.localbit.length - 1; i >= 0; i--){
            adder = temp.add(a.localbit[i], b.localbit[i], carry);
            carry = adder[1];
            answer.localbit[i] = adder[0];
        }
        return answer;
    }


    public static longword subtract(longword a, longword b) throws InvalidBitException {
        //Adds two longwords together one bit at a time making use of the add function from longword.
        longword answer = new longword();
        //Temporary longword only used for accessing add method.
        longword temp = new longword();
        bit carry = new bit(false);
        b = b.twosCompliment();

        for(int i = answer.localbit.length - 1; i >= 0; i--){
            bit[] adder = temp.add(a.localbit[i], b.localbit[i], carry);
            carry = adder[1];
            answer.localbit[i] = adder[0];
        }
        return answer;
    }


}
