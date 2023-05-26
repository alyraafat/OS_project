import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Memory {
    static String memory[];
//    private static Memory instance;
//    private static Parser parser;


    public Memory() {
        this.memory= new String[40];
    }

    public  String[] getMemory() {
        return memory;
    }

    public void setMemory(String[] memory) {
        this.memory = memory;
    }
    public static String read(PCB pcb, String needed) {
        if (memory[0].equals(pcb.getpId()+"")) {
            for (int i = 10; i < 13; i++) {
                String[] y = memory[i].split(" ");
                if (y[0].equals(needed)) {
                    //a=p3
//                    System.out.println(y[2]);
                    return y[2];
                }
            }
        } else if (memory[5].equals(pcb.getpId()+"")) {
            for (int i = 25; i < 28; i++) {
//                System.out.println(memory[i]);
                String[] y = memory[i].split(" ");
                if (y[0].equals(needed)) {
//                    System.out.println(y[2]);
                    return y[2];
                }
            }

        }
        return needed;
    }
    public static void emptyMemory(PCB pcb) {
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
    public static void saveInMemory(PCB pcb, String path) throws IOException {
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
        memory[counter] = end + "";
        counter=start==0?10:25;

        memory[counter++] = "";
        memory[counter++] = "";
        memory[counter++] = "";

        while((line=br.readLine())!=null){
            String[] y = line.split(" ");
            if (y[0].equals("assign") && y[2].equals("readFile")) {
                memory[counter++] = y[2] + " " + y[3];
                memory[counter] = y[0] + " " + y[1] + " " + y[2] + " "+y[3];
            } else if (y[0].equals("assign") && y[2].equals("input")) {
                memory[counter++] = y[2];
                memory[counter] = y[0] + " " + y[1] + " " + y[2];
            } else {
                memory[counter] = line;
            }
            counter++;
        }

        br.close();
    }

    public static int blockedInMemory(){
        if (Parser.generalBlocked.contains(Integer.parseInt(memory[0]))){
            return 0;
        }else if(Parser.generalBlocked.contains(Integer.parseInt(memory[5]))){
            return 5;
        }else{
        return -1;}
    }
//    public static Memory getInstance() {
//        if (instance == null){
//            instance = new Memory();
//        }
//        return instance;
//    }


}
