package com.codeyasam.posis.test.service.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.codeyasam.posis.test.controller.EndUserControllerIntegrationTest;
import com.codeyasam.posis.test.service.integration.EndUserIntegrationTest;
import com.codeyasam.posis.test.service.unit.EndUserServiceUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	EndUserControllerIntegrationTest.class,
	EndUserIntegrationTest.class,
	EndUserServiceUnitTest.class
})
public class EndUserTestSuite {

}
