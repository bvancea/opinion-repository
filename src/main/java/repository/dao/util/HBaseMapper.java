/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.dao.util;

/**
 *
 * @author Alex Marchis
 */
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

public interface HBaseMapper<T> {

    public T mapFromResult(Result result, String columnFamilyName);
    public Put mapToPut(T record, String columnFamilyName);
}
