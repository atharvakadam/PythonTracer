/**
 * Name - Atharva Kadam
 * SBU ID. - 111593743
 * Recitation No. - R-09
 * CSE 214 - Section 02
 * Homework 3
 */



import java.util.Scanner;
import java.util.Stack;
import java.io.*;

/**
 * This Class is used to calculate the order of n and log(n) complexity
 */
class Complexity{
    private int n_power;
    private int log_power;

    /**
     * This is a default empty constructor for Complexity
     */
    public Complexity(){

    }

    /**
     * This is a constructors with parameters.
     * @param n_power
     * @param log_power
     */
    public Complexity(int n_power,int log_power){
        this.n_power = n_power;
        this.log_power = log_power;
    }


    /**
     * This is an accessor method to get the N_power
     * @return the order of n
     */
    public int getN_power() {
        return n_power;
    }


    /**
     * This is an accessor method to get the log_power
     * @returnthe the order of log(n)
     */
    public int getLog_power() {
        return log_power;
    }


    /**
     * This is an mutator method to set the n_power
     * @param n_power
     */
    public void setN_power(int n_power) {
        this.n_power = n_power;
    }


    /**
     * This is an mutator method to set the log_power
     * @param log_power
     */
    public void setLog_power(int log_power) {
        this.log_power = log_power;
    }


    /**
     * This is used to return neatly formatted string in the required format.
     * @return Formatted string for Order of Complexity
     */
    @Override
    public String toString() {
        if (getN_power() ==0 && getLog_power() == 0){
            return "O(1)";
        }
        else if (getN_power()==1 && getLog_power() ==0){
            return "O(n)";
        }
        else if (getN_power()==0 & getLog_power()==1){
            return "O(log(n))";
        }
        else if (getN_power() ==1 && getLog_power() ==1){
            return "O(n*log(n))";
        }
        else if (getN_power()>1 && getLog_power() ==0){
            return "O(n^" + getN_power() + ")";
        }
        else if (getN_power() ==0 && getLog_power()>1){
            return "O(log(n)^" + getLog_power() + ")";
        }
        else {
            return "O(n^" + getN_power() +"log(n)^" + getLog_power() + ")";
        }

    }



}

/**
 * This class is used to create block objects in the stack.
 */
class CodeBlock{
    public static final int DEF = 0, FOR = 1, WHILE = 2, IF = 3, ELIF = 4, ELSE = 5;
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String name;
    private String loopVariable;
    String loopType;


    public static final String[] BLOCK_TYPES = {"def", "for", "while", "if", "elif", "else"};

    /**
     * This is a default constructor for class Codeblock
     */
    public CodeBlock(){
        this.loopType = "";
        this.blockComplexity = null;
        this.highestSubComplexity = null;
        this.name = "";
        this.loopVariable = "";


    }

    /**
     * This is a parameterized constructor for CodeBlock
     * @param blockComplexity
     */
    public CodeBlock(Complexity blockComplexity){
        this.blockComplexity = blockComplexity;
        this.loopType = "";
        //this.blockComplexity = null;
        this.highestSubComplexity = null;
        this.name = "";
        this.loopVariable = null;
        //this.highestSubComplexity = highestSubComplexity;


    }

    /**
     * This is an accessor method used to get Block Complexity
     * @return the block complexity of the specified block
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * This is an accessor method used to get Highest sub Complexity of the blocks within the main block
     * @return The highest sub Complexity of the specified block
     */
    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    /**
     * This is an accessor method used to get name of the block
     * @return name of the block
     */
    public String getName() {
        return name;
    }

    /**
     * This is an accessor method used to get loop variable in a while loop
     * @return returns loop Variable used to update while loop
     */
    public String getLoopVariable() {
        return loopVariable;
    }

    /**
     * This is an mutator method used to set Block Complexity
     * @param blockComplexity
     */
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    /**
     * This is an mutator method used to set Highest sub complexity
     * @param highestSubComplexity
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
    }

    /**
     * This is an mutator method used to set name of the block
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is an mutator method used to set loop variable of while block
     * @param loopVariable
     */
    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }

    /**
     * This is an accessor method used to set loop type of the block
     * @return loop type of the block
     */
    public String getLoopType() {
        return loopType;
    }

    /**
     * This is an mutator method used to set loop type of the block
     * @param loopType
     */
    public void setLoopType(String loopType) {
        this.loopType = loopType;
    }

    /**
     * This is method used to calculate total Complexity of the stack
     * @return Total complexity of the Stack
     */
    public Complexity getTotalComplexity()
    {
        Complexity totalcomplex = new Complexity();
        int npow=this.getBlockComplexity().getN_power()+this.getHighestSubComplexity().getN_power();
        int logpow=this.getBlockComplexity().getLog_power()+this.getHighestSubComplexity().getLog_power();
        totalcomplex.setN_power(npow);
        totalcomplex.setLog_power(logpow);
        return totalcomplex;

    }

}

/**
 * We have used the inbuilt Stack ADT
 */
class BlockStack {


}

/**
 * This class contains the trace file method and is the main public class which is used to trace through the file
 * and return the complexity
 */
public class PythonTracer {

    public static final int SPACE_COUNT = 4;

    /**
     * This is a method used to trace the python file and determine the overall order of complexity
     * @param filename
     * @return The overall order of complexity of the python file
     */
    public static Complexity traceFile(String filename) {
        Stack<CodeBlock> blockStack = new Stack<>();
        try {
            CodeBlock codeBlock = new CodeBlock();

            CodeBlock oldTop = new CodeBlock();
            CodeBlock Top = new CodeBlock();
            Complexity oldTopComplexity = new Complexity();
            Scanner file = new Scanner(new File(filename));
            //FileInputStream fis = new FileInputStream(filename);

            //InputStreamReader inStream = new InputStreamReader(fis);

            //BufferedReader reader = new BufferedReader(inStream);
            //String data = reader.readLine();

            while (file.hasNext()) {
                String line = file.nextLine();
                int spaces = (int)(line.chars().filter(Character::isWhitespace).count());//line.indexof(line.trim)
                if (!line.isEmpty() && !line.contains("#")) {
                    int numOfIndents = spaces/SPACE_COUNT;
                    //System.out.println(line);
                    while (numOfIndents<blockStack.size()){
                        if (numOfIndents == 0){
                            file.close();
                            return blockStack.peek().getBlockComplexity();
                        }
                        else {



                            oldTop = blockStack.pop();
                            oldTopComplexity = oldTop.getTotalComplexity();


                            if (oldTopComplexity.getN_power()> blockStack.peek().getHighestSubComplexity().getN_power()) {
                                blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                                Top.setHighestSubComplexity(oldTopComplexity);
                                System.out.println("Leaving the block " + " Updating the block ");
                                //System.out.println();

                            }
                            else if (oldTopComplexity.getN_power() == blockStack.peek().getHighestSubComplexity().getN_power() &&
                                    oldTopComplexity.getLog_power()>blockStack.peek().getHighestSubComplexity().getLog_power()){
                                blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                                Top.setHighestSubComplexity(oldTopComplexity);
                                System.out.println("Leaving the block " + "Updating the block ");
                            }
                            else {
                                System.out.println("Leaving the block ," + "Nothing to update");
                            }
                            System.out.println("Block Complexity: " + blockStack.peek().getBlockComplexity() + " " + "    Highest sub Complexity: " + Top.getHighestSubComplexity() +"\n");
                        }
                    }
                    //String keyword = "";
                    if (line.contains("for")||line.contains("while")||line.contains("def")||line.contains("if")||line.contains("elif")||line.contains("else")){
                        //Complexity highsubcomp = new Complexity(0,0);
                        if (line.contains("for")){
                            Complexity complexity1 = new Complexity(1,0);
                            Complexity complexity2 = new Complexity(0,1);

                            CodeBlock forBlock1;
                            if (line.endsWith(" N:")){
                                forBlock1 = new CodeBlock(complexity1);
                                forBlock1.setHighestSubComplexity(new Complexity(0,0));
                               // forBlock1.getBlockComplexity().setN_power(1);
                               // forBlock1.getBlockComplexity().setLog_power(0);
                                blockStack.push(forBlock1);
                                //System.out.println(forBlock1.getName());
                                forBlock1.setName(blockStack.peek().getName() + "" + "1.");
                                System.out.println("Entering block " + forBlock1.getName() );
                                System.out.println("BLOCK " + forBlock1.getName() + ",    " + "Block Complexity: " + forBlock1.getBlockComplexity()+ "    Highest sub Complexity: " +  forBlock1.getHighestSubComplexity()+"\n");
                            }
                            else if (line.endsWith("log_N:")){
                                forBlock1 = new CodeBlock(complexity2);
                                forBlock1.setHighestSubComplexity(new Complexity(0,0));
                                // forBlock1.getBlockComplexity().setN_power(0);
                                // forBlock1.getBlockComplexity().setLog_power(1);
                                forBlock1.setName(blockStack.peek().getName() + "" + "1.");
                                blockStack.push(forBlock1);
                                //System.out.println(forBlock1.getName());
                                System.out.println("Entering block " + forBlock1.getName() );
                                System.out.println("BLOCK " + forBlock1.getName() + ",    " +"Block Complexity: " + forBlock1.getBlockComplexity()+ "    Highest sub Complexity: " +  forBlock1.getHighestSubComplexity()+"\n");
                            }
                        }
                        else if (line.contains("while")){
                            //codeBlock.setLoopVariable("");
                            Complexity complexity3 = new Complexity(0,0);
                            CodeBlock whileBlock1 = new CodeBlock(complexity3);
                            whileBlock1.setHighestSubComplexity(new Complexity(0,0));
                            //String[] strarr = line.split(" ");
                            String str = line.trim().substring(line.indexOf("e")+1,
                                    line.indexOf("e")+2);
                            whileBlock1.setLoopVariable(str);
                           //whileBlock1.getBlockComplexity().setN_power(0);
                            //whileBlock1.getBlockComplexity().setLog_power(0);
                            whileBlock1.loopType = "while";
                            whileBlock1.setName(blockStack.peek().getName()+ "1.");
                            blockStack.push(whileBlock1);
                            System.out.println("Entering block " + whileBlock1.getName() + ": ");
                            System.out.println("BLOCK " + whileBlock1.getName() + ",    " + "Block Complexity: " + whileBlock1.getBlockComplexity() + "     Highest sub Complexity: " +  whileBlock1.getHighestSubComplexity()+"\n");
                            //System.out.println();



                        }

                        else {
                            Complexity complexity4 = new Complexity(0,0);
                            CodeBlock codeBlock2 = new CodeBlock(complexity4);
                            codeBlock2.setHighestSubComplexity(new Complexity(0,0));
                            if (line.contains("def")){
                                codeBlock2.setName(codeBlock2.getName()  + "1.");
                                Top = codeBlock;
                            }
                            else {
                                codeBlock2.setName(codeBlock2.getName() + "1.");
                            }
                            //codeBlock2.getBlockComplexity().setN_power(0);
                            //codeBlock2.getBlockComplexity().setLog_power(0);
                            blockStack.push(codeBlock2);
                            System.out.println("Entering block " + codeBlock2.getName() +": ");
                            System.out.println("BLOCK " + codeBlock2.getName() + ",    " +"Block Complexity: " + codeBlock2.getBlockComplexity() + "    Highest Sub Complexity: " + codeBlock2.getHighestSubComplexity()+"\n");

                            //System.out.println(codeBlock2.getBlockComplexity().getN_power() + " " + codeBlock2.getBlockComplexity().getLog_power());

                        }

                    }
                    else if (blockStack.peek().loopType.equals("while") && line.contains("-=") || line.contains("/=")){
                        if (line.contains("-=")){
                            blockStack.peek().getBlockComplexity().setN_power(1);
                            blockStack.peek().getBlockComplexity().setLog_power(0);
                            System.out.println("Update Statement found, " + "updating the block " + blockStack.peek().getName());
                            System.out.println(("BLOCK " + blockStack.peek().getName() + ",    " + "Block Complexity: "  + blockStack.peek().getBlockComplexity()) +  "     Highest sub Complexity: " +  blockStack.peek().getHighestSubComplexity() + "\n");
                        }
                        else if (line.contains("/=")){
                            blockStack.peek().getBlockComplexity().setN_power(0);
                            blockStack.peek().getBlockComplexity().setLog_power(1);
                            System.out.println("Update Statement found, " + "updating the block " + blockStack.peek().getName());
                            System.out.println("BLOCK " + blockStack.peek().getName() + ",    "+ "Block Complexity: " + blockStack.peek().getBlockComplexity()  + "     Highest sub Complexity: " + blockStack.peek().getHighestSubComplexity()+ "\n");

                        }



                    }


                }
                else {
                    //line = file.nextLine();

                }


            }
            while (blockStack.size()>1){
                oldTop = blockStack.pop();
                oldTopComplexity = oldTop.getBlockComplexity();
                if (oldTopComplexity.getN_power()>blockStack.peek().getHighestSubComplexity().getN_power()){
                    blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                }
                else if (oldTopComplexity.getN_power() ==
                        blockStack.peek().getHighestSubComplexity().getN_power() &&
                        oldTopComplexity.getLog_power()>blockStack.peek().getHighestSubComplexity().getLog_power()){
                    blockStack.peek().setHighestSubComplexity(oldTopComplexity);
                }
                System.out.println("Overall Complexity :" + blockStack.peek().getHighestSubComplexity());
                System.out.println("Leaving Block " + blockStack.peek().getName());

            }


        } catch (IOException io) {
            System.out.println("FILE NOT FOUND!!");
        }



    return blockStack.pop().getBlockComplexity();
    }

    /**
     * This is the main method used to call the tracefile method and used to take input and exit the program successfully
     * @param args
     */
    public static void main(String[]args){
        System.out.println("Please enter a file name (or 'quit' to quit): ");
        Scanner input = new Scanner(System.in);
        String inputFileName = input.nextLine();

        if (inputFileName.equals("quit")){
            System.out.println("Program terminating successfully....");

        }

        else {
            traceFile(inputFileName);
        }

    }
}

