package uk.seicfg.orm.repositories;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.seicfg.orm.entities.SEIUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "seicfg-orm-context.xml"})
public class SEIUserRepositoryTest {
	
	@Autowired
	private SEIUserRepository seiuserRepository;
	
	@Test
	public void retrieveAllTest() {
		List<SEIUser> users = seiuserRepository.findAll();
		for(SEIUser user : users) {
			System.out.println(user);
		}
	}
	
}
