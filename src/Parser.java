import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Parser {
     static String input;

     String readFile;
     static Memory memoryInstance=Memory.getInstance();
     static String[] memory= memoryInstance.getMemory();
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
     static Scheduler scheduler=new Scheduler();
//     static File harddisk=new File("Disk");
//     static File temp=new File("temp");

     static int t1,t2,t3;
     private static Parser instance;
     private Parser(){

     }
     public static Parser getInstance() {
          if (instance == null){
               instance = new Parser();
          }
          return instance;
     }

     public static void updatePC(PCB pcb) {
          int x= pcb.getPc();
          if (pcb.getpId() == 1) {

               if (memory[0].equals("1")) {
                    memory[2] =(x+1)+"" ;
               } else if (memory[5].equals("1")) {
                    memory[7] = (x+1)+"";
               }
          } else if (pcb.getpId() == 2) {

               if (memory[0].equals("2")) {
                    memory[2] = (x+1)+"";
               } else if (memory[5].equals("2")) {
                    memory[7] = (x+1)+"";
               }

          } else if (pcb.getpId() == 3) {

               if (memory[0].equals("3")) {
                    memory[2] = (x+1)+"";
               } else if (memory[5].equals("3")) {
                    memory[7] =(x+1)+"";
               }
          }
          pcb.setPc(pcb.getPc()+1);

     }

     public static int spaceAvailable(String[] mem) {
          if (mem[0]==null|| mem[0].equals("")) {
               return 0;
          } else if (mem[5]==null || mem[5].equals("")) {
               return 5;
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
          int j=0;
          if (availbleSpace == 0) {
               for(int i = 0; i < 5; i++) {
                    memory[i] = disk?Disk[j]:temp[j];
                    j++;
               }
               for(int i = 10; i < 25; i++) {
                    memory[i] = disk?Disk[j]:temp[j];
                    j++;
               }
               memory[3] = "0";
               memory[4] = "24";
               System.out.println("process with id "+ memory[0]+ "is swapped from disk to memory");

          } else if (availbleSpace == 5) {

               for(int i = 5; i < 10; i++) {
                    memory[i] = disk?Disk[j]:temp[j];
                    j++;
               }
               for(int i = 25; i < 40; i++) {
                    memory[i] = disk?Disk[j]:temp[j];
                    j++;
               }
               memory[8] = "5";
               memory[9] = "39";
               System.out.println("process with id "+ memory[5]+ "is swapped from disk to memory");
          }

     }

     public int swapMemToDisk(){
          int emptied=-1;
          if (!(memory[1].equals("Running"))) {
               System.out.println("process with id "+ memory[0]+ "is swapped From memory to disk");
               int j=0;
               for (int i = 0; i < 5; i++) {
                    Disk[j] = memory[i];
                    memory[i] = "";
                    j++;

               }
               for (int i = 10; i < 25; i++) {
                    Disk[j] = memory[i];
                    memory[i] = "";

               }
               emptied= 0;
            }
          else{
               System.out.println("process with id "+ memory[5]+ "is swapped from memory to disk");
               int j=0;
               for (int i = 5; i < 10; i++) {
                    Disk[j] = memory[i];
                    memory[i] = "";
                    j++;
               }
               for (int i = 25; i < 40; i++) {
                    Disk[j] = memory[i];
                    memory[i] = "";
                    j++;
               }
               emptied= 5;
          }
          return emptied;
     }
     public PCB createProcess(String path) throws IOException {
          int pcbId=-1;
          if(path=="src/Program_1.txt"){
               pcbId=1;
         } else if (path=="src/Program_2.txt") {
               pcbId=2;
          }else if (path=="src/Program_3.txt"){
               pcbId=3;
          }
          String state= "Ready";
          int pc=-1;
          int memStart=-1;
          int memEnd=-1;
//          System.out.println(spaceAvailable(memory));
          if (spaceAvailable(memory)==-1){
               //if memory is full
               int emptied=swapMemToDisk();
               memStart= emptied;
               memEnd = emptied== 0 ? 24 : 39;
               pc=emptied==0?13:28;

          } else {
               if(spaceAvailable(memory)==0){
                   pc=13;
               }else{
                    pc=28;
               }

               memStart= spaceAvailable(memory);
               memEnd = spaceAvailable(memory) == 0 ? 24 : 39;

          }

          PCB pcb = new PCB(pcbId,state,pc,memStart,memEnd);
          memoryInstance.saveInMemory(pcb,path);
          this.Ready.add(pcb.getpId());
          return pcb;

     }
     public int execute(PCB pcb, int timeSlice) throws IOException {
          int pcValue = pcb.getPc();
          String Input="";
          for (int i = pcValue; i < pcValue + timeSlice && i < pcb.getMemEnd(); i++) {
               if (memory[i]==null) {
                    pcb.setPc(pcb.getMemEnd());
                    break;
               }
               String[] y = memory[i].split(" ");
               System.out.println("The Instruction that's currently executing is " + memory[i] + " in Process " + pcb.getpId());
               System.out.println("********");
               if (y[0].equals("print")) {
                    systemCall.print( pcb,y[1]);
               }
               else if (y[0].equals("assign")) {
                    if (y[2].equals("input")) {
                         System.out.println("Please enter an input: ");
                         Scanner sc = new Scanner(System.in);
                         Input = sc.nextLine();
                         input=Input;
                         systemCall.assign(y[1], input, pcb);
                         }

                     else if (y[2].equals("readFile")) {
                          systemCall.readFile(memoryInstance.read(pcb, y[1]));
                          systemCall.assign(y[1], readFile, pcb);
                    }
               } else if (y[0].equals("writeFile")) {

                    systemCall.writeFile(memoryInstance.read(pcb, y[1]), memoryInstance.read(pcb, y[2]));

               } else if (y[0].equals("printFromTo")) {
                    systemCall.printFromTo(y[1], y[2], pcb);
               } else if (y[0].equals("semWait")) {
                    if (y[1].equals("userInput")) {
                         userInput.semWait(pcb);
                    } else if (y[1].equals("userOutput")) {
                         userOutput.semWait(pcb);
                    } else if (y[1].equals("file")) {
                         file.semWait(pcb);
                    }

                    if (generalBlocked.contains(pcb.getpId())) {
                         updatePC(pcb);

                         break;
                    }
               } else if (y[0].equals("semSignal")) {
                    if (y[1].equals("userInput")) {
                         userInput.semSignal(pcb);
                    } else if (y[1].equals("userOutput")) {
                         userOutput.semSignal(pcb);
                    } else if (y[1].equals("file")) {
                         file.semSignal(pcb);
                    }
               }
               t1--;
               t2--;
               t3--;

               if (t1 == 0) {
                    System.out.println("Process 1" + " arrived.");
                   scheduler.pcb1= createProcess("src/Program_1.txt");

               }
               else if (t2 == 0) {
                    System.out.println("Process 2" + " arrived.");
                    scheduler.pcb2=createProcess("src/Program_2.txt");

               }
               else if (t3 == 0) {
                    System.out.println("Process 3" + " arrived.");
                   scheduler.pcb3= createProcess("src/Program_3.txt");
               }
                updatePC(pcb);

          }
          if(!generalBlocked.contains(pcb.getpId())){
          changeState(pcb,"Ready");
          this.Ready.add(pcb.getpId());
          }
          return pcb.getPc();
     }





     public  void changeState(PCB pcb,String state) {
          pcb.setState(state);
          if (pcb.getpId() == 1) {
               if (memory[0].equals("1")) {
                    memory[1] = state;
               } else if (memory[5].equals("1")) {
                    memory[6] = state;
               }

          }
          else if (pcb.getpId() == 2) {

               if (memory[0].equals("2")) {
                    memory[1] = state;
               } else if (memory[5].equals("2")) {
                    memory[6] = state;
               }

          }
          else if (pcb.getpId() == 3) {

               if (memory[0].equals("3")) {
                    memory[1] = state;
               } else if (memory[5].equals("3")) {
                    memory[6] = state;
               }

          }

     }
     public void printQueues(){
          System.out.print("Ready Queue: ");
          printQueue(Ready);
          System.out.print("General Blocked Queue: ");
          printQueue(generalBlocked);
          System.out.print("userInput Blocked Queue: ");
          printQueue(inputBlocked);
          System.out.print("userOutput Blocked Queue: ");
          printQueue(outputBlocked);
          System.out.print("file Blocked Queue: ");
          printQueue(fileBlocked);
          System.out.println("");
          System.out.println("*********************");
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
     public static void printQueue(Queue<Integer> q) {
          for (Integer item : q) {
               System.out.print(item + " ");
          }
          System.out.println();
     }
     public static void main(String[] args) throws IOException {
        int Q=0;
//		saveToMemory("D:\\GUC\\CODING\\ProjectOS\\Program_1.txt", 1);
//		saveToMemory("D:\\GUC\\CODING\\ProjectOS\\Program_2.txt", 2);
//		saveToMemory("D:\\GUC\\CODING\\ProjectOS\\Program_3.txt", 3);

          Scanner sc = new Scanner(System.in);
          System.out.println("Please Enter The Arrival Time Of The First Process: ");
          t1 = sc.nextInt();

          System.out.println("Please Enter The Arrival Time Of The Second Process: ");
          t2 = sc.nextInt();

          System.out.println("Please Enter The Arrival Time Of The Third Process: ");
          t3 = sc.nextInt();

          System.out.println("Please Enter The Quanta: ");
          Q = sc.nextInt();

//		t1 = 0;
//		t2 = 1;
//		t3 = 4;
//		Q = 2;
//          BufferedReader br = new BufferedReader(new FileReader(HardDisk));
//          String x;
//          FileWriter diskWriter = new FileWriter(HardDisk);
//          while (((x = br.readLine()) != null)) {
//               diskWriter.write("" + System.lineSeparator());
//          }
//          diskWriter.close();
          scheduler = new Scheduler();
          scheduler.schedule(t1, t2, t3, Q);

     }

}
