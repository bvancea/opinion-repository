package api.dao.util;

import api.model.Document;
import api.model.Opinion;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 12:34 PM
 *
 * Mapper utility class
 */
@Component
public class DocumentMapper implements HBaseMapper<Document>{

    public static Document mapResultToDocument(Result result, String columnFamilyName) {

        byte[] docId = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("docId"));
        byte[] title = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("title"));
        byte[] content = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("content"));
        byte[] addedDate = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("addedDate"));

        Document document = new Document();

        document.setId(Bytes.toString(docId));
        document.setTitle(Bytes.toString(title));
        document.setContent(Bytes.toString(content));
        document.setAddedDate(new Date(Bytes.toLong(addedDate)));

        return document;
    }

    @Override
    public Document mapFromResult(Result result, String columnFamilyName) {

        byte[] docId = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("docId"));
        byte[] title = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("title"));
        byte[] content = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("content"));
        byte[] addedDate = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("addedDate"));

        Document document = new Document();

        String idString = (docId == null)?null:Bytes.toString(docId);
        String titleString = (title == null)?null:Bytes.toString(title);
        String contentString = (docId == null)?null:Bytes.toString(content);
        Long addedDateLong = (docId == null)?0:Bytes.toLong(addedDate);
        
        
        document.setId(idString);
        document.setTitle(titleString);
        document.setContent(contentString);
        document.setAddedDate(new Date(addedDateLong));

        return document;
    }

    @Override
    public Put mapToPut(Document record, String columnFamilyName) {
        String rowKey = record.getId();
        Put p = new Put(Bytes.toBytes(rowKey));
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("docId"),Bytes.toBytes(record.getId()) );
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("title"),Bytes.toBytes(record.getTitle()) );
        p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("content"),Bytes.toBytes(record.getContent()) );

        if (record.getAddedDate() != null) {
            p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("addedDate"),Bytes.toBytes(record.getAddedDate().getTime()));
        } else {
            p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("addedDate"),Bytes.toBytes(Calendar.getInstance().getTimeInMillis()));
        }

        return p;
    }
}
