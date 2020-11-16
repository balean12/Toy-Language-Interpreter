package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.PrgState;
import Domain.Types.Type;
import Domain.Value.Value;

public class VarDeclStmt implements IStmt{
    String id;
    Type type;
    public VarDeclStmt(String idd, Type typed) {id =idd; type =typed;}

    public String getId() {return id;}
    public Type getType() {return type;}

    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symbolTable = state.getSymTable();
        if(symbolTable.isDefined(id)) throw new StatementException("Variable is already declared;\n");
        else symbolTable.add(id, type.defaultValue());
        return state;
    }
    public IStmt deepCopy() { return new VarDeclStmt(id, type.deepCopy());}

    public String toString() { return this.type + " " + this.id;}
}
