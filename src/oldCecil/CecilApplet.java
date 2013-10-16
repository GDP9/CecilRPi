package oldCecil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CecilApplet extends JApplet implements ActionListener
{
	/*The next three are parameters passed in to the applet from the browser*/
	String		mc_id, user, msg;
	int 		startAddress=0;
	/*Now we've got lots of formatting stuff*/
	Font 		headerFont=new Font("SansSerif",Font.ITALIC, 18);
	Font		opFont=new Font("Monospaced", Font.PLAIN, 14);
	Font		bodyFont=new Font("SansSerif", Font.PLAIN, 14);
	Color		appColour=Color.GRAY;
	Color		textColour=Color.lightGray;
	Color		offColour=Color.blue;
	Color		onColour=Color.YELLOW;
	/*editor objects*/
	JTextArea 	sourceCode=editor.create();
	JButton 	compCompile=new JButton("Compile");
	JButton		compClear=new JButton("Clear");
	JButton 	compHelp=new JButton("Help");
	/*terminal objects*/
	/*"termWindow" comes from the terminal object, the rest are declared here*/
	JTextArea	termWindow=terminal.create();
	JButton 	opRun=new JButton("Run");
	JButton 	opHalt=new JButton("Halt");
	JButton 	opClear=new JButton("Clear");
	JButton 	opHelp=new JButton("Help");
	JButton 	opMemDump=new JButton("MemDump");
	/* Text fields */
	JLabel 		startLabel=new JLabel("from:");
	JTextField 	MDstart=new JTextField("0", 4);
	JLabel 		endLabel=new JLabel("To:");
	JTextField 	MDend=new JTextField("12", 4);
	/*status variables*/
	static boolean		compiled=false;
	
	public void init() 
	{
		/*Get parameters*/
		mc_id=getParameter("CHANNEL");
		user=getParameter("USERNAME");
		msg="User is "+user+" and this is machine number "+mc_id;
		/*Initialise start Vector*/
		SIM30.writeToMemory(0, 1023);
		/*Set up main window as border layout*/
		Container mainWindow=getContentPane();
		mainWindow.setBackground(appColour);
		BorderLayout layout = new BorderLayout();
		mainWindow.setLayout(layout);
		/*Add constituent parts*/
		makeTopWindow(mainWindow);
		makeCentreWindow(mainWindow);
		/*Initialise the SIM30*/
		/*SIM30.main();*/
	}
	private void makeTopWindow(Container window)
	{
		/*Put title at the top*/
		/*  Create panel with flow layout*/
		JPanel topPanel=new JPanel();
		FlowLayout topFlow=new FlowLayout();
		topPanel.setLayout(topFlow);
		/*  Set colours and text*/
		topPanel.setBackground(Color.GRAY);
		JLabel title=new JLabel("SIM30");
		title.setFont(headerFont);
		title.setForeground(Color.lightGray);
		topPanel.add(title);
		/*  and add it to the main window ('north')*/
		window.add(topPanel, BorderLayout.NORTH);		
	}
	private void makeLedDisplay(Container panel)
	{
		/*Set up LED display window*/
		JPanel ledPanel=new JPanel();
		FlowLayout ledFlow=new FlowLayout();
		ledPanel.setLayout(ledFlow);
		ledPanel.setBackground(appColour);
		/*...and add in the content*/
		JLabel ledLabel=new JLabel("Loc: ");
		ledLabel.setFont(bodyFont);
		ledLabel.setForeground(textColour);
		ledPanel.add(ledLabel);
		JTextField ledLoc=new JTextField("1017", 4);
		ledPanel.add(ledLoc);
		/*Add in the 10! leds*/
		JPanel lightsPanel=new JPanel();
		GridLayout lightsLayout=new GridLayout(1,10);
		lightsPanel.setLayout(lightsLayout);
		JButton led1=new JButton("");
		led1.setBackground(offColour);
		lightsPanel.add(led1);
		JButton led2=new JButton("");
		led2.setBackground(onColour);
		lightsPanel.add(led2);
		JButton led3=new JButton("");
		led3.setBackground(offColour);
		lightsPanel.add(led3);
		JButton led4=new JButton("");
		led4.setBackground(offColour);
		lightsPanel.add(led4);
		/*JButton led5=new JButton("");
		led5.setBackground(offColour);
		lightsPanel.add(led5);
		JButton led6=new JButton("");
		led6.setBackground(offColour);
		lightsPanel.add(led6);
		JButton led7=new JButton("");
		led7.setBackground(onColour);
		lightsPanel.add(led7);
		JButton led8=new JButton("");
		led8.setBackground(offColour);
		lightsPanel.add(led8);
		JButton led9=new JButton("");
		led9.setBackground(offColour);
		lightsPanel.add(led9);
		JButton led10=new JButton("");
		led10.setBackground(offColour);
		lightsPanel.add(led10);*/
		/*Add lights to the led display*/
		ledPanel.add(lightsPanel);
		/*and add the led panel to the container*/
		panel.add(ledPanel, BorderLayout.NORTH);
	}
	private void makeOpButtons(Container panel)
	{
		/*Set up panel for output buttons*/
		JPanel opButtonsPanel=new JPanel();
		FlowLayout opButFlow=new FlowLayout();
		opButtonsPanel.setLayout(opButFlow);
		opButtonsPanel.setBackground(appColour);
		/*...and add in the content*/
		opRun.addActionListener(this);
		opButtonsPanel.add(opRun);
		opHalt.addActionListener(this);
		opButtonsPanel.add(opHalt);
		opClear.addActionListener(this);
		opButtonsPanel.add(opClear);
		opHelp.addActionListener(this);
		opButtonsPanel.add(opHelp);
		opMemDump.addActionListener(this);
		opButtonsPanel.add(opMemDump);
		startLabel.setFont(bodyFont);
		startLabel.setForeground(textColour);
		opButtonsPanel.add(startLabel);
		opButtonsPanel.add(MDstart);
		endLabel.setFont(bodyFont);
		endLabel.setForeground(textColour);
		opButtonsPanel.add(endLabel);
		opButtonsPanel.add(MDend);

		panel.add(opButtonsPanel, BorderLayout.SOUTH);
	}
	public void makeCompileButtons(Container panel)
	{	
		/*Set up panel for compile buttons*/
		JPanel CompileButtonsPanel=new JPanel();
		FlowLayout compButFlow=new FlowLayout();
		CompileButtonsPanel.setLayout(compButFlow);
		CompileButtonsPanel.setBackground(appColour);
		/*...and add in the content*/
		compCompile.addActionListener(this);
		CompileButtonsPanel.add(compCompile);
		compClear.addActionListener(this);
		CompileButtonsPanel.add(compClear);
		compHelp.addActionListener(this);
		CompileButtonsPanel.add(compHelp);
		panel.add(CompileButtonsPanel, BorderLayout.SOUTH);
	}
	private void makeCentreWindow(Container window)
	{
		/*Create main working area with source code at left, output at right*/
		JPanel centrePanel=new JPanel();
		GridLayout centreGrid=new GridLayout(1,2);
		centrePanel.setLayout(centreGrid);
		centrePanel.setBackground(appColour);

		/*Create source code panel*/
		JPanel sourcePanel=new JPanel();
		BorderLayout sourceBorder=new BorderLayout();
		sourcePanel.setLayout(sourceBorder);
		sourceCode.setBackground(Color.blue);
		sourceCode.setForeground(Color.cyan);
		JScrollPane scrollbar=new JScrollPane(sourceCode,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sourcePanel.add(scrollbar, BorderLayout.CENTER);
		/*Add source code buttons*/
		makeCompileButtons(sourcePanel);
		
		/*Put LED display & terminal window at the right*/
		/*Divide up space*/
		JPanel rightPanel=new JPanel();
		BorderLayout rightBorder=new BorderLayout();
		rightPanel.setLayout(rightBorder);
		rightPanel.setBackground(appColour);
		/*Add in LED display window*/
		makeLedDisplay(rightPanel);
		/*Set up terminal window*/
		JPanel opPanel=new JPanel();
		BorderLayout opBorder=new BorderLayout();
		opPanel.setLayout(opBorder);
		opPanel.setBackground(appColour);
		termWindow.setBackground(Color.black);
		termWindow.setForeground(Color.white);
		termWindow.setFont(opFont);
		JScrollPane opscroll=new JScrollPane(termWindow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		opPanel.add(opscroll, BorderLayout.CENTER);
		rightPanel.add(opPanel);
		/*Set up output buttons*/
		makeOpButtons(rightPanel);
		
		centrePanel.add(sourcePanel);
		centrePanel.add(rightPanel);
		window.add(centrePanel, BorderLayout.CENTER);

	}
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == compCompile) compiled = CECILcompiler.compile();
		if(event.getSource() == compClear) sourceCode.setText("");
		if(event.getSource() == compHelp) Help.showCommands();
		if(event.getSource() == opRun) {
			terminal.addStars();
			terminal.add("Executing program: ...\n");
			SIM30.Run();
		}
		if(event.getSource() == opClear) termWindow.setText("");
		if(event.getSource() == opHelp) Help.showMemory();
		if(event.getSource() == opMemDump) {
			int start = Integer.parseInt(MDstart.getText());
			int end = Integer.parseInt(MDend.getText());
			SIM30.MemDump(start, end);
		}
	}
}
