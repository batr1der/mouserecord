package mouserecord;

import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        MouseRecord mr = new MouseRecord();

        if(args.length < 1) {
            throw new Exception("No arguments.");
        }

        switch(args[0]) {
            case "record":
                mr.record(args[1]);
                break;
            case "play":
                mr.play(args[1]);
                break;
        }
    }
}
