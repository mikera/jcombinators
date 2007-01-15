package mikera.parser;



public class Whitespace extends Parser {
	public Whitespace() {

	}
	
	public Result parseNew(String s, int pos, ResultList rl) {
		Integer num=(Integer)(rl.getData());
		
		int left=s.length()-pos;
		if (num==null) num=new Integer(0);
		
		for (int i=num.intValue(); i<left; i++) {
			char c=s.charAt(pos+i);
			if (Character.isWhitespace(c)) {
				Result r= new Result(null,s,pos,i+1);
				rl.setData(new Integer(i+1));
				return r;
			} else {
				break;
			}
		}
		rl.setData(new Integer(s.length()+1));
		return null;
		
	}
}
