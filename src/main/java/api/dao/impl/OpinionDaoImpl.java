package api.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
import api.dao.OpinionDao;
import api.dao.base.BasePersistence;
import api.model.Opinion;

import javax.transaction.NotSupportedException;
import java.util.*;

import javax.transaction.NotSupportedException;
import java.util.Calendar;
import java.util.Map;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import api.dao.util.OpinionMapper;
import api.dao.util.OpinionResultMapper;
import api.model.OpinionResult;

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

    @Value(value = "${targetPrefix}")
    private String targetPrefix;

    @Value(value = "${sentimentPrefix}")
    private String sentimentPrefix;

    @Value(value = "${opinionPrefix}")
    private String opinionPrefix;

    @Value(value = "${opinionIdSearchPrefix}")
    private String opinionIdSearchPrefix;

    @Value(value = "${splitToken}")
    private String splitToken;

    @Value(value = "${concatToken}")
    private String concatToken;

    @Value(value = "${opinion.table.name}")
    private String tableName;

    @Value(value = "${opinion.id.table.name}")
    private String idTableName;

    @Value(value = "${opinion.table.column.family.name}")
    private String columnFamilyName;


    @Autowired
    private OpinionMapper mapper;
    
    @Autowired
    private OpinionResultMapper resultmapper;

    @Override
    public Opinion save(final Opinion opinion) throws NotSupportedException {
        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                Put p = mapper.mapToPut(opinion, columnFamilyName);
                table.put(p);
                return opinion;
            }
        });

        return opinion;
    }

    @Override
    public Opinion saveInIdTable(final Opinion opinion) {
        template.execute(idTableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                Put p = mapper.mapToIdPut(opinion, columnFamilyName);
                table.put(p);
                return opinion;
            }
        });

        return opinion;
    }

    @Override
    public OpinionResult saveOpinionResult(final OpinionResult opinionResult){
        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                Put p = resultmapper.mapToPut(opinionResult, columnFamilyName);
                table.put(p);
                return opinionResult;
            }
        });

        return opinionResult;
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
    public List<Opinion> findAll() throws NotSupportedException {
        
       /* Scan scan = new Scan();
        
        byte[] startColumn = Bytes.toBytes(""+ opinionPrefix);
        byte[] endColumn = Bytes.toBytes(""+ opinionPrefix);
        endColumn[endColumn.length-1]++;
        
        Filter f = new ColumnRangeFilter(startColumn , true, endColumn, false);
        scan.setFilter(f);*/

        List<Opinion> opinions =  template.find(tableName, columnFamilyName, new RowMapper<Opinion>() {
            @Override
            public Opinion mapRow(Result result, int rowNum) throws Exception {
                return mapper.mapFromResult(result, columnFamilyName);
            }
        });
        
        return opinions;
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
        
        byte[] startColumn = Bytes.toBytes(""+opinionPrefix);
        byte[] endColumn = Bytes.toBytes(""+opinionPrefix);
        endColumn[endColumn.length-1]++;
        
        Filter f = new ColumnRangeFilter(startColumn , true, endColumn, false);
        scan.setFilter(f);

        List<Opinion> opinions = new ArrayList<Opinion>();
        List<OpinionResult> opinionResults = template.find(tableName, scan, new RowMapper<OpinionResult>() {

            @Override
            public OpinionResult mapRow(Result result, int rowNum) throws Exception {

                return resultmapper.mapFromResult(result, columnFamilyName);
            }
        });
        
        for(OpinionResult opr : opinionResults){
            opinions.addAll(opr.getOpinions());
        }
        
        return opinions;
    }

        @Override
    public List<Opinion> save(final Iterable<Opinion> opinions) {

            template.execute(tableName, new TableCallback<Object>() {
                public Object doInTable(HTableInterface table) throws Throwable {
                    List<Put> puts = new ArrayList<Put>();

                    for (Opinion opinion : opinions) {
                        Put put = mapper.mapToPut(opinion,columnFamilyName);
                        puts.add(put);
                    }

                    table.put(puts);
                    return opinions;
                }
            });

            return (List) opinions;
        }


    @Override
    public List<Opinion> findByHolderAndTarget(String holderName, String targetEntity) {
        Scan scan = new Scan();

        byte[] startRow = Bytes.toBytes(holderName);
        byte[] stopRow = Bytes.toBytes(holderName);
        stopRow[stopRow.length-1]++;

        scan.addFamily(Bytes.toBytes(columnFamilyName));
        scan.setStartRow(Bytes.toBytes(holderName));
        scan.setStartRow(startRow);
        scan.setStopRow(stopRow);
        
        byte[] startColumn = Bytes.toBytes(""+opinionPrefix+targetEntity);
        byte[] endColumn = Bytes.toBytes(""+opinionPrefix+targetEntity);
        endColumn[endColumn.length-1]++;
        
        Filter f = new ColumnRangeFilter(startColumn , true, endColumn, false);
        scan.setFilter(f);
        
        List<Opinion> opinions = new ArrayList<Opinion>();
        List<OpinionResult> opinionResults = template.find(tableName, scan, new RowMapper<OpinionResult>() {

            @Override
            public OpinionResult mapRow(Result result, int rowNum) throws Exception {

                return resultmapper.mapFromResult(result, columnFamilyName);
            }
        });
        
        for(OpinionResult opr : opinionResults){
            opinions.addAll(opr.getOpinions());
        }
        
        return opinions;
    }

    @Override
    public List<Opinion> findUnexpanded() {
        Scan scan = new Scan();

        byte[] columnQualifier = Bytes.toBytes("Expanded");

        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes(columnFamilyName),
                columnQualifier,
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(0)
        );
        filter.setFilterIfMissing(true);
        filter.setLatestVersionOnly(true);
        scan.setFilter(filter);

        List<Opinion> opinions = template.find(tableName, scan, new RowMapper<Opinion>() {

            @Override
            public Opinion mapRow(Result result, int rowNum) throws Exception {

                return mapper.mapFromResult(result, columnFamilyName);
            }
        });

        return opinions;
    }


    @Override
    public Opinion find(String id) {
        Scan scan = new Scan();
        
        byte[] startColumn = Bytes.toBytes(""+opinionIdSearchPrefix+id);
        byte[] endColumn = Bytes.toBytes(""+opinionIdSearchPrefix+id);
        endColumn[endColumn.length-1]++;
        
        Filter f = new ColumnRangeFilter(startColumn , true, endColumn, false);
        scan.setFilter(f);
        
        
        List<OpinionResult> opinionResults = template.find(tableName, scan, new RowMapper<OpinionResult>() {

            @Override
            public OpinionResult mapRow(Result result, int rowNum) throws Exception {

                return resultmapper.mapFromResult(result, columnFamilyName);
            }
        });
        
        Opinion originalOpinion = null;
        
        for(OpinionResult opr : opinionResults){
            originalOpinion = opr.getOriginalOpinion(); 
        }
        return originalOpinion;
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
