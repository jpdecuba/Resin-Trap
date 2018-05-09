package Client.Model.Repositories.Local;

import Client.HoneyPot.logging.LogConnection;
import Client.Model.Repositories.Interface.ILogSerialisation;
import Client.Model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class LogLocal implements ILogSerialisation {
    @Override
    public void SaveLog(LogConnection log, User user) {
        throw new NotImplementedException();
    }

    @Override
    public void SaveLogs(Iterable<LogConnection> logs, User user) {
        throw new NotImplementedException();
    }

    @Override
    public Set<LogConnection> GetAllLogs() {
        Set<LogConnection> logs = new HashSet<>();
        ObjectInputStream objectReader = null;
        try {
            InputStream inputStream = new FileInputStream(System.getenv("APPDATA") + "/Honeypot/AllLogs.txt");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            objectReader = new ObjectInputStream(bufferedInputStream);

            Object object = objectReader.readObject();
            while (object != null) {
                logs.add((LogConnection) object);

                objectReader = new ObjectInputStream(bufferedInputStream);
                object = objectReader.readObject();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logs;
    }
}
