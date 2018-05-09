package Client.Model.Repositories.Database;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.Repositories.Interface.ILogSerialisation;
import Client.Model.User;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class LogDatabase implements ILogSerialisation {

    @Override
    public void SaveLog(LogConnection log, User user) {
        try{
            String sql = "INSERT INTO ServerLogs (accountID, Service, RemoteIP, Port, Item) VALUES (?,?,?,?,?)";
            PreparedStatement statement = Database.connection().prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setString(2, log.getProtocol());
            statement.setString(3, log.getDstIP().toString());
            statement.setInt(4, log.getDstPort());
            statement.setString(5, log.message());
            statement.execute();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    @Override
    public void SaveLogs(Iterable<LogConnection> logs, User user) {
        for (LogConnection lc : logs) {
            SaveLog(lc, user);
        }
    }

    @Override
    public Set<LogConnection> GetAllLogs() {
        Set<LogConnection> logs = new HashSet<LogConnection>();
        try{
            String sql = "SELECT * FROM ServerLogs";
            PreparedStatement statement = Database.connection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                LogConnection log = new LogConnection(null,
                        InetAddress.getByName(rs.getString("RemoteIP").substring(1)), 0, rs.getInt("Port"),
                        rs.getString("Service"),rs.getInt("accountID"),rs.getDate("Date"));

                if(log != null){
                    String sql2 = "SELECT name FROM Account WHERE id = ?";
                    PreparedStatement statement2 = Database.connection().prepareStatement(sql2);
                    statement2.setInt(1, log.getUserId());
                    ResultSet rs2 = statement2.executeQuery();
                    while(rs2.next()){
                        log.SetUsername(rs2.getString(1));
                    }
                    logs.add(log);
                }
            }
            return logs;
        } catch (SQLException | UnknownHostException sqle){
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<LogConnection> GetLogsByUser() {
        return null;
    }
}