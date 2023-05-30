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
        Parser.t1 = t1;
        Parser.t2 = t2;
        Parser.t3 = t3;
        Scheduler.schedule(q);
    }
    public static void main(String[] args) throws IOException {
        run();
    }

}
