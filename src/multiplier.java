/*
FileName: multiplier.java
Author: Daniel St Andrews
Purpose: Implement a function that multiplies two longwords together.
ICSI404
Fall 2022
Professor Phipps
 */

public class multiplier {

    public static longword multiply(longword a, longword b) throws InvalidBitException {
        //Longword to store answer
        longword answer = new longword();
        //Longword to copy first bit into if bit is set on secondary longword
        longword copyLongword = new longword();
        int count = 0;

        for(int i = 31; i > 0; i--){
            //If the bit at place i is zero, do nothing
            if(b.getBit(i).getValue()){
                //If a bit is set, copy it into copyLongword
                copyLongword.copy(a);
                //Then, shift it left by the current iteration count.
                copyLongword = copyLongword.leftShift(count);
                //Add the copyLongword to answer (init as 0), repeat until finished
                answer = rippleAdder.add(answer, copyLongword);
            }
            //Increment count outside of if to keep track of iterations.
            count++;
        }
        return answer;
    }
}
