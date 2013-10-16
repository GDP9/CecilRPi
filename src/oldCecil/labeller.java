package oldCecil;

public class labeller 
{

	/**
	 * @param args
	 */
	static int		labelTableSize = 100;
	static String[]	labelTable = new String[labelTableSize];
	static int[]	locations = new int[labelTableSize];
	static int		noOfLocs;
	
	public static void main(String[] args) 
	{	// TODO Auto-generated method stub
	}

	public static void initialise()
	{	noOfLocs=0;
	}
	
	public static boolean notInTable(String label)
	{   boolean val;
		int i;
		val=true;
		
		if(noOfLocs!=0)
		{for(i=1;i<=noOfLocs;i++)
			{if(label.compareTo(labelTable[i])==0) val=false;
			}
		}
		return val;
	}
	
	public static int findLabel(String label)
	{   int val;
		int i;
		val=-1;
		
		if(noOfLocs!=0)
		{for(i=1;i<=noOfLocs;i++)
			{
				if(labelTable[i].compareTo(label)==0) val=i;
			}
		}
		return val;
	}
	
	public static boolean addLabel(String newLabel, int location)
	//The following code checks that newLabel is indeed new, then adds it to labelTable
	{	boolean	ok;
		int		t;
		
		ok = false;
	    //If the label is a number, then ignore progpos and store in the literal number
	     
		/*Val(mylabel, temp, errcode);
	     if errcode=0 then location := temp;   TO SORT OUT NEXT TIME!*/
		//terminal.addLine("label found: "+newLabel+" loc: "+Integer.toString(location));
		if(notInTable(newLabel))
		{	noOfLocs++;
			labelTable[noOfLocs]=newLabel;
			locations[noOfLocs]=location;
			terminal.addLine("Label assigned: >"+newLabel+"<, loc: "+Integer.toString(location));
			ok=true;
		}
		else
		{
			terminal.addLine("Found: "+newLabel+" second time");
			t=findLabel(newLabel);
			if(locations[t]!=location) CECILcompiler.errMess("Label <"+newLabel+"> declared twice!");
			else ok=true;
		}
		return (ok);
	}
	
	public static int getAddress(String label)
	{
		int		location;
		
		location = findLabel(label);
		if(location < 0) 
		{	CECILcompiler.errMess("Label <"+label+"> not found!");
			CECILcompiler.labelsNotFound = true;
		}
		else location=locations[location];
		return (location);
	}
	
	public static void printLabelTable()
	{
		int i;
		terminal.addLine("Labels:\n");
		for(i=1;i<=noOfLocs;i++)
		{
			terminal.addLine("label: "+labelTable[i]+", loc: "+String.valueOf(locations[i]));
		}
		return;
	}
}
