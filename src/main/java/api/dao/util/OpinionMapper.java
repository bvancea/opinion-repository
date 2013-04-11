package api.dao.util;

import api.model.Opinion;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/11/13
 * Time: 4:32 PM
 */
@Component(value = "opinionMapper")
public class OpinionMapper implements HBaseMapper<Opinion> {

    @Override
    public Opinion mapFromResult(Result result, String columnFamilyName) {
        byte[] holder = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("holder"));
        byte[] entity = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("entity"));
        byte[] attribute = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("attribute"));
        byte[] sentimentWord = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("sentiment_word"));
        byte[] sentimentOrientation = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("sentiment_orientation"));
        byte[] positionSW = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("positionSW"));
        byte[] positionT = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("positionT"));
        byte[] communitized = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("communitized"));
        byte[] expanded = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("expanded"));
        byte[] document = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("document"));
        byte[] timestamp = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("timestamp"));
        byte[] id = result.getRow();


        String idString = (id == null)?null:Bytes.toString(id);
        String holderString = (holder == null)?null:Bytes.toString(holder);
        String entityString = (entity == null)?null:Bytes.toString(entity);
        String attributeString = (entity == null)?null:Bytes.toString(attribute);
        String sentimentWordString = (sentimentWord == null)?null:Bytes.toString(sentimentWord);
        Double sentimentOrientationDouble = (sentimentOrientation == null)?0:Bytes.toDouble(sentimentOrientation);
        Integer positionSWInt = (positionSW == null)? -1:Bytes.toInt(positionSW);
        Integer positionTInt = (positionSW == null)? -1:Bytes.toInt(positionT);
        Integer cummunitizedInt = (positionSW == null)? -1:Bytes.toInt(communitized);
        Integer expandedInt = (positionSW == null)? -1:Bytes.toInt(expanded);
        String documentIdString = (document == null)?null:Bytes.toString(document);

        Opinion opinion = new Opinion();

        opinion.setId(idString);

        opinion.setHolder(holderString);
        opinion.setAttribute(attributeString);
        opinion.setEntity(entityString);
        opinion.setSentimentWord(sentimentWordString);
        opinion.setSentimentOrientation(sentimentOrientationDouble);
        opinion.setPositionSW(positionSWInt);
        opinion.setPositionT(positionTInt);
        opinion.setCommunitized(cummunitizedInt);
        opinion.setExpanded(expandedInt);
        opinion.setDocument(documentIdString);

        if (timestamp != null) {
            opinion.setTimestamp(new Date(Bytes.toLong(timestamp)));
        }

        return opinion;
    }

    @Override
    public Put mapToPut(Opinion record, String columnFamilyName) {

        String rowKey = /*timestamp + "-" + */record.getHolder() + "-" + record.getEntity();

        Put p = new Put(Bytes.toBytes(rowKey));

        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("holder"),Bytes.toBytes(record.getHolder()) );
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("entity"),Bytes.toBytes(record.getEntity()) );
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("attribute"),Bytes.toBytes(record.getAttribute()) );
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("sentiment_word"),Bytes.toBytes(record.getSentimentWord()) );
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("sentiment_orientation"),Bytes.toBytes(record.getSentimentOrientation()) );
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("positionSW"),Bytes.toBytes(record.getPositionSW()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("positionT"),Bytes.toBytes(record.getPositionT()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("communitized"),Bytes.toBytes(record.getCommunitized()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("expanded"),Bytes.toBytes(record.getExpanded()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("document"),Bytes.toBytes(record.getDocument()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("timestamp"),Bytes.toBytes(record.getTimestamp().getTime()));

        return p;
    }
}
