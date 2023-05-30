import java.io.IOException;

public class Scheduler {
    static PCB pcb1 = null;
    static PCB pcb2 = null;
    static PCB pcb3 = null;
    static int executing;

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
        Parser.Ready.remove(Integer.parseInt(id));
        SystemCall.printAllData();
        executing=Parser.execute(pcb,tslice,justArrived);
    }

    private static void processFinished(int processId, PCB pcb, int id){
        if (pcb.getPc() == (pcb.getMemEnd()+1)||Memory.memory[pcb.getPc()]==null||Memory.memory[pcb.getPc()].equals("")||Memory.memory[pcb.getPc()].equals("null")) {
            Parser.changeState(pcb,"Finished");
            System.out.println("Process " + processId + " is finished. ******************");
            Parser.Ready.remove(id);
            SystemCall.printAllData();
        }
    }
    public static void schedule(int tslice) throws IOException {
        int processId=-1;
        boolean isFirstArrival =true;
        System.out.println("*******************************************");
        System.out.println("*******************************************");
        System.out.println("Clock cycle: " + ((Parser.counter)++));
        fixTimings(true);
        SystemCall.printAllData();
        while (!Parser.Ready.isEmpty()||isAllArrived()) {
            if(!Parser.Ready.isEmpty()){
                processId = Parser.Ready.peek();
            }
            if (processId == 1) {
                processRunning("1",processId,tslice,pcb1,isFirstArrival);
                isFirstArrival = false;
            } else if (processId == 2) {
                processRunning("2",processId,tslice,pcb2,isFirstArrival);
                isFirstArrival = false;
            } else if (processId == 3) {
                processRunning("3",processId,tslice,pcb3,isFirstArrival);
                isFirstArrival = false;
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
            } if (processId == 2) {
                processFinished(processId,pcb2,2);
            } if (processId == 3) {
                processFinished(processId,pcb3,3);
            }
            processId=-1;
        }
        SystemCall.printAllData();
    }

    public static boolean isAllArrived(){
        return pcb1==null||pcb2==null||pcb3==null;
    }

}
