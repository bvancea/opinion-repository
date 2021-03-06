package api.dao.base;

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
 *
 * Hold hbase configuration and template here.
 */
@Repository
public class BasePersistence {

    @Autowired
    protected Configuration hbaseConfiguration;

    @Inject
    protected HbaseTemplate template;



}
