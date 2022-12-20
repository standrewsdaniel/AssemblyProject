/*
FileName: computer.java
Author: Daniel St Andrews
Purpose: Memory class that implements a 1024 byte bit array and allows for reading and writing.
ICSI404
Fall 2022
Professor Phipps
 */

import java.util.Arrays;
import java.util.PropertyPermission;

public class computer {


    int temp = 0;

    //~~~~~~~~GLOBALS~~~~~~~~
    longword SP = new longword(1020);
    private memory mem = new memory();
    //If the halted bit is set to true it is running, if its set to false its halted
    bit haltedStatus = new bit();

    //Count in where you are in memory
    longword PC = new longword();
    longword currentInstruction = new longword();
    longword[] register = new longword[16];
    longword op1 = new longword();
    longword op2 = new longword();
    longword result = new longword();
    bit[] opCode = new bit[4];

    //Stores the integer value read from the bottom 8 of a current instruction, for MOVE.
    int integerValue;

    int count = 0;

    //Note, keep track of this in case of the register number being not reset in between runs. Make sure indexing is correct.
    int registerNumber;


    //Comparison code storage, two ways for now
    bit[] cc = new bit[2];
    String compCode = "";
    //Defaults to normal increment
    int branchAmount = 16;
    boolean doBranch = false;
    boolean currInstPreloadFlag = false;



    public computer() throws InvalidBitException {

        for(int i = 0; i < register.length; i++){
            register[i] = new longword(0);
        }
    }

    public void run() throws InvalidBitException {

        //While the computer is not halted, loop
        while(haltedStatus.getValue())
        {
            fetch();
            decode();
            execute();
            store();
            //Shift to the second part of the register
            currentInstruction = currentInstruction.leftShift(16);
            decode();
            execute();
            store();
        }
        //Computer is halted / not running.
        System.out.println("Halted. Exiting...");
    }

    public void fetch() throws InvalidBitException {
        //Fetch method

        //Checks to see if move is happening, if not, perform op
        String operator = Arrays.toString(opCode);
        //Checks to see if a jump is being performed, doesnt update currinstruction if so, otherwise increment.
        if(operator.equals("[0, 0, 1, 1]")){
            PC = rippleAdder.add(PC, new longword(16));
        }
        //BRANCH PATH
        else if(doBranch){
            //Increment by the amount specified in the branch command
            PC = rippleAdder.add(PC, new longword(branchAmount));
            currentInstruction = mem.read(PC);
            doBranch = false;
        }
        else if(currInstPreloadFlag){
            PC = rippleAdder.add(PC, new longword(16));
            currInstPreloadFlag = false;
        }
        else {
            currentInstruction = mem.read(PC);
            PC = rippleAdder.add(PC, new longword(16));
        }


    }

    public void decode(){
        //Takes current instruction and gets opCode and 2 registers to work with.
        op1 = currentInstruction;
        op2 = currentInstruction;

        op1 = op1.leftShift(20);
        op1 = op1.rightShift(28);

        op2 = op2.leftShift(24);
        op2 = op2.rightShift(28);
    }

    public void execute() throws InvalidBitException {
        //~~~~SANITY NOTE:~~~~
        //OpCode - bits 16-19
        //Register - bits 20-23
        //Value - bits 24-31
        //8th bit to check on is bit 23
        //Last bit is 31

        longword valueStorage = new longword();

        longword temporaryRegister = new longword(-256);

        longword valueRegister = new longword();

        longword modifiedRegister = new longword();

        //Getting opCode
        opCode[0] = currentInstruction.localbit[0];
        opCode[1] = currentInstruction.localbit[1];
        opCode[2] = currentInstruction.localbit[2];
        opCode[3] = currentInstruction.localbit[3];

        //HALT - Checks for halt instruction and stops the pc if true.
        String operator = Arrays.toString(opCode);
        if(operator.equals("[0, 0, 0, 0]")){
            haltedStatus.set(false);
        }
        //MOVE - Moves from one specified register into another.
        else if(operator.equals("[0, 0, 0, 1]")){
            //Move OP read

            //Get register number after finding out move is called.
            valueRegister.localbit[28] = currentInstruction.localbit[4];
            valueRegister.localbit[29] = currentInstruction.localbit[5];
            valueRegister.localbit[30] = currentInstruction.localbit[6];
            valueRegister.localbit[31] = currentInstruction.localbit[7];

            registerNumber = valueRegister.getSigned();

            if(registerNumber > 16){
                throw new InvalidBitException("Register Number Is Invalid. (> 16)");
            }
            else{
                longword value = new longword(0);
                int position = 31;
                boolean tempRet;
                for(int i = value.localbit.length-17; i > 7; i--){
                    tempRet = currentInstruction.localbit[i].getValue();
                    bit assnValue = new bit(tempRet);
                    value.localbit[position] = assnValue;
                    position--;
                }
                integerValue = value.getSigned();

                //Put the value into the register before extending. Only extend if the number is negative.
                register[registerNumber].set(integerValue);

                //Checking second register, first register checked (1339-7811-9090**)
                //SIGN EXTENSION
                if(!register[registerNumber].localbit[24].getValue()){
                    register[registerNumber].leftShift(24);
                    register[registerNumber].rightShift(24);
                    result = register[registerNumber];
                }
                else{
                    register[registerNumber].leftShift(24);
                    register[registerNumber].rightShift(24);
                    result = rippleAdder.add(register[registerNumber], temporaryRegister);
                    //Make sure that the finished version of the register is put into the correct location
                    register[registerNumber] = result;
                }

            }
        }
        //JUMP - Jumps to register specified by user
        else if(operator.equals("[0, 0, 1, 1]")){
            //Once the path goes here, set current instruction to specified register and re run.

            longword value = new longword(0);
            int position = 31;
            boolean tempRet;
            for(int i = value.localbit.length-17; i > 7; i--){
                tempRet = currentInstruction.localbit[i].getValue();
                bit assnValue = new bit(tempRet);
                value.localbit[position] = assnValue;
                position--;
            }
            integerValue = value.getSigned();

            if(integerValue > 16){
                throw new InvalidBitException("Register number is invalid.");
            }

            //Makes it so that the computer reads instruction from register[index]
            currentInstruction = register[integerValue];

        }
        //INTERRUPT
        else if(operator.equals("[0, 0, 1, 0]")){
            //This indicates interrupt code
            if(currentInstruction.localbit[15].getValue()){
                //Print all 1024 bytes of memory to the screen. In byte increments.
                mem.bytePrint();
                temp++;
            }
            else{
                //Print all the registers to the screen.
                for(int i = 0; register.length > i; i++){
                    System.out.println(register[i].toString());
                }
            }

        }
        //COMPARE OP CODE
        else if(operator.equals("[0, 1, 0, 0]")){

            //Get the last 8 bits from the current instruction.
            longword value = new longword(0);
            int position = 31;
            boolean tempRet;
            for(int i = value.localbit.length-17; i > 7; i--){
                tempRet = currentInstruction.localbit[i].getValue();
                bit assnValue = new bit(tempRet);
                value.localbit[position] = assnValue;
                position--;
            }

            //Could do this with a loop but Ill just do it straight up since its only 4 bits.
            //Gets the register number, checks that registers value, and stores it for comparison.
            longword firstReg = new longword();
            firstReg.localbit[31] = value.localbit[27];
            firstReg.localbit[30] = value.localbit[26];
            firstReg.localbit[29] = value.localbit[25];
            firstReg.localbit[28] = value.localbit[24];
            int regNum = firstReg.getSigned();
            int firstRegValue = register[regNum].getSigned();

            longword secondReg = new longword();
            secondReg.localbit[31] = value.localbit[31];
            secondReg.localbit[30] = value.localbit[30];
            secondReg.localbit[29] = value.localbit[29];
            secondReg.localbit[28] = value.localbit[28];
            regNum = secondReg.getSigned();
            int secRegValue = register[regNum].getSigned();


            //Sets comparison code accordingly.
            //Greater than only (not equal)

            //Greater than equal to
            if(firstRegValue >= secRegValue){
                cc[0] = new bit(false);
                cc[1] = new bit(true);
                compCode = "01";
                //If strictly greater than, change code
                if(firstRegValue > secRegValue){
                    cc[0] = new bit(false);
                    cc[1] = new bit(true);
                    compCode = "10";
                }
                //If also equal,
                else if(firstRegValue == secRegValue){
                    cc[0] = new bit(false);
                    cc[1] = new bit(true);
                    compCode = "11";
                }
            }
            //Less than or equal to
            else if(firstRegValue <= secRegValue){
                cc[0] = new bit(false);
                cc[1] = new bit(false);
                compCode = "00";
                if(firstRegValue == secRegValue){
                    cc[0] = new bit(false);
                    cc[1] = new bit(true);
                    compCode = "01";
                }
                else if(firstRegValue != secRegValue){
                    cc[0] = new bit(false);
                    cc[1] = new bit(true);
                    compCode = "00";
                }
            }


        }
        //BRANCH CODE:
        else if(operator.equals("[0, 1, 0, 1]")){

            if(compCode.equals("")){
                throw new InvalidBitException("Branch command called without existing comparison! Compare first!");
            }

            //Get the last 8 bits from the current instruction.
            longword value = new longword(0);
            int position = 31;
            boolean tempRet;
            for(int i = value.localbit.length-17; i > 7; i--){
                tempRet = currentInstruction.localbit[i].getValue();
                bit assnValue = new bit(tempRet);
                value.localbit[position] = assnValue;
                position--;
            }
            int localIntegerValue = value.getSigned();
            branchAmount = localIntegerValue;
            String comparisonCheck = "";


            //Now, check if the comp code matches the instruction, and if it does, branch
            if(currentInstruction.localbit[4].getValue()){
                //Can either use getValue here ^ or compare to bit directly, same result.

                comparisonCheck = comparisonCheck + "1";
                if(currentInstruction.localbit[5].getValue()){
                    comparisonCheck = comparisonCheck + "1";
                }
                else {
                    comparisonCheck = comparisonCheck + "0";
                }
            }
            else{
                comparisonCheck = comparisonCheck + "0";
                if(currentInstruction.localbit[5].getValue()){
                    comparisonCheck = comparisonCheck + "1";
                }
                else {
                    comparisonCheck = comparisonCheck + "0";
                }
            }

            if(compCode.equals(comparisonCheck)){
                doBranch = true;
            }
            else{
                //This is just a path to show that the code does not perform the branch if the command is not valid.
                //The PC will simply increment normally.
            }

        }
        //STACK CODE
        else if(operator.equals("[0, 1, 1, 0]")){
            String stackCode = "";


            //Build the stack code to tell what stack op is happening
            if(currentInstruction.localbit[4].getValue()){
                stackCode += "1";
                if(currentInstruction.localbit[5].getValue()){
                    stackCode += "1";
                }
                else{
                    stackCode += "0";
                }
            }
            else{
                stackCode += "0";
                if(currentInstruction.localbit[5].getValue()){
                    stackCode += "1";
                }
                else{
                    stackCode += "0";
                }
            }

            int index;

            //Check the stack code and perform operation accordingly
            if(stackCode.equals("")){
                throw new InvalidBitException("Stack is not built. Stopping...");
            }
            else if(stackCode.equals("00")){
                //Push Path

                int tempRegNum;
                int valueStore;
                longword conversion = new longword();

                //Gets the reg num from the instruction
                conversion.localbit[28] = currentInstruction.localbit[12];
                conversion.localbit[29] = currentInstruction.localbit[13];
                conversion.localbit[30] = currentInstruction.localbit[14];
                conversion.localbit[31] = currentInstruction.localbit[15];

                //Should be the value from the register number
                tempRegNum = conversion.getSigned();
                valueStore = register[tempRegNum].getSigned();

                //grabs the index of the mem to write to
                index = SP.getSigned();
                //Writes to the mem loc
                mem.manualWrite(index, new longword(valueStore));
                //Decrements sp
                spDec();
            }
            else if(stackCode.equals("01")){
                //Pop path
                index = SP.getSigned();
                mem.manualWrite(index, new longword(0));
                spInc();
            }
            else if(stackCode.equals("10")){
                //Call Path
                //pushes instruction from mem loc onto stack
                longword storage = new longword();
                int ind = 31;

                for(int i = 15; i > 6; i--){
                    storage.localbit[ind] = currentInstruction.localbit[i];
                    ind--;
                }

                longword trueCallValue = new longword();
                trueCallValue = mem.read(storage);

                index = SP.getSigned();
                mem.manualWrite(index, trueCallValue);
                spInc();
            }
            else if(stackCode.equals("11")){
                //Return path
                //Prints the stack

                for(int i = SP.getSigned() * 8; i < 8192; i++){
                    if(i % 8 == 0){
                        System.out.println("\nIndex: " + i / 8 + " ");
                    }
                    System.out.print(mem.memoryArr[i].toString());
                }
                System.out.println();

            }
        }

    }

    public void store() throws InvalidBitException {

        //Checks to see if move is happening, if not, perform op
        String operator = Arrays.toString(opCode);
        if(operator.equals("[0, 0, 0, 1]")) {
            //Copy the value from result into the register indicated by instruction.
            //This ignores writing into memory sequentially and allows store to be overridden by the move position instruction.
            if(count < 16){
                register[registerNumber] = result;
            }
        }
        else if(operator.equals("[null, null, null, null]")){
            //This is a temp fix for first run. When preloading opcode is null, store regardless.
            register[count] = result;
            mem.write(PC, result);
        }
        else if(operator.equals("[0, 0, 1, 1]")){
            //Jump condition, current instruction needs to be changed here
            currentInstruction = register[integerValue];

        }
        else{
            //Takes control bits and passes it into alu along with op1 and op2.
            result = alu.doOp(opCode, op1, op2);

            //Copy the value from result into the register indicated by instruction.
            //Temp check to make sure doesnt iterate outside memory.
            if(count < 16){
                register[count] = result;
                mem.write(PC, result);
            }
        }
    }

    public void preLoad(String[] arr) throws InvalidBitException {
        //Converts string to register. Reads into result and stores.
        longword localLongword = new longword();
        int localIntStorage = 0;
        int longwordCount = 0;
        boolean localBoolean = false;


        for(int i = 0; i < arr.length; i++){
            char[] currChar = arr[i].toCharArray();
            for(int j = 0; j < arr[i].length(); j++){
                localIntStorage = Character.getNumericValue(currChar[j]);
                if(localIntStorage == 1){
                    localBoolean = true;
                }
                localLongword.localbit[longwordCount].set(localBoolean);
                localBoolean = false;
                longwordCount++;
            }
        }
        result = localLongword;
        store();
    }

    //This was made for testing purposes.
    //Allows the current instruction to be set on a pc that has already read a halt code, to execute one more time.
    //Allows for a machine that has already read to a halt point in memory to preserve its state and continue
    //  executing. Mainly important for commands that require 3+ instructions to execute cleanly, like branch.
    public void preLoadCurrentInstruction(String[] arr) throws InvalidBitException {
        //Converts string to register. Reads into result and stores.
        longword localLongword = new longword();
        int localIntStorage = 0;
        int longwordCount = 0;
        boolean localBoolean = false;
        haltedStatus = new bit(true);


        for(int i = 0; i < arr.length; i++){
            char[] currChar = arr[i].toCharArray();
            for(int j = 0; j < arr[i].length(); j++){
                localIntStorage = Character.getNumericValue(currChar[j]);
                if(localIntStorage == 1){
                    localBoolean = true;
                }
                localLongword.localbit[longwordCount].set(localBoolean);
                localBoolean = false;
                longwordCount++;
            }
        }
        currInstPreloadFlag = true;
        currentInstruction = localLongword;
        result = localLongword;
        store();
    }

    //Just for snagging the comp code if branch is called.
    public void getCompCode(){
        System.out.println(compCode);;
    }

    public void spInc() throws InvalidBitException {
        SP = rippleAdder.add(SP, new longword(4));
    }

    public void spDec() throws InvalidBitException{
        SP = rippleAdder.subtract(SP, new longword(4));
    }

}
