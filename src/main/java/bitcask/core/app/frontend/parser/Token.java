package bitcask.core.app.frontend.parser;

public sealed interface Token permits Token.Get, Token.Set, Token.Del, 
    Token.Identifier, Token.EOS {

    record Get() implements Token {} 
    record Set() implements Token {}
    record Del() implements Token {}

    record Identifier(String value) implements Token {}

    record EOS() implements Token {}
}
