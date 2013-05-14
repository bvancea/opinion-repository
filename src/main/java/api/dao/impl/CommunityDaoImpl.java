/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao.impl;

import api.dao.CommunityDao;
import api.dao.base.BasePersistence;
import api.dao.util.CommunityMapper;
import api.model.Community;
import java.util.List;
import java.util.Map;
import javax.transaction.NotSupportedException;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;
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
public class CommunityDaoImpl extends BasePersistence implements CommunityDao {
    
    @Value(value = "${community.table.name}")
    private String tableName;
    
    @Value(value = "${community.table.holder.CF.name}")
    private String holderCF;
    
    @Autowired
    private CommunityMapper mapper;

    @Override
    public Community save(final Community community) throws NotSupportedException {
        template.execute(tableName, new TableCallback<Community>() {
            public Community doInTable(HTableInterface table) throws Throwable {
                Put p = mapper.mapToPut(community, null);
                table.put(p);
                return community;
            }
        });

        return community;
    }

    @Override
    public void delete(Community object) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Community find(long id) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Community find(String id) throws NotSupportedException {
        Scan scan = new Scan();

        byte[] startRow = Bytes.toBytes(id);
        byte[] stopRow = Bytes.toBytes(id);
        stopRow[stopRow.length-1]++;
        scan.setStartRow(startRow);
        scan.setStopRow(stopRow);
        
        List<Community> communities =  template.find(tableName, scan, new RowMapper<Community>() {
            @Override
            public Community mapRow(Result result, int rowNum) throws Exception {
               
                return mapper.mapFromResult(result, null);
            }
        });
        
        return communities.get(0);
    }

    @Override
    public List<Community> findAll() throws NotSupportedException {
        Scan scan = new Scan();
        
        List<Community> communities =  template.find(tableName, scan, new RowMapper<Community>() {
            @Override
            public Community mapRow(Result result, int rowNum) throws Exception {
               
                return mapper.mapFromResult(result, null);
            }
        });
        
        return communities;
    }

    @Override
    public List<Community> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Community> findByHolderName(String holderName) {
        Scan scan = new Scan();
         
        scan.addFamily(Bytes.toBytes(holderCF));
        
        byte[] startColumn = Bytes.toBytes(holderName);
        byte[] endColumn = Bytes.toBytes(holderName);
        endColumn[endColumn.length-1]++;
        
        Filter f = new ColumnRangeFilter(startColumn , true, endColumn, false);
        scan.setFilter(f);
        
        
        List<Community> communities = template.find(tableName, scan, new RowMapper<Community>() {

            @Override
            public Community mapRow(Result result, int rowNum) throws Exception {

                return mapper.mapFromResult(result, null);
            }
        });
        
        return communities;
    }
}
