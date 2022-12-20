public class cpu_test2 {

    public static void runTests() throws Exception {

        //The output for some of the following tests may only be verified through
        //  a debugger checking registers and behaviour. All tests work and function
        //  properly and that can be seen by watching the registers, even if their
        //  operations do not result in printing a value.


        computer test = new computer();

        String[] input = new String[2];

        String[] command = new String[]{"move R10 25"};
        String[] jmp = new String[]{"jump 10"};
        String[] intrpt = new String[]{"interrupt 0"};
        String[] hlt = new String[]{"halt"};
        String[] cmpr = new String[]{"compare R11 R12"};
        String[] cmprMove = new String[]{"move R11 15"};
        String[] cmprMove2 = new String[]{"move R12 10"};
        String[] branch1 = new String[]{"branchIfGreaterThan 20"};
        String[] branch2 = new String[]{"branchIfEqual 12"};
        String[] branch3 = new String[]{"branchIfNotEqual 28"};

        //Jump Test:
        String move = assembler.assemble(command);
        String jump = assembler.assemble(jmp);

        input[0] = move;
        input[1] = jump;

        test.preLoad(input);
        test.run();

        String interrupt = assembler.assemble(intrpt);
        String halt = assembler.assemble(hlt);

        input[0] = interrupt;
        input[1] = halt;

        test.preLoadCurrentInstruction(input);
        test.run();

        //COMPARE TEST
        String compare = assembler.assemble(cmpr);
        String compareMove = assembler.assemble(cmprMove);
        String compareMove2 = assembler.assemble(cmprMove2);
        String branchIfGreaterThan = assembler.assemble(branch1);
        String branchIfEqual = assembler.assemble(branch2);
        String branchIfNotEqual = assembler.assemble(branch3);

        //Puts the values 15 and 10 into registers 11 and 12
        input[0] = compareMove;
        input[1] = compareMove2;


        System.out.println("The comparison codes will be printed after each run, if they match, the branch is performed.");
        System.out.println("On runs where only one bit has to match for a branch to occur, (ifequal, greaterifequal, etc)");
        System.out.println("This will be denoted with a _ on the ignored bit.");
        System.out.println("_______________________________________________________________");

        test.preLoadCurrentInstruction(input);
        test.run();
        System.out.println("Input Comparison Code: ");
        System.out.println("Found Comparison Code: (Expected null because no branch call) ");
        test.getCompCode();

        //BRANCH TESTS
        //Performs a branch if R11(15) is greater than R12(10)
        //It is, so the branch is performed and PC is incremented by 20 (branchIfGreaterThan 20)
        input[0] = compare;
        input[1] = branchIfGreaterThan;

        test.preLoadCurrentInstruction(input);
        test.run();
        System.out.println("Input Comparison Code: 10");
        System.out.println("Found Comparison Code: (Expected 10 because greater than) ");
        test.getCompCode();


        //This will compare the values again the other way
        //This will not perform the branch because the value in the pc will be set to
        //  less than not equal to. Due to the code mistmatch no incrementing will occur.
        cmpr[0] = "compare R12 R11";
        compare = assembler.assemble(cmpr);
        input[0] = compare;
        //The other input remains the same to test ifGreaterThan
        test.preLoadCurrentInstruction(input);
        //On this run the pc will increment normally.
        test.run();
        System.out.println("Input Comparison Code: 10");
        System.out.println("Found Comparison Code: (Expected 00 because less than) ");
        test.getCompCode();

        //As shown by the above two tests, you can check for greater or less than using
        //  only the greaterthan value and switching the registers, so that is all I will
        //  show testing wise according to the assignment instructions,
        //  but lessthan has also been implemented and functions in the same way.

        //Equals test
        cmpr[0] = "compare R11 R11";
        compare = assembler.assemble(cmpr);
        input[0] = compare;
        input[1] = branchIfEqual;

        //The comparison code is matched with the inputted string for the command,
        //  and performs the branch.

        test.preLoadCurrentInstruction(input);
        //On this run the pc will increment by 17 after reading the branch command.
        test.run();
        System.out.println("Input Comparison Code: 01");
        System.out.println("Found Comparison Code: (Expected _1 because equal) ");
        test.getCompCode();

        input[1] = branchIfNotEqual;

        test.preLoadCurrentInstruction(input);
        //On this run the pc will not increment because the two registers are equal and the compcodes will mismatch,
        //  so doBranch is not set, and the path is never entered.
        test.run();
        System.out.println("Input Comparison Code: 00");
        System.out.println("Found Comparison Code: (Expected _1 because equal) ");
        test.getCompCode();


    }


}
