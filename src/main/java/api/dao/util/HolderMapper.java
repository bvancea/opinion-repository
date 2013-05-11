/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao.util;

import api.model.Holder;
import api.model.HolderRelationship;
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
@Component(value = "holderMapper")
public class HolderMapper implements HBaseMapper<Holder> {
    
    @Value(value = "${splitToken}")
    private String splitToken;
    
    @Value(value = "${concatToken}")
    private String concatToken;
    
    @Value(value = "${relationshipPrefix}")
    private String relationshipPrefix;

    @Override
    public Holder mapFromResult(Result result, String columnFamilyName) {
        Holder holder = new Holder();
        
        String rowKey = Bytes.toString(result.getRow());
        
        byte[] birthDate = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("birthDate"));
        byte[] gender = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("gender"));
        byte[] religion = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("religion"));
        byte[] location = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("location"));
             
        List<KeyValue> list = result.list();
        for (KeyValue kv: list) {         
            String columnQ = Bytes.toString(kv.getQualifier());
            if(columnQ.startsWith(relationshipPrefix)){
                HolderRelationship hrel = new HolderRelationship();
                String hrKey = columnQ.substring(relationshipPrefix.length());
                hrel.setHolderWithWhomName(hrKey);
                hrel.setHolderName(rowKey);
                
                String hrValue = Bytes.toString(kv.getValue());
                String[] hrValueSplit = hrValue.split(splitToken);
                
                hrel.setType(Integer.parseInt(hrValueSplit[0]));
                
                Date timestamp = new Date(Long.parseLong(hrValueSplit[1]));
                hrel.setTimestamp(timestamp);
                
                holder.getRelationships().add(hrel);

            }
            
            
            
        }
        
        holder.setName(rowKey);
        
        Date bd = new Date(Bytes.toLong(birthDate));
        holder.setBirthDate(bd);
        
        int gnd = Bytes.toInt(gender);
        holder.setGender(gnd);
        
        holder.setReligion(Bytes.toString(religion));
        holder.setLocation(Bytes.toString(location));
        
        return holder;
        
    }

    @Override
    public Put mapToPut(Holder record, String columnFamilyName) {
        String rowKey = record.getName();
        
        Put p = new Put(Bytes.toBytes(rowKey));
        
        
        long timestamp = record.getBirthDate().getTime();
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("birthDate"),Bytes.toBytes(timestamp));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("gender"),Bytes.toBytes(record.getGender()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("religion"),Bytes.toBytes(record.getReligion()));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("location"),Bytes.toBytes(record.getLocation()));
        
        for(HolderRelationship hrel : record.getRelationships()){
            String opinionKey = relationshipPrefix + hrel.getHolderWithWhomName();
            String opinionValue = ""+ hrel.getType() + concatToken +
                                    + hrel.getTimestamp().getTime();


            p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(opinionKey),Bytes.toBytes(opinionValue));

        }
        
        return p;
    }
    
    public Put mapRelationshipToPut (HolderRelationship rel, String columnFamilyName){
        String rowKey = rel.getHolderName();
        
        Put p = new Put(Bytes.toBytes(rowKey));
        String opinionKey = relationshipPrefix + rel.getHolderWithWhomName();
        String opinionValue = ""+ rel.getType() + concatToken +
                                    + rel.getTimestamp().getTime();

        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes(opinionKey),Bytes.toBytes(opinionValue));
        
        return p;
    }
    
}
