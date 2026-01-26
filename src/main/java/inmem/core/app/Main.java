package inmem.core.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try {
            repl();
        }catch (IOException e) {
            System.out.println("Error: " + e);
            System.exit(-1);
        }
    }

    public static void repl() throws IOException {
        //clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.printf("inmem > ");
            final String query = buff.readLine().trim();

            if (query.equals("quit")) {
                break;
            }
        }
    }

}
