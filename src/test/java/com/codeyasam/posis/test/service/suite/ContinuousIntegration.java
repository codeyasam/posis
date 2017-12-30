package com.codeyasam.posis.test.service.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	EndProductTestSuite.class,
	InventoryTestSuite.class,
	PointOfSaleTestSuite.class,
	EndUserTestSuite.class,
	TagTestSuite.class
})
public class ContinuousIntegration {

}
