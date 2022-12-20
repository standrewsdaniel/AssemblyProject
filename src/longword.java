/*
FileName: longword.java
Author: Daniel St Andrews
Purpose: Implement longword class over bit class.
ICSI404
Fall 2022
Professor Phipps
 */


public class longword {
    bit[] localbit = new bit[32];
    public longword(){
        for(int i = 0; i < 32; i++){
            localbit[i] = new bit(false);
        }
    }//DC

    public longword(int value) throws InvalidBitException {

        for(int i = 0; i < 32; i++){
            localbit[i] = new bit(false);
        }
        set(value);
    }//NDC

    public bit getBit(int i) throws InvalidBitException{
        try{
            return localbit[i];
        }
        catch(Exception E) {
            throw new InvalidBitException("Bit at requested position is invalid.");
        }
    }// get bit i's value

    public void setBit(int i, bit value) throws InvalidBitException {
        try{
            localbit[i] = value;
        }
        catch(Exception E){
            throw new InvalidBitException("Cannot set bit, position is invalid.");
        }
    }// set bit i's value

    public longword and(longword other) throws InvalidBitException {
        //Longword to return at the end of the method
        longword temp = new longword();

        for(int i = 0; i < 32; i++){
            if(localbit[i].and(other.getBit(i)).getValue() == true){
                temp.setBit(i, new bit());
            }
            else{
                temp.setBit(i, new bit(false));
            }
        }
        return temp;
    }// and two longwords, returning a third

    public longword or(longword other) throws InvalidBitException {
        //Longword to return at the end of the method
        longword temp = new longword();

        for(int i = 0; i < 32; i++){
            if(localbit[i].or(other.getBit(i)).getValue() == true){
                temp.setBit(i, new bit());
            }
            else{
                temp.setBit(i, new bit(false));
            }
        }
        return temp;
    }// or two longwords, returning a third

    //Not Done
    public longword xor(longword other) throws InvalidBitException {
        //Longword to return at the end of the method
        longword temp = new longword();

        for(int i = 0; i < 32; i++){
            if(localbit[i].xor(other.getBit(i)).getValue() == true){
                temp.setBit(i, new bit());
            }
            else{
                temp.setBit(i, new bit(false));
            }
        }
        return temp;
    }// xor two longwords, returning a third

    public longword not() throws InvalidBitException {

        longword temp = new longword();
        for(int i = 0; i < 32; i++){
            if(localbit[i].getValue() == true){
                temp.getBit(i).set(false);
            }
            else{
                temp.getBit(i).set(true);
            }
        }
        return temp;
    }// negate this longword, creating another


    public longword rightShift(int amount) {
        longword temp = new longword();

        if(amount < 0){
            Math.abs(amount);
            leftShift(amount);
        }
        else{
            for(int i = 0; i < 32; i++){
                try{
                    temp.localbit[i+amount] = localbit[i];
                }
                catch(Exception E){
                    //Does nothing but catch error
                }
            }
        }

        return temp;
    }// rightshift this longword by amount bits, creating a new longword

    public longword leftShift(int amount) {
        longword temp = new longword();

        for(int i = 0; i < 32; i++){
            try{
                temp.localbit[i-amount] = localbit[i];
            }
            catch(Exception E){
               //Does nothing but catch error
            }
        }

        return temp;
    }// leftshift this longword by amount bits, creating a new longword


    public long getUnsigned() {
        double temp = 0;
        long result;

        for(int i = localbit.length-1; i >= 0; i--){
            if(localbit[i].getValue()){
                temp = temp + Math.pow(2, localbit.length-1-i);
                //temp = temp + Math.pow(2, i);
            }
        }
        result = (long)temp;
        return result;
    }// returns the value of this longword as a long


    public int getSigned() throws InvalidBitException {
        int val = 0;
        longword primaryLong = new longword();
        longword secondaryLong = new longword();
        int factor = 0;

        if(localbit[0].getValue() == true){
            primaryLong = not();
            secondaryLong.setBit(31, new bit());
            bit carry = new bit(false);
            for(int i = localbit.length-1; i >= 0; i--){
                bit[] adder = add(primaryLong.getBit(i), secondaryLong.getBit(i), carry);
                carry = adder[1];
                if(adder[0].getValue()){
                    val = val - powerMethod(factor);
                }
                factor++;
            }
        }
        else{
            int result;

            for(int i = localbit.length-1; i >= 0; i--){
                if(localbit[i].getValue()){
                    val = val + powerMethod(localbit.length-1-i);
                }
            }
        }
        return val;
    }// returns the value of this longword as an int

    public void copy(longword other) throws InvalidBitException {
        try{
            localbit = other.localbit;
        }
        catch(Exception E){
            throw new InvalidBitException("Bit position or access invalid.");
        }

    }// copies the values of the bits from another longword into this one

    public void set(int value) throws InvalidBitException {
        longword temp = new longword();
        longword xorLong = new longword();
        longword result = new longword();
        double localRemainder = 0;
        double localResult = 0;
        int i = 0;

        if(value < 0){
            //Convert number to binary and then do twos compliment
            value = Math.abs(value);

            while(value != 0){
                localRemainder = value % 2;
                if(localRemainder == 1){
                    temp.localbit[localbit.length-1-i].set(true);
                }
                value = value / 2;
                i++;
            }
            temp = temp.twosCompliment();
            localbit = temp.localbit;
        }
        else {
            while(value != 0){
                localRemainder = value % 2;
                if(localRemainder == 1){
                    temp.localbit[localbit.length-1-i].set(true);
                }
                value = value / 2;
                i++;
            }
            localbit = temp.localbit;
        }
    } // set the value of the bits of this longword (used for tests)

    @Override
    public String toString(){
        String temp = "";

        for(int i = 0; i < 32; i++)
        {
            if(i == 31){
                temp = temp + localbit[i].toString();
                break;
            }
            else {
                temp = temp + localbit[i].toString() + ",";
            }
        }
        return temp;
    } // returns a comma separated string of 0's and 1's: "0,0,0,0,0 (etcetera)" for example

    //**** HELPER METHODS ****

    //Method to perform twos compliment on the current localbit array
    public longword twosCompliment() throws InvalidBitException {
        longword resultLong = new longword();
        longword xLong = new longword();
        longword yLong = new longword();

        xLong = this.not();
        yLong.setBit(31, new bit());

        bit carry = new bit(false);

        //Cout = X and Y or ((X xor Y) and Cin)
        for(int j = localbit.length-1; j >= 0; j--){
            bit[] adder = add(xLong.getBit(j), yLong.getBit(j), carry);
            carry = adder[1];
            resultLong.localbit[j] = adder[0];
        }
        return resultLong;
    }


    //Returns value to a power without use of math.pow to avoid double
    public int powerMethod(int value){
        if(value == 0){
            return 1;
        }
        return 2 * powerMethod(value-1);
    }

    public bit[] add(bit primary, bit secondary, bit in){
        bit[] temp;
        bit one = primary.xor(secondary).xor(in);
        bit two = primary.and(secondary).or((primary.xor(secondary)).and(in));

        temp = new bit[]{one, two};

        return temp;
    }//Adds one, from slides
}
