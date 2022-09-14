package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.inter.service.IDynamciActivityTracking;
import com.example.demo.model.CsvModalData;

@Service("CyclePlusTracker")
public class CycleDynamicActivityTracking implements IDynamciActivityTracking {

	@Override
	public String checkAndSetStatus(List<CsvModalData> filterByProductId) {
		if(filterByProductId.size()>2)
		{
			String response=checkCoOrdinates(filterByProductId);
			return response;
		} 		
		return "N/A";
	}

	private String checkCoOrdinates(List<CsvModalData> filterByProductId) {
		// TODO Auto-generated method stub
		List<CsvModalData> latestConsecutiveData=getLatestConseCutiveSetOfData(filterByProductId);
		Boolean isMatched=checkConsecutiveGpsDataAvailiable(latestConsecutiveData);
		String reponse=isMatched ? "ACTIVE":"INACTIVE";
		return reponse;
	}


	private List<CsvModalData> getLatestConseCutiveSetOfData(List<CsvModalData> filterByProductId) {
			if(filterByProductId.size()==3)
			{
				return filterByProductId;
			}
			List<CsvModalData> sortedProducts=filterByProductId.subList(0, 3);
		return sortedProducts;
	}
	
	  Boolean checkConsecutiveGpsDataAvailiable(List<CsvModalData>latestConsecutiveData) {
	    int count = 0;
	    for (int currentIndex = 1; currentIndex < latestConsecutiveData.size(); currentIndex++) {
	        if (latestConsecutiveData.get(currentIndex).getLatitude().equals
	        		(latestConsecutiveData.get(currentIndex-1).getLatitude())
	        		&&
	        		latestConsecutiveData.get(currentIndex).getLongitide().equals
	        		(latestConsecutiveData.get(currentIndex-1).getLongitide())
	        		) {
	            count++;
	            if (count >= latestConsecutiveData.size()-1) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
}
