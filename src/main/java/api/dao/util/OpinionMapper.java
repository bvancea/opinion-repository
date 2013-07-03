/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao.util;

/**
 *
 * @author Alex Marchis
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import api.model.Opinion;
import java.util.Iterator;
import java.util.ListIterator;

@Component(value = "opinionMapper")
public class OpinionMapper implements HBaseMapper<Opinion> {

    @Value(value = "${targetPrefix}")
    private String targetPrefix;

    @Value(value = "${sentimentPrefix}")
    private String sentimentPrefix;

    @Value(value = "${splitToken}")
    private String splitToken;

    @Value(value = "${concatToken}")
    private String concatToken;

    @Override
    public Opinion mapFromResult(Result result, String columnFamilyName) {
        Opinion opinion = new Opinion();

        //String rowKey = Bytes.toString(result.getRow());

        byte[] id = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Id"));
        byte[] holder = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Holder"));
        byte[] entity = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Entity"));
        byte[] so = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("SentimentOrientation"));
        byte[] sw = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("SentimentWord"));
        byte[] document = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Document"));
        byte[] timestamp = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Timestamp"));
        byte[] posSW = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("PositionSW"));
        byte[] posT = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("PositionT"));
        byte[] expanded = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Expanded"));
        byte[] comm = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Communitized"));

        opinion.setId(Bytes.toString(id));
        opinion.setHolder(Bytes.toString(holder));
        opinion.setEntity(Bytes.toString(entity));
        opinion.setSentimentOrientation(Bytes.toDouble(so));
        opinion.setSentimentWord(Bytes.toString(sw));
        opinion.setDocument(Bytes.toString(document));
        opinion.setTimestamp(new Date(Bytes.toLong(timestamp)));
        opinion.setPositionSW(Bytes.toInt(posSW));
        opinion.setPositionT(Bytes.toInt(posT));
        opinion.setExpanded(Bytes.toInt(expanded));
        opinion.setCommunitized(Bytes.toInt(comm));

        List<String> targetExpansions = new ArrayList<String>();
        List<Float> targetExpansionWeights = new ArrayList<Float>();
        List<String> sentimentExpansions = new ArrayList<String>();
        List<Float> sentimentExpansionWeights = new ArrayList<Float>();
        List<KeyValue> list = result.list();
        for (KeyValue kv: list) {
            String columnQ = Bytes.toString(kv.getQualifier());
            if(columnQ.startsWith(targetPrefix)){
                String targetEx = columnQ.substring(targetPrefix.length());
                float weight = Bytes.toFloat(kv.getValue());
                targetExpansions.add(targetEx);
                targetExpansionWeights.add(weight);

            } else if (columnQ.startsWith(sentimentPrefix)){
                String sentimentEx = columnQ.substring(targetPrefix.length());
                float weight = Bytes.toFloat(kv.getValue());
                sentimentExpansions.add(sentimentEx);
                sentimentExpansionWeights.add(weight);
            }
        }

        opinion.setTargetExpansions(targetExpansions);
        opinion.setTargetExpansionWeights(targetExpansionWeights);
        opinion.setSentimentWordExpansions(sentimentExpansions);
        opinion.setSentimentWordExpansionWeights(sentimentExpansionWeights);

        return opinion;
    }

    @Override
    public Put mapToPut(Opinion record, String columnFamilyName) {

        String rowKey = record.getHolder() + concatToken + record.getId();
        Put p = new Put(Bytes.toBytes(rowKey));

        preparePut(p, record, columnFamilyName);

        return p;
    }

    public Put mapToIdPut(Opinion record, String columnFamilyName) {

        String rowKey = record.getId() + concatToken + record.getHolder();
        Put p = new Put(Bytes.toBytes(rowKey));

        preparePut(p, record, columnFamilyName);

        return p;
    }

    private Put preparePut(Put p, Opinion record, String columnFamilyName){

        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Id"),
                Bytes.toBytes(record.getId()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Holder"),
                Bytes.toBytes(record.getHolder()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Entity"),
                Bytes.toBytes(record.getEntity()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("SentimentOrientation"),
                Bytes.toBytes(record.getSentimentOrientation()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("SentimentWord"),
                Bytes.toBytes(record.getSentimentWord()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Document"),
                Bytes.toBytes(record.getDocument()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Timestamp"),
                Bytes.toBytes(record.getTimestamp().getTime()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("PositionSW"),
                Bytes.toBytes(record.getPositionSW()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("PositionT"),
                Bytes.toBytes(record.getPositionT()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Expanded"),
                Bytes.toBytes(record.getExpanded()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("Communitized"),
                Bytes.toBytes(record.getCommunitized()));

        ListIterator<String> targetIt =
                record.getTargetExpansions().listIterator();
        ListIterator<Float> targetWeightIt =
                record.getTargetExpansionWeights().listIterator();

        while(targetIt.hasNext() && targetWeightIt.hasNext()){
            String columnKey = targetPrefix + targetIt.next();
            float value = targetWeightIt.next();

            p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(columnKey),
                    Bytes.toBytes(value));
        }

        ListIterator<String> sentimentIt =
                record.getSentimentWordExpansions().listIterator();
        ListIterator<Float> sentimentWeightIt =
                record.getSentimentWordExpansionWeights().listIterator();

        while(sentimentIt.hasNext() && sentimentWeightIt.hasNext()){
            String columnKey = sentimentPrefix + sentimentIt.next();
            float value = sentimentWeightIt.next();

            p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(columnKey),
                    Bytes.toBytes(value));
        }

        return p;
    }
}