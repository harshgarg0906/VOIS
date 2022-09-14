package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.inter.service.IDataProcessingService;
import com.example.demo.inter.service.IDynamciActivityTracking;
import com.example.demo.model.CsvModalData;

@Service("GeneralTracker")
public class GeneralDynaicActivityTracking implements IDynamciActivityTracking {
	

	@Override
	public String checkAndSetStatus(List<CsvModalData> filterByProductId) {
		return null;
	}

}
