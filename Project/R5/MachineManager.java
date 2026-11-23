package R5;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MachineManager {
    private List<Machine> machineList;

    public MachineManager() {
        machineList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            machineList.add(new Machine(i, "세탁기"));
        }
        for (int i = 11; i <= 15; i++) {
            machineList.add(new Machine(i, "건조기"));
        }
    }

    public void printAllMachinesStatus() {
        System.out.println("\n=== 현재 세탁실 기기 현황 ===");
        for (Machine machine : machineList) {
            System.out.println(machine.getStatusString());
        }
        System.out.println("=============================\n");
    }

    public String getMachineType(int machineId) {
        for (Machine machine : machineList) {
            if (machine.getId() == machineId) {
                return machine.getType();
            }
        }
        return "알 수 없음";
    }

    public boolean startMachine(int machineId) {
        return "SUCCESS".equals(startMachineWithCheck(machineId, null));
    }

    public String startMachineWithCheck(int machineId, String userName) {
        for (Machine machine : machineList) {
            if (machine.getId() == machineId) {
                if (!machine.isAvailable()) {
                    return "이미 작동 중인 기기입니다.";
                }
                if (userName != null && machine.getReservedBy() != null && !machine.getReservedBy().equals(userName)) {
                    return "다른 사용자(" + machine.getMaskedReservedName() + ")가 예약한 기기입니다.";
                }
                machine.setAvailable(false);
                machine.setReservedBy(null);
                return "SUCCESS";
            }
        }
        return "존재하지 않는 기기 번호입니다.";
    }

    public String reserveMachine(int machineId, String userName) {
        for (Machine machine : machineList) {
            if (machine.getId() == machineId) {
                if (machine.isAvailable()) {
                    return "현재 빈자리입니다. 예약 대신 '기기 작동'을 이용해 주세요.";
                }
                if (machine.getReservedBy() != null) {
                    return "이미 다른 사용자(" + machine.getMaskedReservedName() + ")가 예약한 기기입니다.";
                }
                machine.setReservedBy(userName.trim());
                return "SUCCESS";
            }
        }
        return "존재하지 않는 기기 번호입니다.";
    }

    public void setMachineEndTime(int machineId, LocalDateTime endTime) {
        for (Machine machine : machineList) {
            if (machine.getId() == machineId) {
                machine.setEndTime(endTime);
                break;
            }
        }
    }

    public void finishMachine(int machineId) {
        for (Machine machine : machineList) {
            if (machine.getId() == machineId) {
                machine.setAvailable(true);
                machine.setEndTime(null);
                break;
            }
        }
    }

    public String getAllStatusText() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 현재 세탁실 기기 현황 ===\n\n");
        for (Machine machine : machineList) {
            sb.append(machine.getStatusString()).append("\n");
        }
        return sb.toString();
    }
}