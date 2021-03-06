package api.dao.impl;

import api.dao.ContradictionDao;
import api.dao.base.BasePersistence;
import api.model.Contradiction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 11:07 PM
 *
 * Implementation of the Contradiction DAO.
 */
@Repository
public class ContradictionDaoImpl extends BasePersistence implements ContradictionDao {

    @Value(value = "${contradiction.table.name}")
    private String tableName;

    @Value(value = "${contradiction.table.column.familiy.name}")
    private String columnFamilyName;

    @Override
    public Contradiction save(Contradiction object) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public void delete(Contradiction object) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public void delete(String id) throws NotSupportedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Contradiction find(long id) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public Contradiction find(String id) throws NotSupportedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Contradiction> findAll() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public List<Contradiction> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new NotSupportedException();
    }
}
