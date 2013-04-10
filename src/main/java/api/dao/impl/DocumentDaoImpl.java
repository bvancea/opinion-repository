package api.dao.impl;

import api.dao.DocumentDao;
import api.dao.base.BasePersistence;
import api.dao.util.MapperUtil;
import api.model.Document;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import javax.transaction.NotSupportedException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 11:27 AM
 *
 */
@Repository
public class DocumentDaoImpl extends BasePersistence implements DocumentDao {

    @Value(value = "${document.table.name}")
    private String tableName;

    @Value(value = "${document.table.column.familiy.name}")
    private String columnFamilyName;

    @Override
    public Document save(final Document document) throws NotSupportedException {

        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {

                String rowKey = document.getId();
                Put p = new Put(Bytes.toBytes(rowKey));
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("docId"),Bytes.toBytes(document.getId()) );
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("title"),Bytes.toBytes(document.getTitle()) );
                p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("content"),Bytes.toBytes(document.getContent()) );

                if (document.getAddedDate() != null) {
                    p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("addedDate"),Bytes.toBytes(document.getAddedDate().getTime()));
                } else {
                    p.add(Bytes.toBytes(columnFamilyName), Bytes.toBytes("addedDate"),Bytes.toBytes(Calendar.getInstance().getTimeInMillis()));

                }

                table.put(p);
                return document;
            }
        });

        return document;
    }


    @Override
    public void delete(final String id) throws NotSupportedException {

        template.execute(tableName, new TableCallback<Object>() {

            public Object doInTable(HTableInterface table) throws Throwable {
                Delete delete = new Delete(Bytes.toBytes(id));
                table.delete(delete);
                return null;
            }
        });
    }


    @Override
    public Document find(final String id) throws NotSupportedException {
        Document document = template.execute(tableName, new TableCallback<Document>() {
            @Override
            public Document doInTable(HTableInterface hTableInterface) throws Throwable {
                Get get = new Get(Bytes.toBytes(id));
                Result result = hTableInterface.get(get);

                return MapperUtil.mapResultToDocument(result, columnFamilyName);
            }
        });

        return document;
    }

    @Override
    public List<Document> findAll() throws NotSupportedException {

        return template.find(tableName, columnFamilyName, new RowMapper<Document>() {
            @Override
            public Document mapRow(Result result, int rowNum) throws Exception {
               /* byte[] docId = result.getValue(Bytes.toBytes(columnFamilyName), Bytes.toBytes("docId"));
                byte[] title = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("title"));
                byte[] content = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("content"));
                byte[] addedDate = result.getValue(Bytes.toBytes(columnFamilyName),Bytes.toBytes("addedDate"));

                Document document = new Document();

                document.setId(Bytes.toString(docId));
                document.setTitle(Bytes.toString(title));
                document.setContent(Bytes.toString(content));
                document.setAddedDate(new Date(Bytes.toLong(addedDate)));*/

                return MapperUtil.mapResultToDocument(result,columnFamilyName);
            }
        });
    }

    @Override
    public List<Document> filterFind(Map<String, Object> filter) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public Document find(long id) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public void delete(Document object) throws NotSupportedException {
        throw new NotSupportedException();
    }

}
