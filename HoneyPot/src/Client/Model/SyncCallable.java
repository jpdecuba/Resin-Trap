package Client.Model;

import Client.Model.Repositories.DatabaseSynchronisation;

import java.util.concurrent.Callable;

public class SyncCallable implements Callable<Boolean> {

    private DatabaseSynchronisation dbSync = new DatabaseSynchronisation();

    @Override
    public Boolean call() throws Exception {
        boolean check = dbSync.SyncLocalAndCloud();
        return check;
    }
}
