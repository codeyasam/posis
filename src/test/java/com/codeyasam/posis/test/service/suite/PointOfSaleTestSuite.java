package com.codeyasam.posis.test.service.suite;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;

import com.codeyasam.posis.test.controller.PointOfSaleControllerIntegrationTest;
import com.codeyasam.posis.test.service.integration.PointOfSaleServiceIntegrationTest;
import com.codeyasam.posis.test.service.unit.PointOfSaleServiceUnitTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({PointOfSaleServiceUnitTest.class, 
	PointOfSaleServiceIntegrationTest.class,
	PointOfSaleControllerIntegrationTest.class})
public class PointOfSaleTestSuite {

}
