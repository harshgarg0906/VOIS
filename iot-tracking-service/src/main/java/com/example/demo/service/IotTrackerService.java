package com.example.demo.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.custom.exception.NoDataIsLoadedInMemory;
import com.example.demo.inter.service.IDataProcessingService;
import com.example.demo.inter.service.IDynamciActivityTracking;
import com.example.demo.inter.service.IIotTracerService;
import com.example.demo.model.CsvModalData;
import com.example.demo.model.FilePath;
import com.example.demo.model.IotTrackerResponse;
import com.example.demo.model.ReportDeviceDetails;
import com.example.demo.utility.CsvDataProcessor;


@Service
public class IotTrackerService implements IIotTracerService {
	
	List<CsvModalData> csvFilterData=new ArrayList<>();
	
	@Autowired
	IDataProcessingService iDataProcessingService;
	
	private final BeanFactory beanFactory;

    @Autowired
    public IotTrackerService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

	@Override
	public IotTrackerResponse searchPath(FilePath filePath) throws IotDataNotFoundException ,Exception {
		Optional<Boolean> checkFileExist = checkFileExist(filePath);
		if(checkFileExist.isPresent())
		{
			if(checkFileExist.get()==true)
			{ 
				List<String[]> csvData = CsvDataProcessor.readLineByLine(Paths.get(filePath.getFilePath()));
				csvFilterData=csvData.stream().filter((data)->{
					if(data.length!=0 && (data[0].contains("# Initial data file") || data[0].contains("DateTime")))
					{
						return false;
					}
					return true;
				})
				.map((data)->{
					 CsvModalData csvModalData=new CsvModalData();
					 csvModalData.setDataTime(data[0]);
					 csvModalData.setEventId(data[1]);
					 csvModalData.setProductId(data[2]);
					 csvModalData.setLatitude(data[3]);
					 csvModalData.setLongitide(data[4]);
					 csvModalData.setBattery(data[5]);
					 csvModalData.setLight(data[6]);
					 csvModalData.setAirplaneMode(data[7]);
					 return csvModalData;
				}).collect(Collectors.toList());
				return new IotTrackerResponse("data refreshed");
			}else
			{
				throw new IotDataNotFoundException("ERROR: no data file found");
			}
		}
		 throw new Exception("ERROR: A technical exception occurred");
	}

	Optional<Boolean> checkFileExist(FilePath filePath) {
		 Path path = Paths.get(filePath.getFilePath());
		 Boolean exists = Files.exists(path);
		 return Optional.ofNullable(exists);
	}
	
	 
	 @Override
	 public Boolean checkInMemoryDataPrsent()
	 {
		 return this.csvFilterData.size()==0 ? false:true;
	 }

	 @Override
	public ReportDeviceDetails reportDeviceDetails(String productId, Optional<String> timeStamp) throws 
	IotDataNotFoundException, GpsNotReportedException,NoDataIsLoadedInMemory,Exception {		
		Boolean isDataPrsent=checkInMemoryDataPrsent();
		if(isDataPrsent)
		{
			return iDataProcessingService.evaluateCsvData(productId, timeStamp,this.csvFilterData);
		}else
		{
			throw  new NoDataIsLoadedInMemory("Please Refresh the Data");
		}	
	}
	 
	@Override
	public ReportDeviceDetails dynamicActivityTracking(String productId, Optional<String> timeStamp)
				throws NoDataIsLoadedInMemory, IotDataNotFoundException, GpsNotReportedException, Exception {
		List<CsvModalData> filterByProductId=new ArrayList<>();
		Boolean isDataPrsent=checkInMemoryDataPrsent();
		if(isDataPrsent)
		{
			CsvModalData answer=null;
			filterByProductId=iDataProcessingService.reverseSortedFilterProductById(productId,this.csvFilterData);
			if(filterByProductId.size()>0)
			{ 
				String deviceType=iDataProcessingService.findTypeOfDevice(filterByProductId.get(0));
				IDynamciActivityTracking dynamciActivityTrackingService = beanFactory.getBean(deviceType, 
				          IDynamciActivityTracking.class);
				String status=dynamciActivityTrackingService.checkAndSetStatus(filterByProductId);
				answer = iDataProcessingService.filterDataOnTimeStamp(timeStamp, filterByProductId, answer);
				if(answer != null)
				{
					return iDataProcessingService.evaluateAirPlaneData(answer, status);
				}
			}
			else{
				throw new IotDataNotFoundException("ERROR: Id "+productId+" not found");
			}
		}
	
		throw  new NoDataIsLoadedInMemory("Please Refresh the Data");
	}
	
}
