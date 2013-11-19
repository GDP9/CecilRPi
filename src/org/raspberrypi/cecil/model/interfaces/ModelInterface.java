package org.raspberrypi.cecil.model.interfaces;

import java.io.File;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;
import org.raspberrypi.cecil.pojo.Instruction;
import org.raspberrypi.cecil.pojo.Program;

/**
 * CECIL Application ModelInterface
 * Implemented by Model.java
 * Contains methods to be called from Model.java by the Controller
 * 
 * The MIT License (MIT)
 * Copyright (c) 2013 Southampton University group GDP9
 * 
 * @authors Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10)
 * Southampton University, United Kingdom
 * @version 1.2
 * 
 * @date 14/11/2013
 * 
 */
public interface ModelInterface {
	/*
	 * Calls to Compiler and Runner
	 */
	public void compile(Program program); 
	public void run(); 
	public int stepThrough();
	
	/*
	 * Calls to get Memory and View Output 
	 */
	public ErrorOutputStream getErrorStream();
	public StandardOutputStream getStdStream();
	public void setErrorStream(ErrorOutputStream stream);
	
	public ArrayList<Instruction> getInstructions();
	public void setViewToDefault();
	
	public int instructionToMnemonic(String name);
	public boolean isBinaryInstruction(String name);
	public boolean isInstruction(String name);
	
	public void updateLine();
	public boolean isProgramTerminated();
	/*
	 * Calls on input editor converter
	 */
	public File programToFile(Program program, String fileName);
	public Program fileToProgram(File fileName);
}