/*
FileName: memory_test.java
Author: Daniel St Andrews
Purpose: Test implementation of memory class and its functionalities.
ICSI404
Fall 2022
Professor Phipps
 */

public class memory_test {

    public static void runTests() throws InvalidBitException {
        //~~~~~~~~~~~~~~~~~~~VARS~~~~~~~~~~~~~~~~~~~~~
        longword writeAddress = new longword(2);
        longword readAddress = new longword(2);
        longword value = new longword(10);
        longword result = new longword();
        memory mem = new memory();

        //Write to position 2 in memory, then read from it and print values.
        mem.write(writeAddress, value);
        result = mem.read(readAddress);
        System.out.println(result.getSigned());

        //Check writing to last available memory location.
        writeAddress = new longword(255);
        readAddress =  new longword(255);
        value = new longword(2348);
        mem.write(writeAddress, value);
        result = mem.read(readAddress);
        System.out.println(result.getSigned());

        //Checks values hold between read and write calls
        result = mem.read(new longword(2));
        System.out.println(result.getSigned());

        //Check functionality with negative numbers being written to memory.
        writeAddress = new longword(6);
        readAddress = new longword(6);
        value = new longword(-43);
        mem.write(writeAddress, value);
        result = mem.read(readAddress);
        System.out.println(result.getSigned());

        //Check that a memory location can be overwritten.
        writeAddress = new longword(2);
        readAddress = new longword(2);
        value = new longword(9001);
        mem.write(writeAddress, value);
        result = mem.read(readAddress);
        System.out.println(result.getSigned());
    }

}
