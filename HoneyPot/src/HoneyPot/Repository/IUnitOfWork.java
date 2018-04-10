package HoneyPot.Repository;

public interface IUnitOfWork {
    void SetLog(ILogSerialisation logSerialisation);
    ILogSerialisation GetLog();

    int Complete();
}
