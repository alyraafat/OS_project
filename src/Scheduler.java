import java.io.IOException;

public class Scheduler {
    static Parser parser=Parser.getInstance();
    PCB pcb1 = null;
    PCB pcb2 = null;
    PCB pcb3 = null;
    int executing;
    static Memory memoryInstance=Memory.getInstance();
    static Scheduler instance=null;
    private Scheduler(){}
    public static Scheduler getInstance() {
        if (instance == null){
            instance = new Scheduler();
        }
        return instance;
    }
   public void fixTimings(int t1, int t2, int t3) throws IOException {
        if (t1 < t2 && t1 < t3) {
            pcb1= parser.createProcess("src/Program_1.txt");
            parser.t2=parser.t2-t1;
            parser.t3=parser.t3-t1;
            parser.t1 =-1;
        } else if (t2 < t1 && t2 < t3) {
            pcb2= parser.createProcess("src/Program_2.txt");
            parser.t1=parser.t1-t2;
            parser.t3=parser.t3-t2;
            parser.t2 =-1;
        } else if (t3 < t1 && t3 < t2) {
            pcb3=parser.createProcess("src/Program_3.txt");
            parser.t2=parser.t2-t3;
            parser.t1=parser.t1-t3;
            parser.t3 =-1;
        } else if (t1==t2&&t2==t3) {
            pcb1= parser.createProcess("src/Program_1.txt");
            pcb2= parser.createProcess("src/Program_2.txt");
            pcb2= parser.createProcess("src/Program_3.txt");
            parser.t1 =-1;
            parser.t2 =-1;
            parser.t3 =-1;
        } else if (t1==t3) {
            pcb1= parser.createProcess("src/Program_1.txt");
            pcb2= parser.createProcess("src/Program_3.txt");
            parser.t1 =-1;
            parser.t3 =-1;
            parser.t2=parser.t2-t3;
        }else if (t2==t3) {
            pcb1= parser.createProcess("src/Program_3.txt");
            pcb2= parser.createProcess("src/Program_2.txt");
            parser.t2 =-1;
            parser.t3 =-1;
            parser.t1=parser.t1-t2;
        }else if(t1==t2){
            pcb1= parser.createProcess("src/Program_1.txt");
            pcb2= parser.createProcess("src/Program_2.txt");
            parser.t1 =-1;
            parser.t2 =-1;
            parser.t3=parser.t3-t2;
        }
    }
    private void processRunning(String id, int processId, int tslice, PCB pcb) throws IOException {
        if (!parser.memory[0].equals(id) && !parser.memory[5].equals(id)) {
           if(parser.spaceAvailable(memoryInstance.getMemory())==-1){
                parser.swapTemp(parser.temp);
                parser.swapDiskToMem();
                parser.swapFileToFile(parser.temp);
           }else {
               parser.swapDiskToMem();
           }
        }
        parser.changeState(pcb,"Running");
        parser.Ready.remove(Integer.parseInt(id));
        System.out.println("Process " + processId + " is Running.");
        executing=parser.execute(pcb,tslice);
    }
    private void processFinished(int processId, PCB pcb, int id){
        if (pcb.getPc() == pcb.getMemEnd()) {
            parser.changeState(pcb,"Finished");
            memoryInstance.emptyMemory(pcb);
            System.out.println("Process " + processId + " is finished. ******************");
            parser.Ready.remove(id);
        }
    }
    public void schedule(int t1, int t2, int t3, int tslice) throws IOException {
        int processId=-1;
        fixTimings(t1,t2,t3);
        while (!parser.Ready.isEmpty()) {
            processId = parser.Ready.peek();
            parser.printQueues();
            memoryInstance.printMem(0);
            memoryInstance.printMem(1);
            if (processId == 1) {
                processRunning("1",processId,tslice,pcb1);
//                if (!parser.memory[0].equals("1") && !parser.memory[5].equals("1")) {
////                    parser.swapDiskToTemp();
////                    parser.swapMemToDisk();
////                    parser.swapToMem(false);
//                    parser.swapTemp(parser.temp);
//                    parser.SwapDiskToMem();
//                    parser.swapFiletoFile(parser.temp);
//                }
//                parser.changeState(pcb1,"Running");
//                parser.Ready.remove(1);
//                System.out.println("Process " + processId + " is Running.");
//                executing=parser.execute(pcb1,tslice);
            }
            else if (processId == 2) {
                processRunning("2",processId,tslice,pcb2);
//                if (!parser.memory[0].equals("2") && !parser.memory[5].equals("2")) {
//                    parser.swapTemp(parser.temp);
//                    parser.SwapDiskToMem();
//                    parser.swapFiletoFile(parser.temp);
//                }
//                parser.changeState(pcb2,"Running");
//                parser.Ready.remove(2);
//                System.out.println("Process " + processId + " is Running.");
//                executing=parser.execute(pcb2,tslice);
            } else if (processId == 3) {
                processRunning("3",processId,tslice,pcb3);
//                if (!parser.memory[0].equals("3") && !parser.memory[5].equals("3")) {
//
//                    parser.swapTemp(parser.temp);
//                    parser.SwapDiskToMem();
//                    parser.swapFiletoFile(parser.temp);
//                }
//                parser.changeState(pcb3,"Running");
//                parser.Ready.remove(3);
//                System.out.println("Process " + processId + " is Running.");
//                executing=parser.execute(pcb3,tslice);
            }
            if (processId == 1) {
                processFinished(processId,pcb1,1);
//                if (pcb1.getPc() == pcb1.getMemEnd()) {
//                    parser.changeState(pcb1,"Finished");
//                    memoryInstance.emptyMemory(pcb1);
//                    System.out.println("Process " + processId + " is finished. ******************");
//                    parser.Ready.remove(1);
//                }
            } else if (processId == 2) {
                processFinished(processId,pcb2,2);
//                if (pcb2.getPc() == pcb2.getMemEnd()) {
//                    parser.changeState(pcb2,"Finished");
//                    memoryInstance.emptyMemory(pcb2);
//                    System.out.println("Process " + processId + " is finished. ******************");
//                    parser.Ready.remove(2);
//                }
            } if (processId == 3) {
                processFinished(processId,pcb3,3);
//                if (pcb3.getPc() == pcb3.getMemEnd()) {
//                    parser.changeState(pcb3,"Finished");
//                    memoryInstance.emptyMemory(pcb3);
//                    System.out.println("Process " + processId + " is finished. ******************");
//                    parser.Ready.remove(3);
//                }
            }
        }
    }
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

}
