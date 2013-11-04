package org.raspberrypi.cecil.view;

import java.awt.ItemSelectable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.raspberrypi.cecil.pojo.CecilInstruction;

public class Frame extends JFrame implements CecilViewInterface {
	private static final int WIDTH = 1100;
	private static final int HEIGHT = 750;
	private Font schoolbellFont = null;
	
	private DefaultTableModel mdlInput;
	String[] tooltips = { "American", "Japanese ", "Latin ", "English"};
	private ArrayList<CecilInstruction> instructions;
	private ArrayList<String> instructionList;
	
	private JPanel northPanel;
	private JPanel centerRightPanel;
	private JPanel centerLeftPanel;
	private JPanel southPanel;
	private JPanel consolePanel;
	private JPanel registerPanel;
	private JPanel flagPanel;
	
	private JTextPane xRegister;
	private JTextPane yRegister;
	private JTextPane accRegister;
	private JTextPane txtConsole;
	
	private JTable tblInput;
	private JTable tblMemory;
	
//	private JButton btnFile;
	private JButton btnCompile;
	private JButton btnRun;
	private JButton btnStepThrough;
//	private JButton btnSettings;
//	private JButton btnHelp;
	private JLabel lblCarry;
	private JLabel lblZero;
	private JLabel lblNegative;
	
	public BackgroundMenuBar menuBar;
		
	/**
	 * Create the frame.
	 */
	public Frame() {
		setupDefaultFrame();
		setupColours();
		setupButtonIcons();
		setupFlagIcons();
//		setupFonts();
		
		ArrayList<String> examples = new ArrayList<String>();
		examples.add("0");
		examples.add("0");
		examples.add("0");
		setYStack(examples);
		setAccStack(examples);
		setXStack(examples);
	}

	private void setupDefaultFrame() {
		/*
		 * Setup main frame and panels
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().setLayout(new GridBagLayout());
		menuBar = new BackgroundMenuBar();
		menuBar.setOpaque(true);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/resources/cecil_title.png").getPath());
		JMenuItem iconItem = new JMenuItem();
		iconItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		iconItem.setIcon(icon);
		menuBar.add(iconItem);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setIcon(new ImageIcon(getClass().getResource("/resources/vdk-directory.png").getPath()));
		menuBar.add(fileMenu);
		
		JMenuItem menuItem = new JMenuItem("A text only item");
		fileMenu.add(menuItem);
		
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setIcon(new ImageIcon(getClass().getResource("/resources/vdk-settings.png").getPath()));
		menuBar.add(settingsMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setIcon(new ImageIcon(getClass().getResource("/resources/vdk-help.png").getPath()));
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
		
		if (instructionList == null) {
			//Default instructions
			instructionList = new ArrayList<String>();
			instructionList.add("Instruction 1");
			instructionList.add("Instruction 2");
			instructionList.add("Instruction 3");
		}
		
		northPanel = new JPanel();	
		northPanel.setLayout(new GridLayout(1,3));
		northPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		centerLeftPanel = new JPanel();
		centerLeftPanel.setLayout(new GridLayout(1,1));
		centerLeftPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 10, 5), new TitledBorder(null, "Input Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		
		centerRightPanel = new JPanel();
		centerRightPanel.setLayout(new GridLayout(2,1));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);
		
		southPanel = new JPanel();
		southPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 20, 10), new TitledBorder(null, "Memory", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		southPanel.setLayout(new GridLayout(1,1));
		
		/*
		 * Add north panel
		 */
		GridBagConstraints gbc_north = new GridBagConstraints();
		gbc_north.fill = GridBagConstraints.BOTH;
		gbc_north.gridx = 0;
		gbc_north.gridy = 0;
		gbc_north.weightx = 1;
		gbc_north.weighty = 0.05;
//		gbc_north.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(northPanel, gbc_north);
		
		/*
		 * Add centre panel
		 */
		GridBagConstraints gbc_centre = new GridBagConstraints();
		gbc_centre.fill = GridBagConstraints.BOTH;
		gbc_centre.gridx = 0;
		gbc_centre.gridy = 1;
		gbc_centre.weightx = 1;
		gbc_centre.weighty = 0.55;
//		gbc_centre.insets = new Insets(0, 10, 0, 10);
		getContentPane().add(centerPanel, gbc_centre);		
		
		/*
		 * Add south panel
		 */
		GridBagConstraints gbc_south = new GridBagConstraints();
		gbc_south.fill = GridBagConstraints.BOTH;
		gbc_south.gridx = 0;
		gbc_south.gridy = 2;
		gbc_south.weightx = 1;
		gbc_south.weighty = 0.4;
//		gbc_south.insets = new Insets(10, 10, 20, 10);
		getContentPane().add(southPanel, gbc_south);
		
		/*
		 * Setup north panel subpanels
		 */
		JPanel topLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topLeft.setOpaque(false);
		northPanel.add(topLeft);
		
		JPanel topCentre = new JPanel(new FlowLayout(FlowLayout.CENTER));
		topCentre.setOpaque(false);
		northPanel.add(topCentre);
		
		JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topRight.setOpaque(false);
		northPanel.add(topRight);
		
		/*
		 * Setup north panel buttons
		 */
//		btnFile = new JButton("File");
//		btnFile.setToolTipText("File");
//		topLeft.add(btnFile);
		
		btnCompile = new JButton("Compile");
		btnCompile.setToolTipText("Compile");
		topCentre.add(btnCompile);
		
		btnRun = new JButton("Run");
		btnRun.setToolTipText("Run");
		topCentre.add(btnRun);
		
		btnStepThrough = new JButton("Step through");
		btnStepThrough.setToolTipText("Step through");
		topCentre.add(btnStepThrough);
		
//		btnSettings = new JButton("Settings");
//		btnSettings.setToolTipText("Settings");
//		topRight.add(btnSettings);
//		
//		btnHelp = new JButton("Help");
//		btnHelp.setToolTipText("Help");
//		topRight.add(btnHelp);
		
		/*
		 * Registers
		 */
		JPanel centerRightTopPanel = new JPanel();
		centerRightTopPanel.setLayout(new GridBagLayout());
		centerRightTopPanel.setOpaque(false);
		centerRightPanel.add(centerRightTopPanel);
		
		registerPanel = new JPanel();
		registerPanel.setLayout(new GridLayout(1,3));
		registerPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 5, 10, 10), new TitledBorder(null, "Registers", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		
		GridBagConstraints gbc_register = new GridBagConstraints();
		gbc_register.fill = GridBagConstraints.BOTH;
		gbc_register.gridx = 0;
		gbc_register.gridy = 0;
		gbc_register.weightx = 1;
		gbc_register.weighty = 0.9;
		centerRightTopPanel.add(registerPanel, gbc_register);
		
		/*
		 * X register
		 */
		xRegister = new JTextPane();
		xRegister.setEditable(false);
		xRegister.setToolTipText("X register");
		xRegister.setBorder(new BevelBorder(BevelBorder.LOWERED));
		//Center the text
		StyledDocument doc = xRegister.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		/*
		 * Y register
		 */
		yRegister = new JTextPane();
		yRegister.setEditable(false);
		yRegister.setToolTipText("Y register");
		yRegister.setBorder(new BevelBorder(BevelBorder.LOWERED));
		doc = yRegister.getStyledDocument();
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		/*
		 * Acc register
		 */
		accRegister = new JTextPane();
		accRegister.setEditable(false);
		accRegister.setToolTipText("Acc register");
		accRegister.setBorder(new BevelBorder(BevelBorder.LOWERED));
		doc = accRegister.getStyledDocument();
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		/*
		 * Add the register panes
		 */
		JScrollPane scrollPane = new JScrollPane(xRegister);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder(new EmptyBorder(0, 0, 0, 0), "X", TitledBorder.CENTER, TitledBorder.TOP, null, null)));
		scrollPane.setOpaque(false);
		registerPanel.add(scrollPane);
		
		scrollPane = new JScrollPane(accRegister);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder(new EmptyBorder(0, 0, 0, 0), "ACC", TitledBorder.CENTER, TitledBorder.TOP, null, null)));
		scrollPane.setOpaque(false);
		registerPanel.add(scrollPane);
		
		scrollPane = new JScrollPane(yRegister);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Y", TitledBorder.CENTER, TitledBorder.TOP, null, null)));
		scrollPane.setOpaque(false);
		registerPanel.add(scrollPane);
		
		/*
		 * Flags
		 */
		flagPanel = new JPanel();
		flagPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 5, 10, 10), new TitledBorder(null, "Status Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		flagPanel.setLayout(new GridLayout(1,3));
		
		GridBagConstraints gbc_flagpanel = new GridBagConstraints();
		gbc_flagpanel.fill = GridBagConstraints.BOTH;
		gbc_flagpanel.gridx = 0;
		gbc_flagpanel.gridy = 1;
		gbc_flagpanel.weightx = 1;
		gbc_flagpanel.weighty = 0.1;
		centerRightTopPanel.add(flagPanel, gbc_flagpanel);
		
		JPanel carryPanel = new JPanel(new GridLayout(1,1));
		carryPanel.setOpaque(false);
		carryPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblCarry = new JLabel("Carry", SwingConstants.CENTER);
		lblCarry.setBorder(BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5)));
		lblCarry.setOpaque(true);
		carryPanel.add(lblCarry);
		flagPanel.add(carryPanel);
		
		JPanel zeroPanel = new JPanel(new GridLayout(1,1));
		zeroPanel.setOpaque(false);
		zeroPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblZero = new JLabel("Zero", SwingConstants.CENTER);
		lblZero.setBorder(BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5)));
		lblZero.setOpaque(true);
		zeroPanel.add(lblZero);
		flagPanel.add(zeroPanel);
		
		JPanel negativePanel = new JPanel(new GridLayout(1,1));
		negativePanel.setOpaque(false);
		negativePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblNegative = new JLabel("Negative", SwingConstants.CENTER);
		lblNegative.setBorder(BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5)));
		lblNegative.setOpaque(true);
		negativePanel.add(lblNegative);
		flagPanel.add(negativePanel);
		
		/*
		 * Console
		 */		
		consolePanel = new JPanel();
		consolePanel.setLayout(new GridLayout(1,1));
		consolePanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 5, 10, 10), new TitledBorder(null, "Output console", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		centerRightPanel.add(consolePanel);
					
		txtConsole = new JTextPane();
		JScrollPane consoleScroll = new JScrollPane(txtConsole);
		consoleScroll.setOpaque(false);
		consoleScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		txtConsole.setEditable(false);
		consolePanel.add(consoleScroll);
		
		/*
		 * Input editor
		 */
		mdlInput = new DefaultTableModel();
		mdlInput.addColumn("#");
		mdlInput.addColumn("Label");
		mdlInput.addColumn("Instruction");
		mdlInput.addColumn("Data");
		mdlInput.addRow(new Object[]{1,"","",""});
		
		tblInput = new JTable(mdlInput);

		setUpInstructionColumn(tblInput, tblInput.getColumnModel().getColumn(2));
//		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		DefaultTableCellRenderer aligncenter = new DefaultTableCellRenderer();
		aligncenter.setHorizontalAlignment(JLabel.CENTER);
		tblInput.getColumnModel().getColumn(0).setCellRenderer(aligncenter);
		tblInput.setRowHeight(25);		
		tblInput.getColumnModel().getColumn(2).setPreferredWidth(150);
		tblInput.getColumnModel().getColumn(0).setPreferredWidth(5);
		tblInput.setFillsViewportHeight(true);
		tblInput.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tblInput.getSelectedRow() + 1 == (tblInput.getRowCount())) {

						mdlInput.addRow(new Object[] {tblInput.getSelectedRow()+2, "", "", "" });
						//System.out.println(table_1.getValueAt(table_1.getSelectedRow()+1, 0));
					}
				}
			}

			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		JScrollPane inputScroll = new JScrollPane(tblInput);
		inputScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		inputScroll.setOpaque(false);
		centerLeftPanel.add(inputScroll, gbc_scrollPane);
		
		DefaultTableModel mdlMemory = new DefaultTableModel();
		for (int i = 0; i < 20; i++) {
			mdlMemory.addColumn(i, new Object[]{0});
		}
		
		tblMemory = new JTable(mdlMemory);
		tblMemory.setFillsViewportHeight(true);

		tblMemory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane memoryScroll = new JScrollPane(tblMemory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		memoryScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		memoryScroll.setOpaque(false);
		southPanel.add(memoryScroll);
	}
	
	private void setupColours() {
		Color background = new Color(255, 204, 245);
		Color innerPanel = new Color(255, 255, 255);
		getContentPane().setBackground(background);
		menuBar.setColour(background);
		
		northPanel.setBackground(background);
		centerLeftPanel.setBackground(background);
		centerRightPanel.setBackground(background);
		southPanel.setBackground(background);

		btnCompile.setBackground(background);
		
		registerPanel.setBackground(background);
		xRegister.setBackground(innerPanel);
		yRegister.setBackground(innerPanel);
		accRegister.setBackground(innerPanel);

		flagPanel.setBackground(background);
		lblCarry.setBackground(innerPanel);
		lblZero.setBackground(innerPanel);
		lblNegative.setBackground(innerPanel);

		consolePanel.setBackground(background);
		txtConsole.setBackground(innerPanel);

		tblInput.setBackground(innerPanel);
		// tblInput.setBackground(new Color(255, 255, 102));
		tblMemory.setBackground(innerPanel);
	}
	
	private void setupButtonIcons() {
		//File
//		try {
//			Image img = ImageIO.read(getClass().getResource("/resources/vdk-directory.png"));
//			btnFile.setIcon(new ImageIcon(img));
//			
//			img = ImageIO.read(getClass().getResource("/resources/vdk-directory-colour.png"));
//			btnFile.setRolloverIcon(new ImageIcon(img));
//			
//			
//		} catch (IOException e) {
//			System.out.println("Error creating buttons: could not set button icon");
//		}
		
		//Compile
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-build.png"));
			btnCompile.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-build-colour.png"));
			btnCompile.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		//Run
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-play.png"));
			btnRun.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-play-colour.png"));
			btnRun.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		//Step through
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-step.png"));
			btnStepThrough.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-step-colour.png"));
			btnStepThrough.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
//		//Settings
//		try {
//			Image img = ImageIO.read(getClass().getResource("/resources/vdk-settings.png"));
//			btnSettings.setIcon(new ImageIcon(img));
//			
//			img = ImageIO.read(getClass().getResource("/resources/vdk-settings-colour.png"));
//			btnSettings.setRolloverIcon(new ImageIcon(img));
//		} catch (IOException e) {
//			System.out.println("Error creating buttons: could not set button icon");
//		}
//		
//		//Help
//		try {
//			Image img = ImageIO.read(getClass().getResource("/resources/vdk-help.png"));
//			btnHelp.setIcon(new ImageIcon(img));
//			
//			img = ImageIO.read(getClass().getResource("/resources/vdk-help-colour.png"));
//			btnHelp.setRolloverIcon(new ImageIcon(img));
//		} catch (IOException e) {
//			System.out.println("Error creating buttons: could not set button icon");
//		}
	}
	
	private void setupFlagIcons() {
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-light.png"));
			lblCarry.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-light.png"));
			lblZero.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-light.png"));
			lblNegative.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
	}
	
	private void setupFonts() {
		try {
			schoolbellFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/Schoolbell.ttf"));
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		schoolbellFont = schoolbellFont.deriveFont(20f);
		UIManager.put("ToolTip.font", new FontUIResource(schoolbellFont));
		
		btnCompile.setFont(schoolbellFont);
		btnRun.setFont(schoolbellFont);
		btnStepThrough.setFont(schoolbellFont);
		((TitledBorder) flagPanel.getBorder()).setTitleFont(schoolbellFont);
		lblCarry.setFont(schoolbellFont);
		lblZero.setFont(schoolbellFont);
		lblNegative.setFont(schoolbellFont);
		((TitledBorder) registerPanel.getBorder()).setTitleFont(schoolbellFont);
		xRegister.setFont(schoolbellFont);
		((TitledBorder) xRegister.getBorder()).setTitleFont(schoolbellFont);
		yRegister.setFont(schoolbellFont);
		((TitledBorder) yRegister.getBorder()).setTitleFont(schoolbellFont);
		accRegister.setFont(schoolbellFont);
		((TitledBorder) accRegister.getBorder()).setTitleFont(schoolbellFont);
		((TitledBorder) consolePanel.getBorder()).setTitleFont(schoolbellFont);
		txtConsole.setFont(schoolbellFont);
		((TitledBorder) centerLeftPanel.getBorder()).setTitleFont(schoolbellFont);
		tblInput.getTableHeader().setFont(schoolbellFont);
		tblInput.setRowHeight(40);
		tblInput.setFont(schoolbellFont);
		((TitledBorder) southPanel.getBorder()).setTitleFont(schoolbellFont);
		tblMemory.getTableHeader().setFont(schoolbellFont);
		tblMemory.setFont(schoolbellFont);
		tblMemory.setRowHeight(40);
	}
	
	public void setUpInstructionColumn(JTable table, TableColumn instructionColumn) {
		Java2sAutoComboBox comboBox = new Java2sAutoComboBox(instructionList);
		comboBox.setEditable(true);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ItemSelectable is = (ItemSelectable)actionEvent.getSource();
				System.out.println(", Selected: " + selectedString(is));
			}
		};
		comboBox.addActionListener(actionListener);
		comboBox.setRenderer(new MyComboBoxRenderer());
		instructionColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
	
	static private String selectedString(ItemSelectable is) {
		Object selected[] = is.getSelectedObjects();
		return ((selected.length == 0) ? "null" : (String)selected[0]);
	} 
	 
	class MyComboBoxRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {	
				setBackground(list.getSelectionBackground());
				setForeground(Color.BLUE);
				if (-1 < index) {
					if (instructions != null && instructions.get(index) != null && instructions.get(index).getTooltipDescription() != null) {
						list.setToolTipText(instructions.get(index).getTooltipDescription());//get the tooltip according the selected item's index
					} else {
						list.setToolTipText("No description available");
					}
				}
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setFont(list.getFont());
			setText((value == null) ? "" : value.toString());
			setBorder(getBorder());
			return this;
		}
	}
	
	public class Java2sAutoTextField extends JTextField {
		class AutoDocument extends PlainDocument {
			public void replace(int i, int j, String s, AttributeSet attributeset) throws BadLocationException {
				super.remove(i, j);
				insertString(i, s, attributeset);
			}

			public void insertString(int i, String s, AttributeSet attributeset) throws BadLocationException {
				if (s == null || "".equals(s))
					return;
				String s1 = getText(0, i);
				String s2 = getMatch(s1 + s);
				int j = (i + s.length()) - 1;
				if (isStrict && s2 == null) {
					s2 = getMatch(s1);
					j--;
				} else if (!isStrict && s2 == null) {
					super.insertString(i, s, attributeset);
					return;
				}
				if (autoComboBox != null && s2 != null)
					autoComboBox.setSelectedValue(s2);
				super.remove(0, getLength());
				super.insertString(0, s2, attributeset);
				setSelectionStart(j + 1);
				setSelectionEnd(getLength());
			}

			public void remove(int i, int j) throws BadLocationException {
				int k = getSelectionStart();
				if (k > 0)
					k--;
				String s = getMatch(getText(0, k));
				if (!isStrict && s == null) {
					super.remove(i, j);
				} else {
					super.remove(0, getLength());
					super.insertString(0, s, null);
				}
				if (autoComboBox != null && s != null)
					autoComboBox.setSelectedValue(s);
				try {
					setSelectionStart(k);
					setSelectionEnd(getLength());
				} catch (Exception exception) {
				}
			}
		}

		public Java2sAutoTextField(List list) {
			isCaseSensitive = false;
			isStrict = true;
			autoComboBox = null;
			if (list == null) {
				throw new IllegalArgumentException("values can not be null");
			} else {
				dataList = list;
				init();
				return;
			}
		}

		Java2sAutoTextField(List list, Java2sAutoComboBox b) {
			isCaseSensitive = false;
			isStrict = true;
			autoComboBox = null;
		    if (list == null) {
		    	throw new IllegalArgumentException("values can not be null");
		    } else {
		    	dataList = list;
		    	autoComboBox = b;
		    	init();
		    	return;
		    }
		}

		private void init() {
			setDocument(new AutoDocument());
			if (isStrict && dataList.size() > 0)
				setText(dataList.get(0).toString());
		}

		private String getMatch(String s) {
			for (int i = 0; i < dataList.size(); i++) {
				String s1 = dataList.get(i).toString();
				if (s1 != null) {
					if (!isCaseSensitive && s1.toLowerCase().startsWith(s.toLowerCase()))
						return s1;
					if (isCaseSensitive && s1.startsWith(s))
						return s1;
				}
			}

			return null;
		}
		
		public boolean isCaseSensitive() {
			return isCaseSensitive;
		}

		public void setCaseSensitive(boolean flag) {
			isCaseSensitive = flag;
		}

		public boolean isStrict() {
			return isStrict;
		}

		public void setStrict(boolean flag) {
			isStrict = flag;
		}

		public List getDataList() {
			return dataList;
		}

		public void setDataList(List list) {
			if (list == null) {
				throw new IllegalArgumentException("values can not be null");
			} else {
				dataList = list;
				return;
			}
		}

		private List dataList;
		private boolean isCaseSensitive;
		private boolean isStrict;
		private Java2sAutoComboBox autoComboBox;
	}
	
	 /*JComboBox auto-complete*/
	public class Java2sAutoComboBox extends JComboBox {
		private class AutoTextFieldEditor extends BasicComboBoxEditor {
			private Java2sAutoTextField getAutoTextFieldEditor() {
				return (Java2sAutoTextField) editor;
			}

			AutoTextFieldEditor(java.util.List list) {
				editor = new Java2sAutoTextField(list, Java2sAutoComboBox.this);
			}
		}

		public Java2sAutoComboBox(java.util.List list) {
			isFired = false;
			autoTextFieldEditor = new AutoTextFieldEditor(list);
			setEditable(true);
			setModel(new DefaultComboBoxModel(list.toArray()) {
				protected void fireContentsChanged(Object obj, int i, int j) {
					if (!isFired)
						super.fireContentsChanged(obj, i, j);
				}
			});
			setEditor(autoTextFieldEditor);
		}

		public boolean isCaseSensitive() {
			return autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
		}

		public void setCaseSensitive(boolean flag) {
			autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
		}

		public boolean isStrict() {
			return autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
		}

		  /*public void setStrict(boolean flag) {
		    autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
		  }*/

		public java.util.List getDataList() {
			return autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
		}

		public void setDataList(java.util.List list) {
			autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
			setModel(new DefaultComboBoxModel(list.toArray()));
		}

		void setSelectedValue(Object obj) {
			if (isFired) {
				return;
			} else {
				isFired = true;
				setSelectedItem(obj);
				fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder, 1));
				isFired = false;
				return;
			}
		}

		protected void fireActionEvent() {
			if (!isFired)
				super.fireActionEvent();
		}

		private AutoTextFieldEditor autoTextFieldEditor;
		private boolean isFired;

	}

	public class BackgroundMenuBar extends JMenuBar {
		private Color bgColour = Color.WHITE;
		public void setColour(Color colour) {
			bgColour = colour;
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(bgColour);
			g2d.fillRect(0, 0, getWidth()-1,  getHeight()-1);
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("ToolTip.background", new ColorUIResource(255, 140, 0));//setting the background of the tooltip
			Frame frame = new Frame();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");//setting a Napkin like look
			SwingUtilities.updateComponentTreeUI(frame);
//			frame.setMenuBar(menuBar);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Interface methods
	 * 
	 */

	@Override
	public void setAccStack(ArrayList<String> values) {
		if (values != null) {
			StyledDocument doc = accRegister.getStyledDocument();
			
			Style style = accRegister.addStyle("Plain", null);
			StyleConstants.setForeground(style, Color.BLACK);
			
			for (int i = 0; i < values.size(); i++) {
				if (i == 0) {
					try {
						doc.insertString(0, values.get(i), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} else if (i < values.size() - 1) {
					try {
						doc.insertString(0, values.get(i)+System.getProperty("line.separator"), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} else {
					try {
						StyleConstants.setFontSize(style, 20);
//						StyleConstants.setBold(style, true);
						doc.insertString(0, "- "+values.get(i)+" -"+System.getProperty("line.separator"), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			accRegister.setText("");
		}
	}

	@Override
	public void setXStack(ArrayList<String> values) {
		if (values != null) {
			StyledDocument doc = xRegister.getStyledDocument();
			
			Style style = xRegister.addStyle("Plain", null);
			StyleConstants.setForeground(style, Color.BLACK);
			
			for (int i = 0; i < values.size(); i++) {
				if (i == 0) {
					try {
						doc.insertString(0, values.get(i), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} else if (i < values.size() - 1) {
					try {
						doc.insertString(0, values.get(i)+System.getProperty("line.separator"), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} else {
					try {
						StyleConstants.setFontSize(style, 20);
//						StyleConstants.setBold(style, true);
						doc.insertString(0, "- "+values.get(i)+" -"+System.getProperty("line.separator"), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			xRegister.setText("");
		}
	}

	@Override
	public void setYStack(ArrayList<String> values) {
		if (values != null) {
			StyledDocument doc = yRegister.getStyledDocument();
			
			Style style = yRegister.addStyle("Plain", null);
			StyleConstants.setForeground(style, Color.BLACK);
			
			for (int i = 0; i < values.size(); i++) {
				if (i == 0) {
					try {
						doc.insertString(0, values.get(i), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} else if (i < values.size() - 1) {
					try {
						doc.insertString(0, values.get(i)+System.getProperty("line.separator"), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} else {
					try {
						StyleConstants.setFontSize(style, 20);
//						StyleConstants.setBold(style, true);
						doc.insertString(0, "- "+values.get(i)+" -"+System.getProperty("line.separator"), style);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			yRegister.setText("");
		}
	}

	@Override
	public void setCarryFlag(boolean flagIsOn) {
		try {
			Image img;
			if (flagIsOn) {
				img = ImageIO.read(getClass().getResource("/resources/vdk-light-colour.png"));
			} else {
				img = ImageIO.read(getClass().getResource("/resources/vdk-light.png"));
			}
			lblCarry.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
	}

	@Override
	public void setZeroFlag(boolean flagIsOn) {
		try {
			Image img;
			if (flagIsOn) {
				img = ImageIO.read(getClass().getResource("/resources/vdk-light-colour.png"));
			} else {
				img = ImageIO.read(getClass().getResource("/resources/vdk-light.png"));
			}
			lblZero.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
	}

	@Override
	public void setNegativeFlag(boolean flagIsOn) {
		try {
			Image img;
			if (flagIsOn) {
				img = ImageIO.read(getClass().getResource("/resources/vdk-light-colour.png"));
			} else {
				img = ImageIO.read(getClass().getResource("/resources/vdk-light.png"));
			}
			lblNegative.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
	}

	@Override
	public void setConsoleText(ArrayList<String> text) {
		if (text != null) {
			txtConsole.setText("");
			for (int i = 0; i < text.size(); i++) {
				txtConsole.setText(txtConsole.getText()+text.get(i)+System.getProperty("line.separator"));
			}
		}
	}

	@Override
	public void setMemoryAllocation(HashMap<Integer, Integer> values) {
		if (values == null) {
			HashMap<Integer, Integer> exampleValues = new HashMap<Integer, Integer>();
			exampleValues.put(1, 0);
			exampleValues.put(2, 0);
			exampleValues.put(3, 0);
			values = exampleValues;
		}
		
		DefaultTableModel mdlMemory = new DefaultTableModel();
		for (Map.Entry<Integer, Integer> entry : values.entrySet()) {
			mdlMemory.addColumn(entry.getKey(), new Object[]{entry.getValue()});
		}
		tblMemory.setModel(mdlMemory);
	}

	@Override
	public void setInstructionList(ArrayList<CecilInstruction> instructions) {
		this.instructions = instructions;
		instructionList = new ArrayList<String>();
		for (CecilInstruction instruction : instructions) {
			if (instruction != null && instruction.getInstructionName() != null) {
				instructionList.add(instruction.getInstructionName());
			}
		}
	}
}