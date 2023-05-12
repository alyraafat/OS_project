public class Mutex {
    int flag;
    String owner;
    String mutexName;

    public Mutex(String mutexName) {
        this.mutexName=mutexName;
        this.flag = 1;

    }
    public void semWait(PCB pcb){

    }

    public String getMutexName() {
        return mutexName;
    }

    public void semSignal(PCB pcb){

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
