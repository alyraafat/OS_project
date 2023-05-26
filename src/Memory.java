import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Memory {
    static String memory[];

    public Memory() {
        this.memory= new String[40];
    }

    public static int blockedInMemory(){
        if (Parser.generalBlocked.contains(Integer.parseInt(memory[0]))){
            return 0;
        }else if(Parser.generalBlocked.contains(Integer.parseInt(memory[5]))){
            return 5;
        }else{
            return -1;
        }
    }

}
