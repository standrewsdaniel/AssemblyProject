/*
FileName: InvalidBitException.java
Author: Daniel St Andrews
Purpose: Acts as an exception to be thrown whenever a bit position is null or oob.
ICSI404
Fall 2022
Professor Phipps
 */

public class InvalidBitException extends Exception{
    public InvalidBitException(String message){
        super(message);
    }
}
