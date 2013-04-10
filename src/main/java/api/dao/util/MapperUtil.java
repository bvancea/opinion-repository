package api.dao.util;

import api.model.Document;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 12:34 PM
 *
 * Mapper utility class
 */
public class MapperUtil {

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
}
