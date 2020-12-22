package Domain.TableViewData;

public class HeapData {
    private int address;
    private String value;

    public HeapData(int address, String value) { this.address = address; this.value = value; }

    public int getAddress() { return address; }
    public String getValue() { return value; }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return address + " " + this.value;
    }
}
