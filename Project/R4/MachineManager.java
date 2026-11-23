package R4;

import java.util.ArrayList;
import java.util.List;

public class MachineManager {
    private List<Machine> machineList;

    public MachineManager() {
        machineList = new ArrayList<>();
        machineList.add(new Machine(1, "세탁기"));
        machineList.add(new Machine(2, "세탁기"));
        machineList.add(new Machine(3, "세탁기"));
        machineList.add(new Machine(4, "건조기"));
        machineList.add(new Machine(5, "건조기"));
    }

    public void printAllMachinesStatus() {
        System.out.println("\n=== 현재 세탁실 기기 현황 ===");
        for (Machine machine : machineList) {
            System.out.println(machine.getStatusString());
        }
        System.out.println("=============================\n");
    }

    public boolean startMachine(int machineId) {
        for (Machine machine : machineList) {
            if (machine.getId() == machineId) {
                if (machine.isAvailable()) {
                    machine.setAvailable(false); 
                    return true; 
                } else {
                    System.out.println("오류: 이미 작동 중인 기기입니다.");
                    return false; 
                }
            }
        }
        System.out.println("오류: 존재하지 않는 기기 번호입니다.");
        return false;   
    }
    public void finishMachine(int machineId) {
        for (Machine machine : machineList) {
            if (machine.getId() == machineId) {
                machine.setAvailable(true);
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