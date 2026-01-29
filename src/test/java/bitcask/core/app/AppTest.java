package bitcask.core.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import bitcask.core.app.frontend.parser.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


public class AppTest {

    @Test
    public void tokenize() {
        String query = "set name jakub";
        List<Token> tokens = new Scanner(query).tokenize();  

        List<Token> expected = new ArrayList<>();
        expected.add(new Token.Set());
        expected.add(new Token.Identifier("name"));
        expected.add(new Token.Identifier("jakub"));
        expected.add(new Token.EOS());

        assertEquals(tokens, expected);
    }

    @Test
    public void parseSetStatement() {
        String query = "set name jakub";
        List<Token> tokens = new Scanner(query).tokenize();
        Statement stmnt = new Parser(tokens).parse().get();
        
        Statement exptected = new Statement.Set(
            new Statement.SetCore("name", "jakub")
        );

        assertEquals(stmnt, exptected);
    }
    
    @Test
    public void parseGetStatement() {
        String query = "get name";
        List<Token> tokens = new Scanner(query).tokenize();
        Statement stmnt = new Parser(tokens).parse().get();
        
        Statement exptected = new Statement.Get(
            new Statement.GetCore("name")
        );

        assertEquals(stmnt, exptected);
    }
    
    @Test
    public void parseDelStatement() {
        String query = "del name";
        List<Token> tokens = new Scanner(query).tokenize();
        Statement stmnt = new Parser(tokens).parse().get();
        
        Statement exptected = new Statement.Del(
            new Statement.DelCore("name")
        );

        assertEquals(stmnt, exptected);
    }
}
