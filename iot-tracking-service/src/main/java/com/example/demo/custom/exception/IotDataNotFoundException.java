package com.example.demo.custom.exception;

public class IotDataNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IotDataNotFoundException(String message)
	{
		super(message);
	}

}
