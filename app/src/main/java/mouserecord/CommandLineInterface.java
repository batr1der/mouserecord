package mouserecord;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CommandLineInterface extends Thread {

    private BufferedReader input;
    MouseRecord mr;

    public CommandLineInterface (MouseRecord mr) {
        input = new BufferedReader(new InputStreamReader(System.in));
        this.mr = mr;
    }

    public void run()  {
        try{
            while(true) {
                System.out.print(" >>> ");
                String cmd = input.readLine();
                if(cmd.equals("stop")) {
                    mr.stopped = true;
                    break;
                }   
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}
