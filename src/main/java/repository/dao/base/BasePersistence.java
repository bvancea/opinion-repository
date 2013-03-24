package repository.dao.base;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/16/13
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BasePersistence {

    @Autowired
    protected Configuration hbaseConfiguration;

    @Inject
    protected HbaseTemplate template;



}
