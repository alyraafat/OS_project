import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
     static Memory memory=Memory.getInstance();
     Queue<Integer> Ready = new LinkedList<Integer>();
     Queue<Integer> GeneralBlocked = new LinkedList<Integer>();
     Queue<Integer> InputBlocked = new LinkedList<Integer>();
     Queue<Integer> OutputBlocked = new LinkedList<Integer>();
     Queue<Integer> fileBlocked = new LinkedList<Integer>();
     File HardDisk = new File("Disk");

     public static void changeState(PCB pcb,String state) {
          pcb.setState(state);



     }
     public static int ifSpace(String[] mem) {
          if (mem[0].equals("")) {
               return 0;
          } else if (mem[15]==null || mem[15].equals("")) {
               return 15;
          } else
               return -1;
     }
     public static void createProcess(String path) throws FileNotFoundException {
     int pcbId=PCB.idReached++;
     String state= "new";
     int pc=0;
     int memStart=ifSpace(memory.getMemory());
     int memEnd=ifSpace(memory.getMemory())==0?15:30;







     }
     public static void saveToMemory( PCB pcb,String path) throws FileNotFoundException {
          File file = new File(path);
          BufferedReader br = new BufferedReader(new FileReader(file));

     }


     public Queue<Integer> getReady() {
          return Ready;
     }

     public void setReady(Queue<Integer> ready) {
          Ready = ready;
     }

     public Queue<Integer> getGeneralBlocked() {
          return GeneralBlocked;
     }

     public void setGeneralBlocked(Queue<Integer> generalBlocked) {
          GeneralBlocked = generalBlocked;
     }

     public Queue<Integer> getInputBlocked() {
          return InputBlocked;
     }

     public void setInputBlocked(Queue<Integer> inputBlocked) {
          InputBlocked = inputBlocked;
     }

     public Queue<Integer> getOutputBlocked() {
          return OutputBlocked;
     }

     public void setOutputBlocked(Queue<Integer> outputBlocked) {
          OutputBlocked = outputBlocked;
     }

     public Queue<Integer> getFileBlocked() {
          return fileBlocked;
     }

     public void setFileBlocked(Queue<Integer> fileBlocked) {
          this.fileBlocked = fileBlocked;
     }



}
