public class Memory {
    private static String memory[];
    private static Memory instance;


    private Memory() {
    this.memory= new String[40];
    }

    public  String[] getMemory() {
        return memory;
    }

    public static void setMemory(String[] memory) {
        Memory.memory = memory;
    }

    public static Memory getInstance() {
        if (instance == null){
            instance = new Memory();
        }
        return instance;
    }


}
