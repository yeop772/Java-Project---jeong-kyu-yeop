package R5;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Machine {
    private int id;
    private String type;
    private boolean isAvailable;
    private LocalDateTime endTime;
    private String reservedBy;

    public Machine(int id, String type) {
        this.id = id;
        this.type = type;
        this.isAvailable = true;
        this.endTime = null;
        this.reservedBy = null;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getReservedBy() { return reservedBy; }
    public void setReservedBy(String reservedBy) { this.reservedBy = reservedBy; }

    public String getMaskedReservedName() {
        if (reservedBy == null || reservedBy.isEmpty()) {
            return "";
        }
        int len = reservedBy.length();
        if (len == 1) {
            return reservedBy;
        }
        if (len == 2) {
            return reservedBy.charAt(0) + "*";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(reservedBy.charAt(0));
        for (int i = 0; i < len - 2; i++) {
            sb.append("*");
        }
        sb.append(reservedBy.charAt(len - 1));
        return sb.toString();
    }

    public String getStatusString() {
        String reserveText = (reservedBy != null) ? " (예약: " + getMaskedReservedName() + ")" : "";
        if (isAvailable || endTime == null) {
            return id + "번 " + type + " - 빈자리" + reserveText;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return id + "번 " + type + " - 작동 중 (" + endTime.format(formatter) + " 종료 예정)" + reserveText;
    }
}