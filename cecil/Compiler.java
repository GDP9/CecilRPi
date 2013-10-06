import javax.swing.JTextArea;

/**
 * 
 */

/**
 * @author 	David
 * @date	21.03.06
 * @version	0.1
 */
public class Compiler 
{
	/**
	 * @param args
	 */
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
	}

	public static void compile(JTextArea source)
	{
		terminal.addStars();
		terminal.add("Starting compiler...\n");
		SIM30.writeToMemory(1, 0);
		SIM30.writeToMemory(7, 1);
		SIM30.writeToMemory(4, 2);
		SIM30.writeToMemory(8, 3);
		SIM30.writeToMemory(2, 4);
		SIM30.writeToMemory(9, 5);
		SIM30.writeToMemory(0, 6);
		SIM30.writeToMemory(21, 7);
		SIM30.writeToMemory(3, 8);
			
	}
}
