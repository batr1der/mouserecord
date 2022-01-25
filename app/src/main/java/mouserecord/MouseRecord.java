package mouserecord;

import java.util.Arrays;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.AWTException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class MouseRecord {

    private PointerInfo mi;
    private Point p;
    private int x;
    private int y;
    private Robot robot;
    private int bufferedSize = 64;

    public boolean stopped = false;
    CommandLineInterface cli;

    public MouseRecord() {
        cli = new CommandLineInterface(this);
        cli.start();
    }

    public void record(String recordFileName) throws IOException, InterruptedException {
        File file = new File(recordFileName);
        if(file.exists()) {
            System.out.println("\nFile "+recordFileName+" already exists.");
            System.exit(0);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int[][] buffer = new int[bufferedSize][2];

        int i = 0;
        outerLoop:
        while(true) {
            mi = MouseInfo.getPointerInfo();
            p = mi.getLocation();
            buffer[i][0] = (int) p.getX();
            buffer[i][1] = (int) p.getY();
            i++;

            if(i==bufferedSize) {
                for(int x=0; x<buffer.length; x++) {
                    writer.write(String.valueOf(buffer[x][0])+" "+String.valueOf(buffer[x][1]+"\n"));
                }
                writer.flush();
                if(stopped) {
                    System.out.println("Mouse data was written to '"+recordFileName+"'.");
                    break outerLoop;
                }
                buffer = new int[bufferedSize][2];
                i = 0;
            }

            Thread.sleep(18);
        }
        writer.close();
    }

    public void play(String recordFileName) throws IOException, InterruptedException, AWTException {
        robot = new Robot();
        File file = new File(recordFileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while((line=reader.readLine())!=null) {
            if(stopped) break;
            x = Integer.parseInt(line.split(" ")[0]);
            y = Integer.parseInt(line.split(" ")[1]);
            robot.mouseMove(x, y);
            Thread.sleep(18);
        }
        cli.stop();
        System.out.println("\nFile '"+recordFileName+"' was successfully played.");
        System.exit(0);
        reader.close();
    }

}
