/*
FileName: multiplier_test.java
Author: Daniel St Andrews
Purpose: Test the implementation of the multiplier class.
ICSI404
Fall 2022
Professor Phipps
 */

public class multiplier_test {

    public static void runTests() throws InvalidBitException {
        //First longword to multiply
        longword primary = new longword(5);
        //Second longword to multiply by
        longword secondary =  new longword(5);

        longword answer = new longword();


        System.out.println("Multiplying 5 and 5. Answer should be 25.");
        answer = multiplier.multiply(primary, secondary);
        System.out.println(answer.getSigned());
        System.out.println("-----------------------------------------");

        primary = new longword(250);
        secondary = new longword(250);

        System.out.println("Multiplying 250 and 250. Answer should be 62,500.");
        answer = multiplier.multiply(primary, secondary);
        System.out.println(answer.getSigned());
        System.out.println("-----------------------------------------");

        primary = new longword(0);
        secondary = new longword(250);

        System.out.println("Multiplying 250 and 0. Answer should be 0.");
        answer = multiplier.multiply(primary, secondary);
        System.out.println(answer.getSigned());
        System.out.println("-----------------------------------------");

        primary = new longword(10);
        secondary = new longword(250);

        System.out.println("Multiplying 250 and 0. Answer should be 2500.");
        answer = multiplier.multiply(primary, secondary);
        System.out.println(answer.getSigned());
        System.out.println("-----------------------------------------");

        primary = new longword(13);
        secondary = new longword(2556);

        System.out.println("Multiplying 13 and 2556. Answer should be 33228.");
        answer = multiplier.multiply(primary, secondary);
        System.out.println(answer.getSigned());
        System.out.println("-----------------------------------------");



    }











}
