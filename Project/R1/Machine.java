package R1;
public class Machine {
    private int id;
    private String type;
    private boolean isAvailable;
    
public Machine(int id, String type) {
        this.id = id;
        this.type = type;
        this.isAvailable = true;
}
public int getId() { return id; }
    public String getType() { return type; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public String getStatusString() {
return id + "번 " + type + " - " + (isAvailable ? "빈자리" : "작동 중");
    }
}