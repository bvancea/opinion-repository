/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.dao.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import repository.controller.dto.ContradictionsDTO;
import repository.model.Contradiction;

/**
 *
 * @author Alex Marchis
 */
@Component(value = "contradictionMapper")
public class ContradictionMapper implements HBaseMapper<Contradiction> {
    

    @Value(value = "${splitToken}")
    private String splitToken;
    
    @Value(value = "${concatToken}")
    private String concatToken;

    
    public ContradictionsDTO mapListFromResult(Result result, String columnFamilyName) {
        ContradictionsDTO contradictions = new ContradictionsDTO();
        
        String rowKey = Bytes.toString(result.getRow());
        
        List<KeyValue> list = result.list();
        for (KeyValue kv: list) {
            Contradiction contradiction = new Contradiction();
            String contrOpinionId = Bytes.toString(kv.getQualifier());
            int value = Bytes.toInt(kv.getValue());
            int contradictionType = value;
            contradiction.setFirstOpinionId(rowKey);
            contradiction.setSecondOpinionId(contrOpinionId);
            contradiction.setContradictionType(contradictionType);
            
            contradictions.add(contradiction);
            
        }
        
        return contradictions;
        
    }

    @Override
    public Put mapToPut(Contradiction record, String columnFamilyName) {
        String rowKey = record.getFirstOpinionId();
        
        String contrOpinionKey = record.getSecondOpinionId();
        int contrTypeValue = record.getContradictionType();
        Put p = new Put(Bytes.toBytes( rowKey));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(contrOpinionKey),Bytes.toBytes(contrTypeValue));
        
        return p;
    
    }

    @Override
    public Contradiction mapFromResult(Result result, String columnFamilyName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
