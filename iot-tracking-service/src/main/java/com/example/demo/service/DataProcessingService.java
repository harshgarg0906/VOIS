package com.example.demo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.inter.service.IDataProcessingService;
import com.example.demo.model.CsvModalData;
import com.example.demo.model.ReportDeviceDetails;
import com.example.demo.utility.CsvDataReader;

@Service
public class DataProcessingService implements IDataProcessingService {

	@Override
	public List<CsvModalData> reverseSortedFilterProductById(String productId, List<CsvModalData> csvFilterData) {
		return csvFilterData.stream()
				.filter((data)->data.getProductId().equals(productId))
				.sorted(Comparator.comparing(CsvModalData::getDataTime).reversed()).
				collect(Collectors.toList());
	}
	@Override
	public CsvModalData findTheNearestTimeStampProduct(String timeStamp, List<CsvModalData> filterByProductId,
			CsvModalData answer) throws Exception {
		for(CsvModalData csvModaData:filterByProductId)
		{
			if(Long.parseLong(csvModaData.getDataTime())-Long.parseLong(timeStamp)<0)
			{
				answer=csvModaData;
				break;
			}
		}	
		return answer;
	}

	@Override
	public String findTypeOfDevice(CsvModalData csvModalData) {
		// TODO Auto-generated method stub
		return csvModalData.getProductId().startsWith("WG")?"CyclePlusTracker":"GeneralTracker";
	}


	@Override
	public ReportDeviceDetails setDataAccordingToAirPlaneMode(CsvModalData csvModalData,String ...status)
			throws GpsNotReportedException, Exception {
		ReportDeviceDetails reportDeviceDetails=new ReportDeviceDetails();
		String productName=findTypeOfDevice(csvModalData);
		String batteryStatus=CsvDataReader.checkBatteryStatus(Float.parseFloat(csvModalData.getBattery()) * 100);
		Boolean isAirPlaneModeOn=csvModalData.getAirplaneMode().equalsIgnoreCase("ON")?true:false;
		if(isAirPlaneModeOn)
		{
			extractedGpsRelatedDetails(reportDeviceDetails,"SUCCESS: Location not available:"
					+ " Plase turn off aiplane mode","INACTIVE","","");
		}else
		{
			checkForLongitudeAndLatitiude(csvModalData,reportDeviceDetails,status);
		}
		reportDeviceDetails.setId(csvModalData.getProductId());
		reportDeviceDetails.setName(productName);
		reportDeviceDetails.setDateTime(CsvDataReader.getCurrentEpochTimeStamp(csvModalData.getDataTime()));
		reportDeviceDetails.setBattery(batteryStatus);
		return reportDeviceDetails;
	}
	
	
	@Override
	public CsvModalData filterDataOnTimeStamp(Optional<String> timeStamp, List<CsvModalData> filterByProductId,
			CsvModalData answer) throws Exception {
		String timeStampToBeUsed=timeStamp.isPresent()?timeStamp.get():CsvDataReader.getUtcTimeStamp();
		Optional<CsvModalData>datachecked=filterByProductId.stream().
				filter((data)->data.getDataTime().equals(timeStampToBeUsed)).findFirst();
		answer=datachecked.isPresent()?datachecked.get():findTheNearestTimeStampProduct(timeStampToBeUsed, filterByProductId, answer);
		return answer;
	}

	@Override
	public ReportDeviceDetails evaluateCsvData(String productId, Optional<String> timeStamp,
			List<CsvModalData> csvFilterData) throws GpsNotReportedException, IotDataNotFoundException, Exception {
		List<CsvModalData> filterByProductId;
		CsvModalData answer=null;
		filterByProductId=reverseSortedFilterProductById(productId,csvFilterData);
		answer = filterDataOnTimeStamp(timeStamp, filterByProductId, answer);
		if(answer != null)
		{
			return evaluateAirPlaneData(answer);
		}else
		{
			throw new IotDataNotFoundException("ERROR: Id "+productId+" not found");
		}
	}

	@Override
	public ReportDeviceDetails evaluateAirPlaneData(CsvModalData answer,String ...status) throws GpsNotReportedException, Exception {
		ReportDeviceDetails setDataAccordingToAirPlaneMode = 
				setDataAccordingToAirPlaneMode(answer,status);
		return setDataAccordingToAirPlaneMode;
	}


	private void checkForLongitudeAndLatitiude(CsvModalData csvModalData,
			ReportDeviceDetails reportDeviceDetails, String ...status) throws GpsNotReportedException {
		if(status.length==0 || status[0]==null)
		{
			boolean isGpsDataAvailiable=checkGpsDataAvailaible(csvModalData);
			if(!isGpsDataAvailiable)
			{
				throw new GpsNotReportedException("ERROR: Device could not be located");
			}
			extractedGpsRelatedDetails(reportDeviceDetails,"SUCCESS: Location "
					+ "identified","ACTIVE",csvModalData.getLatitude(),csvModalData.getLongitide());
		}else
		{
			extractedGpsRelatedDetails(reportDeviceDetails,"SUCCESS: Location "
					+ "identified",status[0],csvModalData.getLatitude(),csvModalData.getLongitide());
		}
	}
	
	private void extractedGpsRelatedDetails(ReportDeviceDetails reportDeviceDetails,
			String description,String status,String latitide,String longitude) {
		reportDeviceDetails.setDescription(description);
		reportDeviceDetails.setLat(latitide);
		reportDeviceDetails.setLongValue(longitude);
		reportDeviceDetails.setSatus(status);
	}

	private Boolean checkGpsDataAvailaible(CsvModalData csvModalData) {
	
		return !(csvModalData.getLatitude().isEmpty() && csvModalData.getLongitide().isEmpty());
	}

	
}
