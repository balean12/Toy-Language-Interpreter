package Domain.TableViewData;


import Domain.Value.IValue;

public class SymbolData {
    private String variableName;
    private String value;

    public SymbolData(String variableName, String value) {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName() {
        return this.variableName;
    }
    public String getValue() {
        return value;
    }

    public void setVariable(String variableName){
        this.variableName = variableName;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String toString() {
        return variableName + " - " + this.value;
    }
}
