package com.codeyasam.posis.test.service.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.codeyasam.posis.test.service.integration.EndProductServiceIntegrationTest;
import com.codeyasam.posis.test.service.unit.EndProductServiceUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({EndProductServiceUnitTest.class, 
		EndProductServiceIntegrationTest.class})
public class EndProductTestSuite {

}
