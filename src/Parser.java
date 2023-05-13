import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Parser {
     static String input1;
     static String input2;
     static String input3;
     String readFile;
     static String[] memory= Memory.getInstance().getMemory();
     Queue<Integer> Ready = new LinkedList<Integer>();
     static Queue<Integer> generalBlocked = new LinkedList<Integer>();
     Queue<Integer> inputBlocked = new LinkedList<Integer>();
     Queue<Integer> outputBlocked = new LinkedList<Integer>();
     Queue<Integer> fileBlocked = new LinkedList<Integer>();
     static Mutex userInput = new Mutex("userInput");
     static Mutex userOutput = new Mutex("userOutput");
     static Mutex file = new Mutex("file");
     static String[] Disk=new String[20];
     static  String[] temp=new String[20];
     static SystemCall systemCall=new SystemCall();
     public static void updatePC(PCB pcb) {
          int x= pcb.getPc();
          if (pcb.getpId() == 1) {

               if (memory[0].equals("1")) {
                    memory[2] =(x+1)+"" ;
               } else if (memory[20].equals("1")) {
                    memory[22] = (x+1)+"";
               }
          } else if (pcb.getpId() == 2) {

               if (memory[0].equals("2")) {
                    memory[2] = (x+1)+"";
               } else if (memory[20].equals("2")) {
                    memory[22] = (x+1)+"";
               }

          } else if (pcb.getpId() == 3) {

               if (memory[0].equals("3")) {
                    memory[2] = (x+1)+"";
               } else if (memory[20].equals("3")) {
                    memory[22] =(x+1)+"";
               }
          }
          pcb.setPc(pcb.getPc()+1);

     }

     public static int spaceAvailable(String[] mem) {
          if (mem[0]==null|| mem[0].equals("")) {
               return 0;
          } else if (mem[20]==null || mem[20].equals("")) {
               return 20;
          } else
               return -1;
     }

     public void swapDiskToTemp(){

          for (int i = 0; i < 20 ; i++) {
               temp[i] = Disk[i];
          }
     }
     public  void swapToMem(boolean disk){
          int availbleSpace = spaceAvailable(memory);

          if (availbleSpace == 0) {

               for (int i = 0; i < 20 ; i++) {
                    memory[i] = disk?Disk[i]:temp[i];
               }
               memory[3] = "0";
               memory[4] = "19";
               System.out.println("process with id"+ memory[0]+ "is swapped from disk to memory");

          } else if (availbleSpace == 20) {

               for (int i = 20; i < 40 ; i++) {
                    memory[i] = disk?Disk[i]:temp[i];
               }
               memory[23] = "20";
               memory[24] = "39";
               System.out.println("process with id"+ memory[20]+ "is swapped from disk to memory");
          }

     }

     public int swapMemToDisk(){
          int emptied=-1;
          if (!(memory[1].equals("Running"))) {
               System.out.println("process with id"+ memory[0]+ "is swapped From memory to disk");
               for (int i = 0; i < 20; i++) {
                    Disk[i] = memory[i];
                    memory[i] = "";
                    emptied= 0;
               }

            }
          else{
               System.out.println("process with id"+ memory[20]+ "is swapped from memory to disk");
               for (int i = 0; i < 20; i++) {
                    Disk[i] = memory[i];
                    memory[i] = "";
                    emptied= 20;
               }
          }
          return emptied;
     }
     public PCB createProcess(String path) throws IOException {
          int pcbId=PCB.idReached++;
          String state= "Ready";
          int pc=-1;
          int memStart=-1;
          int memEnd=-1;
          if (spaceAvailable(memory)==-1){
               //if memory is full
               int emptied=swapMemToDisk();
               memStart= emptied;
               memEnd = emptied== 0 ? 19 : 39;


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
          this.Ready.add(pcb.getpId());
          return pcb;

     }
     public int execute(PCB pcb, int timeSlice) throws IOException {
          int pcValue = pcb.getPc();
          String Input="";
          for (int i = pcValue; i < pcValue + timeSlice && i < pcb.getMemEnd(); i++) {
               if (memory[i].equals("null")) {
                    pcb.setPc(pcb.getMemEnd());
                    break;
               }
               String[] y = memory[i].split(" ");
               System.out.println("The Instruction that's currently executing is " + memory[i] + " in Process " + pcb.getpId());
               System.out.println("********");
               if (y[0].equals("print")) {
                    systemCall.print( pcb,y[1]);
               }
               else if (y[0].equals("readFile")) {
                    systemCall.readFile(readFromMemory(pcb, y[1]));


               } else if (y[0].equals("assign")) {
                    if (y[2].equals("input")) {
                         System.out.println("Please enter an input: ");
                         Scanner sc = new Scanner(System.in);
                         Input = sc.nextLine();
                         if (pcb.getpId() == 1) {
                              input1 = Input;
                         } else if (pcb.getpId() == 2) {
                              input2 = Input;
                         } else if (pcb.getpId() == 3) {
                              input3 = Input;
                         }
                         if (pcb.getpId() == 1) {
                              systemCall.assign(y[1], input1, pcb);
                         } else if (pcb.getpId() == 2) {
                              systemCall.assign(y[1], input2, pcb);
                         } else if (pcb.getpId() == 3) {
                              systemCall.assign(y[1], input3, pcb);
                         }
                    } else if (y[2].equals("readFile")) {
                         systemCall.assign(y[1], file3, pcb);
                    }
               } else if (y[0].equals("writeFile")) {

                    systemCall.writeFile(this.readFromMemory(pcb, y[1]), this.readFromMemory(pcb, y[2]));

               } else if (y[0].equals("printFromTo")) {
                    systemCall.printFromTo(y[1], y[2], pcb);
               } else if (y[0].equals("semWait")) {
                    if (y[1].equals("userInput")) {
                         userInput.semWait(y[1], pcb);
                    } else if (y[1].equals("userOutput")) {
                         userOutput.semWait(y[1], pcb);
                    } else if (y[1].equals("file")) {
                         file.semWait(y[1], pcb);
                    }

                    if (generalBlocked.contains(pcb.getpId())) {
                         updatePC(pcb);

                         break;
                    }
               } else if (y[0].equals("semSignal")) {
                    if (y[1].equals("userInput")) {
                         userInput.semSignal(y[1], pcb);
                    } else if (y[1].equals("userOutput")) {
                         userOutput.semSignal(y[1], pcb);
                    } else if (y[1].equals("file")) {
                         file.semSignal(y[1], pcb);
                    }
               }



          }return 0;
     }

     public String readFromMemory(PCB pcb, String needed) {
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
          memory[counter++] = end + "";
          memory[counter++] = "";
          memory[counter++] = "";
          memory[counter++] = "";

          while((line=br.readLine())!=null){
               memory[counter++] = line;
          }
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


     public Queue<Integer> getReady() {
          return Ready;
     }

     public void setReady(Queue<Integer> ready) {
          Ready = ready;
     }


     public Queue<Integer> getGeneralBlocked() {
          return generalBlocked;
     }

     public void setGeneralBlocked(Queue<Integer> generalBlocked) {
          this.generalBlocked = generalBlocked;
     }

     public Queue<Integer> getInputBlocked() {
          return inputBlocked;
     }

     public void setInputBlocked(Queue<Integer> inputBlocked) {
          this.inputBlocked = inputBlocked;
     }

     public Queue<Integer> getOutputBlocked() {
          return outputBlocked;
     }

     public void setOutputBlocked(Queue<Integer> outputBlocked) {
          this.outputBlocked = outputBlocked;
     }

     public Queue<Integer> getFileBlocked() {
          return fileBlocked;
     }

     public void setFileBlocked(Queue<Integer> fileBlocked) {
          this.fileBlocked = fileBlocked;
     }



}
