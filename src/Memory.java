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
    public String read(PCB pcb, String needed) {
        if (memory[0].equals(pcb.getpId()+"")) {
            for (int i = 5; i < 8; i++) {
                String[] y = memory[i].split(" ");
                if (y[0].equals(needed)) {
                    return y[1];
                }
            }
        } else if (memory[20].equals(pcb.getpId()+"")) {
            for (int i = 25; i < 28; i++) {
                String[] y = memory[i].split(" ");
                if (y[0].equals( needed)) {
                    return y[1];
                }
            }

        }
        return needed;
    }
    public void emptyMemory(PCB pcb) {
        if(memory[0].equals(pcb.getpId()+"")) {
            for(int i = 0; i < 20; i++) {
                memory[i]="";
            }
        }
        else if(memory[20].equals(pcb.getpId()+"")) {
            for(int i = 20; i < 40; i++) {
                memory[i]="";
            }
        }
    }
    public void printMem(int place){
        int counter=place==0?0:20;
        System.out.println();
        System.out.println("*******************************");
        System.out.println("Memory Part2 Contains:");
        System.out.println("PCB of the process : ");
        System.out.print("The process ID is " + memory[counter++] + " / ");
        System.out.print("The process State is " + memory[counter++] + " / ");
        System.out.print("The program counter of the process is " + memory[counter++] + " / ");
        System.out.print("The lower boundary of the process in the memory is " + memory[counter++] + " / ");
        System.out.print("The upper boundary of the process in the memory is " + memory[counter++]);
        System.out.println();
        System.out.println("Variables  of the process : ");
        System.out.print("First variable : " + memory[counter++] + " / ");
        System.out.print("Second variable : " + memory[counter++] + " / ");
        System.out.print("Third variable : " + memory[counter++]);
        System.out.println();
        System.out.println("Instructions of the process : ");
        while(true) {
            if(memory[counter]==null||memory[counter]==""){
                break;
            }
            System.out.print(memory[counter++] + " / ");
        }

    }
    public static Memory getInstance() {
        if (instance == null){
            instance = new Memory();
        }
        return instance;
    }


}
