/*
FileName: longword_test.java
Author: Daniel St Andrews
Purpose: Implement longword class over bit class.
ICSI404
Fall 2022
Professor Phipps
 */

import org.w3c.dom.ls.LSOutput;
import java.sql.SQLOutput;

public class longword_test {

    public static void runTests() throws InvalidBitException {
        //Method that consolidates testing methods.
        constructorTest();
        notTest();
        copyTest();
        getBitTest();
        setBitTest();
        setTest();
        andTest();
        orTest();
        xorTest();
        rightShiftTest();
        leftShiftTest();
        getUnsignedTest();
        getSignedTest();
        toStringTest();
    }

    public static void constructorTest() throws InvalidBitException {
        //Tests DC
        longword tester = new longword();
        System.out.println("Testing constructor and printing an initialized longword.");
        System.out.println(tester + "\n");

        System.out.println("Testing constructor by setting another longword to a value and passing it into a new longword and printing it to the screen:");
        longword test2 = new longword();
        test2.set(43678);
        longword test3 = test2;
        System.out.println(test2);
        System.out.println("---------------------------------------------------------");
    }

    public static void notTest() throws InvalidBitException {
        longword tester = new longword();
        System.out.println("Testing not function to negate an empty initialized longword. Expected Ans: All Ones.");
        tester = tester.not();
        System.out.println(tester + "\n");
        tester.set(719);
        System.out.println("Testing not function with non empty longword. Expected Ans: 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,0,1,1,0,0,0,0");
        tester = tester.not();
        System.out.println(tester);
        System.out.println("---------------------------------------------------------");
    }

    public static void copyTest() throws InvalidBitException {
        longword primary = new longword();
        longword secondary = new longword();
        longword result;

        primary.set(43243);
        secondary.copy(primary);

        System.out.println("Testing copy by copying 43243 into another longword. \n");
        System.out.println("First longword:");
        System.out.println(primary);
        System.out.println("Copied longword: Expected Ans: 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,1,1,1,0,1,0,1,1");
        System.out.println(secondary);
        System.out.println("---------------------------------------------------------");
    }

    public static void getBitTest() throws InvalidBitException {
        longword tester = new longword();
        System.out.println("Testing getBit function, returning 22nd bit of 738291 in binary. Expected Ans: 738291");
        tester.set(738291);
        System.out.println(tester.getBit(22));
        System.out.println("Testing getBit, printing entire longword to show correct bit retrieval.");
        System.out.println(tester);
        System.out.println("---------------------------------------------------------");
    }

    public static void setBitTest() throws InvalidBitException {
        longword tester = new longword();
        System.out.println("Testing setBit function, setting bit 24 to true in an otherwise empty longword. Expected Ans: 24th bit set to 1");
        tester.setBit(24, new bit());
        System.out.println(tester);
        System.out.println("---------------------------------------------------------");
    }

    public static void setTest() throws InvalidBitException {
        longword tester = new longword();
        System.out.println("Testing set function, setting longword to 45328 and returning the value with getUnsigned. Expected value is 45328.");
        tester.set(45328);
        System.out.println(tester.getUnsigned());
        System.out.println("Value in binary:");
        System.out.println(tester);
        System.out.println("---------------------------------------------------------");
    }

    public static void andTest() throws InvalidBitException {
        longword testOne = new longword();
        longword testTwo = new longword();
        longword result = new longword();

        System.out.println("Testing and function, performing and on two of the same longwords:");
        testOne.set(5000);
        testTwo.set(5000);
        System.out.println("First longword: " + testOne);
        System.out.println("Second longword: " + testTwo);
        result = testOne.and(testTwo);
        System.out.println("And result; Expected to be same as two longwords");
        System.out.println(result + "\n");

        System.out.println("Testing and function with alternating bits set on two longwords. Expected to be All Zeros");
        testTwo = testTwo.not();
        result = testOne.and(testTwo);
        System.out.println(result + "\n");

        System.out.println("Testing and function on two longwords with 10 and 12.");
        testOne = new longword(10);
        testTwo = new longword(12);
        result = testTwo.and(testOne);
        System.out.println(result + "\n");

        System.out.println("Testing and function with two empty longwords. Expected to be all zeros");
        testOne = new longword();
        testTwo = new longword();
        result = testOne.and(testTwo);
        System.out.println(result);
        System.out.println("---------------------------------------------------------");

    }

    public static void orTest() throws InvalidBitException {
        longword testOne = new longword();
        longword testTwo = new longword();
        longword result = new longword();

        System.out.println("Testing or function on two longwords with opposite set bits. Expected to be all ones.");
        testOne.set(6000);
        testTwo = testOne.not();
        result = testOne.or(testTwo);
        System.out.println(result + "\n");

        System.out.println("Testing or function on two longwords with the same set bits. Expected to be inverse of testTwo.");
        testOne = testTwo;
        result = testOne.or(testTwo);
        System.out.println(result + "\n");

        System.out.println("Testing or function on two longwords with 10 and 12.");
        testOne = new longword(10);
        testTwo = new longword(12);
        result = testTwo.or(testOne);
        System.out.println(result + "\n");

        System.out.println("Testing or function on two longwords that are both empty. Expected to be All zeros");
        testOne = new longword();
        testTwo = new longword();
        result = testOne.or(testTwo);
        System.out.println(result);
        System.out.println("---------------------------------------------------------");
    }

    public static void xorTest() throws InvalidBitException {
        longword testOne = new longword();
        longword testTwo = new longword();
        longword result = new longword();

        System.out.println("Testing xor function on two longwords with opposite set bits. Expected to be all ones.");
        testOne.set(3000);
        testTwo = testOne.not();
        result = testOne.xor(testTwo);
        System.out.println(result + "\n");

        System.out.println("Testing xor function on two longwords with 10 and 12.");
        testOne = new longword(10);
        testTwo = new longword(12);
        result = testTwo.xor(testOne);
        System.out.println(result + "\n");

        System.out.println("Testing xor function on two longwords with the same set bits. Expected to be all zeros. ");
        testTwo = testOne;
        result = testOne.xor(testTwo);
        System.out.println(result + "\n");


        System.out.println("Testing xor function on two longwords that are both empty. Expected to be all zeros.");
        testOne = new longword();
        testTwo = new longword();
        result = testOne.xor(testTwo);
        System.out.println(result);
        System.out.println("---------------------------------------------------------");

    }

    public static void rightShiftTest() throws InvalidBitException {
        longword primary = new longword();
        longword result;
        bit trueValue = new bit();

        primary.setBit(0, trueValue);

        primary.setBit(1, trueValue);

        System.out.println("Testing right shift method, shifting right 20 total places.");
        System.out.println("Pre Shift: \n" + primary);
        result = primary.rightShift(10);
        System.out.println("Post Shift 10 Spaces: \n" + result);
        result = result.rightShift(10);
        System.out.println("Post another 10 space shift: \n" + result);
        System.out.println("---------------------------------------------------------");

    }

    public static void leftShiftTest() throws InvalidBitException {
        longword primary = new longword();
        longword result;
        bit trueValue = new bit();

        primary.setBit(31, trueValue);

        primary.setBit(30, trueValue);

        System.out.println("Testing left shift method, shifting right 20 places. ");
        System.out.println("Pre Shift: \n" + primary);
        result = primary.leftShift(10);
        System.out.println("Post Shift 10 Spaces: \n" + result);
        result = result.leftShift(10);
        System.out.println("Post another 10 space shift: \n" + result);
        System.out.println("---------------------------------------------------------");

    }

    public static void getUnsignedTest() throws InvalidBitException {
        longword primary = new longword();
        longword result;
        System.out.println("Testing getUnsigned method. Setting value to 47387 and returning it from getUnsigned. Expected Value is 47387");
        primary.set(47387);
        System.out.println(primary.getUnsigned());
        System.out.println("Testing getUnsigned method. With 1129083743 and returning it from getUnsigned. Expected value is 1129083743");
        primary.set(1129083743);
        System.out.println(primary.getUnsigned());
        System.out.println("Printing primary in binary to show int value is correct.");
        System.out.println(primary);
        System.out.println("---------------------------------------------------------");
    }

    public static void getSignedTest() throws InvalidBitException {
        longword primary = new longword();
        longword result;
        primary.setBit(0, new bit());
        primary.setBit(1, new bit());
        System.out.println("Testing getSigned method. Printing unsigned then signed values. Expected to be 3221225472 and -1073741824");
        System.out.println(primary);
        System.out.println(primary.getUnsigned());
        System.out.println(primary.getSigned());
        System.out.println("---------------------------------------------------------");
    }

    public static void toStringTest() throws InvalidBitException {
        longword test = new longword();

        test.set(9999);
        System.out.println("Testing toString method, returning longword set to 9999 as a string of bits. Expecting 0s and 1s separated by commas");
        System.out.println(test.toString());

        test = new longword();
        System.out.println("Testing toString again with empty method.");
        System.out.println(test.toString());
        System.out.println("---------------------------------------------------------");
    }


}
