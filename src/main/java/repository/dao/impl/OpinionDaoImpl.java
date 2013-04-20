package repository.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;
import repository.dao.OpinionDao;
import repository.dao.base.BasePersistence;
import repository.model.Opinion;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.transaction.NotSupportedException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import repository.dao.util.OpinionMapper;
import repository.dao.util.OpinionResultMapper;
import repository.model.OpinionResult;

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

    @Value(value = "${opinion.table.column.familiy.name}")
    private String columnFamilyName;
    
    @Autowired
    private OpinionMapper mapper;
    
    @Autowired
    private OpinionResultMapper resultmapper;

    @Override
    public Opinion save(final Opinion opinion) throws NotSupportedException {
        //generate id
        String id = "ID" + Calendar.getInstance().getTimeInMillis();
        
        opinion.setId(id);
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
    public void delete(Opinion object) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public Opinion find(long id) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public List<Opinion> findAll() throws NotSupportedException {
        
        List<Opinion> opinions = new ArrayList<Opinion>();
        List<OpinionResult> opinionResults =  template.find(tableName, columnFamilyName, new RowMapper<OpinionResult>() {
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
    
    public List<Opinion> findByHolderName(final String holderName) {
        Scan scan = new Scan();

        byte[] startRow = Bytes.toBytes(holderName);
        byte[] stopRow = Bytes.toBytes(holderName);
        stopRow[stopRow.length-1]++;

        scan.addFamily(Bytes.toBytes(columnFamilyName));
        scan.setStartRow(Bytes.toBytes(holderName));
        scan.setStartRow(startRow);
        scan.setStopRow(stopRow);

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
    public Opinion findById(String id) {
        Scan scan = new Scan();
        
        byte[] startColumn = Bytes.toBytes(""+opinionIdSearchPrefix+id);
        byte[] endColumn = Bytes.toBytes(""+opinionIdSearchPrefix+id);
        endColumn[endColumn.length-1]++;
        
        Filter f = new ColumnRangeFilter(startColumn , true, endColumn, false);
        scan.setFilter(f);
        
        
        List<Opinion> opinions = template.find(tableName, scan, new RowMapper<Opinion>() {

            @Override
            public Opinion mapRow(Result result, int rowNum) throws Exception {

                return mapper.mapFromResult(result, columnFamilyName);
            }
        });
        return opinions.get(0);
        

    }


    @Override
    public List<Opinion> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new NotSupportedException();
    }

    //@PostConstruct
    public void doStuff() throws NotSupportedException {
        List<Opinion> results = findAll();
        Opinion opinion = new Opinion();
        opinion.setDocument("Alexandra loves Starbucks hot chocolate and coffee.");
        opinion.setHolder("Alexandra");
        opinion.setEntity("coffee");
        opinion.setAttribute("taste");
        opinion.setPositionSW(1);

        opinion.setSentimentWord("loves");
        opinion.setSentimentOrientation(0.99);
        opinion.setTimestamp(Calendar.getInstance().getTime());
        this.save(opinion);
    }

    
    
}
