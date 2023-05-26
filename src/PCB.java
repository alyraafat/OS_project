public class PCB {
    int pId;
    String state;
    int pc;
    int memStart;
    int memEnd;

    public PCB(int pId, String state, int pc, int memStart, int memEnd) {
        this.pId = pId;
        this.state = state;
        this.pc = pc;
        this.memStart = memStart;
        this.memEnd = memEnd;
    }
    public void updatePCB(int startPCB, String[] memory){
        this.state = memory[startPCB+1];
        this.pc = Integer.parseInt(memory[startPCB+2]);
        this.memStart = Integer.parseInt(memory[startPCB+2]);
        this.memEnd = Integer.parseInt(memory[startPCB+4]);
    }


    public int getpId() {
        return pId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getMemEnd() {
        return memEnd;
    }

}
