package Server.Database.LogDB.Repository;

import Client.HoneyPot.logging.LogConnection;
import Client.HoneyPot.logging.LogRecord;
import Client.Model.User;
import Server.Database.Database.Database;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(log.getLogRecords());
            byte[] LogRecordsAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(LogRecordsAsBytes);

            String sql = "INSERT INTO ServerLogs (accountID, Service, RemoteIP, Port, Item, Date,Logrecord) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = Database.connection().prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setString(2, log.getProtocol());
            statement.setString(3, log.getDstIP().toString());
            statement.setInt(4, log.getDstPort());
            statement.setString(5, log.message());

            java.sql.Date sqlDate = new java.sql.Date(log.getDate().getTime());
            statement.setDate(6, sqlDate);
            statement.setBlob(7,bais,LogRecordsAsBytes.length);
            statement.execute();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

                byte[] st = (byte[]) rs.getObject("Logrecord");
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                ArrayList<LogRecord> logrecord = (ArrayList<LogRecord>) ois.readObject();


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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<LogConnection> GetLogs(User usr) {
        Set<LogConnection> logs = new HashSet<LogConnection>();

        try{
            String sql = "SELECT * FROM ServerLogs WHERE accountID = ?";
            PreparedStatement statement = Database.connection().prepareStatement(sql);
            statement.setInt(1,usr.getId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                LogConnection log = new LogConnection(null,
                        InetAddress.getByName(rs.getString("RemoteIP").substring(1)), 0, rs.getInt("Port"),
                        rs.getString("Service"),rs.getInt("accountID"),rs.getDate("Date"));

                byte[] st = rs.getBytes("Logrecord");
                ArrayList<LogRecord> logrecord = null;
                if(st != null) {
                    ByteArrayInputStream baip = new ByteArrayInputStream(st);
                    ObjectInputStream ois = new ObjectInputStream(baip);
                    logrecord = (ArrayList<LogRecord>) ois.readObject();
                    log.AddLogRecord(logrecord);
                }

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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}