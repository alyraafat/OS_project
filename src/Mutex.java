import java.util.Objects;

public class Mutex {
    int flag;
    String owner;
    String mutexName;
    static Parser parser = Parser.getInstance();

    public Mutex(String mutexName) {
        this.mutexName = mutexName;
        this.flag = 1;

    }

    public void semWait(PCB pcb) {
        if (flag == 1) {
            flag = 0;
            owner = String.valueOf(pcb.getpId());
        } else {
            if (Objects.equals(mutexName, "userInput")) {
                parser.changeState(pcb, "Blocked");
                parser.generalBlocked.add(pcb.getpId());
                parser.inputBlocked.add(pcb.getpId());
            } else if (Objects.equals(mutexName, "userOutput")) {
                parser.changeState(pcb, "Blocked");
                parser.generalBlocked.add(pcb.getpId());
                parser.outputBlocked.add(pcb.getpId());
            } else if (Objects.equals(mutexName, "file")) {
                parser.changeState(pcb, "Blocked");
                parser.generalBlocked.add(pcb.getpId());
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
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                }

            } else if (Objects.equals(mutexName, "userOutput")) {
                if (parser.outputBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                } else {
                    int removed = parser.inputBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove(owner);
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                }

            } else {
                if (parser.fileBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                } else {
                    int removed = parser.inputBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove();
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                }
            }
        }
        System.out.println("semSignal Queue print");
        parser.printQueues();
    }
}