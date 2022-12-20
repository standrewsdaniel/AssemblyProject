public class assembler_test {

    //I was going to use a string and delimit on spaces, but it seems the instructions
    //  want us to use a string array, so I am following that verbatim.
    public static void runTests() throws Exception {

        // This was written assuming that all operations in previous code are correct.
        //  If there are any errors with how things run or print, please mind whether
        //  it was a mistake from the code on this assignment or a previous one.

        String andCase[] = new String[]{"and R1 R11"};
        String orCase[] = new String[]{"or R4 R2"};
        String xorCase[] = new String[]{"xor R7 R2"};
        String notCase[] = new String[]{"not R2 R2"};
        String leftShiftCase[] = new String[]{"leftshift R1 R9"};
        String rightShiftCase[] = new String[]{"rightshift R1 R15"};
        String addCase[] = new String[]{"add R1 R2"};
        String subCase[] = new String[]{"subtract R10 R6"};
        String multCase[] = new String[]{"multiply R2 R1"};

        String haltCase[] = new String[]{"halt"};
        String moveCase[] = new String[]{"move R1 100"};
        String negMoveCase[] = new String[]{"move R1 -1"};
        String interruptMemCase[] = new String[]{"interrupt 0"};
        String interruptRegCase[] = new String[]{"interrupt 1"};

        //DO NOT NEED TO BE TESTED THIS ASSIGNMENT
        String jumpCase[] = new String[]{"jump", "R1", "R2"};
        String compareCase[] = new String[]{"compare", "R1", "R2"};
        String branchCase[] = new String[]{"branch", "R2", "R5"};
        String stackCase[] = new String[]{"stack", "R2", "R6"};

        //Local instruction string to print for unit testing
        String instruction = "";
        String[] input = new String[2];

        computer temp = new computer();

        instruction = assembler.assemble(andCase);
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        System.out.println("and Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(orCase);
        System.out.println("or Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(xorCase);
        System.out.println("xor Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(notCase);
        System.out.println("not Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(leftShiftCase);
        System.out.println("LeftShift Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(rightShiftCase);
        System.out.println("RightShift Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(addCase);
        System.out.println("Add Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(subCase);
        System.out.println("Subtract Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(multCase);
        System.out.println("Multiply Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(moveCase);
        System.out.println("Move Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(negMoveCase);
        System.out.println("Neg Move Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(haltCase);
        System.out.println("Halt Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(interruptMemCase);
        System.out.println("Memory Interrupt Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");

        //Testing to make sure whole operations work when done in pc
        input[0] = instruction;
        input[1] = instruction;

        temp.preLoad(input);
        temp.run();

        System.out.println("~~~~~~~~~~~~~~~~~~~");
        instruction = assembler.assemble(interruptRegCase);
        System.out.println("Register Interrupt Instruction:");
        System.out.println(instruction);
        System.out.println("~~~~~~~~~~~~~~~~~~~");




    }


}
