import java.io.IOException;

public class Scheduler {
//    static Parser Parser=Parser.getInstance();
    static PCB pcb1 = null;
    static PCB pcb2 = null;
    static PCB pcb3 = null;
    static int executing;
//    static Memory Memory=;
//    static Scheduler instance=null;
//    private Scheduler(){}
//    public static Scheduler getInstance() {
//        if (instance == null){
//            instance = new Scheduler();
//        }
//        return instance;
//    }
//    public void fixTimings(int t1, int t2, int t3) throws IOException {
//        if (t1 < t2 && t1 < t3) {
//            pcb1= Parser.createProcess("src/Program_1.txt");
//            Parser.t2=Parser.t2-t1;
//            Parser.t3=Parser.t3-t1;
//            Parser.t1 =-1;
//        } else if (t2 < t1 && t2 < t3) {
//            pcb2= Parser.createProcess("src/Program_2.txt");
//            Parser.t1=Parser.t1-t2;
//            Parser.t3=Parser.t3-t2;
//            Parser.t2 =-1;
//        } else if (t3 < t1 && t3 < t2) {
//            pcb3=Parser.createProcess("src/Program_3.txt");
//            Parser.t2=Parser.t2-t3;
//            Parser.t1=Parser.t1-t3;
//            Parser.t3 =-1;
//        } else if (t1==t2&&t2==t3) {
//            pcb1= Parser.createProcess("src/Program_1.txt");
//            pcb2= Parser.createProcess("src/Program_2.txt");
//            pcb2= Parser.createProcess("src/Program_3.txt");
//            Parser.t1 =-1;
//            Parser.t2 =-1;
//            Parser.t3 =-1;
//        } else if (t1==t3) {
//            pcb1= Parser.createProcess("src/Program_1.txt");
//            pcb2= Parser.createProcess("src/Program_3.txt");
//            Parser.t1 =-1;
//            Parser.t3 =-1;
//            Parser.t2=Parser.t2-t3;
//        }else if (t2==t3) {
//            pcb1= Parser.createProcess("src/Program_3.txt");
//            pcb2= Parser.createProcess("src/Program_2.txt");
//            Parser.t2 =-1;
//            Parser.t3 =-1;
//            Parser.t1=Parser.t1-t2;
//        }else if(t1==t2){
//            pcb1= Parser.createProcess("src/Program_1.txt");
//            pcb2= Parser.createProcess("src/Program_2.txt");
//            Parser.t1 =-1;
//            Parser.t2 =-1;
//            Parser.t3=Parser.t3-t2;
//        }
//    }
    public static void fixTimings(boolean isFirstCycle) throws IOException{
        if(!isFirstCycle){
            Parser.t1--;
            Parser.t2--;
            Parser.t3--;
        }
        if (Parser.t1 == 0) {
            System.out.println("Process 1" + " arrived.");
            pcb1= Parser.createProcess("src/Program_1.txt");
        }
        if (Parser.t2 == 0) {
            System.out.println("Process 2" + " arrived.");
            pcb2=Parser.createProcess("src/Program_2.txt");
        }
        if (Parser.t3 == 0) {
            System.out.println("Process 3" + " arrived.");
            pcb3= Parser.createProcess("src/Program_3.txt");
        }
//        printAllData();
    }
    private static void processRunning(String id, int processId, int tslice, PCB pcb, boolean justArrived) throws IOException {
        if (!Memory.memory[0].equals(id) && !Memory.memory[5].equals(id)) {
           if(Parser.spaceAvailable(Memory.memory)==-1){
                Parser.swapTemp(Parser.temp);
                Parser.swapDiskToMem();
                Parser.swapFileToFile(Parser.temp);
           }else {
               Parser.swapDiskToMem();
           }
        }
        Parser.changeState(pcb,"Running");
        SystemCall.printAllData();
        Parser.Ready.remove(Integer.parseInt(id));
//        System.out.println("Process " + processId + " is Running.");
        executing=Parser.execute(pcb,tslice,justArrived);
    }
    public static void readyQueueSwap(int id){
        if (!Parser.Ready.isEmpty()) {
            int current = Parser.Ready.peek();
            System.out.println(current);
            if (current == Parser.wasJustRunning) {
                int swapped = Parser.Ready.remove();
                Parser.Ready.add(id);
                Parser.Ready.add(swapped);
            } else {
                int first = Parser.Ready.remove();
                if (Parser.Ready.isEmpty()) {
                    Parser.Ready.add(first);
                    Parser.Ready.add(id);
                } else {
                    int swapped = Parser.Ready.remove();
                    Parser.Ready.add(first);
                    Parser.Ready.add(id);
                    Parser.Ready.add(swapped);
                }
            }

        }
        else {
            Parser.Ready.add(id);

        }
        Parser.test=1;
    }

    private static void processFinished(int processId, PCB pcb, int id){
        if (pcb.getPc() == (pcb.getMemEnd()+1)||Memory.memory[pcb.getPc()]==null||Memory.memory[pcb.getPc()].equals("")||Memory.memory[pcb.getPc()].equals("null")) {
            Parser.changeState(pcb,"Finished");
//            Memory.emptyMemory(pcb);
            System.out.println("Process " + processId + " is finished. ******************");
            Parser.Ready.remove(id);
            SystemCall.printAllData();

        }
    }
    public static void schedule(int tslice) throws IOException {
        int processId=-1;
//        fixTimings(t1,t2,t3);
        boolean isFirstArrival =true;
        System.out.println("*******************************************");
        System.out.println("*******************************************");
        System.out.println("Clock cycle: " + ((Parser.counter)++));
        fixTimings(true);
        SystemCall.printAllData();
        while (!Parser.Ready.isEmpty()||isAllArrived()) {
//            printAllData();
            if(!Parser.Ready.isEmpty()){
                processId = Parser.Ready.peek();
            }
//            printAllData();
            if (processId == 1) {
                processRunning("1",processId,tslice,pcb1,isFirstArrival);
                isFirstArrival = false;
//                if (!Parser.memory[0].equals("1") && !Parser.memory[5].equals("1")) {
////                    Parser.swapDiskToTemp();
////                    Parser.swapMemToDisk();
////                    Parser.swapToMem(false);
//                    Parser.swapTemp(Parser.temp);
//                    Parser.SwapDiskToMem();
//                    Parser.swapFiletoFile(Parser.temp);
//                }
//                Parser.changeState(pcb1,"Running");
//                Parser.Ready.remove(1);
//                System.out.println("Process " + processId + " is Running.");
//                executing=Parser.execute(pcb1,tslice);
            } else if (processId == 2) {
                processRunning("2",processId,tslice,pcb2,isFirstArrival);
                isFirstArrival = false;
//                if (!Parser.memory[0].equals("2") && !Parser.memory[5].equals("2")) {
//                    Parser.swapTemp(Parser.temp);
//                    Parser.SwapDiskToMem();
//                    Parser.swapFiletoFile(Parser.temp);
//                }
//                Parser.changeState(pcb2,"Running");
//                Parser.Ready.remove(2);
//                System.out.println("Process " + processId + " is Running.");
//                executing=Parser.execute(pcb2,tslice);
            } else if (processId == 3) {
                processRunning("3",processId,tslice,pcb3,isFirstArrival);
                isFirstArrival = false;
//                if (!Parser.memory[0].equals("3") && !Parser.memory[5].equals("3")) {
//                    Parser.swapTemp(Parser.temp);
//                    Parser.SwapDiskToMem();
//                    Parser.swapFiletoFile(Parser.temp);
//                }
//                Parser.changeState(pcb3,"Running");
//                Parser.Ready.remove(3);
//                System.out.println("Process " + processId + " is Running.");
//                executing=Parser.execute(pcb3,tslice);
            } else{
                System.out.println("*******************************************");
                System.out.println("*******************************************");
                System.out.println("Clock cycle: " + ((Parser.counter)++));
                fixTimings(false);
                SystemCall.printAllData();
                isFirstArrival = true;
            }
            if (processId == 1) {
                processFinished(processId,pcb1,1);
//                if (pcb1.getPc() == pcb1.getMemEnd()) {
//                    Parser.changeState(pcb1,"Finished");
//                    Memory.emptyMemory(pcb1);
//                    System.out.println("Process " + processId + " is finished. ******************");
//                    Parser.Ready.remove(1);
//                }
            } if (processId == 2) {
                processFinished(processId,pcb2,2);
//                if (pcb2.getPc() == pcb2.getMemEnd()) {
//                    Parser.changeState(pcb2,"Finished");
//                    Memory.emptyMemory(pcb2);
//                    System.out.println("Process " + processId + " is finished. ******************");
//                    Parser.Ready.remove(2);
//                }
            } if (processId == 3) {
                processFinished(processId,pcb3,3);
//                if (pcb3.getPc() == pcb3.getMemEnd()) {
//                    Parser.changeState(pcb3,"Finished");
//                    Memory.emptyMemory(pcb3);
//                    System.out.println("Process " + processId + " is finished. ******************");
//                    Parser.Ready.remove(3);
//                }
            }
            processId=-1;
        }
        SystemCall.printAllData();
    }

    public static boolean isAllArrived(){
        return pcb1==null||pcb2==null||pcb3==null;
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
