

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "/springMVC.xml")
public class BasicTest{
	ApplicationContext atx=new ClassPathXmlApplicationContext("/springMVC.xml");
	
}
