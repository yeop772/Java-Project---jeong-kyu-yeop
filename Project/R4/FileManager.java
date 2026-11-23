package R4;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_NAME = "usage_logs.txt";

    public static void saveLogs(List<UsageLog> logList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (UsageLog log : logList) {
                bw.write(log.getMachineId() + "," +
                         log.getRoomNumber() + "," +
                         log.getUserName() + "," +
                         log.getGender() + "," +
                         log.getStartTime() + "," +
                         log.getEndTime() + "," +
                         log.isNotified());
                bw.newLine();
            }
            System.out.println(" 데이터가 성공적으로 저장되었습니다.");
        } catch (IOException e) {
            System.out.println(" 데이터 저장 중 오류가 발생했습니다.");
        }
    }

    public static List<UsageLog> loadLogs() {
        List<UsageLog> logList = new ArrayList<>();
        File file = new File(FILE_NAME);
        
        if (!file.exists()) {
            return logList; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    int machineId = Integer.parseInt(data[0]);
                    String roomNumber = data[1];
                    String userName = data[2];
                    String gender = data[3];
                    LocalDateTime startTime = LocalDateTime.parse(data[4]);
                    LocalDateTime endTime = LocalDateTime.parse(data[5]);
                    boolean isNotified = Boolean.parseBoolean(data[6]);

                    UsageLog log = new UsageLog(machineId, roomNumber, userName, gender, startTime, endTime, isNotified);
                    logList.add(log);
                }
            }
            System.out.println(" 이전 데이터를 성공적으로 불러왔습니다.");
        } catch (Exception e) {
            System.out.println(" 데이터 불러오기 중 오류가 발생했습니다.");
        }
        return logList;
    }
}
