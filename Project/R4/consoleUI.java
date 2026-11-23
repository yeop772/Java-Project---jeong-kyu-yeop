package R4;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class consoleUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "MS949");
        MachineManager machineManager = new MachineManager();
        LogManager logManager = new LogManager();
        NotificationManager notificationManager = new NotificationManager(machineManager, logManager); // R3 추가

        // R3: 백그라운드 타이머 실행
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notificationManager.checkAndNotify();
            }
        }, 0, 2000);

        System.out.println("=== 기숙사 세탁실 관리 시스템 시작 (R3) ===");

        while (true) {
            System.out.println("\n1. 세탁실 현황 조회");
            System.out.println("2. 기기 작동 (일지 등록)");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                machineManager.printAllMachinesStatus();
            } else if (choice == 2) {
                System.out.print("사용할 기기 번호를 입력하세요: ");
                int machineId = scanner.nextInt();
                scanner.nextLine();

                System.out.print("호실을 입력하세요 (예: 302호): ");
                String roomNumber = scanner.nextLine();

                System.out.print("이름을 입력하세요: ");
                String userName = scanner.nextLine();

                System.out.print("성별을 입력하세요 (남/여): ");
                String gender = scanner.nextLine();

                boolean isStarted = machineManager.startMachine(machineId);

                if (isStarted) {
                    UsageLog newLog = new UsageLog(machineId, roomNumber, userName, gender);
                    logManager.addLog(newLog);
                    System.out.println("\n안내: " + machineId + "번 기기가 작동을 시작했습니다. (30초 뒤 자동 알림)\n");
                } else {
                    System.out.println("\n안내: 기기 작동에 실패했습니다.\n");
                }
            } else if (choice == 0) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다.\n");
            }
        }
        scanner.close();
    }
}