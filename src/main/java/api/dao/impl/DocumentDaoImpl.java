
package api.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.NotSupportedException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;
import api.dao.DocumentDao;
import api.dao.base.BasePersistence;
import api.dao.util.DocumentMapper;
import api.model.Document;

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

    @Autowired
    private DocumentMapper mapper;

    @Override
    public Document save(final Document document) throws NotSupportedException {

        template.execute(tableName, new TableCallback<Object>() {
            public Object doInTable(HTableInterface table) throws Throwable {

            Put p = mapper.mapToPut(document,columnFamilyName);
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

            return DocumentMapper.mapResultToDocument(result, columnFamilyName);
            }
        });

        return document;
    }

    @Override
    public List<Document> findAll() throws NotSupportedException {

        return template.find(tableName, columnFamilyName, new RowMapper<Document>() {
            @Override
            public Document mapRow(Result result, int rowNum) throws Exception {
            return mapper.mapFromResult(result, columnFamilyName);
            }
        });
    }

    @Override
    public List<Document> filterFind(Map<String, Object> filterMap) throws NotSupportedException {
        Scan scan = new Scan();

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

        Set<Map.Entry<String, Object>> filterEntries = filterMap.entrySet();
        for (Map.Entry entry : filterEntries) {
            SingleColumnValueFilter filter = new SingleColumnValueFilter(
                    Bytes.toBytes(columnFamilyName),
                    Bytes.toBytes(entry.getKey().toString()),
                    CompareFilter.CompareOp.EQUAL,
                    Bytes.toBytes(entry.getValue().toString())
            );
            filterList.addFilter(filter);
        }

        scan.setFilter(filterList);

        return template.find(tableName, scan, new RowMapper<Document>() {

            @Override
            public Document mapRow(Result result, int i) throws Exception {
                return mapper.mapFromResult(result, columnFamilyName);
            }
        });
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
