package api.repository.solr;

import api.model.Opinion;
import api.repository.OpinionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/2/13
 * Time: 10:45 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SolrRepositoryTest {

   /* private ApplicationContext applicationContext = new ClassPathXmlApplicationContext( new String[] {
            "classpath:org/springframework/data/solr/example/applicationContext.xml" });
*/
    @Autowired
    OpinionRepository opinionRepository;

    private static final int ITERATIONS = 100000;

    @Test
    public void testAddOpinions() {

        Random random = new Random();
        for (int i = 0; i < ITERATIONS; i++)   {
            Opinion opinion = createTestOpinion(random);
            opinionRepository.save(opinion);
        }

        System.out.println("Opinions indexed: " +  opinionRepository.count());

    }

    public void testgetOpinions() {

    }

    public Opinion createTestOpinion(Random random) {
        Opinion opinion = new Opinion();

        String id = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String docId = String.valueOf(random.nextInt());
        String holder = TestConstants.holders[random.nextInt(TestConstants.holders.length)];
        String entity = TestConstants.entities[random.nextInt(TestConstants.entities.length)];
        String attribute = TestConstants.attributes[random.nextInt(TestConstants.attributes.length)];
        String sentimentWord = TestConstants.sentimentWords[random.nextInt(TestConstants.sentimentWords.length)];

        int pos = random.nextInt(1024);
        double sentimentOrientation = random.nextDouble();

        opinion.setId(id);
        opinion.setDocument(docId);
        opinion.setHolder(holder);
        opinion.setEntity(entity);
        opinion.setAttribute(attribute);
        opinion.setPositionSW(pos);

        opinion.setSentimentWord(sentimentWord);
        opinion.setSentimentOrientation(sentimentOrientation);
        opinion.setTimestamp(Calendar.getInstance().getTime());

        return opinion;
    }

    public int randomInt(Random random, int end) {
        return random.nextInt(end);
    }

}
