package inmem.core.app.parser;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

public final class Scanner {
    
    private String query;

    public Scanner(String query) {
        this.query = query.toLowerCase();
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        Iterator<String> list = Arrays.asList(query.split(" ")).iterator();

        while (list.hasNext()) {
            String sub = list.next();
            switch (sub) {
                case "get":
                    tokens.add(new Token.Get());
                    break;
                case "set":
                    tokens.add(new Token.Set());
                    break;
                case "del":
                    tokens.add(new Token.Del());
                    break;
                default:
                    tokens.add(new Token.Identifier(sub));
                    break;
            }
        }
        
        tokens.add(new Token.EOS());
        return tokens;
    } 

}
