package com.koreaIT.BAM.controller;

import java.util.Scanner;

public abstract class Controller {
	
	public Scanner sc;
	public int lastId;
	public String cmd;
	
	public abstract void doAction(String cmd, String methodName);
	public abstract void makeTestData();
}
