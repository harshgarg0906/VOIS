package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.model.FilePath;
import com.example.demo.model.IotTrackerResponse;
import com.example.demo.model.ReportDeviceDetails;
import com.example.demo.service.IotTrackerService;

class TestIotTrackerController {
	
	@InjectMocks
	IotTrackerController iotTrackerController;
	
	@Mock
	IotTrackerService iotTrackerService;
	
	FilePath filePath;
	IotTrackerResponse iotTrackerResponse;
	ReportDeviceDetails reportDeviceDetails;

	@BeforeEach
	void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		
		filePath=new FilePath();
		filePath.setFilePath("E:\\\\Coding\\\\FilePath\\\\Data.csv");
		
		iotTrackerResponse=new IotTrackerResponse("data refreshed");
		reportDeviceDetails=new ReportDeviceDetails();
		reportDeviceDetails.setBattery("Full");
		reportDeviceDetails.setId("WG11155638");
		reportDeviceDetails.setName("CyclePlusTracker");
		reportDeviceDetails.setDateTime("25/02/2020  04:31:17");
		reportDeviceDetails.setLat("-0.1736");
		reportDeviceDetails.setLongValue("51.5185");
		reportDeviceDetails.setDescription("SUCCESS: Location identified.");
		
	}

	
	@Test
	void searchPathTest() throws IotDataNotFoundException, Exception {
		when(iotTrackerService.searchPath(filePath)).thenReturn(iotTrackerResponse);
		ResponseEntity<IotTrackerResponse> iotResponse=iotTrackerController.searchPath(filePath);
		assertNotNull(iotResponse);
		assertEquals("data refreshed", iotResponse.getBody().getDescription());
		assertEquals("200 OK", iotResponse.getStatusCode().toString());
	}
	
	@Test
	void reportDeviceDetails() throws IotDataNotFoundException, Exception {
		when(iotTrackerService.reportDeviceDetails("WG11155638",Optional.of("1582605077000"))).thenReturn(reportDeviceDetails);
		ResponseEntity<ReportDeviceDetails> iotResponse=iotTrackerController.reportDeviceDetails("WG11155638",Optional.of("1582605077000"));
		assertNotNull(iotResponse);
		assertEquals("SUCCESS: Location identified.", iotResponse.getBody().getDescription());
		assertEquals("200 OK", iotResponse.getStatusCode().toString());
	}

	@Test
	void dynamicActivityTracking() throws IotDataNotFoundException, Exception {
		when(iotTrackerService.
				dynamicActivityTracking("WG11155638",Optional.of("1582605077000"))).
		thenReturn(reportDeviceDetails);
		ResponseEntity<ReportDeviceDetails> iotResponse=iotTrackerController.
				dynamicActivityTracking("WG11155638",Optional.of("1582605077000"));
		assertNotNull(iotResponse);
		assertEquals("SUCCESS: Location identified.", iotResponse.getBody().getDescription());
		assertEquals("200 OK", iotResponse.getStatusCode().toString());
	}

}
