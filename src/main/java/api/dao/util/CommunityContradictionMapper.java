/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao.util;

import api.controller.dto.ContradictionsDTO;
import api.model.CommunityContradiction;
import java.util.List;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex Marchis
 */
@Component(value = "communityContradictionMapper")
public class CommunityContradictionMapper implements HBaseMapper<CommunityContradiction> {
    
    public ContradictionsDTO mapListFromResult(Result result, String columnFamilyName) {
        ContradictionsDTO contradictions = new ContradictionsDTO();
        
        String rowKey = Bytes.toString(result.getRow());
        
        List<KeyValue> list = result.list();
        for (KeyValue kv: list) {
            CommunityContradiction contradiction = new CommunityContradiction();
            String contrCommunityId = Bytes.toString(kv.getQualifier());
            double strength = Bytes.toDouble(kv.getValue());
            contradiction.setFirstOpinionId(rowKey);
            contradiction.setCommunityId(contrCommunityId);
            contradiction.setStrength(strength);
            
            contradictions.add(contradiction);
            
        }
        
        return contradictions;
        
    }

    @Override
    public CommunityContradiction mapFromResult(Result result, String columnFamilyName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Put mapToPut(CommunityContradiction record, String columnFamilyName) {
        String rowKey = record.getFirstOpinionId();
        
        String contrCommunityKey = record.getCommunityId();
        double strength = record.getStrength();
       
        Put p = new Put(Bytes.toBytes( rowKey));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(contrCommunityKey),Bytes.toBytes(strength));
        
        return p;
    }
    
}
