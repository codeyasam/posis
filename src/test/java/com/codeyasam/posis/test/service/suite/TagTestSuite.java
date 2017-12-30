package com.codeyasam.posis.test.service.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.codeyasam.posis.test.controller.TagControllerIntegrationTest;
import com.codeyasam.posis.test.service.integration.TagServiceIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({TagControllerIntegrationTest.class, 
	TagServiceIntegrationTest.class})
public class TagTestSuite {
	
}
