import java.io.IOException;
import java.util.Scanner;

public class OS {
    public static void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter The Arrival Time Of The First Process: ");
        int t1 = sc.nextInt();

        System.out.println("Please Enter The Arrival Time Of The Second Process: ");
        int t2 = sc.nextInt();

        System.out.println("Please Enter The Arrival Time Of The Third Process: ");
        int t3 = sc.nextInt();

        System.out.println("Please Enter The Quanta: ");
        int q = sc.nextInt();
        Parser parser = new Parser();
        Memory memory = new Memory();
//        System.out.println(memory.getMemory()==null);
//        System.out.println(Memory.memory.length);
        Parser.t1 = t1;
        Parser.t2 = t2;
        Parser.t3 = t3;
        Scheduler.schedule(q);
    }
    public static void main(String[] args) throws IOException {
        run();
//		saveToMemory("D:\\GUC\\CODING\\ProjectOS\\Program_1.txt", 1);
//		saveToMemory("D:\\GUC\\CODING\\ProjectOS\\Program_2.txt", 2);
//		saveToMemory("D:\\GUC\\CODING\\ProjectOS\\Program_3.txt", 3);



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
