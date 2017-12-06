package com.codeyasam.posis.test.procedure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.codeyasam.posis.projection.ProductTypeProjection;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class ProductTypeProcedureIntegrationTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Test
	public void findAllProductTypeProcedure() {
		List<ProductTypeProjection> list = entityManager.createStoredProcedureQuery("find_all_product_type", "ProductTypeMapping")
				.getResultList();
		
		System.out.println(list.get(1).getTypeName());
		Assert.assertEquals(3, list.size());
	}
	
}
