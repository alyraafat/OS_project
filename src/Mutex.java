public class Mutex {
    int flag;
    String owner;

    public Mutex() {
        this.flag = 1;

    }
    public void semWait(){

    }

    public void semSignal(){

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
