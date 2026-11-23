package R1;

import java.util.Scanner;

public class ConsoleUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "MS949");
        MachineManager machineManager = new MachineManager();

        System.out.println("=== 기숙사 세탁실 관리 시스템 시작 (R1) ===");

        while (true) {
            System.out.println("\n1. 세탁실 현황 조회");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                machineManager.printAllMachinesStatus();
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