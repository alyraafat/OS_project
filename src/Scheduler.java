import java.io.IOException;
import java.util.Queue;

public class Scheduler {
    static Parser parser=Parser.getInstance();
    PCB pcb1 = null;
    PCB pcb2 = null;

    public PCB getPcb1() {
        return pcb1;
    }

    public void setPcb1(PCB pcb1) {
        this.pcb1 = pcb1;
    }

    public PCB getPcb2() {
        return pcb2;
    }

    public void setPcb2(PCB pcb2) {
        this.pcb2 = pcb2;
    }

    public PCB getPcb3() {
        return pcb3;
    }

    public void setPcb3(PCB pcb3) {
        this.pcb3 = pcb3;
    }

    PCB pcb3 = null;
    int executing;
    static Memory memoryInstance=Memory.getInstance();

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
            parser.printQueues();
            memoryInstance.printMem(0);
            memoryInstance.printMem(1);

            if (processId == 1) {
                if (parser.memory[0].equals("1") || parser.memory[5].equals("1")) {
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
                if (parser.memory[0].equals("2") || parser.memory[5].equals("2")) {
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
                if (parser.memory[0].equals("3") || parser.memory[5].equals("3")) {
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
                    memoryInstance.emptyMemory(pcb1);
                    System.out.println("Process " + processId + " is finished. ******************");
                    parser.Ready.remove(1);
                }

            } else if (processId == 2) {
                if (pcb2.getPc() == pcb2.getMemEnd()) {
                    parser.changeState(pcb2,"Finished");
                    memoryInstance.emptyMemory(pcb2);
                    System.out.println("Process " + processId + " is finished. ******************");
                    parser.Ready.remove(2);
                }

            } if (processId == 3) {
                if (pcb3.getPc() == pcb3.getMemEnd()) {
                    parser.changeState(pcb3,"Finished");
                    memoryInstance.emptyMemory(pcb3);
                    System.out.println("Process " + processId + " is finished. ******************");
                    parser.Ready.remove(3);
                }

            }

        }
    }


}
