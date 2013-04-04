package api.repository.solr;

import api.model.Opinion;
import api.repository.OpinionRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/2/13
 * Time: 10:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SolrRepositoryTest {

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext( new String[] {
            "classpath*:applicationContext.xml" });

    @Test
    @Ignore
    public void testAddOpinions() {

        OpinionRepository opinionRepository = applicationContext.getBean(OpinionRepository.class);

        Opinion opinion = createTestOpinion();
        opinionRepository.save(opinion);

        System.out.println(opinion);


    }

    public Opinion createTestOpinion() {
        Opinion opinion = new Opinion();
        opinion.setDocument("Alexandra loves Starbucks hot chocolate and coffee.");
        opinion.setHolder("Alexandra");
        opinion.setEntity("coffee");
        opinion.setAttribute("taste");
        opinion.setPosition(1);

        opinion.setSentimentWord("loves");
        opinion.setSentimentOrientation(0.99);
        opinion.setTimestamp(Calendar.getInstance().getTime());

        return opinion;
    }

}
