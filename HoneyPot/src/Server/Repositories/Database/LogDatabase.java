package Server.Repositories.Database;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.Repositories.Database.Database;
import Client.Model.User;
import Server.Repositories.Interface.ILogSerialisation;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                        rs.getString("Service"),rs.getInt("accountID"));
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
    public Set<LogConnection> GetLogsByUser(User usr) {
        Set<LogConnection> logs = new HashSet<LogConnection>();

        try{
            String sql = "SELECT * FROM ServerLogs WHERE accountID = ?";
            PreparedStatement statement = Database.connection().prepareStatement(sql);
            statement.setInt(1,usr.getId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                LogConnection log = new LogConnection(null,
                        InetAddress.getByName(rs.getString("RemoteIP").substring(1)), 0, rs.getInt("Port"),
                        rs.getString("Service"),rs.getInt("accountID"));
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
    public Set<LogConnection> GetLogsAdmin(User usr) {
        Set<LogConnection> logs = new HashSet<>();

        try {
            String sql1 = "SELECT codeID FROM AccountCode WHERE accountID = ?";
            PreparedStatement statement = Database.connection().prepareStatement(sql1);
            statement.setInt(1,usr.getId());
            ResultSet rs1 = statement.executeQuery();
            while (rs1.next()) {
                int codeID = rs1.getInt("codeID");
                String sql2 = "SELECT ServerLogs.*\n" +
                        "FROM AccountCode\n" +
                        "INNER JOIN ServerLogs ON ServerLogs.accountID = AccountCode.accountID\n" +
                        "WHERE codeID = ?";
                statement = Database.connection().prepareStatement(sql2);
                statement.setInt(1,codeID);
                ResultSet rs2 = statement.executeQuery();
                while (rs2.next()) {
                    LogConnection log = new LogConnection(null,
                            InetAddress.getByName(rs2.getString("RemoteIP").substring(1)), 0, rs2.getInt("Port"),
                            rs2.getString("Service"),rs2.getInt("accountID"));
                    logs.add(log);
                }
            }
            return logs;
        }catch (SQLException | UnknownHostException sqle){
            sqle.printStackTrace();
        }
        return null;
    }
}