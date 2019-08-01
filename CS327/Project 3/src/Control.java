

public class Control extends Vertex{
	
	public int code;
	public int pointVal;
	public int openTime;
	public int closeTime;
	
	public Control(String id, int code, int pointVal, int openTime,
			int closeTime){
		super(id);
		this.code = code;
		this.pointVal = pointVal;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

}
