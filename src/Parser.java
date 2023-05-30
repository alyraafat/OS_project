import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
     static int postponed;
     static int counter;
     static String userinput;
     static String input1;
     static String input2;
     static String input3;
     static String readFile;
     static Queue<Integer> Ready;
     static Queue<Integer> generalBlocked;
     static Mutex userInput;
     static Mutex userOutput;
     static Mutex file;
     static File hardDisk;
     static File temp;
     static int t1,t2,t3,wasJustRunning;

     public Parser(){
          temp=new File("src/temp");
          hardDisk =new File("src/hardDisk");
          file = new Mutex("file");
          userOutput = new Mutex("userOutput");
          userInput = new Mutex("userInput");
          generalBlocked = new LinkedList<Integer>();
          Ready = new LinkedList<Integer>();
          postponed=-1;
          counter=0;
     }
     private static void updatePcHelper(int x, String id){
          if (Memory.memory[0].equals(id)) {
               Memory.memory[2] =(x+1)+"" ;
          } else if (Memory.memory[5].equals(id)) {
               Memory.memory[7] = (x+1)+"";
          }
     }
     public static void updatePC(PCB pcb) {
          int x= pcb.getPc();
          if (pcb.getpId() == 1) {
               updatePcHelper(x,"1");
          } else if (pcb.getpId() == 2) {
               updatePcHelper(x,"2");
          } else if (pcb.getpId() == 3) {
               updatePcHelper(x,"3");
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
          int space = spaceAvailable(Memory.memory);
          int pcConfirmation=-1;
          if (space == 0) {
               // file will be inserted from 0
               for (int i = 0; i < 5 && ((st = br.readLine()) != null); i++) {
                    if (i==2){
                        pcConfirmation= Integer.parseInt(st);
                         if (pcConfirmation>24){
                              pcConfirmation=10+(pcConfirmation-25);
                              Memory.memory[i] = String.valueOf(pcConfirmation);
                              changePc(Integer.parseInt(Memory.memory[0]),pcConfirmation);
                        }else {
                              Memory.memory[i] = st;
                        }
                    }else{
                         Memory.memory[i] = st;
                    }

               }
               for (int i = 10; i < 25 && ((st = br.readLine()) != null); i++) {
                    System.out.println(st);
                    Memory.memory[i] = st;
               }
               Memory.memory[3] = "0";
               Memory.memory[4] = "24";
               updatePCB(Integer.parseInt(Memory.memory[0]),0);
               System.out.println("The process that is swapped from disk to memory is " + Memory.memory[0]);

          } else if (space == 5) {
               // 5
               for (int i = 5; i < 10 && ((st = br.readLine()) != null); i++) {
                    if (i==7){
                         pcConfirmation= Integer.parseInt(st);
                         if (pcConfirmation<25){
                              pcConfirmation=25+(pcConfirmation-10);
                              Memory.memory[i] = String.valueOf(pcConfirmation);
                              changePc(Integer.parseInt(Memory.memory[5]),pcConfirmation);
                         }else {
                              Memory.memory[i] = st;
                         }
                    }else{
                    Memory.memory[i] = st;}
               }
               for (int i = 25; i < 40 && ((st = br.readLine()) != null); i++) {
                    Memory.memory[i] = st;
               }
               Memory.memory[8] = "5";
               Memory.memory[9] = "39";
               updatePCB(Integer.parseInt(Memory.memory[5]),5);
               System.out.println("The process that is swapped from disk to memory is " + Memory.memory[5]);
          } else {
               // check not running, swap with it
               File temp = new File("Temp");
               if (!(Memory.memory[1].equals("Running"))) {
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
                                   Memory.memory[i] = String.valueOf(pcConfirmation);
                                   changePc(Integer.parseInt(Memory.memory[0]),pcConfirmation);
                              }else {
                                   Memory.memory[i] = st;
                              }
                         }else{
                              Memory.memory[i] = st;
                         }

                    }
                    for (int i = 10; i < 25 && ((st = br.readLine()) != null); i++) {
                         Memory.memory[i] = st;
                    }
                    Memory.memory[3] = "0";
                    Memory.memory[4] = "24";
                    updatePCB(Integer.parseInt(Memory.memory[0]),0);
                    // temp >> Disk
                    swapFileToFile(temp);

               } else
                    if (!(Memory.memory[6].equals("Running"))) {
                    // Disk >> Mem
                    swapTemp(temp);
                    for (int i = 5; i < 10 && ((st = br.readLine()) != null); i++) {
                         if (i==7){
                              pcConfirmation= Integer.parseInt(st);
                              if (pcConfirmation<25){
                                   pcConfirmation=25+(pcConfirmation-10);
                                   System.out.println(pcConfirmation);
                                   Memory.memory[i] = String.valueOf(pcConfirmation);
                                   changePc(Integer.parseInt(Memory.memory[5]),pcConfirmation);
                              }else {
                                   Memory.memory[i] = st;
                              }
                         }else{
                              Memory.memory[i] = st;}
                    }
                    for (int i = 25; i < 40 && ((st = br.readLine()) != null); i++) {
                         Memory.memory[i] = st;
                    }
                    Memory.memory[8] = "5";
                    Memory.memory[9] = "39";
                    updatePCB(Integer.parseInt(Memory.memory[5]),5);
                         // temp >> Disk
                    swapFileToFile(temp);
               }
          }
     }
     public static void updatePCB(int id, int startPCB){
          if(id==1){
               Scheduler.pcb1.updatePCB(startPCB, Memory.memory);
          } else if (id==2) {
               Scheduler.pcb2.updatePCB(startPCB, Memory.memory);
          }else{
               Scheduler.pcb3.updatePCB(startPCB, Memory.memory);
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
               if (!(Memory.memory[1].equals("Running"))) {
                    swapMemToDiskHelper(writer, 0, 5, 10, 25);
               } else {
                    swapMemToDiskHelper(writer, 5, 10, 25, 40);
               }

          }else if(Memory.blockedInMemory()==0){
               swapMemToDiskHelper(writer, 0, 5, 10, 25);
          }else{
               swapMemToDiskHelper(writer, 5, 10, 25, 40);
          }
          writer.close();
     }
     private static int swapMemToDiskHelper(FileWriter writer, int startPCB,int endPCB, int startInst, int endInst) throws IOException {
          System.out.println("The process that is swapped from memory to disk is "+ Memory.memory[startPCB]);
          for (int i = startPCB; i < endPCB; i++) {
               String data = Memory.memory[i];
               writer.write(data + System.lineSeparator());
               Memory.memory[i] = "";
          }
          for(int i = startInst; i < endInst; i++) {
               String data = Memory.memory[i];
               writer.write(data + System.lineSeparator());
               Memory.memory[i] = "";
          }
          return startPCB;
     }
     public static int swapMemToDisk() throws IOException {
          int id = -1;
          FileWriter writer = new FileWriter(hardDisk);
          if (Memory.blockedInMemory()==-1) {
               if (!(Memory.memory[1].equals("Running"))) {
                   id= swapMemToDiskHelper(writer, 0, 5, 10, 25);
               } else {
                   id= swapMemToDiskHelper(writer, 5, 10, 25, 40);
               }

          }else if(Memory.blockedInMemory()==0){
               id=swapMemToDiskHelper(writer, 0, 5, 10, 25);
          }else{
              id= swapMemToDiskHelper(writer, 5, 10, 25, 40);
          }
          writer.close();
          return id;
     }
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
          int spaceAvailable = spaceAvailable(Memory.memory);
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
          emptyMemory(spaceAvailable);
          SystemCall.saveInMemory(pcb, path);
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
     public static void emptyMemory(int start) {
          if(start==0) {
               for(int i = 0; i < 5; i++) {
                    Memory.memory[i]="";
               }
               for(int i = 10; i < 25; i++) {
                    Memory.memory[i]="";
               }
          }
          else if(start==5) {
               for(int i = 5; i < 10; i++) {
                    Memory.memory[i]="";
               }
               for(int i = 25; i < 40; i++) {
                    Memory.memory[i]="";
               }
          }
     }
     public static int execute(PCB pcb, int timeSlice,Boolean justArrived) throws IOException {
          int pcValue = pcb.getPc();
          for (int i = pcValue; i < pcValue + timeSlice && i <= pcb.getMemEnd(); i++) {
               if (Memory.memory[i]==null||Memory.memory[i].equals("null")||Memory.memory[i].equals("")) {
                    pcb.setPc(pcb.getMemEnd());
                    Scheduler.fixTimings(true);
                    break;
               }

               if(!justArrived){
                    System.out.println("*******************************" );
                    System.out.println("*******************************" );
                    System.out.println("Clock cycle: " + (counter++));
                    Scheduler.fixTimings(false);
               }
               System.out.println("Process " + pcb.getpId() + " is Running.");
               justArrived = false;
               String[] y = Memory.memory[i].split(" ");
               System.out.println("The Instruction that's currently executing is " + Memory.memory[i] + " in Process " + pcb.getpId());
               System.out.println("********");
               if (y[0].equals("print")) {
                    SystemCall.print( pcb,y[1]);
               }
               else if (y[0].equals("input")) {
                    userinput = SystemCall.takeInputFromUser();
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
                                   SystemCall.assign(y[1], input1, pcb);
                              } else if (pcb.getpId() == 2) {
                                   SystemCall.assign(y[1], input2, pcb);
                              } else if (pcb.getpId() == 3) {
                                   SystemCall.assign(y[1], input3, pcb);
                              }
                         }
                    }
                     else if (y[2].equals("readFile")) {
                          //assign b readFile a
                          SystemCall.assign(y[1], readFile, pcb);
                    }
               }
               else if (y[0].equals("readFile")) {
                    SystemCall.readFile(SystemCall.readFromMemory(pcb, y[1]));
               }
               else if (y[0].equals("writeFile")) {
                    SystemCall.writeFile(SystemCall.readFromMemory(pcb, y[1]), SystemCall.readFromMemory(pcb, y[2]));
               } else if (y[0].equals("printFromTo")) {
                    SystemCall.printFromTo(y[1], y[2], pcb);
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
               SystemCall.printAllData();
          }

          boolean finished=(pcb.getPc()==(pcb.getMemEnd()+1)||Memory.memory[pcb.getPc()]==null||Memory.memory[pcb.getPc()].equals("")||Memory.memory[pcb.getPc()].equals("null"));
          wasJustRunning=pcb.getpId();
          if(!generalBlocked.contains(pcb.getpId())&&(!finished)){
               changeState(pcb,"Ready");
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
          }
          else if (pcb.getpId() == 2) {
               changeStateHelper("2",state);
          }
          else if (pcb.getpId() == 3) {
               changeStateHelper("3",state);
          }

     }
     private static void changeStateHelper(String id, String state){
          if (Memory.memory[0].equals(id)) {
               Memory.memory[1] = state;
          } else if (Memory.memory[5].equals(id)) {
               Memory.memory[6] = state;
          }
     }
}
