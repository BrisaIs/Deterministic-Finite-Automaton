package code;
import java.util.*;

class Transition
{
	ArrayList <String> originState = new ArrayList <String>();
	ArrayList <String> keys = new ArrayList <String>();
	ArrayList <String> destinyState = new ArrayList <String>();

	public Transition()
	{

	}

	public void addTransition(String os, String k, String ds)
	{
		this.originState.add(os);
		this.keys.add(k);
		this.destinyState.add(ds);
	}

	public String getOriginState(int i)
	{
		return originState.get(i);
	}

	public String getKey(int i)
	{
		return keys.get(i);
	}

	public String getDestinyState(int i)
	{
		return destinyState.get(i);
	}

	public int getLength()
	{
		return this.keys.size();
	}
}
