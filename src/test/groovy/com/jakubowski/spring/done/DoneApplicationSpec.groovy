package com.jakubowski.spring.done;

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration;
import spock.lang.Specification;

@ContextConfiguration
@SpringBootTest
class DoneApplicationSpec extends Specification {

	void contextLoads() {
		expect:
		1 == 1
	}

}
