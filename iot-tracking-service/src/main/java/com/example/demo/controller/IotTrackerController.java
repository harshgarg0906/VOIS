package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.custom.exception.NoDataIsLoadedInMemory;
import com.example.demo.inter.service.IIotTracerService;
import com.example.demo.model.FilePath;
import com.example.demo.model.IotTrackerResponse;
import com.example.demo.model.ReportDeviceDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "this controller is used for tracking the details of the devices whose data is present in the system")
public class IotTrackerController {
		@Autowired
	IIotTracerService iotTrackerService;
	

	@PostMapping("/iot/event/v1")
	@ApiOperation("This is used to check whether data is availiable on that path or not")
	public ResponseEntity<IotTrackerResponse> searchPath(@ApiParam("Here we will give the path")@RequestBody FilePath filePath) throws IotDataNotFoundException,Exception
	{
		IotTrackerResponse searchPath = iotTrackerService.searchPath(filePath);
		return ResponseEntity.status(HttpStatus.OK).body(searchPath);
	}
	
	@GetMapping("/iot/event/v1")
	@ApiOperation("This is used to check Report device details and location")
	public ResponseEntity<ReportDeviceDetails> reportDeviceDetails(@RequestParam("ProductId") String productId,
			@RequestParam("tstmp") Optional<String> timeStamp) throws NoDataIsLoadedInMemory, IotDataNotFoundException, GpsNotReportedException,Exception
	{
		ReportDeviceDetails reportDeviceDetails =iotTrackerService.reportDeviceDetails(productId,timeStamp);
		return ResponseEntity.status(HttpStatus.OK).body(reportDeviceDetails);
	}
	
	@GetMapping("/iot/event/v2")
	@ApiOperation("This is used for Dynamic activity-tracking")
	public ResponseEntity<ReportDeviceDetails> dynamicActivityTracking(@RequestParam("ProductId") String productId,
			@RequestParam("tstmp") Optional<String> timeStamp) throws NoDataIsLoadedInMemory, IotDataNotFoundException, GpsNotReportedException,Exception
	{
		ReportDeviceDetails reportDeviceDetails =iotTrackerService.dynamicActivityTracking(productId,timeStamp);
		return ResponseEntity.status(HttpStatus.OK).body(reportDeviceDetails);
	}
}
