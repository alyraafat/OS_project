import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Parser {
     static int test=-100;
     static int postponed = -1;
     static int counter=0;
     static String userinput;
     static String input1;
     static String input2;
     static String input3;
     static String readFile;
     static String[] memory= Memory.memory;
     static Queue<Integer> Ready = new LinkedList<Integer>();
     static Queue<Integer> generalBlocked = new LinkedList<Integer>();
     static Queue<Integer> inputBlocked = new LinkedList<Integer>();
     static Queue<Integer> outputBlocked = new LinkedList<Integer>();
     static Queue<Integer> fileBlocked = new LinkedList<Integer>();
     static Mutex userInput = new Mutex("userInput");
     static Mutex userOutput = new Mutex("userOutput");
     static Mutex file = new Mutex("file");
//     static String[] Disk=new String[20];
//     static  String[] temp1=new String[20];
     static SystemCall systemCall=new SystemCall();
//     static Scheduler Scheduler=Scheduler.getInstance();
     static File hardDisk =new File("src/hardDisk");
     static File temp=new File("src/temp");
     static int t1,t2,t3,wasJustRunning;

//     private static Parser instance;
     public Parser(){}
//     public static Parser getInstance() {
//          if (instance == null){
//               instance = new Parser();
//          }
//          return instance;
//     }
     private static void updatePcHelper(int x, String id){
          if (memory[0].equals(id)) {
               memory[2] =(x+1)+"" ;
          } else if (memory[5].equals(id)) {
               memory[7] = (x+1)+"";
          }
     }
     public static void updatePC(PCB pcb) {
          int x= pcb.getPc();
          if (pcb.getpId() == 1) {
               updatePcHelper(x,"1");
//               if (memory[0].equals("1")) {
//                    memory[2] =(x+1)+"" ;
//               } else if (memory[5].equals("1")) {
//                    memory[7] = (x+1)+"";
//               }
          } else if (pcb.getpId() == 2) {
               updatePcHelper(x,"2");
//               if (memory[0].equals("2")) {
//                    memory[2] = (x+1)+"";
//               } else if (memory[5].equals("2")) {
//                    memory[7] = (x+1)+"";
//               }
          } else if (pcb.getpId() == 3) {
               updatePcHelper(x,"3");
//               if (memory[0].equals("3")) {
//                    memory[2] = (x+1)+"";
//               } else if (memory[5].equals("3")) {
//                    memory[7] =(x+1)+"";
//               }
          }
          pcb.setPc(pcb.getPc()+1);
     }

     public static int spaceAvailable(String[] mem) {
          if (mem[0]==null|| mem[0].equals("") || mem[1].equalsIgnoreCase("Finished")) {
               return 0;
          } else if (mem[5]==null || mem[5].equals("") || mem[6].equalsIgnoreCase("Finished")) {
               return 5;
          } else{
               return -1;

          }
     }

//     public void swapDiskToTemp(){
//
//          for (int i = 0; i < 20 ; i++) {
//               temp[i] = Disk[i];
//          }
//     }
     public static void changePc(int id,int value){
          if(id==1){
               Scheduler.pcb1.setPc(value);
          } else if (id==2) {
              Scheduler.pcb2.setPc(value);
          }else{
               Scheduler.pcb3.setPc(value);
          }
     }
     public static void swapDiskToMem() throws IOException {
          BufferedReader br = new BufferedReader(new FileReader(hardDisk));
          String st;
          int space = spaceAvailable(memory);
          int pcConfirmation=-1;
          if (space == 0) {
               // file will be inserted from 0
               for (int i = 0; i < 5 && ((st = br.readLine()) != null); i++) {
                    if (i==2){
                        pcConfirmation= Integer.parseInt(st);
//                        pcConfirmation= 39-pcConfirmation;

                         if (pcConfirmation>24){
                              pcConfirmation=10+(pcConfirmation-25);
                              memory[i] = String.valueOf(pcConfirmation);
                              changePc(Integer.parseInt(memory[0]),pcConfirmation);
                        }else {
                              memory[i] = st;
                        }
                    }else{
                         memory[i] = st;
                    }

               }
               for (int i = 10; i < 25 && ((st = br.readLine()) != null); i++) {
                    System.out.println(st);
                    memory[i] = st;
               }

               memory[3] = "0";
               memory[4] = "24";
               updatePCB(Integer.parseInt(memory[0]),0);
               System.out.println("The process that is swapped from disk to memory is " + memory[0]);

          } else if (space == 5) {
               // 5
               for (int i = 5; i < 10 && ((st = br.readLine()) != null); i++) {
                    if (i==7){
                         pcConfirmation= Integer.parseInt(st);
                         // imagine pc = 24 so it should enter if part
                         if (pcConfirmation<25){
                              pcConfirmation=25+(pcConfirmation-10);
                              memory[i] = String.valueOf(pcConfirmation);
                              changePc(Integer.parseInt(memory[5]),pcConfirmation);
                         }else {
                              memory[i] = st;
                         }
                    }else{
                    memory[i] = st;}
               }
               for (int i = 25; i < 40 && ((st = br.readLine()) != null); i++) {
                    memory[i] = st;
               }
               memory[8] = "5";
               memory[9] = "39";
               updatePCB(Integer.parseInt(memory[5]),5);
               System.out.println("The process that is swapped from disk to memory is " + memory[5]);
          } else {
               // check not running, swap with it
               File temp = new File("Temp");
               if (!(memory[1].equals("Running"))) {
                    swapTemp(temp);
                    // Disk >> Mem
                    for (int i = 0; i < 5 && ((st = br.readLine()) != null); i++) {
                         if (i==2){
                              System.out.println(st);
                              pcConfirmation= Integer.parseInt(st);
//                              pcConfirmation= 39-pcConfirmation;
                              if (pcConfirmation>24){
                                   pcConfirmation=10+(pcConfirmation-25);
                                   System.out.println(pcConfirmation);
                                   memory[i] = String.valueOf(pcConfirmation);
                                   changePc(Integer.parseInt(memory[0]),pcConfirmation);
                              }else {
                                   memory[i] = st;
                              }
                         }else{
                              memory[i] = st;
                         }

                    }
                    for (int i = 10; i < 25 && ((st = br.readLine()) != null); i++) {
                         memory[i] = st;
                    }
                    memory[3] = "0";
                    memory[4] = "24";
                    updatePCB(Integer.parseInt(memory[0]),0);
                    // temp >> Disk
                    swapFileToFile(temp);

               } else
                    if (!(memory[6].equals("Running"))) {
                    // Disk >> Mem
                    swapTemp(temp);
                    for (int i = 5; i < 10 && ((st = br.readLine()) != null); i++) {
                         if (i==7){
                              pcConfirmation= Integer.parseInt(st);
                              if (pcConfirmation<25){
                                   pcConfirmation=25+(pcConfirmation-10);
                                   System.out.println(pcConfirmation);
                                   memory[i] = String.valueOf(pcConfirmation);
                                   changePc(Integer.parseInt(memory[5]),pcConfirmation);
                              }else {
                                   memory[i] = st;
                              }
                         }else{
                              memory[i] = st;}
                    }
                    for (int i = 25; i < 40 && ((st = br.readLine()) != null); i++) {
                         memory[i] = st;
                    }
                    memory[8] = "5";
                    memory[9] = "39";
                    updatePCB(Integer.parseInt(memory[5]),5);
                         // temp >> Disk
                    swapFileToFile(temp);
               }
          }
     }
     public static void updatePCB(int id, int startPCB){
          if(id==1){
               Scheduler.pcb1.updatePCB(startPCB, memory);
          } else if (id==2) {
               Scheduler.pcb2.updatePCB(startPCB, memory);
          }else{
               Scheduler.pcb3.updatePCB(startPCB, memory);
          }
     }

     public static void swapFileToFile(File temp) throws IOException {

          // delete contents of Hard Disk
          BufferedReader br = new BufferedReader(new FileReader(hardDisk));
          String x;
          FileWriter diskWriter = new FileWriter(hardDisk);
          while (((x = br.readLine()) != null)) {
               diskWriter.write("" + System.lineSeparator());
          }
          diskWriter.close();
          br.close();
          // read from temp
          BufferedReader buffer = new BufferedReader(new FileReader(temp));
          String st;

          // write temp into disk
          FileWriter writer = new FileWriter(hardDisk);

          while (((st = buffer.readLine()) != null)) {
               writer.write(st + System.lineSeparator());
          }
          writer.close();
          buffer.close();
     }


     public static void swapTemp(File temp) throws IOException {
          FileWriter writer = new FileWriter(temp);
          if (Memory.blockedInMemory()==-1) {
               if (!(memory[1].equals("Running"))) {
                    swapMemToDiskHelper(writer, 0, 5, 10, 25);
//               System.out.println("The process that is swapped from memory to disk is "+ memory[0]);
//               for (int i = 0; i < 5; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
//               for(int i = 10; i < 25; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
               } else {
                    swapMemToDiskHelper(writer, 5, 10, 25, 40);
//               System.out.println("The process that is swapped from memory to disk is "+ memory[5]);
//               for (int i = 5; i < 10; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
//               for(int i = 25; i < 40; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
               }

          }else if(Memory.blockedInMemory()==0){
               swapMemToDiskHelper(writer, 0, 5, 10, 25);
          }else{
               swapMemToDiskHelper(writer, 5, 10, 25, 40);
          }
          writer.close();
     }
     private static int swapMemToDiskHelper(FileWriter writer, int startPCB,int endPCB, int startInst, int endInst) throws IOException {
          System.out.println("The process that is swapped from memory to disk is "+ memory[startPCB]);
          for (int i = startPCB; i < endPCB; i++) {
               String data = memory[i];
               writer.write(data + System.lineSeparator());
               memory[i] = "";
          }
          for(int i = startInst; i < endInst; i++) {
               String data = memory[i];
               writer.write(data + System.lineSeparator());
               memory[i] = "";
          }
          return startPCB;
     }
     public static int swapMemToDisk() throws IOException {
          int id = -1;
          FileWriter writer = new FileWriter(hardDisk);
          if (!(memory[1].equals("Running"))) {
               id = swapMemToDiskHelper(writer,0,5,10,25);
//               System.out.println("The process that is swapped from memory to disk is "+ memory[0]);
//               for (int i = 0; i < 5; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
//               for(int i = 10; i < 25; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
//               id = 0;
          } else {
               id = swapMemToDiskHelper(writer,5,10,25,40);
//               System.out.println("The process that is swapped from memory to disk is "+ memory[5]);
//               for (int i = 5; i < 10; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
//               for(int i = 25; i < 40; i++) {
//                    String data = memory[i];
//                    memory[i] = "";
//                    writer.write(data + System.lineSeparator());
//               }
//               id = 5;
          }
          writer.close();
          return id;
     }
//     public  void swapToMem(boolean disk){
//          int availbleSpace = spaceAvailable(memory);
//          int j=0;
//          if (availbleSpace == 0) {
//               for(int i = 0; i < 5; i++) {
//                    memory[i] = disk?Disk[j]:temp[j];
//                    j++;
//               }
//               for(int i = 10; i < 25; i++) {
//                    memory[i] = disk?Disk[j]:temp[j];
//                    j++;
//               }
//               memory[3] = "0";
//               memory[4] = "24";
//               System.out.println("process with id "+ memory[0]+ " is swapped from disk to memory");
//
//          } else if (availbleSpace == 5) {
//
//               for(int i = 5; i < 10; i++) {
//                    memory[i] = disk?Disk[j]:temp[j];
//                    j++;
//               }
//               for(int i = 25; i < 40; i++) {
//                    memory[i] = disk?Disk[j]:temp[j];
//                    j++;
//               }
//               memory[8] = "5";
//               memory[9] = "39";
//               System.out.println("process with id "+ memory[5]+ " is swapped from disk to memory");
//          }
//
//     }

//     public int swapMemToDisk(){
//          int emptied=-1;
//          if (!(memory[1].equals("Running"))) {
//               System.out.println("process with id "+ memory[0]+ " is swapped From memory to disk");
//               int j=0;
//               for (int i = 0; i < 5; i++) {
//                    Disk[j] = memory[i];
//                    memory[i] = "";
//                    j++;
//
//               }
//               for (int i = 10; i < 25; i++) {
//                    Disk[j] = memory[i];
//                    memory[i] = "";
//
//               }
//               emptied= 0;
//            }
//          else{
//               System.out.println("process with id "+ memory[5]+ " is swapped from memory to disk");
//               int j=0;
//               for (int i = 5; i < 10; i++) {
//                    Disk[j] = memory[i];
//                    memory[i] = "";
//                    j++;
//               }
//               for (int i = 25; i < 40; i++) {
//                    Disk[j] = memory[i];
//                    memory[i] = "";
//                    j++;
//               }
//               emptied= 5;
//          }
//          return emptied;
//     }
     public static PCB createProcess(String path) throws IOException {
          boolean wasFull = false;
          int removed=-1;
          int pcbId = -1;
          if (path.equals("src/Program_1.txt")) {
               pcbId = 1;
          } else if (path.equals("src/Program_2.txt")) {
               pcbId = 2;
          } else if (path.equals("src/Program_3.txt")) {
               pcbId = 3;
          }
          String state = "Ready";
          int pc = -1;
          int memStart = -1;
          int memEnd = -1;
//          System.out.println(spaceAvailable(memory));
          int spaceAvailable = spaceAvailable(memory);
          if (spaceAvailable == -1) {
               //if memory is full
               int emptied = swapMemToDisk();
               if(!Ready.isEmpty()){
                    wasFull = true;
                    removed=Ready.remove();
               }
               memStart = emptied;
               memEnd = emptied == 0 ? 24 : 39;
               pc = emptied == 0 ? 13 : 28;
          } else {
               if (spaceAvailable == 0) {
                    pc = 13;
               } else {
                    pc = 28;
               }
               memStart = spaceAvailable;
               memEnd = spaceAvailable == 0 ? 24 : 39;
          }
          PCB pcb = new PCB(pcbId, state, pc, memStart, memEnd);
          Memory.saveInMemory(pcb, path);
//          Scheduler.readyQueueSwap(pcbId);
          Ready.add(pcb.getpId());
          if(postponed!=-1){
               Ready.add(postponed);
               postponed=-1;
          }
          if (wasFull){
               Ready.add(removed);
          }
          return pcb;
     }
     public static int execute(PCB pcb, int timeSlice,Boolean justArrived) throws IOException {
          int pcValue = pcb.getPc();
          String Input="";
          for (int i = pcValue; i < pcValue + timeSlice && i <= pcb.getMemEnd(); i++) {
               System.out.println("*******************************" );
               if (memory[i]==null||memory[i].equals("null")||memory[i].equals("")) {
                    pcb.setPc(pcb.getMemEnd());
                    Scheduler.fixTimings(true);
                    break;
               }

               if(!justArrived){
                    System.out.println("*******************************" );
                    System.out.println("Clock cycle: " + (counter++));
                    Scheduler.fixTimings(false);
//                    if (test==1) {
//                         changeState(pcb,"Ready");
//                         Ready.add(pcb.getpId());
//                         test=-1;
//                         return -1;
//                    }
               }
               justArrived = false;
               String[] y = memory[i].split(" ");
               System.out.println("The Instruction that's currently executing is " + memory[i] + " in Process " + pcb.getpId());
               System.out.println("********");
               if (y[0].equals("print")) {
                    systemCall.print( pcb,y[1]);
               }
               else if (y[0].equals("input")) {
                    System.out.println("Please enter an input: ");
                    Scanner sc = new Scanner(System.in);
                    userinput = sc.nextLine();
                    if (pcb.getpId() == 1) {
                         input1 = userinput;
                    } else if (pcb.getpId() == 2) {
                         input2 = userinput;
                    } else if (pcb.getpId() == 3) {
                         input3 = userinput;
                    }
               }
               else if (y[0].equals("assign")) {
                    if (y[2].equals("input")) {
                         if (y[2].equals("input")) {
                              if (pcb.getpId() == 1) {
                                   systemCall.assign(y[1], input1, pcb);
                              } else if (pcb.getpId() == 2) {
                                   systemCall.assign(y[1], input2, pcb);
                              } else if (pcb.getpId() == 3) {
                                   systemCall.assign(y[1], input3, pcb);
                              }
                         }

                    }
                     else if (y[2].equals("readFile")) {
                          //assign b readFile a
                          systemCall.assign(y[1], readFile, pcb);
                    }

               }
               else if (y[0].equals("readFile")) {
                    systemCall.readFile(Memory.read(pcb, y[1]));

               }
               else if (y[0].equals("writeFile")) {
                    systemCall.writeFile(Memory.read(pcb, y[1]), Memory.read(pcb, y[2]));

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
               updatePC(pcb);
               Scheduler.printAllData();
          }
          // mesh el mfrood tb2a getMemEnd + 1

          boolean finished=(pcb.getPc()==(pcb.getMemEnd()+1)||memory[pcb.getPc()]==null||memory[pcb.getPc()].equals("")||memory[pcb.getPc()].equals("null"));
          wasJustRunning=pcb.getpId();
          if(!generalBlocked.contains(pcb.getpId())&&(!finished)){
               changeState(pcb,"Ready");
//               Scheduler.fixTimings(false);
               if(t1-1==0||t2-1==0||t3-1==0){
                    postponed = pcb.getpId();
               }else{
                    Ready.add(pcb.getpId());
               }
          }
          return pcb.getPc();
     }

     public static void changeState(PCB pcb, String state) {
          pcb.setState(state);
          if (pcb.getpId() == 1) {
               changeStateHelper("1",state);
//               if (memory[0].equals("1")) {
//                    memory[1] = state;
//               } else if (memory[5].equals("1")) {
//                    memory[6] = state;
//               }
          }
          else if (pcb.getpId() == 2) {
               changeStateHelper("2",state);
//               if (memory[0].equals("2")) {
//                    memory[1] = state;
//               } else if (memory[5].equals("2")) {
//                    memory[6] = state;
//               }
          }
          else if (pcb.getpId() == 3) {
               changeStateHelper("3",state);
//               if (memory[0].equals("3")) {
//                    memory[1] = state;
//               } else if (memory[5].equals("3")) {
//                    memory[6] = state;
//               }
          }

     }
     private static void changeStateHelper(String id, String state){
          if (memory[0].equals(id)) {
               memory[1] = state;
          } else if (memory[5].equals(id)) {
               memory[6] = state;
          }
     }
     public static void printQueues(){
          System.out.print("Ready Queue: ");
          printQueue(Ready);
          System.out.print("General Blocked Queue: ");
          printQueue(generalBlocked);
          System.out.print("userInput Blocked Queue: ");
//          printQueue(inputBlocked);
          printQueue(userInput.blocked);
          System.out.print("userOutput Blocked Queue: ");
//          printQueue(outputBlocked);
          printQueue(userOutput.blocked);
          System.out.print("file Blocked Queue: ");
//          printQueue(fileBlocked);
          printQueue(file.blocked);
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
          Scheduler.schedule( Q);
//               Parser.createProcess("src/Program_1.txt");
//               Parser.createProcess("src/Program_2.txt");
//               Parser.createProcess("src/Program_3.txt");
//
//               SwapDiskToMem();
//          System.out.println( memory[24]);
//
//          String s=memory[24];
//          System.out.println(s);
//
//          if (s=="null"){
//               System.out.println("mahy sha8ala ahe");
//               System.out.println( memory[24]);
//          }



     }

}
