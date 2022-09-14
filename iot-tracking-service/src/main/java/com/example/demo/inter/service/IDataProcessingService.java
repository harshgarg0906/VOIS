package com.example.demo.inter.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.model.CsvModalData;
import com.example.demo.model.ReportDeviceDetails;

public interface IDataProcessingService {
	
	List<CsvModalData> reverseSortedFilterProductById(String productId,List<CsvModalData> csvFilterData);
	CsvModalData findTheNearestTimeStampProduct(String timeStamp, List<CsvModalData> filterByProductId,
				CsvModalData answer) throws Exception ;
	String findTypeOfDevice(CsvModalData csvModalData);
	ReportDeviceDetails setDataAccordingToAirPlaneMode(CsvModalData csvModalData,
			String ...status) throws GpsNotReportedException,Exception;
	CsvModalData filterDataOnTimeStamp(Optional<String> timeStamp, List<CsvModalData> filterByProductId,
				CsvModalData answer) throws Exception;
	ReportDeviceDetails evaluateCsvData(String productId, Optional<String> timeStamp, List<CsvModalData> csvFilterData)
			throws  GpsNotReportedException, IotDataNotFoundException ,Exception;
	ReportDeviceDetails evaluateAirPlaneData(CsvModalData answer,String ...status) throws GpsNotReportedException, Exception;
	 
}
