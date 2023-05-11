public class memory {
    String memory[];

    public memory() {
        this.memory= new String[40];
    }
    public boolean isMemFull(){
        return !(this.memory[39]==null);
    }


}
