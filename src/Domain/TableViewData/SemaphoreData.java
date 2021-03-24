package Domain.TableViewData;

import java.util.ArrayList;

public class SemaphoreData {
    private Integer index;
    private Integer value;
    private ArrayList<Integer> values;

    public SemaphoreData(Integer index, Integer value, ArrayList<Integer> values){
        this.index = index;
        this.value = value;
        this.values = values;
    }
    public Integer getIndex() {return index;}
    public Integer getValue() {return value;}
    public ArrayList<Integer> getValues() {return values;}

    public void setValue(int value) { this.value = value;}
    public void setIndex(int index) {this.index = index;}

    public String toString() {
        return index + " " + value + " " + values;
    }
}
