/*
FileName: cpu_test1.java
Author: Daniel St Andrews
Purpose: Test implementation of computer class and its functionalities.
ICSI404
Fall 2022
Professor Phipps
 */

public class cpu_test1 {

    //Assignment 7 note: I have not gotten my grades back for asn 5 or 6 as of this submission, and I am completing
    //  this to the best of my ability despite not knowing of my logic or implementation errors of the previous
    //  two assignments.
    //  Please mind this when taking mistakes from previous assignments into account on this submission.
    //  Thank you.


    public static void runTests() throws InvalidBitException{

        computer halt_test = new computer();
        computer move_test = new computer();
        computer interrupt_test = new computer();
        computer init_test = new computer();

        //This halt instruction is only all 1s so that I can quickly see values changing. Temporary.
        String hal = "0000111111111111";
        // 001 001 000 101
        String mov = "0001000100000101";

        //This interrupt code is all 1s so that the values can easily be noticed when updated.
        String interrupt_registers = "0010111111111110";
        String interrupt_memory = "0010111111111111";

        //Confirmed move works with both negative and positive numbers.
        String mov_test = "0001001000001010";
        String mov_test_neg = "0001001011110110";

        //Two 16 bit registers
        String[] input = new String[2];

        //HALT TEST
        input[0] = hal;
        input[1] = hal;

        //PreLoad Test
        halt_test.preLoad(input);
        halt_test.run();

        //INTERRUPT 0 TEST
        input[0] = interrupt_registers;
        input[1] = interrupt_memory;

        //PreLoad Test
        interrupt_test.preLoad(input);
        interrupt_test.run();

        //MOV TEST
        input[0] = mov_test_neg;
        input[1] = interrupt_registers;

        move_test.preLoad(input);
        move_test.run();
    }


}
