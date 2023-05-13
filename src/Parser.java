import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
     static String[] memory= Memory.getInstance().getMemory();
     Queue<Integer> Ready = new LinkedList<Integer>();
     Queue<Integer> GeneralBlocked = new LinkedList<Integer>();
     Queue<Integer> InputBlocked = new LinkedList<Integer>();
     Queue<Integer> OutputBlocked = new LinkedList<Integer>();
     Queue<Integer> fileBlocked = new LinkedList<Integer>();
     static String[] Disk=new String[20];

     public static int spaceAvailable(String[] mem) {
          if (mem[0]==null|| mem[0].equals("")) {
               return 0;
          } else if (mem[20]==null || mem[20].equals("")) {
               return 20;
          } else
               return -1;
     }
     public static int SwapMemToDisk(){
          int emptied=-1;
          if (!(memory[1].equals("Running"))) {
               System.out.println("process with id"+ memory[0]+ "is swapped to disk");
               for (int i = 0; i < 20; i++) {
                    Disk[i] = memory[i];
                    memory[i] = "";
                    emptied= 0;
               }

            }
          else{
               System.out.println("process with id"+ memory[20]+ "is swapped to disk");
               for (int i = 0; i < 20; i++) {
                    Disk[i] = memory[i];
                    memory[i] = "";
                    emptied= 20;
               }
          }
          return emptied;
     }
     public static void createProcess(String path) throws IOException {
          int pcbId=PCB.idReached++;
          String state= "new";
          int pc=-1;
          int memStart=-1;
          int memEnd=-1;
          if (spaceAvailable(memory)==-1){

               //if memory is full

          } else {
               if(spaceAvailable(memory)==0){
                   pc=8;
               }else{
                    pc=28;
               }

               memStart= spaceAvailable(memory);
               memEnd = spaceAvailable(memory) == 0 ? 19 : 39;

          }

          PCB pcb = new PCB(pcbId,state,pc,memStart,memEnd);
          saveInMemory(pcb,path);


     }
     public static void saveInMemory(PCB pcb, String path) throws IOException {
          File file = new File(path);
          BufferedReader br = new BufferedReader(new FileReader(file));

     }

     public  void changeState(PCB pcb,String state) {
          pcb.setState(state);
          if (pcb.getpId() == 1) {
               if (memory[0].equals("1")) {
                    memory[1] = state;
               } else if (memory[20].equals("1")) {
                    memory[21] = state;
               }

          }
          else if (pcb.getpId() == 2) {

               if (memory[0].equals("2")) {
                    memory[1] = state;
               } else if (memory[20].equals("2")) {
                    memory[21] = state;
               }
          }
          else if (pcb.getpId() == 3) {

               if (memory[0].equals("3")) {
                    memory[1] = state;
               } else if (memory[20].equals("3")) {
                    memory[21] = state;
               }
          }

     }
     public static void emptyMemory(PCB pcb) {
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
