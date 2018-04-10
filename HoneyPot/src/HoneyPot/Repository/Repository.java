package HoneyPot.Repository;

public class Repository<T> implements IRepository<T> {

    public Repository( ){
    }

    @Override
    public void Add(T entity) {

    }

    @Override
    public void AddRange(T[] entities) {

    }

    @Override
    public void Remove(T entity) {

    }

    @Override
    public void RemoveRange(T[] entities) {

    }

    @Override
    public T Get(int id) {
        return null;
    }

    @Override
    public Iterable<T> GetAll() {
        return null;
    }

    @Override
    public Iterable<T> Find() {
        return null;
    }


}
