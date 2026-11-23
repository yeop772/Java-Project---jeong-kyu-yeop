package R5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainGUI extends JFrame {
    private MachineManager machineManager;
    private LogManager logManager;
    private NotificationManager notificationManager;
    private JTextArea displayArea;

    public MainGUI() {
        machineManager = new MachineManager();
        logManager = new LogManager();

        List<UsageLog> loadedLogs = FileManager.loadLogs();
        logManager.setLogList(loadedLogs);
        for (UsageLog log : loadedLogs) {
            if (!log.isNotified()) {
                machineManager.startMachine(log.getMachineId());
                machineManager.setMachineEndTime(log.getMachineId(), log.getEndTime());
            }
        }

        notificationManager = new NotificationManager(machineManager, logManager);

        setTitle("기숙사 세탁실 관리 시스템 (GUI Version)");
        setSize(520, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnRefresh = new JButton("🔄 새로고침");
        JButton btnOperate = new JButton("▶ 기기 작동");
        JButton btnReserve = new JButton("📅 예약");
        JButton btnExit = new JButton("❌ 종료 (저장)");

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnOperate);
        buttonPanel.add(btnReserve);
        buttonPanel.add(btnExit);
        add(buttonPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> refreshDisplay());
        btnOperate.addActionListener(e -> operateMachineUI());
        btnReserve.addActionListener(e -> reserveMachineUI());
        btnExit.addActionListener(e -> exitProgram());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProgram();
            }
        });

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notificationManager.checkAndNotify();
                refreshDisplay();
            }
        }, 0, 2000);

        refreshDisplay();
    }

    private void refreshDisplay() {
        displayArea.setText(machineManager.getAllStatusText());
    }

    private void operateMachineUI() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "사용할 기기 번호를 입력하세요 (1~10: 세탁기, 11~15: 건조기):");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int machineId = Integer.parseInt(idStr.trim());

            String room = JOptionPane.showInputDialog(this, "호실을 입력하세요 (예: 4호):");
            if (room == null || room.trim().isEmpty()) return;

            String name = JOptionPane.showInputDialog(this, "이름을 입력하세요:");
            if (name == null || name.trim().isEmpty()) return;

            String gender = JOptionPane.showInputDialog(this, "성별을 입력하세요 (남/여):");
            if (gender == null || gender.trim().isEmpty()) return;

            String result = machineManager.startMachineWithCheck(machineId, name.trim());

            if ("SUCCESS".equals(result)) {
                String machineType = machineManager.getMachineType(machineId);
                int minutes = "건조기".equals(machineType) ? 45 : 36;
                UsageLog log = new UsageLog(machineId, machineType, room.trim(), name.trim(), gender.trim());
                logManager.addLog(log);
                machineManager.setMachineEndTime(machineId, log.getEndTime());
                JOptionPane.showMessageDialog(this, machineId + "번 " + machineType + " 작동 시작! (" + minutes + "분 소요)\n", "성공", JOptionPane.INFORMATION_MESSAGE);
                refreshDisplay();
            } else {
                JOptionPane.showMessageDialog(this, result, "작동 실패", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "기기 번호는 반드시 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void reserveMachineUI() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "예약할 작동 중인 기기 번호를 입력하세요 (1~15):");
            if (idStr == null || idStr.trim().isEmpty()) return;
            int machineId = Integer.parseInt(idStr.trim());

            String name = JOptionPane.showInputDialog(this, "예약자 이름을 입력하세요:");
            if (name == null || name.trim().isEmpty()) return;

            String result = machineManager.reserveMachine(machineId, name.trim());

            if ("SUCCESS".equals(result)) {
                JOptionPane.showMessageDialog(this, machineId + "번 기기 예약이 완료되었습니다.", "예약 성공", JOptionPane.INFORMATION_MESSAGE);
                refreshDisplay();
            } else {
                JOptionPane.showMessageDialog(this, result, "예약 실패", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "기기 번호는 반드시 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exitProgram() {
        FileManager.saveLogs(logManager.getLogList());
        JOptionPane.showMessageDialog(this, "데이터가 안전하게 저장되었습니다.\n프로그램을 종료합니다.");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}