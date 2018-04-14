package HoneyPot.Repository;

import HoneyPot.logging.LogConnection;
import Model.Database.Database.Database;
import Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogDatabase implements ILogSerialisation{

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
    public Iterable<LogConnection> GetAllLogsForService(String service) {
        try{
            String sql = "SELECT * FROM ServerLog WHERE ? = 1";
            PreparedStatement statement = Database.connection().prepareStatement(sql);
            statement.setInt(1, 0);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){

            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return null;
    }
}
