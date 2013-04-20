/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.dao.util;

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
import repository.model.OpinionResult;

/**
 *
 * @author Alex Marchis
 */
@Component(value = "opinionResultMapper")
public class OpinionResultMapper implements HBaseMapper<OpinionResult> {
    
    @Value(value = "${opinionPrefix}")
    private String opinionPrefix;
    
    @Value(value = "${opinionIdSearchPrefix}")
    private String opinionIdSearchPrefix;
    
    @Value(value = "${splitToken}")
    private String splitToken;
    
    @Value(value = "${concatToken}")
    private String concatToken;
    
    @Override
    public OpinionResult mapFromResult(Result result, String columnFamilyName) {     
        
        OpinionResult opinionresult = new OpinionResult();
               
        String rowKey = Bytes.toString(result.getRow());
        String[] keySplit = rowKey.split(splitToken);
        opinionresult.setHolder(keySplit[0]);
        opinionresult.setEntity(keySplit[1]);

        List<Opinion> opinions = new ArrayList<Opinion>();

        List<KeyValue> list = result.list();
        for (KeyValue kv: list) {
            Opinion opinion = new Opinion();
            String columnQ = Bytes.toString(kv.getQualifier());
            if(columnQ.startsWith(opinionPrefix)){
                String opKey = columnQ.substring(opinionPrefix.length());
                String[] opKeySplit = opKey.split(splitToken);
                opinion.setId(opKeySplit[1]);
                opinion.setEntity(opKeySplit[0]);
                opinion.setHolder(opinionresult.getHolder());
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

                opinions.add(opinion);
               
            }
        }

        opinionresult.setOpinions(opinions);

        return opinionresult;
    }

    @Override
    public Put mapToPut(OpinionResult record, String columnFamilyName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
