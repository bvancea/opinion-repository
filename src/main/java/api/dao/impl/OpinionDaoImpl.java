package api.dao.impl;

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
import api.dao.OpinionDao;
import api.dao.base.BasePersistence;
import api.model.Opinion;

import javax.transaction.NotSupportedException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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

    @Override
    public Opinion save(final Opinion opinion) throws NotSupportedException {

        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {
                long timestamp = Calendar.getInstance().getTimeInMillis();
                String rowKey = /*timestamp + "-" + */opinion.getHolder() + "-" + opinion.getEntity();
                Put p = new Put(Bytes.toBytes( rowKey));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("holder"),Bytes.toBytes(opinion.getHolder()) );
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("entity"),Bytes.toBytes(opinion.getEntity()) );
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("attribute"),Bytes.toBytes(opinion.getAttribute()) );
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("sentiment_word"),Bytes.toBytes(opinion.getSentimentWord()) );
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("sentiment_orientation"),Bytes.toBytes(opinion.getSentimentOrientation()) );
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("position"),Bytes.toBytes(opinion.getPosition()));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("document"),Bytes.toBytes(opinion.getDocument()));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("timestamp"),Bytes.toBytes(opinion.getTimestamp().getTime()));
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
                opinion.setPosition(Bytes.toInt(position));
                opinion.setDocument(Bytes.toString(document));

                if (timestamp != null) {
                    opinion.setTimestamp(new Date(Bytes.toLong(timestamp)));
                }

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
                opinion.setPosition(Bytes.toInt(position));
                opinion.setDocument(Bytes.toString(document));
                opinion.setTimestamp(new Date(Bytes.toLong(timestamp)));

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
        opinion.setPosition(1);

        opinion.setSentimentWord("loves");
        opinion.setSentimentOrientation(0.99);
        opinion.setTimestamp(Calendar.getInstance().getTime());
        this.save(opinion);
    }
}
