/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao.util;

import api.model.Community;
import api.model.CommunityFeature;
import api.model.CommunityHolder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alex Marchis
 */
@Component(value = "communityMapper")
public class CommunityMapper implements HBaseMapper<Community> {

    @Value(value = "${splitToken}")
    private String splitToken;
    
    @Value(value = "${concatToken}")
    private String concatToken;
    
    @Value(value = "${community.table.feature.CF.name}")
    private String featureCF;
    
    @Value(value = "${community.table.holder.CF.name}")
    private String holderCF;
    
    
    @Override
    public Community mapFromResult(Result result, String columnFamilyName) {
        Community community = new Community();
               
        String rowKey = Bytes.toString(result.getRow());
        community.setCommunityId(rowKey);
        
        ArrayList<CommunityFeature> features = new ArrayList<CommunityFeature>();
        ArrayList<CommunityHolder> holders = new ArrayList<CommunityHolder>();

        List<KeyValue> list = result.list();
        for(KeyValue kv : list){
           String cf = Bytes.toString(kv.getFamily());
           String cq = Bytes.toString(kv.getQualifier());
           
           
           if(cf.equals(featureCF)){
               double value = Bytes.toDouble(kv.getValue());
               CommunityFeature feature = new CommunityFeature();
               feature.setFeature(cq);
               feature.setValue(value);
               
               features.add(feature);
           } else if(cf.equals(holderCF)){
               String value = Bytes.toString(kv.getValue());
               
               CommunityHolder holder = new CommunityHolder();
               holder.setHolderName(cq);
               String[] valueSplit = value.split(splitToken);
               holder.setValue(Double.parseDouble(valueSplit[0]));
               long timestamp = Long.parseLong(valueSplit[1]);
               holder.setTimestamp(new Date(timestamp));
               
               holders.add(holder);
           }
        }
        
        community.setFeatures(features);
        community.setHolders(holders);
        
        return community;
    }

    @Override
    public Put mapToPut(Community record, String columnFamilyName) {
        String rowKey = record.getCommunityId();
        
        Put p = new Put(Bytes.toBytes(rowKey));
        
        List<CommunityFeature> features = record.getFeatures();
        for(CommunityFeature f : features){
            byte[] feature = Bytes.toBytes(f.getFeature());
            byte[] featureValue = Bytes.toBytes(f.getValue());
            
            p.add(Bytes.toBytes(featureCF), feature, featureValue);
        }
        
        List<CommunityHolder> holders = record.getHolders();
        for(CommunityHolder h : holders){
            byte[] holderName = Bytes.toBytes(h.getHolderName());
            double holderValue =  h.getValue();
            Date date = h.getTimestamp();
            long timestamp = date.getTime();
            
            String value = "" + holderValue + concatToken +
                    timestamp;
            byte[] valueByte = Bytes.toBytes(value);        
            
            p.add(Bytes.toBytes(holderCF), holderName, valueByte);
        }
        
        return p;
    }
    
}
