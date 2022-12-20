/*
FileName: alu.java
Author: Daniel St Andrews
Purpose: Implement longword class over bit class.
ICSI404
Fall 2022
Professor Phipps
 */
import java.util.Arrays;

public class alu {

    public static longword doOp(bit[] operation, longword a, longword b) throws InvalidBitException {
        //Function determines the bit array without making use of getSigned
        //  or getUnsigned. Performs the proper operations and

        //Used to store the answer to the operation performed.
        longword answer = new longword();
        int shiftAmount;
        String operator = Arrays.toString(operation);

        //Compares the bits in the array to the corresponding operator "codes"
        switch (operator){
            //ADD CASE
            case "[1, 0, 0, 0]":
                answer = a.and(b);
                break;
            //OR CASE
            case "[1, 0, 0, 1]":
                answer = a.or(b);
                break;
            //XOR CASE
            case "[1, 0, 1, 0]":
                answer = a.xor(b);
                break;
            //NOT CASE
            case "[1, 0, 1, 1]":
                answer = a.not();
                //b is ignored.
                break;
            //LEFTSHIFT CASE
            case "[1, 1, 0, 0]":
                shiftAmount = b.getSigned();
                //"Ignores" All but the left 5 bits.
                //Used this method since first 5 bits (1+2+4+8+16) = 31
                if(shiftAmount >= 31){
                    shiftAmount = 31;
                }
                answer = a.leftShift(shiftAmount);
                break;
            //RIGHTSHIFT CASE
            case "[1, 1, 0, 1]":
                shiftAmount = b.getSigned();
                //"Ignores" All but the left 5 bits.
                //Used this method since first 5 bits (1+2+4+8+16) = 31
                if(shiftAmount >= 31){
                    shiftAmount = 31;
                }
                answer = a.rightShift(shiftAmount);
                break;
            //ADD CASE
            case "[1, 1, 1, 0]":
                answer = rippleAdder.add(a, b);
                break;
            //SUB CASE
            case "[1, 1, 1, 1]":
                answer = rippleAdder.subtract(a, b);
                break;
            //MULT CASE
            case "[0, 1, 1, 1]":
                answer = multiplier.multiply(a, b);
                break;
        }
        return answer;
    }


}
