import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SystemCall {
    static Parser parser;
    public SystemCall() {

    }
    public void print(PCB pcb,String needed){
        System.out.println(parser.memoryInstance.read(pcb,needed));
    }
    public static void assign(String x, String y, PCB pcb) {

    }
    public static void writeFile(String fileName, String data) {}
    public static void readFile(String fileName) throws FileNotFoundException {
        File needed = new File(fileName);
        Scanner myReader = new Scanner(needed);
        parser.readFile=myReader.next();

    }
    public static void printFromTo(String a, String b, PCB pcb) {}
}
