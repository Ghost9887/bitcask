package inmem.core.app.frontend.parser;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public final class Parser {

    private final List<Token> tokens;
    private int ip = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    //helpers
    private void advance() {
        ip++;
    }

    private Optional<Token> peek() {
        if (ip < tokens.size()) {
            return Optional.of(tokens.get(ip));
        }
        return Optional.empty();
    }

    private <T extends Token> Optional<T> consume(Class<T> expected) {
        Optional<Token> token = peek();

        if (token.isPresent() && expected.isInstance(token.get())) {
            advance();
            return Optional.of(expected.cast(token.get()));
        }
        return Optional.empty();
    }

    public Optional<Statement> parse() {
        Optional<Token> token = peek();

        if (token.isPresent()) {
            switch (token.get()) {
                case Token.Set a -> {
                    Optional<Statement.SetCore> core = parseSet();
                    return core.map(Statement.Set::new);
                }
                case Token.Get b -> {
                    Optional<Statement.GetCore> core = parseGet();
                    return core.map(Statement.Get::new);
                }
                case Token.Del c -> {
                    Optional<Statement.DelCore> core = parseDel();
                    return core.map(Statement.Del::new);
                }
                default -> {
                    System.out.println(
                        "Invalid syntax: Expected ['set', 'get', 'del']"
                    );
                    return Optional.empty();
                }
            }
        }

        System.out.println(
            "Invalid syntax: Expected ['set', 'get', 'del']"
        );
        return Optional.empty();
    }

    private Optional<Statement.SetCore> parseSet() {
        consume(Token.Set.class);

        Optional<Token.Identifier> key = consume(Token.Identifier.class);
        Optional<Token.Identifier> value = consume(Token.Identifier.class);

        if (key.isPresent()) {
            if(value.isPresent()) {
                if(consume(Token.EOS.class).isPresent()) {
                    return Optional.of(
                        new Statement.SetCore(key.get().value(), value.get().value()
                    ));
                }
                System.out.println("Invalid syntax: Expected 'end'");
                return Optional.empty();
            }
            System.out.println("Invalid syntax: Expected 'value'");
            return Optional.empty();
        }
        System.out.println("Invalid syntax: Expected 'key'");
        return Optional.empty();
    }

    private Optional<Statement.GetCore> parseGet() {
        consume(Token.Get.class);
        Optional<Token.Identifier> key = consume(Token.Identifier.class);

        if (key.isPresent()) {
            if (consume(Token.EOS.class).isPresent()) {
                return Optional.of(new Statement.GetCore(key.get().value()));
            }
            System.out.println("Invalid syntax: Expected 'end'");
            return Optional.empty();
        }

        System.out.println("Invalid syntax: Expected 'key'");
        return Optional.empty();
    }

    private Optional<Statement.DelCore> parseDel() {
        consume(Token.Del.class);
        Optional<Token.Identifier> key = consume(Token.Identifier.class);

        if (key.isPresent()) {
            if (consume(Token.EOS.class).isPresent()) {
                return Optional.of(new Statement.DelCore(key.get().value()));
            }
            System.out.println("Invalid syntax: Expected 'end'");
            return Optional.empty();
        }

        System.out.println("Invalid syntax: Expected 'key'");
        return Optional.empty();
    }
}
