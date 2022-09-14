package com.example.demo.inter.service;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.custom.exception.NoDataIsLoadedInMemory;
import com.example.demo.model.FilePath;
import com.example.demo.model.IotTrackerResponse;
import com.example.demo.model.ReportDeviceDetails;

public interface IIotTracerService {

	IotTrackerResponse searchPath(FilePath filePath) throws IotDataNotFoundException,Exception ;

	Boolean checkInMemoryDataPrsent();

	ReportDeviceDetails reportDeviceDetails(String productId, 
			Optional<String> timeStamp) throws IotDataNotFoundException , GpsNotReportedException,Exception;
	
	ReportDeviceDetails dynamicActivityTracking(String productId,
			Optional<String> timeStamp) throws NoDataIsLoadedInMemory, IotDataNotFoundException, GpsNotReportedException
	,Exception;
}
