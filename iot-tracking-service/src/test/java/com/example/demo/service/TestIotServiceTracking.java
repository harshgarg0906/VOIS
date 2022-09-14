package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.custom.exception.NoDataIsLoadedInMemory;
import com.example.demo.inter.service.IDataProcessingService;
import com.example.demo.model.CsvModalData;
import com.example.demo.model.FilePath;
import com.example.demo.model.IotTrackerResponse;
import com.example.demo.model.ReportDeviceDetails;

class TestIotServiceTracking {
	
	@InjectMocks
	IotTrackerService iotTrackerService;
	
	List<CsvModalData> csvFilterData;
	
	@Mock
	IDataProcessingService iDataProcessingService;
	
	FilePath filePath;
	
	FilePath filePathNotFound;
	ReportDeviceDetails reportDeviceDetails;
	List<CsvModalData> csvData;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		filePath=new FilePath();
		filePath.setFilePath("E:\\Coding\\FilePath\\Data.csv");
		
		filePathNotFound=new FilePath();
		filePathNotFound.setFilePath("E:\\Coding\\FilePath\\Data");
		
		csvData=new ArrayList<>();
		csvData.add(new CsvModalData("1234556768", "123490", "WA123458", 
				"93.56", "76.53", "FULL", "OFF", "OFF"));
	}
	
	@Test
	void checkInMemoryDataPrsent() {	
		iotTrackerService.csvFilterData.add(new CsvModalData("1234556768", "123490", "WA123458", 
				"93.56", "76.53", "FULL", "OFF", "OFF"));
		Boolean isPrsent=iotTrackerService.checkInMemoryDataPrsent();
		assertTrue(isPrsent);
	}
	


	@Test
	void reportDeviceDetailsTest() throws IotDataNotFoundException, GpsNotReportedException, NoDataIsLoadedInMemory, Exception {
			
		assertThrows(NoDataIsLoadedInMemory.class, ()->{
		 iotTrackerService.
					reportDeviceDetails("WA123458", Optional.ofNullable("1234556768"));
		});
	}
	
	@Test
	void searchPathTest() throws IotDataNotFoundException, GpsNotReportedException, NoDataIsLoadedInMemory, Exception {
		IotTrackerResponse searchPath=iotTrackerService.searchPath(filePath);
		assertNotNull(searchPath);
		assertEquals("data refreshed", searchPath.getDescription());
	}
	
	@Test
	void searchPathNotFoundTest() throws IotDataNotFoundException, GpsNotReportedException, NoDataIsLoadedInMemory, Exception {	
		assertThrows(IotDataNotFoundException.class, ()->{
			iotTrackerService.searchPath(filePathNotFound);
		});
	}
	
	

}
