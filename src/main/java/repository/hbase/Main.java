/*
 * Copyright 2011-2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package repository.hbase;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import repository.dao.OpinionDao;
import javax.transaction.NotSupportedException;
import repository.model.Opinion;
import repository.model.OpinionResult;

/**
 * WordCount startup class.
 * Bootstraps the Spring container which deploys and runs a Map-Reduce job.
 * <p/>
 * Accepts as optional parameters location of one (or multiple) application contexts that will
 * be used for configuring the Spring container. See the reference documentation for more
 * {@link http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/resources.html information}.
 *  
 * Note that in most (if not all) managed environments writing such a class is not needed
 * as Spring already provides the required integration.
 * 
 * @see org.springframework.web.context.ContextLoaderListener
 * @author Costin Leau
 */
@Component
public class Main {
    
    @Autowired
    static OpinionDao opinionDao;

	private static final String[] CONFIGS = new String[] { "applicationContext.xml" };

	public static void main(String[] args) throws NotSupportedException {
                /*
		String[] res = (args != null && args.length > 0 ? args : CONFIGS);
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(res);
		// shutdown the context along with the VM
                

        ctx.registerShutdownHook();
        */
            
            Opinion original = new Opinion();
            original.setEntity("oentity");
            Opinion expanded = new Opinion();
            expanded.setEntity("eentity");
            
            OpinionResult or = new OpinionResult();
            or.setEntity("oentity");
            ArrayList<Opinion> opinions = new ArrayList<Opinion>();
            opinions.add(expanded);
            opinions.add(original);
            or.setOpinions(opinions);
            Opinion originalresult = or.getOriginalOpinion();
            
            if(originalresult != null){
                System.out.println("Bun");
            } else {
                System.out.println("Rau");
            }
	}
        
   
    
}
