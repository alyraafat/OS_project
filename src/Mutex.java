import java.util.Objects;

public class Mutex {
    int flag;
    String owner;
    String mutexName;
    static Parser parser = Parser.getInstance();
    Scheduler scheduler=Scheduler.getInstance();

    public Mutex(String mutexName) {
        this.mutexName = mutexName;
        this.flag = 1;
    }

    public void semWait(PCB pcb) {
        if (flag == 1) {
            flag = 0;
            owner = String.valueOf(pcb.getpId());
        } else {
            parser.changeState(pcb, "Blocked");
            parser.generalBlocked.add(pcb.getpId());
            if (Objects.equals(mutexName, "userInput")) {
                parser.inputBlocked.add(pcb.getpId());
            } else if (Objects.equals(mutexName, "userOutput")) {
                parser.outputBlocked.add(pcb.getpId());
            } else if (Objects.equals(mutexName, "file")) {
                parser.fileBlocked.add(pcb.getpId());
            }
        }
        System.out.println("Semwait Queue print");
        parser.printQueues();
    }

    public void semSignal(PCB pcb) {
        System.out.println("Owner " + owner + " left the chat.. ");
        if (Objects.equals(owner, String.valueOf(pcb.getpId()))) {
            if (Objects.equals(mutexName, "userInput")) {
                if (parser.inputBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                    System.out.println("emptyyyyyyyyyyyyyy");
                } else {
                    System.out.println(" not emptyyyyyyyyyyyyyy");
                    int removed = parser.inputBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove();
//                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                    if(removed==1){
                        parser.changeState(scheduler.pcb1, "Ready");
                    } else if (removed==2) {
                        parser.changeState(scheduler.pcb2, "Ready");
                    }else {
                        parser.changeState(scheduler.pcb3, "Ready");
                    }
                }

            } else if (Objects.equals(mutexName, "userOutput")) {
                if (parser.outputBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                } else {
                    int removed = parser.outputBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove();
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                    if(removed==1){
                        parser.changeState(scheduler.pcb1, "Ready");
                    } else if (removed==2) {
                        parser.changeState(scheduler.pcb2, "Ready");
                    }else {
                        parser.changeState(scheduler.pcb3, "Ready");
                    }
                }

            } else {
                if (parser.fileBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                } else {
                    int removed = parser.fileBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove();
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);

                    if (removed == 1) {
                        parser.changeState(scheduler.pcb1, "Ready");
                    } else if (removed == 2) {
                        parser.changeState(scheduler.pcb2, "Ready");
                    } else {
                        parser.changeState(scheduler.pcb3, "Ready");
                    }
                }
            }
        }
        System.out.println("semSignal Queue print");
        parser.printQueues();
    }
}