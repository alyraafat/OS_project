import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Mutex {
    int flag;
    String owner;
    String mutexName;
    Queue<Integer> blocked;

    public Mutex(String mutexName) {
        this.mutexName = mutexName;
        this.flag = 1;
        this.blocked = new LinkedList<>();
    }

    public void semWait(PCB pcb) {
        if (flag == 1) {
            flag = 0;
            owner = String.valueOf(pcb.getpId());
        } else {
            Parser.changeState(pcb, "Blocked");
            Parser.generalBlocked.add(pcb.getpId());
            this.blocked.add(pcb.getpId());
        }
        System.out.println("Semwait Queue print: ");
        SystemCall.printQueues();
    }

    public void semSignal(PCB pcb) {
        System.out.println("Owner " + owner + " left the chat.. ");
        if (Objects.equals(owner, String.valueOf(pcb.getpId()))) {
            if (this.blocked.isEmpty()) {
                    flag = 1;
                    owner = "";
            }else {
                int removed = this.blocked.remove();
                owner = String.valueOf(removed);
                Parser.generalBlocked.remove(removed);
                Parser.Ready.add(removed);
                if(removed==1){
                    Parser.changeState(Scheduler.pcb1, "Ready");
                } else if (removed==2) {
                    Parser.changeState(Scheduler.pcb2, "Ready");
                }else {
                    Parser.changeState(Scheduler.pcb3, "Ready");
                }
            }
        }
        System.out.println("semSignal Queue print: ");
        SystemCall.printQueues();
    }
}