package inmem.core.app.frontend.parser;

public sealed interface Statement permits 
    Statement.Get, Statement.GetCore, Statement.Set, Statement.SetCore, 
    Statement.Del, Statement.DelCore {

    record SetCore(String key, String value) implements Statement {}
    record Set(SetCore core) implements Statement {}

    record GetCore(String key) implements Statement {}
    record Get(GetCore core) implements Statement {}

    record DelCore(String key) implements Statement {}
    record Del(DelCore core) implements Statement {}
}
