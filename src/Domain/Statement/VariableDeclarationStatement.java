package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Value.IValue;

public class VariableDeclarationStatement implements IStmt{
    String id;
    IType type;
    public VariableDeclarationStatement(String idd, IType typed) {
        id =idd;
        type =typed;
    }

    public String getId() {return id;}
    public IType getType() {return type;}

    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        if(symbolTable.isDefined(id)) throw new StatementException("Variable is already declared;\n");
        else symbolTable.add(id, type.defaultValue());
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        typeEnvironment.add(id, type);
        return typeEnvironment;
    }

    public IStmt deepCopy() { return new VariableDeclarationStatement(id, type.deepCopy());}

    public String toString() { return this.type + " " + this.id;}
}
