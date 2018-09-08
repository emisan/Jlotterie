package jlotto.factory;

import java.util.Arrays;
import java.util.Random;

public class JLottoFactory {
	
	private int[] choosenNumbers;
	private Random numberGenerator;
	
	/**
	 * Creates a new instance of JLottoCalculator with a Random Object
	 */
	public JLottoFactory() {
		this.numberGenerator = new Random();
	}
	
	/**
	 * Generates a random array of numbers from 0 to 49
	 * without no duplication of the numbers
	 * <p></p>
	 * @return a integer array of six numbers from 0 to 49
	 */
	public int[] generateNumbers(int amount) {
		return this.sortChoosing(this.choosenNumbers = this.fillNumbers(amount));
	}
	
	/*
	 * Fills an integer array with randomly	
	 * generated amount of integers values
	 *
	 * @param amount the size how many numbers should be generated
	 * @return an integer array with randomly generated integer values
	 *         with no duplicated numbers
	 */
	private int[] fillNumbers(int amount) {
		int i = 0, j = 0;
		int[] arr = new int[amount];
		
		while(i < arr.length) {
			arr[i] = this.generateNumber();
			i++;
		}
		while(j < arr.length) {
			if(this.numberExistsTwice(arr, arr[j])) {
				arr[j] = this.generateNumber();
				j--;
			}
			j++;
		}
		
		return arr;
	}
	
	/*
	 * Runs through an integer array and 
	 * checks if a number is existing twice.
	 * 
	 * If the internal integer control flag <b>twice</b> is larger than 2,
	 * the number is twice in the array.
	 *
	 * @param arr the integer array
	 * @param num the integer number
	 * @return boolean result if a number is twice detected in the 
	 *         array or not
	 */
	public boolean numberExistsTwice(int[] arr, int num) {
		boolean check = false;
		int i = 0, twice = 0;
		while(i < arr.length) {
			/* check for duplication */
			if (twice < 2) {
				if (arr[i] == num) {
					twice++;
				}
			}
			if(twice == 2) {
				/* break condition of while-loop */
				i = arr.length;
				/* number exists twice, otherwise,
				 * state twice == 2 never reaches, then number is not twice in array
				 * --> check = start initialization = false*/
				check = true;
			}
			i++;
		}
		return check;
	}
	
	/*
	 * Generates a single random integer from 1 to 49
	 * 
	 * @return an integer number from the range of 1 to 49 
	 */
	private int generateNumber() {
		return this.generateNumber(this.numberGenerator.nextInt(49));
	}
	// recursion
	private int generateNumber(int num) {
		return num != 0 ? num : this.generateNumber();
	}

	/**
	 * Generates a single number of the range of the numbers 1 to 9
	 * <p></p>
	 * @return a number from the range of the numbers 1 to 9
	 */
	public int generateSpecialNumber() {
		return this.generateSpecialNumber(this.numberGenerator.nextInt(9));
	}
	// recursion
	private int generateSpecialNumber(int num) {
		return num != 0 ? num : this.generateSpecialNumber();
	}
	
	/** 
     * The BubbleSort algorithm for setting selected or generated lottery numbers in order
     * <p></p>
     * @param array the array of integer digits (chosen lottery numbers)
     * @return an integer array of sorted integer digits (chosen lottery numbers)
     */
    public int[] sortChoosing(int[] array) {
    	Arrays.sort(array);
//        for(int i = 0; i < array.length; i++) {
//            for(int j = 0; j < array.length - 1; j++) {
//                /* swapping mechanism ( bubbling ) */
//                if(array[j] > array[j + 1]) {
//                    int temp = array[j];
//                    array[j] = array[j + 1];
//                    array[j + 1] = temp;
//                }
//            }
//        }
        return array;
    }
    
    /** 
     * The BubbleSort algorithm for sorting a String array
     * <p></p>
     * @param array the string array to be sorted
     * @return a sorted String array
     */
    public String[] sort(String[] array) {
    	Arrays.sort(array);
//        for(int i = 0; i < array.length; i++) {
//            for(int j = 0; j < array.length - 1; j++) {
//                /* swapping mechanism ( bubbling ) */
//                if(array[j].compareTo(array[j + 1]) < 0) {
//                    String temp = array[j];
//                    array[j] = array[j + 1];
//                    array[j + 1] = temp;
//                }
//            }
//        }
        return array;
    }
	
}