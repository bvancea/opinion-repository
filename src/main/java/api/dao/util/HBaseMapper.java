package api.dao.util;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/11/13
 * Time: 4:18 PM
 */
public interface HBaseMapper<T> {

    public T mapFromResult(Result result, String columnFamilyName);
    public Put mapToPut(T record, String columnFamilyName);
}
