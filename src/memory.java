/*
FileName: memory.java
Author: Daniel St Andrews
Purpose: Memory class that implements a 1024 byte bit array and allows for reading and writing.
ICSI404
Fall 2022
Professor Phipps
 */
public class memory {

    bit[] memoryArr = new bit[8192];

    //DC
    public memory(){
        for(int i = 0; i < 8192; i++){
            memoryArr[i] = new bit(false);
        }
    }

    public longword read(longword address) throws InvalidBitException {
        longword printRead = new longword();
        //Store length of a position, size of a longword.
        int longwordPos = 32;

        //TEMP COMMENT OUT

        if(address.getSigned() > 255 || address.getSigned() < 0){
            throw new InvalidBitException("Out Of Memory Bounds.");
        }
        else {
            int count = address.getSigned() * longwordPos;

            for(int i = printRead.localbit.length-1; i >= 0; i--){
                //Make sure that the values in mem are populated, if not, exit.
                if(memoryArr[count] == null){
                    throw new InvalidBitException("Null at specified memory location. Exiting...");
                }
                else /*Read*/ {
                    printRead.localbit[i] = memoryArr[count];
                    count++;
                }
            }
        }
        return printRead;
    }

    public void write(longword address, longword value) throws InvalidBitException {
        int longwordPos = 32;

        //Make sure within bounds
        if(address.getSigned() > 255 || address.getSigned() < 0){
            throw new InvalidBitException("Out of Memory Bounds.");
        }
        else {
            int count = address.getSigned() * longwordPos;

            //Write
            for(int i = value.localbit.length - 1; i >= 0; i-- ){
                memoryArr[count] = value.localbit[i];
                count++;
            }
        }
    }

    /*
    Method for writing manually to specific bytes as specified by the stack pointer.
     */
    public void manualWrite(int byteNumber, longword value){

        int count = byteNumber * 8;

        for(int i = value.localbit.length - 1; i >= 0; i-- ){
            if(count != 8192){
                memoryArr[count] = value.localbit[i];
                count++;
            }
            else break;
        }

    }

    public void bytePrint(){
        //For use in the interruption opcode call, makes it so that the
        //  array is being printed in the form of bytes and not in bits or longwords.

        for(int i = 0; i < 8192; i++) {
            if(i % 8 == 0){
                //Prints new line after every byte.
                System.out.println("");
            }
            System.out.printf(memoryArr[i].toString());
        }
        System.out.println("");
    }
}
