public class cpu_test3 {


    public static void main(String[] args) throws Exception {

        //Runs collection of unit tests in a combined fashion.
        alu_test.runTests();
        bit_test.runTests();
        longword_test.runTests();
        rippleAdder_test.runTests();
        multiplier_test.runTests();
        memory_test.runTests();
        cpu_test1.runTests();
        assembler_test.runTests();
        cpu_test2.runTests();
        runTests();

    }

    public static void runTests() throws Exception{

        computer stackTest = new computer();

        String[] input = new String[2];

        String[] call = new String[]{"call 6"};
        String[] psh = new String[]{"push R11"};
        String[] pop = new String[]{"pop"};
        String[] int0 = new String[]{"interrupt 0"};
        String[] halt = new String[]{"halt"};
        String[] int1 = new String[]{"interrupt 1"};
        String[] ret = new String[]{"return"};

        String callString = assembler.assemble(call);
        String interrupt0String = assembler.assemble(int0);
        String haltString = assembler.assemble(halt);
        String interrupt1String = assembler.assemble(int1);
        String returnString = assembler.assemble(ret);
        String push = assembler.assemble(psh);
        String popString = assembler.assemble(pop);

        input[0] = callString;
        input[1] = interrupt0String;

        //CALL TEST

        //Calls the 6th byte and prints the succeeding entries to the stack pointer.
        //Then prints out memory in 8 byte blocks.
        stackTest.preLoadCurrentInstruction(input);
        stackTest.run();

        input[0] = haltString;
        input[1] = interrupt1String;

        stackTest.preLoadCurrentInstruction(input);
        stackTest.run();

        //PUSH TEST
        input[0] = push;
        input[1] = returnString;

        //Note: R11 is empty at push, so return prints empty members
        stackTest.preLoadCurrentInstruction(input);
        stackTest.run();

        //POP TEST
        input[0] = popString;
        input[1] = haltString;

        stackTest.preLoadCurrentInstruction(input);
        stackTest.run();


    }



}
