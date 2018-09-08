package jlotto.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jlotto.factory.JLottoFactory;

public class ReadWriteUtils {
	
	private String readContent;
	
	private BufferedReader br;
	private BufferedWriter bw;
	
	private ArrayList<String> list;
	private JLottoFactory jfl;
	
	/**
	 * Creates a new instance of ReadWriteUtils
	 */
	public ReadWriteUtils() {
		
		this.readContent = "";
		this.list = new ArrayList<String>();
		this.jfl = new JLottoFactory();
	}
	
	
	/**
	 * Clears the content of a File object not of a directory
	 * <p></p>
	 * @param file the File which content has to be deleted ( cleared )
	 */
	public void clearFileContent(File file) {
		if (file.isFile()) {
			file = new File(file.getName());
			this.writeToFile(file, "", false);
		}
	}
	
	/**
	 * Removes ( clears ) all elements of an ArrayList
	 * <p></p>
	 * @param list the ArrayList to be cleared
	 */
	public void clearList(ArrayList<String> list) {
		list.removeAll(list);
	}
	
	/*
	 * Copies elements of an ArrayList into a String array
	 * 
	 * @param list the ArrayList of generic type String
	 * @param sArr the String array
	 * @throws ArrayIndexOutOfBoundsException if the String
	 *         array is smaller in length as the ArrayList
	 */
	private void copy(ArrayList<String> list, String[] sArr) {
		try {
			for (int i = 0; i < list.size(); i++) {
				sArr[i] = list.get(i);
			}
		}
		catch(ArrayIndexOutOfBoundsException offset) {
		}
	}
	
	/**
	 * Removes duplicated entries form a File objects content 
	 * ( not from a directory )
	 * <p></p>
	 * @param file the File object which content should 
	 *             be removed from duplicated content
	 */
	public void removeDuplicateContent(File file) {
		
		/* copy tokens into elements-array */
		ArrayList<String> elements = new ArrayList<String>();
		
		this.readFromTo(file, this.getList());
		
		/* sort the content */
		elements = this.getSortedList(this.getList());
		
		System.out.println("Aufruf aus removeDup");
		this.showList(elements);
		
		/* remove duplications in ArrayList */
		elements = this.removeDuplicateContent(elements);
		
		this.trimList(elements);
		
		/* rewrite it with the new one */
		this.writeToFile(file, elements, false);
		
		this.showList(elements);
	}
	
	/** 
	 * Reads the content of a File object, not of a directory
	 * <p></p>
	 * @param file the File which content has to be read
	 */
	public void readFrom(File file) {
		try {
			if (file.isFile()) {
				this.br = new BufferedReader(new FileReader(file));
				String s = "";
				while ((s = this.br.readLine()) != null) {
					this.readContent += s + "\n";
				}
			}
			this.br.close();
		}
		catch(IOException ioexec) {
		}
	}
	
	/**
	 * Reads the content of a File object (not a directory) 
	 * and copies it to an ArrayList
	 * <p></p>
	 * @param file the File which content has to be read
	 * @param list the ArrayList to contain the File content
	 */
	public void readFromTo(File file, ArrayList<String> list) {
		this.clearList(list);
		/* read file content and add it to the list */
		try {
			if (file.isFile()) {
				this.br = new BufferedReader(new FileReader(file));
				String s = "";
				while ((s = this.br.readLine()) != null) {
					list.add(s);
				}
			}
			this.br.close();
		}
		catch(IOException ioexec) {
		}
	}
	
	/**
	 * Copies the elements of an ArrayList into another ArrayList
	 * <br></br>
	 * @param firstList the list which holds the data to be copied
	 * @param secondList the list to hold the copied elements
	 */
	public void copy(ArrayList<String> firstList, ArrayList<String> secondList) {
		int i = 0;
		while(i < firstList.size()) {
			secondList.add(firstList.get(i));
			i++;
		}
	}
	
	private void showList(ArrayList<String> list) {
		/* show new content */
		System.out.println("Actual list content :");
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
	}
	
	/**
	 * Removes empty elements added into an ArrayList
	 * <p></p>
	 * @param list the ArrayList to trim
	 */
	public void trimList(ArrayList<String> list) {
		ArrayList<String> trimList = new ArrayList<String>();
		int i = 0;
		while(i < list.size()) {
			if(!list.get(i).equals("")) {
				trimList.add(list.get(i));
			}
			i++;
		}
		list = new ArrayList<String>();
		this.copy(trimList, list);
	}
	
	/**
	 * Writes the content of an ArrayList into a File object 
	 * ( not a directory ).
	 * <p></p>
	 * @param file the file where to write the content
	 * @param list the content of the generic ArrayList of Strings
	 * @param append if <code><b>true</b></code> this adds new content 
	 *        at the end of file content, otherwise 
	 *        <code><b>false</b> does not
	 */
	public void writeToFile(File file, ArrayList<String> list, boolean append) {
		try {
			this.readContent = "";
			this.bw = new BufferedWriter(new FileWriter(file, append));
			int i = 0;
			while(i < list.size()) {
				this.bw.write(list.get(i) + "\n");
				i++;
			}
			this.bw.close();
		}
		catch(IOException ioexec) {
		}
	}
	
	/**
	 * Writes a content to a file object (not a directory) and <br></br>
	 * defines if the content should be append or not at the end of <br></br>
	 * the files content
	 * <p></p>
	 * @param file the file where to write the content
	 * @param content the new content of the file object
	 * @param append true adds new content at the end of file content,
	 *        otherwise false will not
	 */
	public void writeToFile(File file, String content, boolean append) {
		try {
			this.readContent = "";
			this.bw = new BufferedWriter(new FileWriter(file, append));
			this.bw.write(content + "\n");
			this.bw.close();
		}
		catch(IOException ioexec) {
		}
	}
	
	/**
	 * Copies elements of a String array into an ArrayList
	 * <p></p>
	 * @param sArr the String array
	 * @param list an ArrayList of the generic type String
	 * @return an ArrayList with the elements of the string array
	 */
	public ArrayList<String> getCopy(String[] sArr, ArrayList<String> list) {
		list = new ArrayList<String>();
		for(int i = 0; i < sArr.length; i++) {
			list.add(sArr[i]);
		}
		return list;
	}
	
	/**
	 * Returns the ArrayList used of ReadWriteUtils
	 * <p></p>
	 * @return internal ArrayList - list
	 */
	public ArrayList<String> getList() {
		return this.list;
	}
	
	/*
	 * Returns a sorted ArrayList
	 * 
	 * @param list the Arraylist to sort
	 * @return a sorted ArrayList
	 */
	private ArrayList<String> getSortedList(ArrayList<String> list) {
		
		/* create new String array */
		String[] sList = new String[list.size()];
		/* copy list in string array */
	    this.copy(list, sList);
	    /* sort string array */
	    sList = this.jfl.sort(sList);
	    /* put sorted string array elements into the list */
	    return this.getCopy(sList, list);
	}
	
	/**
	 * Checks if the array list has duplicated elements 
	 * and removes them if duplication exists
	 * <p></p>
	 * @param list the ArrayList containing strings ( elements )
	 * @return a new ArrayList containing no duplicated elements
	 */
	public ArrayList<String> removeDuplicateContent(ArrayList<String> list) {
		
		int i = 0;
		int j = 1;
		while(i < list.size()) {
			while(j < list.size()) {
				if(list.get(i).equals(list.get(j))) {
					list.remove(j);
				}
				j++;
			}
			j = 1;
			i++;
		}
		return list;
	}
	
	/**
	 * Returns the content of a file object ( not a directory )
	 * <p></p>
	 * @return an internal string text representing of the file content
	 */
	public String getFileContent() {
		return this.readContent;
	}
	
	/**
	 * Returns the content of a File object as a string text
	 * <p></p>
	 * @param file the File should be not a directory
	 * @return a string presentation of the file content
	 */
	public String getContent(File file) {
		
		this.readFrom(file);
		return this.getFileContent();
	}
	
}
