import java.io.IOException;
import java.util.Queue;

public class Scheduler {
    static Parser parser;
    PCB pcb1 = null;
    PCB pcb2 = null;
    PCB pcb3 = null;
    int executing;

    public void schedule(int t1, int t2, int t3, int tslice) throws IOException {
        int processId=-1;
        if (t1 < t2 && t1 < t3) {
            pcb1= parser.createProcess("src/Program_1.txt");
        } else if (t2 < t1 && t2 < t3) {
            pcb2= parser.createProcess("src/Program_2.txt");
        } else if (t3 < t1 && t3 < t2) {
            pcb3=parser.createProcess("src/Program_3.txt");
        }
        while (!parser.Ready.isEmpty()) {

                processId = parser.Ready.peek();

            printMem(0);
            printMem(1);

            if (processId == 1) {
                if (parser.memory[0].equals("1") || parser.memory[20].equals("1")) {
                    parser.changeState(pcb1,"Running");
                    parser.Ready.remove(1);
                    System.out.println("Process " + processId + " is Running.");
                    executing=parser.execute(pcb1,tslice);

                }
                else{

                    parser.swapDiskToTemp();
                    parser.swapMemToDisk();
                    parser.swapToMem(false);
                    parser.changeState(pcb1,"Running");
                    parser.Ready.remove(1);
                    System.out.println("Process " + processId + " is Running.");
                    executing=parser.execute(pcb1,tslice);

                }

             }
            else if (processId == 2) {
                if (parser.memory[0].equals("2") || parser.memory[20].equals("2")) {
                    parser.changeState(pcb2,"Running");
                    parser.Ready.remove(2);
                    System.out.println("Process " + processId + " is Running.");
                    executing=parser.execute(pcb2,tslice);
                }
                else{

                    parser.swapDiskToTemp();
                    parser.swapMemToDisk();
                    parser.swapToMem(false);
                    parser.changeState(pcb2,"Running");
                    parser.Ready.remove(2);
                    System.out.println("Process " + processId + " is Running.");
                    executing=parser.execute(pcb2,tslice);
                }

            } else if (processId == 3) {
                if (parser.memory[0].equals("3") || parser.memory[20].equals("3")) {
                    parser.changeState(pcb3,"Running");
                    parser.Ready.remove(3);
                    System.out.println("Process " + processId + " is Running.");
                    executing=parser.execute(pcb3,tslice);
                }
                else{

                    parser.swapDiskToTemp();
                    parser.swapMemToDisk();
                    parser.swapToMem(false);
                    parser.changeState(pcb3,"Running");
                    parser.Ready.remove(3);
                    System.out.println("Process " + processId + " is Running.");
                    executing=parser.execute(pcb3,tslice);
                }

            }

            if (processId == 1) {
                if (pcb1.getPc() == pcb1.getMemEnd()) {
                    parser.changeState(pcb1,"Finished");
                    parser.emptyMemory(pcb1);
                    System.out.println("Process " + processId + " is finished. ******************");
                    parser.Ready.remove(1);
                }

            } else if (processId == 2) {
                if (pcb2.getPc() == pcb2.getMemEnd()) {
                    parser.changeState(pcb2,"Finished");
                    parser.emptyMemory(pcb2);
                    System.out.println("Process " + processId + " is finished. ******************");
                    parser.Ready.remove(2);
                }

            } if (processId == 3) {
                if (pcb3.getPc() == pcb3.getMemEnd()) {
                    parser.changeState(pcb3,"Finished");
                    parser.emptyMemory(pcb3);
                    System.out.println("Process " + processId + " is finished. ******************");
                    parser.Ready.remove(3);
                }

            }

        }
    }

//      System.out.print("Ready Queue: ");
//    printQueues(parser.Ready);
//            System.out.print("General Blocked Queue: ");
//    printQueues(parser.generalBlocked);
//            System.out.print("userInput Blocked Queue: ");
//    printQueues(parser.inputBlocked);
//            System.out.print("file Blocked Queue: ");
//    printQueues(parser.fileBlocked);
//            System.out.print("userOutput Blocked Queue: ");
//    printQueues(parser.outputBlocked);

    public void printMem(int place){
       int counter=place==0?0:20;
        System.out.println();
        System.out.println("*******************************");
        System.out.println("Memory Part2 Contains:");
        System.out.println("PCB of the process : ");
        System.out.print("The process ID is " + parser.memory[counter++] + " / ");
        System.out.print("The process State is " + parser.memory[counter++] + " / ");
        System.out.print("The program counter of the process is " + parser.memory[counter++] + " / ");
        System.out.print("The lower boundary of the process in the memory is " + parser.memory[counter++] + " / ");
        System.out.print("The upper boundary of the process in the memory is " + parser.memory[counter++]);
        System.out.println();
        System.out.println("Variables  of the process : ");
        System.out.print("First variable : " + parser.memory[counter++] + " / ");
        System.out.print("Second variable : " + parser.memory[counter++] + " / ");
        System.out.print("Third variable : " + parser.memory[counter++]);
        System.out.println();
        System.out.println("Instructions of the process : ");
        while(true) {
            if(parser.memory[counter]==null||parser.memory[counter]==""){
                break;
            }
            System.out.print(parser.memory[counter++] + " / ");
        }

    }
    public static void printQueues(Queue<Integer> q) {
        for (Integer item : q) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
