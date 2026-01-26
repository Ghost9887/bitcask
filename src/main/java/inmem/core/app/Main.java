package inmem.core.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import inmem.core.app.frontend.parser.*;

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
            else if (query.equals("clear")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                continue;
            }

            Scanner scanner = new Scanner(query);  
            List<Token> tokens = scanner.tokenize();

            Parser parser = new Parser(tokens);
            Optional<Statement> stmnt = parser.parse();
            if (stmnt.isPresent()) {
                System.out.println(stmnt.get());
            }
        }
    }

}
