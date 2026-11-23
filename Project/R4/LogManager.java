package R4;

import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private List<UsageLog> logList;

    public LogManager() {
        logList = new ArrayList<>();
    }

    public void addLog(UsageLog log) {
        logList.add(log);
    }

    public List<UsageLog> getLogList() {
        return logList;
    }

    public void setLogList(List<UsageLog> loadedList) {
        this.logList = loadedList;
    }
}