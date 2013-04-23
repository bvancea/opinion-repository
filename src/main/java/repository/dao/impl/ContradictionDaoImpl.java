package repository.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.transaction.NotSupportedException;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;
import repository.controller.dto.ContradictionsDTO;
import repository.dao.ContradictionDao;
import repository.dao.base.BasePersistence;
import repository.dao.util.ContradictionMapper;
import repository.model.Contradiction;

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
    
    @Value(value = "${contradiction.table.opinion.CF.name}")
    private String opinionCFamily;
    

    @Value(value = "${contradiction.table.name}")
    private String tableName;
    
    @Autowired
    private ContradictionMapper mapper;

    @Override
    public Contradiction save(final Contradiction contradiction) throws NotSupportedException {
        template.execute(tableName, new TableCallback<Contradiction>() {
            public Contradiction doInTable(HTableInterface table) throws Throwable {

            Put p = mapper.mapToPut(contradiction,opinionCFamily);
            table.put(p);
            
            contradiction.invertOpinionIDs();
            
            p = mapper.mapToPut(contradiction,opinionCFamily);
            table.put(p);
            
            contradiction.invertOpinionIDs();
            
            return contradiction;
            }
        });

        return contradiction;
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
        List<Contradiction> finalResult = new ArrayList<Contradiction>();
        List<ContradictionsDTO> contradictionResults;
        contradictionResults = template.find(tableName, opinionCFamily, new RowMapper<ContradictionsDTO>() {
            @Override
            public ContradictionsDTO mapRow(Result result, int rowNum) throws Exception {
                return mapper.mapListFromResult(result, opinionCFamily);
            }
        });
        
        for(ContradictionsDTO contradictionResult : contradictionResults){
            finalResult.addAll(contradictionResult);
        }
        
        return finalResult;
    }

    @Override
    public List<Contradiction> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new NotSupportedException();
    }
}
