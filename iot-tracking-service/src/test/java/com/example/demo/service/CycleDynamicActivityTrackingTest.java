package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.CsvModalData;

class CycleDynamicActivityTrackingTest {
	
	@InjectMocks
	CycleDynamicActivityTracking cycleDynamicActivityTracking;

	@Mock
	List<CsvModalData > csvMockList;
	
	List<CsvModalData > csvList;
	
	List<CsvModalData > csvNonActiveList;
	
	List<CsvModalData > csvListMoreData;
	
	List<CsvModalData > csvListDiffrentData;
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		csvList=new ArrayList<>();
		csvList.add(new CsvModalData("1234556767", "123450", "WA123456", "23.56", "45.45", "FULL", "OFF", "ON"));
		csvList.add(new CsvModalData("1234556769", "123430", "WA123457", "23.56", "65.45", "FULL", "ON", "OFF"));
		csvList.add(new CsvModalData("1234556768", "123490", "WA123458", "93.56", "76.53", "FULL", "OFF", "OFF"));
		
		csvNonActiveList=new ArrayList<>();
		csvNonActiveList.add(new CsvModalData("1234556767", "123450", "WA123456", "23.56", "45.45", "FULL", "OFF", "ON"));
		csvNonActiveList.add(new CsvModalData("1234556769", "123430", "WA123457", "23.56", "65.45", "FULL", "ON", "OFF"));
		csvNonActiveList.add(new CsvModalData("1234556768", "123490", "WA123458", "", "", "FULL", "OFF", "OFF"));
		
		
		csvListMoreData=new ArrayList<>();
		csvListMoreData.add(new CsvModalData("1234556767", "123450", "WA123456", "23.56", "45.45", "FULL", "OFF", "ON"));
		csvListMoreData.add(new CsvModalData("1234556769", "123430", "WA123457", "23.56", "45.45", "FULL", "ON", "OFF"));
		csvListMoreData.add(new CsvModalData("1234556768", "123490", "WA123458", "23.56", "45.45", "FULL", "OFF", "OFF"));
		csvListMoreData.add(new CsvModalData("1234556769", "123491", "WA123459", "23.56", "45.45", "LOW", "ON", "OFF"));
		
		
		csvListDiffrentData=new ArrayList<>();
		csvListDiffrentData.add(new CsvModalData("1234556767", "123450", "WA123456", "23.56", "45.45", "FULL", "OFF", "ON"));
		csvListDiffrentData.add(new CsvModalData("1234556769", "123430", "WA123457", "23.56", "45.45", "FULL", "ON", "OFF"));
		csvListDiffrentData.add(new CsvModalData("1234556768", "123490", "WA123458", "23.56", "45.4123", "FULL", "OFF", "OFF"));
		csvListDiffrentData.add(new CsvModalData("1234556769", "123491", "WA123459", "23.56", "45.48", "LOW", "ON", "OFF"));
		
	}

	@Test
	void checkAndSetStatusTestNegative() {
		when(csvMockList.size()).thenReturn(1);
		String response=cycleDynamicActivityTracking.checkAndSetStatus(csvMockList);
		assertEquals("N/A", response);
	}
	
	@Test
	void checkAndSetStatusTestActive() {
		String response=cycleDynamicActivityTracking.checkAndSetStatus(csvList);
		assertEquals("ACTIVE", response);
	}

	@Test
	void checkAndSetStatusTestNonActive() {
		String response=cycleDynamicActivityTracking.checkAndSetStatus(csvListMoreData);
		assertEquals("INACTIVE", response);
	}
	
	@Test
	void checkConsecutiveGpsDataAvailiableFalse() {
		Boolean response=cycleDynamicActivityTracking.checkConsecutiveGpsDataAvailiable(csvNonActiveList);
		assertTrue(response);
	}
	
	@Test
	void checkConsecutiveGpsDataAvailiableTrue() {
		Boolean response=cycleDynamicActivityTracking.checkConsecutiveGpsDataAvailiable(csvList);
		assertTrue(response);
	}
	
	@Test
	void checkConsecutiveGpsDataAvailiable() {
		String response=cycleDynamicActivityTracking.checkAndSetStatus(csvListDiffrentData);
		assertEquals("ACTIVE", response);
	}
}
