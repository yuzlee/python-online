import com.rolrence.bulletscreen.util.*;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Rolrence on 2017/6/2.
 *
 */
public class Test {
    public static void main(String[] args) {
        try {
            StreamDelegate delegate = new StreamDelegate(s -> {
                System.out.println(s);
                return true;
            });
            PipedOutputStream pout = new PipedOutputStream();
            PipedInputStream pin = new PipedInputStream();
            pin.connect(pout);
            String command = "D:\\Workspace\\Experiment\\bullet-screen\\pythonutil\\x64\\Release\\pythonutil.exe";
//            String command = "C:\\Users\\Rolrence\\Desktop\\a.exe";
            ProcessStreamRunnable p = new ProcessStreamRunnable(command, pin, delegate);
            Thread thread = new Thread(p);
            thread.start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                // System.out.print("Input: ");
                String content = scanner.nextLine();
                pout.write(content.getBytes());
                pout.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
