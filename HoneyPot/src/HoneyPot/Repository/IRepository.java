package HoneyPot.Repository;

public interface IRepository<T> {

    void Add(T entity);
    void AddRange(T[] entities);

    void Remove(T entity);
    void RemoveRange(T[] entities);

    T Get(int id);
    Iterable<T> GetAll();
    Iterable<T> Find();
}
