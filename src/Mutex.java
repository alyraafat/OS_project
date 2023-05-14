public class Mutex {
    int flag;
    String owner;
    String mutexName;
    static Parser parser;

    public Mutex(String mutexName) {
        this.mutexName=mutexName;
        this.flag = 1;

    }
    public void semWait(PCB pcb){
        if(flag==1){
            flag=0;
            owner= String.valueOf(pcb.getpId());
        }
        else {
            if(mutexName=="userInput"){
                parser.changeState(pcb,"Blocked");
                parser.generalBlocked.add(pcb.getpId());
                parser.inputBlocked.add(pcb.getpId());
            } else if (mutexName=="userOutput") {
                parser.changeState(pcb,"Blocked");
                parser.generalBlocked.add(pcb.getpId());
                parser.outputBlocked.add(pcb.getpId());
            } else if (mutexName=="file") {
                parser.changeState(pcb,"Blocked");
                parser.generalBlocked.add(pcb.getpId());
                parser.fileBlocked.add(pcb.getpId());
            }
        }
        parser.printQueues();
    }

    public void semSignal(PCB pcb){
        if(owner==String.valueOf(pcb.getpId())){
            if(mutexName=="userInput") {
                if (parser.inputBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                } else {
                    int removed=parser.inputBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove(owner);
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                }

            } else if (mutexName=="userOutput") {
                if (parser.outputBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                } else {
                    int removed=parser.inputBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove(owner);
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                }

            }else {
                if (parser.fileBlocked.isEmpty()) {
                    flag = 1;
                    owner = "";
                } else {
                    int removed=parser.inputBlocked.remove();
                    owner = String.valueOf(removed);
                    parser.generalBlocked.remove();
                    parser.changeState(pcb, "Ready");
                    parser.Ready.add(removed);
                }
            }
        }
        parser.printQueues();
    }
    public String getMutexName() {
        return mutexName;
    }
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
