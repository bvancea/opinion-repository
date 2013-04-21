/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.dao.util;

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
import repository.model.Opinion;



@Component(value = "opinionMapper")
public class OpinionMapper implements HBaseMapper<Opinion> {

    @Value(value = "${opinionPrefix}")
    private String opinionPrefix;
    
    @Value(value = "${opinionIdSearchPrefix}")
    private String opinionIdSearchPrefix;
    
    @Value(value = "${splitToken}")
    private String splitToken;
    
    @Value(value = "${concatToken}")
    private String concatToken;
    
    @Override
    public Opinion mapFromResult(Result result, String columnFamilyName) {
        Opinion opinion = new Opinion();
               
        String rowKey = Bytes.toString(result.getRow());
        String[] keySplit = rowKey.split(splitToken);
        opinion.setHolder(keySplit[0]);

        List<Opinion> opinions = new ArrayList<Opinion>();

        List<KeyValue> list = result.list();
        for (KeyValue kv: list) {
            String columnQ = Bytes.toString(kv.getQualifier());
            if(columnQ.startsWith(opinionPrefix)){
                String opKey = columnQ.substring(opinionPrefix.length());
                String[] opKeySplit = opKey.split(splitToken);
                opinion.setId(opKeySplit[1]);
                opinion.setEntity(opKeySplit[0]);
                String opValue = Bytes.toString(kv.getValue());
                String[] opValueSplit = opValue.split(splitToken);
                opinion.setSentimentOrientation(Double.parseDouble(opValueSplit[0]));
                opinion.setSentimentWord(opValueSplit[1]);
                opinion.setDocument(opValueSplit[2]);
                opinion.setPositionSW(Integer.parseInt(opValueSplit[3]));
                opinion.setPositionT(Integer.parseInt(opValueSplit[4]));
                opinion.setExpanded(Integer.parseInt(opValueSplit[5]));
                opinion.setCommunitized(Integer.parseInt(opValueSplit[6]));
                Date timestamp = new Date(Long.parseLong(opValueSplit[7]));
                opinion.setTimestamp(timestamp);
               
            } else if (columnQ.startsWith(opinionIdSearchPrefix)){
                String opKey = columnQ.substring(opinionIdSearchPrefix.length());
                String[] opKeySplit = opKey.split(splitToken);
                opinion.setId(opKeySplit[0]);
                opinion.setEntity(opKeySplit[1]);
                String opValue = Bytes.toString(kv.getValue());
                String[] opValueSplit = opValue.split(splitToken);
                opinion.setSentimentOrientation(Double.parseDouble(opValueSplit[0]));
                opinion.setSentimentWord(opValueSplit[1]);
                opinion.setDocument(opValueSplit[2]);
                opinion.setPositionSW(Integer.parseInt(opValueSplit[3]));
                opinion.setPositionT(Integer.parseInt(opValueSplit[4]));
                opinion.setExpanded(Integer.parseInt(opValueSplit[5]));
                opinion.setCommunitized(Integer.parseInt(opValueSplit[6]));
                Date timestamp = new Date(Long.parseLong(opValueSplit[7]));
                opinion.setTimestamp(timestamp);
            }
        }

        return opinion;
    }

    @Override
    public Put mapToPut(Opinion record, String columnFamilyName) {

        
        String rowKey = record.getHolder() + concatToken + record.getEntity();
        String opinionKey = opinionPrefix + record.getEntity() + concatToken + record.getId();
        String altOpinionKey = opinionIdSearchPrefix + record.getId() + concatToken + record.getEntity();
        String opinionValue = ""+record.getSentimentOrientation() + concatToken +
                                record.getSentimentWord() + concatToken +
                                record.getDocument() + concatToken +
                                record.getPositionSW() + concatToken +
                                record.getPositionT() + concatToken +
                                record.getExpanded() + concatToken +
                                record.getCommunitized() + concatToken +
                                record.getTimestamp().getTime();
        Put p = new Put(Bytes.toBytes(rowKey));

        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(opinionKey),Bytes.toBytes(opinionValue));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(altOpinionKey),Bytes.toBytes(opinionValue));

        return p;
    }

    

   
}
