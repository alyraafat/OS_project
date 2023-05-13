import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        PCB pc = new PCB(1,"new",2,0,19);
        String[] mem=Memory.getInstance().getMemory();
        Parser.createProcess("src/Program_1.txt");
        Parser.createProcess("src/Program_1.txt");

        for(int i=0;i<40;i++){
            System.out.println(mem[i]);
        }
    }
}