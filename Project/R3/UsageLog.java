package R3;

import java.time.LocalDateTime;

public class UsageLog {
    private int machineId;
    private String roomNumber;
    private String userName;
    private String gender;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isNotified;

    public UsageLog(int machineId, String roomNumber, String userName, String gender) {
        this.machineId = machineId;
        this.roomNumber = roomNumber;
        this.userName = userName;
        this.gender = gender;
        this.startTime = LocalDateTime.now();
        this.endTime = this.startTime.plusSeconds(10);
        this.isNotified = false;
    }

    public UsageLog(int machineId, String roomNumber, String userName, String gender, LocalDateTime startTime, LocalDateTime endTime, boolean isNotified) {
        this.machineId = machineId;
        this.roomNumber = roomNumber;
        this.userName = userName;
        this.gender = gender;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isNotified = isNotified;
    }

    public int getMachineId() { return machineId; }
    public String getRoomNumber() { return roomNumber; }
    public String getUserName() { return userName; }
    public String getGender() { return gender; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isNotified() { return isNotified; }
    public void setNotified(boolean notified) { this.isNotified = notified; }
}