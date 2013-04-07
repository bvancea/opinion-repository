package repository.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;
import repository.dao.AOpinionDao;
import repository.dao.base.BasePersistence;
import repository.model.AOpinion;
import repository.model.ATarget;

import javax.transaction.NotSupportedException;
import java.util.Calendar;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 *
 * Implementation of the Opinion DAO.
 */
@Repository
public class AOpinionDaoImpl extends BasePersistence implements AOpinionDao {

    @Value(value = "${opinion.table.name}")
    private String tableName;

    @Value(value = "${opinion.table.column.familiy.name}")
    private String columnFamilyName;

    @Override
    public AOpinion save(final AOpinion para_opinion) throws NotSupportedException {
        
        final AOpinion opinion = new AOpinion();
        opinion.setHolder("Alex");
        opinion.setTarget(new ATarget("bucile",null));
        opinion.setSentimentOrientation(0.9);
        opinion.setSentimentWord("iubeste");
        opinion.setDocumentID("docID1");
        opinion.setPositionSW(2);
        opinion.setPositionT(3);
        opinion.setExpandedFlag(0);
        opinion.setCommunitizedFlag(0);
        
        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                long timestamp = Calendar.getInstance().getTimeInMillis();
                String rowKey = /*timestamp + "-" + */opinion.getHolder() + "+" + opinion.getTarget();
                String opinionKey = "O:" + timestamp + "+" + opinion.getTarget();
                String opinionValue = ""+opinion.getSentimentOrientation() + "+" +
                                        opinion.getSentimentWord() + "+" +
                                        opinion.getDocumentID() + "+" +
                                        opinion.getPositionSW() + "+" +
                                        opinion.getPositionT() + "+" +
                                        opinion.getExpandedFlag() + "+" +
                                        opinion.getCommunitizedFlag() + "+" +
                                        opinion.getTimestamp();
                Put p = new Put(Bytes.toBytes( rowKey));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(opinionKey),Bytes.toBytes(opinionValue));
                
                table.put(p);
                return opinion;
            }
        });

        return opinion;
    }

    @Override
    public void delete(AOpinion object) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public AOpinion find(long id) throws NotSupportedException {
        throw new NotSupportedException();
    }

     @Override
    public List<AOpinion> findAll() throws NotSupportedException {

        return template.find(tableName, columnFamilyName, new RowMapper<AOpinion>() {
            @Override
            public AOpinion mapRow(Result result, int rowNum) throws Exception {
                byte[] holder = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("holder"));
                byte[] entity = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("entity"));
                byte[] attribute = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("attribute"));
                byte[] sentimentWord = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("sentiment_word"));
                byte[] sentimentOrientation = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("sentiment_orientation"));
                byte[] position = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("position"));
                byte[] document = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("document"));
                byte[] timestamp = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("timestamp"));
                byte[] id = result.getRow();

                AOpinion opinion = new AOpinion();

                opinion.setId(Bytes.toString(id));
                   /*
                opinion.setHolder(Bytes.toString(holder));
                opinion.setAttribute(Bytes.toString(attribute));
                opinion.setEntity(Bytes.toString(entity));
                opinion.setSentimentWord(Bytes.toString(sentimentWord));
                opinion.setSentimentOrientation(Bytes.toDouble(sentimentOrientation));
                opinion.setPosition(Bytes.toInt(position));
                opinion.setDocument(Bytes.toString(document));
                opinion.setTimestamp(new Date(Bytes.toLong(timestamp)));
                */
                return opinion;
            }
        });
    }

    

    @Override
    public List<AOpinion> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new NotSupportedException();
    }

    //@PostConstruct
    public void doStuff() throws NotSupportedException {
        List<AOpinion> results = findAll();
        AOpinion opinion = new AOpinion();
        opinion.setDocumentID("Alexandra loves Starbucks hot chocolate and coffee.");
        opinion.setHolder("Alexandra");


        opinion.setSentimentWord("loves");
        opinion.setSentimentOrientation(0.99);
        opinion.setTimestamp(Calendar.getInstance().getTime());
        this.save(opinion);
    }
}
