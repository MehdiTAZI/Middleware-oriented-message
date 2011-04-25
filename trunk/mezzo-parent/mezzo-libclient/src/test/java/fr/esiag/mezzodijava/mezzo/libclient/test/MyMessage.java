package fr.esiag.mezzodijava.mezzo.libclient.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyMessage implements Serializable {
    /**
     * 
     */
    public List<String> myList;
    public String myString;
    public int myInt;
    public int[] myArray;

    public MyMessage(String myString) {
        this.myString = myString;
        myArray = new int[4];
        myArray[0] = 1;
        myArray[1] = 2;
        myArray[2] = 3;
        myArray[3] = 4;
        myInt = 777;
        myList = new ArrayList<String>();
        myList.add("un");
        myList.add("deux");
        myList.add("trois");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(myArray);
        result = prime * result + myInt;
        result = prime * result
    	    + ((myList == null) ? 0 : myList.hashCode());
        result = prime * result
    	    + ((myString == null) ? 0 : myString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
    	return true;
        if (obj == null)
    	return false;
        if (getClass() != obj.getClass())
    	return false;
        MyMessage other = (MyMessage) obj;
        if (!Arrays.equals(myArray, other.myArray))
    	return false;
        if (myInt != other.myInt)
    	return false;
	if (myList == null) {
	    if (other.myList != null)
		return false;
	} else if (!myList.equals(other.myList))
	    return false;
        if (myString == null) {
    	if (other.myString != null)
    	    return false;
        } else if (!myString.equals(other.myString))
    	return false;
        return true;
    }
  
    
}