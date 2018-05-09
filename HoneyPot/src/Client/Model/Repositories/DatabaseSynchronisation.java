package Client.Model.Repositories;

import Client.Main.Main;
import Client.Model.Repositories.Database.LogDatabase;
import Client.Model.Repositories.Local.LogLocal;
import Client.Model.Repositories.Repository.LogRepository;
import Client.HoneyPot.logging.LogConnection;

public class DatabaseSynchronisation {
    private LogRepository dbRepo = new LogRepository(new LogDatabase());
    private LogRepository localRepo = new LogRepository(new LogLocal());
    private LogConnection latestDbLog = null;
    private LogConnection latestLocalLog = null;
    private LogConnection[] dbLogs;
    private LogConnection[] localLogs;

    private static final int LogsEqual = 0;
    private static final int localToCloud = 1;
    private static final int cloudToLocal = -1;

    public boolean SyncLocalAndCloud() {
        switch (CheckLogDates()){
            case LogsEqual:
                return true;

            case localToCloud:
                SyncLocalToCloud();
                return true;

            case cloudToLocal:
                SyncCloudToLocal();
                return true;

            default:
                return false;
        }
    }

    private int CheckLogDates(){
        if(latestDbLog != null || latestLocalLog != null){
            latestDbLog = null;
            latestLocalLog = null;
        }

        dbLogs = dbRepo.GetAllLogs().toArray(new LogConnection[0]);
        localLogs = localRepo.GetAllLogs().toArray(new LogConnection[0]);
        latestDbLog = GetLatestLog(dbLogs);
        latestLocalLog = GetLatestLog(localLogs);

        if(latestDbLog != null || latestLocalLog != null)
            return latestDbLog.compareTo(latestLocalLog);
        else
            return Integer.MAX_VALUE;
    }

    private LogConnection GetLatestLog(LogConnection[] logs){
        LogConnection log = null;
        for(int i = 0; i < logs.length; i++){
            if(log == null || log.compareTo(logs[i]) > 0){
                log = logs[i];
            }
        }
        return log;
    }

    private void SyncLocalToCloud(){
        for(int i = 0; i < localLogs.length; i++){
            if(localLogs[i].compareTo(latestDbLog) > 0){
                dbRepo.SaveLog(localLogs[i], Main.GetAccount());
            }
        }
    }

    private void SyncCloudToLocal(){
        for(int i = 0; i < dbLogs.length; i++){
            if(dbLogs[i].compareTo(latestLocalLog) > 0){
                localRepo.SaveLog(dbLogs[i], Main.GetAccount());
            }
        }
    }
}
