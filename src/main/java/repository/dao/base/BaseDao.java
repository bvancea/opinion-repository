package repository.dao.base;

import repository.model.AOpinion;

import java.util.List;

import javax.transaction.NotSupportedException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/16/13
 * Time: 9:53 PM
 *
 * Base implementation for all daos.
 */
public interface BaseDao<T> {

    //public Opinion save(T object) throws NotSupportedException;
    public T save(T object) throws NotSupportedException;
    public void delete(T object) throws NotSupportedException;
    public T find(long id) throws NotSupportedException;
    public List<T> findAll() throws NotSupportedException;
    public List<T> filterFind(Map<String, Object> filter) throws NotSupportedException;

}
