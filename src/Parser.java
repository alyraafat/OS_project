import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
     Memory memory=Memory.getInstance();
     Queue<Integer> Ready = new LinkedList<Integer>();
     Queue<Integer> GeneralBlocked = new LinkedList<Integer>();
     Queue<Integer> InputBlocked = new LinkedList<Integer>();
     Queue<Integer> OutputBlocked = new LinkedList<Integer>();
     Queue<Integer> fileBlocked = new LinkedList<Integer>();
     File HardDisk = new File("Disk");

     public static void changeState(PCB pcb,String state) {
          pcb.setState(state);



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
