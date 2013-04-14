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
    
    @Value(value = "${opinionExpansionPrefix}")
    private String expansionPrefix;
    
    @Value(value = "${splitToken}")
    private String splitToken;
    
    @Value(value = "${opinion.table.name}")
    private String tableName;

    @Value(value = "${opinion.table.column.familiy.name}")
    private String columnFamilyName;

    @Override
    public Opinion save(final Opinion opinion) throws NotSupportedException {

        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                long timestamp = Calendar.getInstance().getTimeInMillis();
                String rowKey = /*timestamp + "-" + */opinion.getHolder() + "-" + opinion.getEntity();
                String opinionKey = "O:" + opinion.getEntity() + "+" + opinion.getId();
                String opinionValue = ""+opinion.getSentimentOrientation() + "+" +
                                        opinion.getSentimentWord() + "+" +
                                        opinion.getDocument() + "+" +
                                        opinion.getPositionSW() + "+" +
                                        opinion.getPositionT() + "+" +
                                        //opinion.getExpandedFlag() + "+" +
                                        //opinion.getCommunitizedFlag() + "+" +
                                        opinion.getTimestamp();
                Put p = new Put(Bytes.toBytes( rowKey));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Expanded"),Bytes.toBytes(""+opinion.getExpanded()));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Communitized"),Bytes.toBytes(""+opinion.getCommunitized()));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(opinionKey),Bytes.toBytes(opinionValue));
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

        return template.find(tableName, columnFamilyName, new RowMapper<Opinion>() {
            @Override
            public Opinion mapRow(Result result, int rowNum) throws Exception {

                Opinion opinion = new Opinion();
               
                String rowKey = Bytes.toString(result.getRow());
                String[] keySplit = rowKey.split(splitToken);
                opinion.setHolder(keySplit[0]);
                opinion.setEntity(keySplit[1]);
                byte[] expandedBytes = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("Expanded"));
                byte[] communitizedBytes = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("Communitized"));
                
                int expandedInt = Integer.parseInt(Bytes.toString(expandedBytes));
                int communitizedInt = Integer.parseInt(Bytes.toString(communitizedBytes));
                opinion.setExpanded(expandedInt);
                opinion.setCommunitized(communitizedInt);
                
                List<Opinion> expandedOpinions = new ArrayList<Opinion>();
                
                List<KeyValue> list = result.list();
                for (KeyValue kv: list) {
                    Opinion opinionEx = new Opinion();
                    String columnQ = Bytes.toString(kv.getQualifier());
                    if(columnQ.startsWith(expansionPrefix)){
                        String opKey = columnQ.substring(expansionPrefix.length());
                        String[] opKeySplit = opKey.split(splitToken);
                        opinionEx.setId(opKeySplit[1]);
                        opinionEx.setEntity(opKeySplit[0]);
                        
                        String opValue = Bytes.toString(kv.getValue());
                        String[] opValueSplit = opValue.split(splitToken);
                        opinionEx.setSentimentOrientation(Double.parseDouble(opValueSplit[0]));
                        opinionEx.setSentimentWord(opValueSplit[1]);
                        opinionEx.setDocument(opValueSplit[2]);
                        opinionEx.setPositionSW(Integer.parseInt(opValueSplit[3]));
                        opinionEx.setPositionT(Integer.parseInt(opValueSplit[4]));
                        Date timestamp = new Date(Long.parseLong(opValueSplit[5]));
                        opinionEx.setTimestamp(timestamp);
                        
                        expandedOpinions.add(opinionEx);
                        
                        //if the nested opinion is the original opinion
                        // get values from nested to original
                        if(opinionEx.getEntity().equals(opinion.getEntity())){
                            opinion.setId(opinionEx.getId());
                            opinion.setSentimentOrientation(opinionEx.getSentimentOrientation());
                            opinion.setSentimentWord(opinionEx.getSentimentWord());
                            opinion.setDocument(opinionEx.getDocument());
                            opinion.setPositionSW(opinionEx.getPositionSW());
                            opinion.setPositionT(opinionEx.getPositionT());
                            opinion.setTimestamp(opinionEx.getTimestamp());
                        }  
                    }
                }
                
                opinion.setExpandedOpinions(expandedOpinions);
                
                return opinion;
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
                byte[] holder = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("holder"));
                byte[] entity = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("entity"));
                byte[] attribute = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("attribute"));
                byte[] sentimentWord = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("sentiment_word"));
                byte[] sentimentOrientation = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("sentiment_orientation"));
                byte[] position = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("position"));
                byte[] document = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("document"));
                byte[] timestamp = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("timestamp"));
                byte[] id = result.getRow();

                Opinion opinion = new Opinion();

                opinion.setId(Bytes.toString(id));

                opinion.setHolder(Bytes.toString(holder));
                opinion.setAttribute(Bytes.toString(attribute));
                opinion.setEntity(Bytes.toString(entity));
                opinion.setSentimentWord(Bytes.toString(sentimentWord));
                opinion.setSentimentOrientation(Bytes.toDouble(sentimentOrientation));
                opinion.setPositionSW(Bytes.toInt(position));
                opinion.setDocument(Bytes.toString(document));
                opinion.setTimestamp(new Date(Bytes.toLong(timestamp)));
                
                String rowKey = Bytes.toString(result.getRow());
                List<KeyValue> list = result.list();
                for (KeyValue kv: list) {
                    String opKey = Bytes.toString(kv.getQualifier());
                    String opValue = Bytes.toString(kv.getValue());
                    //p("row: "+rowKey+ ", opKey: "+ opKey + " opValue: " + opValue);
                }

                return opinion;
            }
        });

        //return null;
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
