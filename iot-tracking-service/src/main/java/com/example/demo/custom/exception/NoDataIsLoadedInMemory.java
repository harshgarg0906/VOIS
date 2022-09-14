package com.example.demo.custom.exception;

public class NoDataIsLoadedInMemory  extends Exception{

	private static final long serialVersionUID = 1L;
	public NoDataIsLoadedInMemory(String message)
	{
		super(message);
	}

}
