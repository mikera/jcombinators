package mikera.parser;


public final class ResultList implements ResultSource {
	private Parser parser=null;
	private Object data=null;
	private Result first=null;
	private Result next=null;
	private boolean end=false;
	private String string=null;
	private int position=0;
	
	public ResultList(Parser p, String s, int pos) {
		parser=p;
		string=s;
		position=pos;
		end=false;
	}
	
	public Result first() {
		if (first==null) {
			ensureNext();
			next=null;
		}
		return first;
	}
	
	public int count() {
		int i=0;
		Result r=first();
		while (r!=null) {
			i++;
			r=r.getNext();
		}
		return i;
	}
	
	private boolean hasNext() {
		return (next!=null)||(end==false);
	}
	
	/** 
	 * Ensures we have a next element if available
	 */
	private void ensureNext() {
		if (!hasNext()) return;
		next=parser.parseNew(string, position, this);
		if (next!=null) {
			parser.action(next);
			next.nextSource=this;
		} else {
			end=true;
		}
		if (first==null) first=next;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object d) {
		data=d;
	}
	
	/**
	 * ResultSource interface
	 */
	public Result nextResult() {
		return next();
	}
	
	private Result next() {
		if (end) return null;
		if (next==null) ensureNext();
		if (next==null) return null;
		Result r=next;
		next=null;
		return r;
	}
}
