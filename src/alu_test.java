/*
FileName: alu_test.java
Author: Daniel St Andrews
Purpose: Test the implementation of the alu class.
ICSI404
Fall 2022
Professor Phipps
 */

public class alu_test {

    public static void runTests() throws InvalidBitException {
        //Longwords to have operations performed on
        longword first = new longword(10);
        longword second = new longword(12);
        longword answer = new longword();

        //Declarations to avoid using true and false values directly
        bit trueBit = new bit();
        bit falseBit = new bit(false);

        //OPERATORS: SET MANUALLY
        bit[] and = new bit[]{trueBit, falseBit, falseBit, falseBit};
        bit[] or = new bit[]{trueBit, falseBit, falseBit, trueBit};
        bit[] xor = new bit[]{trueBit, falseBit, trueBit, falseBit};
        bit[] not = new bit[]{trueBit, falseBit, trueBit, trueBit};
        bit[] leftShift = new bit[]{trueBit, trueBit, falseBit, falseBit};
        bit[] rightShift = new bit[]{trueBit, trueBit, falseBit, trueBit};
        bit[] add = new bit[]{trueBit, trueBit, trueBit, falseBit};
        bit[] sub = new bit[]{trueBit, trueBit, trueBit, trueBit};
        bit[] mult = new bit[]{falseBit, trueBit, trueBit, trueBit};

        //AND
        answer = alu.doOp(and, first, second);
        System.out.println("And test: Expected Ans: 1,0,0,0 (Last 4 bits, 2^0-2^3)");
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //OR
        answer = alu.doOp(or, first, second);
        System.out.println("Or test: Expected Ans: 1,1,1,0 (Last 4 bits, 2^0-2^3)");
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //XOR
        answer = alu.doOp(xor, first, second);
        System.out.println("Xor test: Expected Ans: 0,1,1,0 (Last 4 bits, 2^0-2^3)");
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //NOT
        answer = alu.doOp(not, first, second);
        System.out.println("Not test: Expected Ans: 0,1,0,1 (Last 4 bits, 2^0-2^3, rest should be 1.)");
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //LEFTSHIFT
        answer = alu.doOp(leftShift, first, second);
        System.out.println("LeftShift test: Expected Ans: 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0");
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //RIGHTSHIFT
        answer = alu.doOp(rightShift, first, second);
        System.out.println("RightShift test: Expected Ans: All zeros. (Bits shifted out of bounds.)");
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //ADD
        answer = alu.doOp(add, first, second);
        System.out.println("Add test: Expected Ans: 22 in dec, binary also printed.");
        System.out.println(answer.getSigned());
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //SUB
        answer = alu.doOp(sub, first, second);
        System.out.println("Sub test: Expected Ans: -2 in dec, binary also printed.");
        System.out.println(answer.getSigned());
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //MULT
        answer = alu.doOp(mult, first, second);
        System.out.println("Mult test: Expected Ans: 120 in dec, binary also printed.");
        System.out.println(answer.getSigned());
        System.out.println(answer);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
