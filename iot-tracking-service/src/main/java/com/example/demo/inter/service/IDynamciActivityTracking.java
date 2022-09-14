package com.example.demo.inter.service;

import java.util.List;

import com.example.demo.model.CsvModalData;

public interface IDynamciActivityTracking {
	
	public String checkAndSetStatus(List<CsvModalData> filterByProductId);

}
