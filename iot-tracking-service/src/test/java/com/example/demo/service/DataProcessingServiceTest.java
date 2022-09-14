package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.model.CsvModalData;
import com.example.demo.model.ReportDeviceDetails;

class DataProcessingServiceTest {
	
	@InjectMocks
	DataProcessingService dataProcessingService;
	
	List<CsvModalData > csvList;
	
	CsvModalData csvModalData;
	
	CsvModalData csvModalDataGen;
	CsvModalData csvModalDataAirPlaneModeOnAndGpsNotAvailaible;
	CsvModalData csvGpsDataNotAvaliaible;
	
	ReportDeviceDetails reportDeviceDetails;
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		csvList=new ArrayList<>();
		csvList.add(new CsvModalData("1582605137000", "123490", "WA123458", "93.56", "76.53", "40", "OFF", "OFF"));
		csvList.add(new CsvModalData("1234556767", "123450", "WA123456", "23.56", "45.45", "72", "OFF", "ON"));
		csvList.add(new CsvModalData("1234556766", "123430", "WA123457", "23.56", "65.45", "30", "ON", "OFF"));
		
		csvModalData=new CsvModalData("1234556767", "123450", "WG123456", "23.56", "45.45", "98", "OFF", "OFF");
		csvModalDataGen=new CsvModalData("1234556767", "123450", "69123456", "23.56", "45.45", "60", "OFF", "ON");
		csvModalDataAirPlaneModeOnAndGpsNotAvailaible=new CsvModalData("1234556767", "123450", "WG123456", "34.45", "64.65", "98", "OFF", "ON");
		csvGpsDataNotAvaliaible=new CsvModalData("1234556767", "123450", "WG123456", "", "", "98", "OFF", "OFF");
		
		reportDeviceDetails=new ReportDeviceDetails("WA12345", "CycleTracker", "25/02/2020  04:31:17", "43.34", "-0.541", "", "Full", "");
	}

	@Test
	void reverseSortedFilterProductByIdTestFound() {
		
		List<CsvModalData> reverseSortedFilterProductById = dataProcessingService.
				reverseSortedFilterProductById("WA123458", csvList);
		assertNotNull(reverseSortedFilterProductById);
	}
	
	@Test
	void reverseSortedFilterProductByIdTestNotFound() {
		
		List<CsvModalData> reverseSortedFilterProductById = dataProcessingService.
				reverseSortedFilterProductById("WA123458T", csvList);
		int length=reverseSortedFilterProductById.size();
		assertEquals(0, length);
	}
	
	@Test
	void findTheNearestTimeStampProduct() throws Exception {
		
		CsvModalData csvModalData= dataProcessingService.
				findTheNearestTimeStampProduct("1234556769", csvList,null);
		assertNotNull(csvModalData);
		assertEquals("1234556767", csvModalData.getDataTime());
	}
	
	@Test
	void findTypeOfDeviceWG() throws Exception {
		
		String deviceType= dataProcessingService.
				findTypeOfDevice(csvModalData);
		assertEquals("CyclePlusTracker", deviceType);
	}
	
	@Test
	void findTypeOfDeviceGen() throws Exception {
		
		String deviceType= dataProcessingService.
				findTypeOfDevice(csvModalDataGen);
		assertEquals("GeneralTracker", deviceType);
	}
	
	@Test
	void filterDataOnTimeStamp() throws Exception  {
		
		CsvModalData csvModalData= dataProcessingService.
				filterDataOnTimeStamp(Optional.ofNullable("1234556766"),csvList,null);
		assertNotNull(csvModalData);
		assertEquals("1234556766", csvModalData.getDataTime());
	}
	
	@Test
	void setDataAccordingToAirPlaneModeOffTest() throws Exception  {
		
		ReportDeviceDetails reportDeviceDetails= dataProcessingService.
				setDataAccordingToAirPlaneMode(csvModalData);
		assertNotNull(reportDeviceDetails);
		assertEquals("FULL", reportDeviceDetails.getBattery());
		assertEquals("SUCCESS: Location identified",reportDeviceDetails.getDescription());
		assertEquals("1970-01-15 12:25:56.767", reportDeviceDetails.getDateTime());
		assertEquals("ACTIVE", reportDeviceDetails.getSatus());
		assertEquals("45.45", reportDeviceDetails.getLongValue());
		assertEquals("23.56", reportDeviceDetails.getLat());
		assertEquals("CyclePlusTracker", reportDeviceDetails.getName());
		assertEquals("WG123456", reportDeviceDetails.getId());
		
	}
	
	@Test
	void setDataAccordingToAirPlaneModeOnTest() throws Exception  {
		
		ReportDeviceDetails reportDeviceDetails= dataProcessingService.
				setDataAccordingToAirPlaneMode(csvModalDataAirPlaneModeOnAndGpsNotAvailaible);
		assertNotNull(reportDeviceDetails);
		assertEquals("INACTIVE", reportDeviceDetails.getSatus());
	}
	
	@Test
	void evaluateCsvDataTestProductFound() throws Exception  {
		
		ReportDeviceDetails reportDeviceDetails=dataProcessingService.evaluateCsvData("WA123458",
				Optional.ofNullable("1582605137000"),csvList);
		assertNotNull(reportDeviceDetails);
		assertEquals("2020-02-25 10:02:17.000", reportDeviceDetails.getDateTime());
	}
	
	
	@Test
	void checkForLongitudeAndLatitiudeTestStatus() throws Exception  {
		
		ReportDeviceDetails reportDeviceDetails=dataProcessingService.
				setDataAccordingToAirPlaneMode(csvModalData,"ACTIVE");
		assertNotNull(reportDeviceDetails);
		assertEquals("ACTIVE", reportDeviceDetails.getSatus());
	}
	
	@Test
	void csvGpsDataNotAvaliaibleTest() throws Exception  {
	
		assertThrows(GpsNotReportedException.class, ()->{
			dataProcessingService.
			setDataAccordingToAirPlaneMode(csvGpsDataNotAvaliaible);
		});
	}
	
	@Test
	void evaluateCsvDataTestProductNotFound() throws Exception  {
		
		assertThrows(IotDataNotFoundException.class, ()->{
			dataProcessingService.
			evaluateCsvData("WA123459",Optional.ofNullable("123456778"),csvList);
		});
	}

}
