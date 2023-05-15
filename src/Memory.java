import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
            for (int i = 10; i < 13; i++) {
                String[] y = memory[i].split(" ");
                if (y[0].equals(needed)) {
                    return y[2];
                }
            }
        } else if (memory[5].equals(pcb.getpId()+"")) {
            for (int i = 30; i < 35; i++) {
                String[] y = memory[i].split(" ");
                if (y[0].equals( needed)) {
                    return y[2];
                }
            }

        }
        return needed;
    }
    public void emptyMemory(PCB pcb) {
        if(memory[0].equals(pcb.getpId()+"")) {
            for(int i = 0; i < 5; i++) {
                memory[i]="";
            }
            for(int i = 10; i < 25; i++) {
                memory[i]="";
            }
        }
        else if(memory[5].equals(pcb.getpId()+"")) {
            for(int i = 5; i < 10; i++) {
                memory[i]="";
            }
            for(int i = 25; i < 40; i++) {
                memory[i]="";
            }
        }
    }
    public void saveInMemory(PCB pcb, String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer sb = new StringBuffer();
        String line;

        int start = pcb.memStart;
        int end = pcb.memEnd;
        int counter = pcb.memStart;

        memory[counter++] = pcb.getpId() + "";
        memory[counter++] = pcb.getState();
        memory[counter++] = pcb.getPc() + "";
        memory[counter++] = start + "";
        memory[counter++] = end + "";
        counter+=5;
        memory[counter++] = "";
        memory[counter++] = "";
        memory[counter++] = "";

        while((line=br.readLine())!=null){
            memory[counter++] = line;
        }
    }
    public void printMem(int place){
        int counter=place==0?0:5;
        System.out.println();
        System.out.println("*******************************");
        System.out.println("Memory" + " " +(place+1) +" Contains:");
        System.out.println("PCB of the process : ");
        System.out.print("The process ID is " + memory[counter++] + " / ");
        System.out.print("The process State is " + memory[counter++] + " / ");
        System.out.print("The program counter of the process is " + memory[counter++] + " / ");
        System.out.print("The lower boundary of the process in the memory is " + memory[counter++] + " / ");
        System.out.print("The upper boundary of the process in the memory is " + memory[counter]);
        System.out.println();
        if (place==0){
            counter=10;
        }else{
            counter=25;
        }

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
