package com.example.demo.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.custom.exception.GpsNotReportedException;
import com.example.demo.custom.exception.IotDataNotFoundException;
import com.example.demo.custom.exception.NoDataIsLoadedInMemory;
import com.example.demo.exception.model.IotTrackerExceptionHandlerModel;

@ControllerAdvice
public class IotTrackerExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<IotTrackerExceptionHandlerModel> iotExceptionHandler(IotDataNotFoundException exception)
	{
		IotTrackerExceptionHandlerModel iotTrackerExceptionHandlerModel=new IotTrackerExceptionHandlerModel();
		iotTrackerExceptionHandlerModel.setDescription(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(iotTrackerExceptionHandlerModel);
	}
	
	@ExceptionHandler
	public ResponseEntity<IotTrackerExceptionHandlerModel> exceptionHandler(NoDataIsLoadedInMemory exception)
	{
		IotTrackerExceptionHandlerModel iotTrackerExceptionHandlerModel=new IotTrackerExceptionHandlerModel();
		iotTrackerExceptionHandlerModel.setDescription(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(iotTrackerExceptionHandlerModel);
	}
	
	@ExceptionHandler
	public ResponseEntity<IotTrackerExceptionHandlerModel> exceptionHandler(GpsNotReportedException exception)
	{
		IotTrackerExceptionHandlerModel iotTrackerExceptionHandlerModel=new IotTrackerExceptionHandlerModel();
		iotTrackerExceptionHandlerModel.setDescription(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iotTrackerExceptionHandlerModel);
	}
	
	@ExceptionHandler
	public ResponseEntity<IotTrackerExceptionHandlerModel> exceptionHandler(Exception exception)
	{
		IotTrackerExceptionHandlerModel iotTrackerExceptionHandlerModel=new IotTrackerExceptionHandlerModel();
		iotTrackerExceptionHandlerModel.setDescription(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(iotTrackerExceptionHandlerModel);
	}
}
