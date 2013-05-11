/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao.impl;

import api.dao.HolderDao;
import api.dao.base.BasePersistence;
import api.dao.util.HolderMapper;
import api.model.Holder;
import api.model.HolderRelationship;
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
public class HolderDaoImpl extends BasePersistence implements HolderDao {
    @Value(value = "${splitToken}")
    private String splitToken;
    
    @Value(value = "${concatToken}")
    private String concatToken;
    
    @Value(value = "${holder.table.name}")
    private String tableName;

    @Value(value = "${holder.table.column.family.name}")
    private String columnFamilyName;
    
    @Value(value = "${relationshipPrefix}")
    private String relationshipPrefix;
    
    @Autowired
    private HolderMapper mapper;

    @Override
    public HolderRelationship saveHolderRelationship(final HolderRelationship holderRelationship) {
        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                Put p = mapper.mapRelationshipToPut(holderRelationship, columnFamilyName);
                table.put(p);
                holderRelationship.invertFollowRelationship();
                p = mapper.mapRelationshipToPut(holderRelationship, columnFamilyName);
                table.put(p);
                holderRelationship.invertFollowRelationship();
                return holderRelationship;
            }
        });

        return holderRelationship;
    }

    @Override
    public Holder save(final Holder holder) throws NotSupportedException {
        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                Put p = mapper.mapToPut(holder, columnFamilyName);
                table.put(p);
                return holder;
            }
        });

        return holder;
    }

    @Override
    public void delete(Holder object) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Holder find(long id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Holder find(String id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Holder> findAll() throws NotSupportedException {
        
        List<Holder> holders;
        holders =  template.find(tableName, columnFamilyName, new RowMapper<Holder>() {
            @Override
            public Holder mapRow(Result result, int rowNum) throws Exception {
               
                return mapper.mapFromResult(result, columnFamilyName);
            }
        });

        return holders;
    }

    @Override
    public List<Holder> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
