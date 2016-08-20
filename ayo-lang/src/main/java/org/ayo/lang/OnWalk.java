package org.ayo.lang;

public interface OnWalk<T> {

	/**
	 * 
	 * @param index
	 * @param t  current element
	 * @param total list.size()
	 */
	boolean process(int index, T t, int total);
	
}
