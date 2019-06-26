package com.revature.tests;

import static org.junit.Assert.assertArrayEquals;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.service.ServiceUtil;

public class UnitTests {
	
	private static final ServiceUtil service = new ServiceUtil();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	//Login tests
	@Test
	public void infoGet() {
		final String userTest = "TestAccount";
		final String [] expected = {"1", "TestAccount", "password", "2000"};
		assertArrayEquals(expected, service.getUserInfo(userTest));
		
	}
	
	@Test
	public void infoGetInvalid() {
		final String userTest = "InvalidUser";
		final String [] expected = {null, null, null, null};
		assertArrayEquals(expected, service.getUserInfo(userTest));
	}
	
	
}
