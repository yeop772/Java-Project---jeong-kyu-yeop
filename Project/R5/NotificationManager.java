package R5;

import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;

public class NotificationManager {
    private MachineManager machineManager;
    private LogManager logManager;

    public NotificationManager(MachineManager machineManager, LogManager logManager) {
        this.machineManager = machineManager;
        this.logManager = logManager;
    }

    public void checkAndNotify() {
        LocalDateTime now = LocalDateTime.now();
        List<UsageLog> logs = logManager.getLogList();

        for (UsageLog log : logs) {
            if (!log.isNotified() && now.isAfter(log.getEndTime())) {
                machineManager.finishMachine(log.getMachineId());
                log.setPreNotified(true);
                log.setNotified(true);

                String message = "알람 " + log.getRoomNumber() + " " + log.getUserName() + "님!\n"
                               + log.getMachineId() + "번 기기 작업이 완료되었습니다!\n세탁물을 회수해 주세요.";
                JOptionPane.showMessageDialog(null, message, "작업 완료 알림", JOptionPane.INFORMATION_MESSAGE);
            } else if (!log.isPreNotified() && !log.isNotified() && now.isAfter(log.getEndTime().minusMinutes(5))) {
                log.setPreNotified(true);

                String message = "예고 알람 " + log.getRoomNumber() + " " + log.getUserName() + "님!\n"
                               + log.getMachineId() + "번 기기 작업 완료 5분 전입니다!\n미리 준비해 주세요.";
                JOptionPane.showMessageDialog(null, message, "완료 5분 전 알림", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}