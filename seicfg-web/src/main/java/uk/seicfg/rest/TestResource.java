package uk.seicfg.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tests")
public class TestResource extends AbstractResource{

	@RequestMapping(value ="/test", method = RequestMethod.GET)
	@ResponseBody
	public String findAll() {
		System.out.println("EventResource findAll");
		// return dao.findAll();
		return "Hello World";
	}
}
