package R5;

import java.time.LocalDateTime;

public class UsageLog {
    private int machineId;
    private String roomNumber;
    private String userName;
    private String gender;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isPreNotified;
    private boolean isNotified;

    public UsageLog(int machineId, String machineType, String roomNumber, String userName, String gender) {
        this.machineId = machineId;
        this.roomNumber = roomNumber;
        this.userName = userName;
        this.gender = gender;
        this.startTime = LocalDateTime.now();
        if ("건조기".equals(machineType)) {
            this.endTime = this.startTime.plusMinutes(45);
        } else {
            this.endTime = this.startTime.plusMinutes(36);
        }
        this.isPreNotified = false;
        this.isNotified = false;
    }

    public UsageLog(int machineId, String roomNumber, String userName, String gender, LocalDateTime startTime, LocalDateTime endTime, boolean isPreNotified, boolean isNotified) {
        this.machineId = machineId;
        this.roomNumber = roomNumber;
        this.userName = userName;
        this.gender = gender;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isPreNotified = isPreNotified;
        this.isNotified = isNotified;
    }

    public int getMachineId() { return machineId; }
    public String getRoomNumber() { return roomNumber; }
    public String getUserName() { return userName; }
    public String getGender() { return gender; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isPreNotified() { return isPreNotified; }
    public void setPreNotified(boolean preNotified) { this.isPreNotified = preNotified; }
    public boolean isNotified() { return isNotified; }
    public void setNotified(boolean notified) { this.isNotified = notified; }
}