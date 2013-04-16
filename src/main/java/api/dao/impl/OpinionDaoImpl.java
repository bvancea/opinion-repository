package api.dao.impl;

import api.dao.OpinionDao;
import api.dao.base.BasePersistence;
import api.dao.util.HBaseMapper;
import api.dao.util.OpinionMapper;
import api.model.Opinion;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import javax.transaction.NotSupportedException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/17/13
 * Time: 10:33 AM
 *
 * Implementation of the Opinion DAO.
 */
@Repository
public class OpinionDaoImpl extends BasePersistence implements OpinionDao {

    @Value(value = "${opinion.table.name}")
    private String tableName;

    @Value(value = "${opinion.table.column.familiy.name}")
    private String columnFamilyName;

    @Autowired
    private OpinionMapper mapper;

    @Override
    public Opinion save(final Opinion opinion) throws NotSupportedException {

        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                Put put = mapper.mapToPut(opinion,columnFamilyName);
                table.put(put);
                return opinion;
            }
        });

        return opinion;
    }

    @Override
    public void delete(Opinion object) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public void delete(String id) throws NotSupportedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Opinion find(long id) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public Opinion find(String id) throws NotSupportedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Opinion> findAll() throws NotSupportedException {

        return template.find(tableName, columnFamilyName, new RowMapper<Opinion>() {
            @Override
            public Opinion mapRow(Result result, int rowNum) throws Exception {
                return mapper.mapFromResult(result,columnFamilyName);
            }
        });
    }

    public List<Opinion> findByHolderName(final String holderName) {
        Scan scan = new Scan();

        byte[] startRow = Bytes.toBytes(holderName);
        byte[] stopRow = Bytes.toBytes(holderName);
        stopRow[stopRow.length-1]++;

        scan.addFamily(Bytes.toBytes(columnFamilyName));
        scan.setStartRow(Bytes.toBytes(holderName));
        scan.setStartRow(startRow);
        scan.setStopRow(stopRow);

        return template.find(tableName, scan, new RowMapper<Opinion>() {

            @Override
            public Opinion mapRow(Result result, int i) throws Exception {
            return mapper.mapFromResult(result, columnFamilyName);
            }
        });
    }


    /*
     * I think this only works on strings.
     */
    @Override
    public List<Opinion> filterFind(Map<String, Object> filterMap) throws NotSupportedException {
        Scan scan = new Scan();

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

        Set<Map.Entry<String, Object>> filterEntries = filterMap.entrySet();
        for (Map.Entry entry : filterEntries) {
            SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes(columnFamilyName),
                Bytes.toBytes(entry.getKey().toString()),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(entry.getValue().toString())
            );
            filterList.addFilter(filter);
        }

        scan.setFilter(filterList);

        return template.find(tableName, scan, new RowMapper<Opinion>() {

            @Override
            public Opinion mapRow(Result result, int i) throws Exception {
            return mapper.mapFromResult(result, columnFamilyName);
            }
        });
    }

}
