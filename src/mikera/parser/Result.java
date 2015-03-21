package mikera.parser;
import java.util.*;
import java.lang.ref.*;


public final class Result implements ResultSource {
	public Object object;
	public String string;
	public int position;
	public int length;
	public ResultSource nextSource;
	
	public Result(Object o, String s,int p, int l) {
		object=o;
		string=s;
		position=p;
		length=l;
	}
	
	public Result nextResult() {
		return this;
	}
	
	public Result getNext() {
		if (nextSource==null) return null;
		
		Result next=nextSource.nextResult();
		if (next!=null) {
			nextSource=next;
		}
		return next;
	}
	
	/**
	 * Gets a sub-result, assuming that the result object is a List
	 * 
	 * @param i Index of sub-result
	 * @return
	 */
	public Result getSubResult(int i) {
		if (!(object instanceof List)) {
			throw new Error("Result object is not a List");
		}
		List l=(List)object;
		return (Result)l.get(i);
	}
	
	public String matchedString() {
		return string.substring(position,position+length);
	}
	
	public String asString() {
		return (String)object;
	}
	
	public double asDouble() {
		return ((Double)object).doubleValue();
	}
	
	public int asInteger() {
		return ((Integer)object).intValue();
	}
}
