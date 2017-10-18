package com.codeyasam.posis.test.service;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;

import com.codeyasam.posis.test.controller.PointOfSaleControllerIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({PointOfSaleServiceTest.class, 
	PointOfSaleServiceIntegrationTest.class,
	PointOfSaleControllerIntegrationTest.class})
public class PointOfSaleTestSuite {

}
