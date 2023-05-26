import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;

public class SystemCall {
//    static Parser Parser=Parser.getInstance();

    public static void print(PCB pcb, String needed){
        System.out.println(Memory.read(pcb,needed));
    }
    public static void printAllData(){
        printQueues();
        printMem(0);
        printMem(1);
    }
    public static void printMem(int place){
        int counter=place==0?0:5;
        System.out.println();
        System.out.println("*******************************");
        System.out.println("Memory" + " " +(place+1) +" Contains:");
        System.out.println("PCB of the process : ");
        System.out.print("The process ID is " + Memory.memory[counter++] + " / ");
        System.out.print("The process State is " + Memory.memory[counter++] + " / ");
        System.out.print("The program counter of the process is " + Memory.memory[counter++] + " / ");
        System.out.print("The lower boundary of the process in the memory is " + Memory.memory[counter++] + " / ");
        System.out.print("The upper boundary of the process in the memory is " + Memory.memory[counter]);
        System.out.println();
        counter = place==0?10:25;

        System.out.println("Variables  of the process : ");
        System.out.print("First variable : " + Memory.memory[counter++] + " / ");
        System.out.print("Second variable : " + Memory.memory[counter++] + " / ");
        System.out.print("Third variable : " + Memory.memory[counter++]);
        System.out.println();
        System.out.println("Instructions of the process : ");
        while(!(counter>=Memory.memory.length||Memory.memory[counter]==null||Memory.memory[counter].equals("")||Memory.memory[counter].equals("null"))) {
            System.out.print(Memory.memory[counter++] + " / ");
        }
        System.out.println();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
    }
    public static void printQueues(){
        System.out.print("Ready Queue: ");
        printQueue(Parser.Ready);
        System.out.print("General Blocked Queue: ");
        printQueue(Parser.generalBlocked);
        System.out.print("userInput Blocked Queue: ");
//          printQueue(inputBlocked);
        printQueue(Parser.userInput.blocked);
        System.out.print("userOutput Blocked Queue: ");
//          printQueue(outputBlocked);
        printQueue(Parser.userOutput.blocked);
        System.out.print("file Blocked Queue: ");
//          printQueue(fileBlocked);
        printQueue(Parser.file.blocked);
        System.out.println("");
        System.out.println("------------------");
    }
    public static void printQueue(Queue<Integer> q) {
        for (Integer item : q) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static String takeInputFromUser(){
        System.out.println("Please enter an input: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    public static void assign(String x, String y, PCB pcb) {
        String result=x+" = "+y;

        if (Memory.memory[0].equals(pcb.getpId()+"")) {
            assignHelper(result,10,13);
//            for (int i=10;i<13;i++) {
//                if (Parser.memory[i]==null||Parser.memory[i].equals("")) {
//                    Parser.memory[i]=result;
//                    System.out.println(result);
//                    break;
//                }
//            }
        }
        else if (Memory.memory[5].equals(pcb.getpId()+"")) {
            assignHelper(result,25,28);
//            for (int i=25;i<28;i++) {
//                if (Parser.memory[i]==null||Parser.memory[i].equals("")) {
//                    Parser.memory[i]=result;
//                    break;
//                }
//            }
        }
    }
    private static void assignHelper(String result, int start, int end){
        String[] resultArr = result.split(" ");
        for (int i=start;i<end;i++) {
            if (Memory.memory[i] != null && !Memory.memory.equals("")) {
                String[] var = Memory.memory[i].split(" ");
                if (var[0].equals(resultArr[0])) {
                    Memory.memory[i] = result;
                    return;
                }
            }
        }
        for (int i=start;i<end;i++) {
            if (Memory.memory[i]==null||Memory.memory[i].equals("")) {
                Memory.memory[i]=result;
                return;
            }
        }

    }
    public static void writeFile(String fileName, String data) throws IOException {
        File file=new File(fileName);
        if(file.createNewFile()){
        	System.out.println("File created: " + file.getName());
         }
        else {
             System.out.println("File already exists.");
        }
        FileWriter fileWriter=new FileWriter(file);
        fileWriter.write(data);
        fileWriter.close();
        System.out.println("Successfully wrote to the file.");
    }
    public static void readFile(String fileName) throws FileNotFoundException {
        File needed = new File("src/"+fileName);
        Scanner myReader = new Scanner(needed);
        Parser.readFile=myReader.next();
        myReader.close();

    }
    public static void printFromTo(String a, String b, PCB pcb) {
        int start,end;
        start = Integer.parseInt(Memory.read(pcb, a));
        end = Integer.parseInt(Memory.read(pcb, b));
        for (int i = ++start; i < end; i++) {
            System.out.println(i);
        }
    }
}
