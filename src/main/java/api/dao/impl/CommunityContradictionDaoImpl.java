/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao.impl;

import api.controller.dto.ContradictionsDTO;
import api.dao.CommunityContradictionDao;
import api.dao.base.BasePersistence;
import api.dao.util.CommunityContradictionMapper;
import api.model.CommunityContradiction;
import api.model.Contradiction;
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

/**
 *
 * @author Alex Marchis
 */
@Repository
public class CommunityContradictionDaoImpl extends BasePersistence implements CommunityContradictionDao {
    
    @Value(value = "${contradiction.table.opinion.CF.name}")
    private String opinionCFamily;
    
    @Value(value = "${contradiction.table.community.CF.name}")
    private String communityCFamily;
    

    @Value(value = "${contradiction.table.name}")
    private String tableName;
    
    @Autowired
    private CommunityContradictionMapper mapper;

    @Override
    public CommunityContradiction save(final CommunityContradiction contradiction) throws NotSupportedException {
         template.execute(tableName, new TableCallback<CommunityContradiction>() {
            public CommunityContradiction doInTable(HTableInterface table) throws Throwable {

            Put p = mapper.mapToPut(contradiction,communityCFamily);
            table.put(p);
                        
            return contradiction;
            }
        });

        return contradiction;
    }

    @Override
    public void delete(CommunityContradiction object) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CommunityContradiction find(long id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CommunityContradiction find(String id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CommunityContradiction> findAll() throws NotSupportedException {
        List<CommunityContradiction> finalResult = new ArrayList<CommunityContradiction>();
        List<ContradictionsDTO> contradictionResults;
        contradictionResults = template.find(tableName, communityCFamily, new RowMapper<ContradictionsDTO>() {
            @Override
            public ContradictionsDTO mapRow(Result result, int rowNum) throws Exception {
                return mapper.mapListFromResult(result, communityCFamily);
            }
        });
        
        for(ContradictionsDTO contradictionResult : contradictionResults){
            for(Contradiction c : contradictionResult){
                finalResult.add((CommunityContradiction)c);
            }
        }
        
        return finalResult;
    }

    @Override
    public List<CommunityContradiction> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
