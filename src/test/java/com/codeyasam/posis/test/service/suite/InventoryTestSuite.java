package com.codeyasam.posis.test.service.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.codeyasam.posis.test.service.integration.InventoryServiceIntegrationTest;
import com.codeyasam.posis.test.service.unit.InventoryServiceUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	InventoryServiceUnitTest.class,
	InventoryServiceIntegrationTest.class
})
public class InventoryTestSuite {

}
