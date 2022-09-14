package com.example.demo.custom.exception;

public class GpsNotReportedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public GpsNotReportedException(String message)
	{
		super(message);
	}
}
