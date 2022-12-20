import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class assembler {

    // TOKENS FOR INSTRUCTION BUILDING
    private static final String AND = "1000";
    private static final String OR = "1001";
    private static final String XOR = "1010";
    private static final String NOT = "1011";
    private static final String LFTSHFT = "1100";
    private static final String RGHTSHFT = "1101";
    private static final String ADD = "1110";
    private static final String SUB = "1111";
    private static final String MULT = "0111";

    private static final String HALT = "0000";
    private static final String MOVE = "0001";
    private static final String INTERRUPT = "0010";
    private static final String JUMP = "0011";
    private static final String COMPARE = "0100";
    private static final String BRANCH = "0101";
    private static final String STACK = "0110";

    public static HashMap<String, String> registers = new HashMap<String, String>();

    public static String assemble(String[] command) throws Exception {

        //So this is supposed to return the string of binary from this method.
        String built = "";

        //Delimits on spaces to get parts of command
        String[] commands = command[0].split(" ");

        //Hash map init for register ret
        registers.put("R0", "0000");
        registers.put("R1", "0001");
        registers.put("R2", "0010");
        registers.put("R3", "0011");
        registers.put("R4", "0100");
        registers.put("R5", "0101");
        registers.put("R6", "0110");
        registers.put("R7", "0111");
        registers.put("R8", "1000");
        registers.put("R9", "1001");
        registers.put("R10", "1010");
        registers.put("R11", "1011");
        registers.put("R12", "1100");
        registers.put("R13", "1101");
        registers.put("R14", "1110");
        registers.put("R15", "1111");

        //Find the command that is being inputted.
        //The function should return before hitting the end of the switch.
        switch(commands[0]){

            //Halt Case
            case "halt":
                built = built + HALT;
                built = built + "000000000000";
                return built;

            //Move Case
            case "move":
                built = built + MOVE;
                if(commands[1] != null){
                    String regNum = registers.get(commands[1]);
                    if(regNum == null){
                        throw new Exception("Invalid Argument!");
                    }

                    built = built + regNum;
                    if(commands[2] != null){
                        //Converts string to int
                        int val = Integer.parseInt(commands[2]);

                        //Stores int as binary number
                        String holdingStr = Integer.toBinaryString(val);

                        //If val is negative,
                        if(val < 0){

                            //Convert to 2 compliment rep and grab only last 8 for formatting
                            String negProof = holdingStr.substring(holdingStr.length()-8);

                            //Pass in and finish building with 2s compliment value
                            built = moveHelper(negProof, built);
                        }
                        //If val is positive or 0
                        else{
                            //Pass in without 2s and perform normal instruction building
                            built = moveHelper(holdingStr, built);
                        }
                        return built;
                    }
                }
                else {
                    throw new Exception("Not enough parameters!");
                }
                break;

            // Interrupt Case
            case "interrupt":
                if(Objects.equals(commands[1], "1")){
                    built = built + INTERRUPT;
                    built = built + "111111111111";
                    return built;
                }
                else if(Objects.equals(commands[1], "0")){
                    built = built + INTERRUPT;
                    built = built + "111111111110";
                    return built;
                }
                break;

                //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
                //~~~~~~~~~~~~~~~~~~~~~~START OF ALU COMMANDS~~~~~~~~~~~~~~~~~~~~~~~\
                //\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\

            // And Case
            case "and":
                built = built + AND;

                built = aluHelper(commands, built);

                break;


            // Or Case
            case "or":
                built = built + OR;

                built = aluHelper(commands, built);

                break;

            //XOR Case
            case "xor":
                built = built + XOR;

                built = aluHelper(commands, built);

                break;

            //NOT Case
            case "not":
                built = built + NOT;

                built = aluHelper(commands, built);

                break;

            //Left Shift Case
            case "leftshift":
                built = built + LFTSHFT;

                built = aluHelper(commands, built);

                break;

            //Right Shift Case
            case "rightshift":
                built = built + RGHTSHFT;

                built = aluHelper(commands, built);

                break;

            // Add Case
            case "add":
                built = built + ADD;

                built = aluHelper(commands, built);

                break;

            //Subtract Case
            case "subtract":
                built = built + SUB;

                built = aluHelper(commands, built);

                break;

            case "multiply":
                built = built + MULT;

                built = aluHelper(commands, built);

                break;


            //                             FOR ASSIGNMENT 9!!!!!!!!!!!!!!!!!!!!!!!!
            // Jump Case
            case "jump":
                built = built + JUMP + "0000";

                //Converts string to int
                int val = Integer.parseInt(commands[1]);

                if(val < 0){
                    throw new Exception("Register cannot be negative!");
                }

                //Stores int as binary number
                String holdingStr = Integer.toBinaryString(val);

                built = moveHelper(holdingStr, built);

                if(built.length() > 15){
                    return built;
                }
                break;

            // Compare Case
            case "compare":
                //Compare must be called before branch, as calling compare will
                //  set the necessary info for branch to run.

                built = built + COMPARE;
                //Since compare has no "argument" in the second 4 bits its set to 0.
                built = built + "0000";

                String reg1 = registers.get(commands[1]);
                String reg2 = registers.get(commands[2]);

                built = built + reg1 + reg2;

                if(built.length() > 15){
                    return built;
                }
                break;

            // Branch Case
            case "branchIfEqual":
            case "branchIfNotEqual":
            case "branchIfLessThan":
            case "branchIfGreaterThan":
            case "branchIfLessThanEqualTo":
            case "branchIfGreaterThanEqualTo":
                built = built + BRANCH;

                if(commands[0].equals("branchIfEqual")){
                    built = built + "01";
                }
                else if(commands[0].equals("branchIfNotEqual")){
                    built = built + "00";
                }
                else if(commands[0].equals("branchIfLessThan")){
                    //This is where things get tricky, come back to it
                    built = built + "00";
                }
                else if(commands[0].equals("branchIfGreaterThan")){
                    built = built + "10";
                }
                else if(commands[0].equals("branchIfLessThanEqualTo")){
                    built = built + "01";
                }
                else if(commands[0].equals("branchIfGreaterThanEqualTo")){
                    built = built + "11";
                }

                //Now build the remaining 2 bytes,

                //Check for invalid arguments
                if(commands[1] == null){
                    throw new Exception("Invalid Branch Argument!");
                }
                else{
                    int branchVal = Integer.parseInt(commands[1]);

                    String stringHolder = Integer.toBinaryString(branchVal);

                    //This checks and sets the negative bit if the value is negative.
                    //Uses modified movehelper without function call
                    if(branchVal < 0){
                        //Branch val is negative!
                        built = built + "1";

                        //Convert to 2 compliment rep and grab only last 9 for formatting
                        String negativeProof = stringHolder.substring(stringHolder.length()-9);

                        //Stores number as a decimal value
                        int i = Integer.parseInt(stringHolder, 10);

                        //Formats as 8 bit bin number
                        String result = String.format("%09d", i);

                        //Check to make sure formatting is correct and won't create invalid instruction
                        if(result.length() > 9){
                            throw new Exception("Number too large!");
                        }
                        else{
                            //Finishes building the string.
                            built = built + result;
                            return built;
                        }

                    }
                    else{
                        built = built + "0";

                        //Stores number as a decimal value
                        int i = Integer.parseInt(stringHolder, 10);

                        //Formats as 8 bit bin number
                        String result = String.format("%09d", i);

                        //Check to make sure formatting is correct and won't create invalid instruction
                        if(result.length() > 9){
                            throw new Exception("Number too large!");
                        }
                        else{
                            //Finishes building the string.
                            built = built + result;
                            return built;
                        }
                    }
                }

            // Stack Case
            case "call":
            case "return":
            case "push":
            case "pop":
                built = built + STACK;

                switch (commands[0]) {
                    case "call" -> {
                        //Tacks on the two bits not used for addr
                        built = built + "10";
                        int branchVal = Integer.parseInt(commands[1]);
                        String stringHolder = Integer.toBinaryString(branchVal);

                        //Stores number as a decimal value
                        int i = Integer.parseInt(stringHolder, 10);

                        built = built + "0";

                        //Formats as 8 bit bin number
                        String result = String.format("%09d", i);

                        //Check to make sure formatting is correct and won't create invalid instruction
                        if (result.length() > 9) {
                            throw new Exception("Number too large!");
                        } else {
                            //Finishes building the string.
                            built = built + result;
                            return built;
                        }
                    }
                    case "return" -> {
                        //Builds the return string instantly and returns the instruction
                        built = built + "110000000000";
                        return built;
                    }
                    case "push" -> {
                        //Builds the push command padding
                        built = built + "00000000";

                        //gets the reg num
                        String regNum = registers.get(commands[1]);
                        built = built + regNum;

                        //returns instruction
                        return built;
                    }
                    case "pop" -> {
                        //Builds the push command padding
                        built = built + "010000000000";

                        //GETTING REG NUM IS REDUNDANT!!! TOP OF STACK ALWAYS POPPED

                        //gets the reg num
                        //String regNum = registers.get(commands[1]);
                        //built = built + regNum;

                        //returns instruction
                        return built;
                    }
                }
                //Shouldnt hit this condition honestly
                break;

        }

        if(built.equals("")){
            throw new Exception("Instruction Building Error!! No States reached.");
        }

        return built;
    }

    // ALU Helper Method for cases that deal with ALU ops
    public static String aluHelper(String[] commands, String built) throws Exception {

        if(commands[1] != null) {
            String regNum = registers.get(commands[1]);
            if (regNum != null) {

                //Add reg num to string
                built = built + regNum;

                if(commands[2] != null){
                    String secondReg = registers.get(commands[2]);

                    if(secondReg == null){
                        throw new Exception("Invalid Argument!");
                    }
                    //Add next reg to string
                    built = built + secondReg;

                    //Adds empty values on the end for alu to store result in.
                    built = built + "0000";
                }
                else{
                    throw new Exception("Invalid Argument!");
                }
            }
            else{
                throw new Exception("Invalid Argument!");
            }
        }
        return built;
    }

    public static String moveHelper(String arg, String built) throws Exception {

        //Stores number as a decimal value
        int i = Integer.parseInt(arg, 10);

        //Formats as 8 bit bin number
        String result = String.format("%08d", i);

        //Check to make sure formatting is correct and won't create invalid instruction
        if(result.length() > 8){
            throw new Exception("Number too large!");
        }
        else{
            //Finishes building the string.
            built = built + result;
            return built;
        }
    }


}
