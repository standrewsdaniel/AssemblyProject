/*
FileName: bit_test.java
Author: Daniel St Andrews
Purpose: Test implementation of bit class.
ICSI404
Fall 2022
Professor Phipps
 */

import javax.sound.midi.SysexMessage;

public class bit_test {

    public static void runTests(){
        bit testBit = new bit();
        bit tempBit = new bit();
        bit resultBit = new bit();

        //runTests is the Method for running all tests on methods defined in the bit class.
        //General format for this method is separating "work" from organizational print statements.

        System.out.println("Check NDC and toString (Should print t):");
        System.out.println(testBit.toString());

        System.out.println("Test Set() Method, Sets to t:");

        testBit.set();
        System.out.println(testBit.toString());

        System.out.println("Test Set(value) Method, Sets to f:");

        boolean testValue = false;
        testBit.set(testValue);
        System.out.println(testBit.toString());

        System.out.println("Test Clear() Method, Sets to f:");

        testBit.clear();
        System.out.println(testBit.toString());

        System.out.println("Test toggle() Method, Sets to t:");

        testBit.toggle();
        System.out.println(testBit.toString());

        System.out.println("Test getValue() Method, print boolean assigned from getValue, should be true");

        //Only used for assignment on this one call.
        boolean tempBool;
        tempBool = testBit.getValue();
        System.out.println(tempBool);

        System.out.println("Test and() Method on tempBit and testBit, print returned value, should be true as both are true. ");

        //Used for comparison against existing declared bit.

        resultBit = tempBit.and(testBit);
        System.out.println(resultBit.toString());

        System.out.println("Test and() Method on tempBit and testBit, print returned value, should be false as one is true and one is not. ");

        testBit.set(false);
        resultBit = tempBit.and(testBit);
        System.out.println(resultBit.toString());

        System.out.println("Test and() Method on tempBit and testBit, print returned value, should be false as both variables are false.");

        tempBit.set(false);
        resultBit = testBit.and(tempBit);
        System.out.println(resultBit.toString());

        //Note, at this stage of execution, both tempBit and testBit have values of false.

        System.out.println("Test or() Method on tempBit and testBit, print returned value, should be false as both variables are false.");

        resultBit = testBit.or(tempBit);
        System.out.println(resultBit.toString());

        System.out.println("Test or() Method on tempBit and testBit, print returned value, should be true as one is true and one is false.");

        testBit.set();
        resultBit = tempBit.or(testBit);
        System.out.println(resultBit.toString());

        System.out.println("Test or() Method on tempBit and testBit, print returned value, should be true as both variables are true.");

        tempBit.set();
        resultBit = tempBit.or(testBit);
        System.out.println(resultBit.toString());

        System.out.println("Test xor() Method on tempBit and testBit, print returned value, should be false as both variables are true.");

        resultBit = tempBit.xor(testBit);
        System.out.println(resultBit.toString());

        System.out.println("Test xor() Method on tempBit and testBit, print returned value, should be true as one is true and one is false.");

        tempBit.set(false);
        resultBit = tempBit.xor(testBit);
        System.out.println(resultBit.toString());

        System.out.println("Test xor() Method on tempBit and testBit, print returned value, should be false as both are false.");

        testBit.set(false);
        resultBit = tempBit.xor(testBit);
        System.out.println(resultBit.toString());

        //Note once again, both are now false.

        System.out.println("Test not() Method on tempBit, print returned value, should be true as starting value is false.");

        resultBit = tempBit.not();
        System.out.println(resultBit.toString());

        System.out.println("Test not() Method on tempBit, print returned value, should be false as starting value is true.");

        resultBit = tempBit.not();
        System.out.println(resultBit.toString());

    }



}
