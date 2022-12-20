/*
FileName: alu_test.java
Author: Daniel St Andrews
Purpose: Test the implementation of the rippleAdder class that adds and subtracts longwords.
ICSI404
Fall 2022
Professor Phipps
 */

public class rippleAdder_test {

    public static void runTests() throws InvalidBitException {
        addTest();
        subtractTest();
        bit_test.runTests();
        longword_test.runTests();
    }

    public static void addTest() throws InvalidBitException {
        longword first = new longword(100);
        longword second = new longword(5);
        longword answer = new longword();

        System.out.println("Adding 100 and 5, should be 105.");
        answer = rippleAdder.add(first, second);
        System.out.println(answer.getSigned());

        System.out.println("Adding 105 and 100, should be 205");
        second = rippleAdder.add(answer, first);
        System.out.println(second.getSigned());

        System.out.println("Adding 10000 and -1, should be 9999");
        first = new longword(10000);
        second = new longword(-1);
        answer = rippleAdder.add(first, second);
        System.out.println(answer.getSigned());

        System.out.println("Adding -10 and 10, should be zero.");
        first = new longword(-10);
        second = new longword(10);

        answer = rippleAdder.add(first, second);
        System.out.println(answer.getSigned());

        System.out.println("---------------------------------------------------------");


    }

    public static void subtractTest() throws InvalidBitException {
        longword first = new longword(10);
        longword second = new longword(10);
        longword answer = new longword();

        System.out.println("Subtracting 10 and 10, should be zero.");
        answer = rippleAdder.subtract(first, second);
        System.out.println(answer.getSigned());

        System.out.println("Subtracting -10 and -10, should be zero");
        first = new longword(-10);
        second = new longword(-10);
        answer = rippleAdder.subtract(first, second);
        System.out.println(answer.getSigned());

        System.out.println("Subtracting 921 and -22, should be 943");
        first = new longword(921);
        second = new longword(-22);
        answer = rippleAdder.subtract(first, second);
        System.out.println(answer.getSigned());

        System.out.println("Subtracting 21 and 22, should be -1");
        first = new longword(21);
        second = new longword(22);
        answer = rippleAdder.subtract(first, second);
        System.out.println(answer.getSigned());

    }



}
