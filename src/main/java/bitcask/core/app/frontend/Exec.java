package bitcask.core.app.frontend;

import bitcask.core.app.frontend.parser.Statement;
import bitcask.core.app.backend.Bitcask;

import java.util.Optional;

public class Exec {
    
    private Statement statement;

    public Exec(Statement statement) {
        this.statement = statement;
    }

    public void execute() {
        switch (statement) {
            case Statement.Set a -> {
                Bitcask.add(a.core().key(), a.core().value());
                System.out.println(1);
            }
            case Statement.Get b -> {
                Optional<String> out = Bitcask.get(b.core().key());
                if (out.isPresent()) {
                    System.out.println(out.get());
                    break;
                }
                System.out.println("Doesn't exist");
            }
            case Statement.Del c -> {
                Bitcask.delete(c.core().key());
                System.out.println(-1);
            }
            default -> {
                break;
            }
        }
    }

}
