/*
FileName: bit.java
Author: Daniel St Andrews
Purpose: Implement a bit using a boolean value. To be used as a basis for larger arrays of bits.
ICSI404
Fall 2022
Professor Phipps
 */

public class bit {
    private boolean localBit;

    //DC
    public bit(){
        localBit = true;
    }

    //NDC
    public bit(boolean value)
    {
        localBit = value;
    }

    public void set(boolean value){
        localBit = value;
    }

    public void toggle(){
        if(localBit == true)
            localBit = false;
        else
            localBit = true;
    }

    public void set(){
        localBit = true;
    }

    public void clear(){
        localBit = false;
    }

    public boolean getValue(){
        return localBit;
    }


    // Was not sure if allowed to use && in if statements as per directions, so done with nested ifs instead.
    // The following methods are coded a bit strangely due to avoidance of logical comparators.
    // Temp bit is used to avoid setting any directly involved variables.
    public bit and(bit other){
        bit temp = new bit(false);
        if(localBit)
        {
            if(other.localBit){
                temp.set();
            }
        }
        else{
            temp.clear();
        }
        return temp;
    }

    public bit or(bit other){
        //Starts bit as false and checks to see if either bit is true, if not, returns false
        bit temp = new bit(false);
        if ((localBit)){
            if(other.localBit)
            {
                temp.set();
            }
            else {
                temp.set();
            }
        }
        else {
            if(other.localBit)
            {
                temp.set();
            }
        }
        return temp;
    }

    public bit xor(bit other){

        bit temp = new bit(false);

        if(localBit == true)
            if(other.localBit == false)
            {
                temp.set();
            }
        if(localBit == false)
            if(other.localBit == true)
            {
                temp.set();
            }
        if(localBit == other.localBit){
                temp.clear();
            }
        return temp;
    }

    public bit not(){
        bit temp = new bit(false);
        if(localBit){
            temp.clear();
        }
        else{
            temp.set();
        }
        return temp;
    }

    @Override
    public String toString(){
        if(localBit){
            return "1";
        }
        return "0";
    }
}
