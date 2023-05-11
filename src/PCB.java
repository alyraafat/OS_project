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

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
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

    public int getMemStart() {
        return memStart;
    }

    public void setMemStart(int memStart) {
        this.memStart = memStart;
    }

    public int getMemEnd() {
        return memEnd;
    }

    public void setMemEnd(int memEnd) {
        this.memEnd = memEnd;
    }
}
