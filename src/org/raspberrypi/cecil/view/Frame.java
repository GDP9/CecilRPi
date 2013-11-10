package org.raspberrypi.cecil.view;

import java.awt.ItemSelectable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
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
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.Component;

import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.JComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.raspberrypi.cecil.controller.Controller;
import org.raspberrypi.cecil.pojo.Instruction;

/**
 * Controller class for interfacing between the model and view.
 * This class creates an instance of the view (Frame) and model (Cecil) classes.
 * This class contains the main method.
 * 
 * MIT Open Source License
 * @author Karishma Nune (kkn1g10), Cathy Jin (cj8g10)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 07/11/2013
 *
 */
public class Frame extends JFrame implements ViewInterface {
	private static final int WIDTH = 1100;
	private static final int HEIGHT = 750;
	
	//Controller
	private Controller controller;
	
	//Instructions
	private ArrayList<Instruction> instructions;
	private ArrayList<String> instructionList;
	
	//Panels
	private JPanel northPanel;
	private JPanel centerRightPanel;
	private JPanel centerLeftPanel;
	private JPanel southPanel;
	private JPanel consolePanel;
	private JPanel registerPanel;
	private JPanel flagPanel;
	
	//Registers
	private JList<String> xRegister;
	private JList<String> yRegister;
	private JList<String> accRegister;
	private JScrollPane xScroll;
	private JScrollPane yScroll;
	private JScrollPane accScroll;
	
	//Console
	private JTextPane txtConsole;
	
	//Input
	private JTable tblInput;
	
	//Memory
	private JTable tblMemory;
	
	//Action buttons
	private JButton btnCompile;
	private JButton btnRun;
	private JButton btnStepThrough;
	
	//Status flags
	private JLabel lblCarry;
	private JLabel lblZero;
	private JLabel lblNegative;
	
	//Menu
	private BackgroundMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu settingsMenu;
	private JMenu helpMenu;
	private JMenuItem menuOpen;
	private JMenuItem menuSave;
	private JMenuItem menuExit;
	private JMenuItem menuPreferences;
	private JMenuItem menuWelcome;
	private JMenuItem menuAbout;
	private JMenuItem menuDocumentation;
	
	//Misc
	private JTextField input;
	private Java2sAutoComboBox comboBox;
	private FontUIResource font1 = new FontUIResource("Arial", Font.PLAIN, 18);
	private ArrayList<ArrayList<String>> currentProgram;
	
	/**
	 * Creates the view with default fonts, colours, and values.
	 */
	public Frame(Controller controller) {
		this.controller = controller;
		drawFrame();
	}
	
	private void drawFrame() {
		setupDefaultFrame();
		setupColours();
		setupButtonIcons();
		setupFlagIcons();
		setupFonts(font1);
		setupProgramCode();
		setVisible(true);
	}

	private void setupDefaultFrame() {
		/*
		 * Setup main frame and panels
		 */
		getContentPane().removeAll();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			System.out.println("ERROR: "+e1.getMessage());
		}
		getContentPane().setLayout(new GridBagLayout());
		setTitle("CECIL");
		
		if (instructionList == null) {
			//Default instructions
			instructionList = new ArrayList<String>();
			instructionList.add("load");
			instructionList.add("print");
			instructionList.add("stop");
			instructionList.add("add");
			instructionList.add("insert");
		}
		
		/*
		 * Menu bar
		 */
		menuBar = new BackgroundMenuBar();
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/resources/cecil_title.png").getPath());
		JMenuItem iconItem = new JMenuItem();
		iconItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		iconItem.setIcon(icon);
		menuBar.add(iconItem);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		menuOpen = new JMenuItem("Open");
		menuOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onOpenClicked();
			}
		});
		menuSave = new JMenuItem("Save");
		menuSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onSaveClicked();
			}
		});
		menuExit = new JMenuItem("Exit");
		
		fileMenu.add(menuOpen);
		fileMenu.add(menuSave);
		fileMenu.addSeparator();
		fileMenu.add(menuExit);
		
		settingsMenu = new JMenu("Settings");
		settingsMenu.setRolloverEnabled(true);
		menuBar.add(settingsMenu);
		
		menuPreferences = new JMenuItem("Preferences");
		final FontChooser fc = new FontChooser(this);
		menuPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fc.setVisible(true);
			}
		});
		settingsMenu.add(menuPreferences);

		helpMenu = new JMenu("Help");
		helpMenu.setRolloverEnabled(true);
		menuBar.add(helpMenu);
		
		menuWelcome = new JMenuItem("Welcome");
		menuDocumentation = new JMenuItem("Documentation");
		menuAbout = new JMenuItem("About CECIL");
		
		helpMenu.add(menuWelcome);
		helpMenu.add(menuDocumentation);
		helpMenu.addSeparator();
		helpMenu.add(menuAbout);
		
		setJMenuBar(menuBar);
		
		/*
		 * Panels
		 */
		northPanel = new JPanel();	
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
		
		centerLeftPanel = new JPanel();
		centerLeftPanel.setLayout(new GridLayout(1,1));
		centerLeftPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 10, 5), new TitledBorder(null, "Program", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		
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
		getContentPane().add(southPanel, gbc_south);
		
		/*
		 * Setup north panel buttons
		 */		
		btnCompile = new JButton("Compile");
		btnCompile.setToolTipText("Compile");
		btnCompile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onCompileClicked();
			}
		});
		northPanel.add(btnCompile);
		
		btnRun = new JButton("Run");
		btnRun.setToolTipText("Run");
		btnRun.setEnabled(false);
		btnRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onRunClicked();
			}
		});
		northPanel.add(btnRun);
		
		btnStepThrough = new JButton("Step through");
		btnStepThrough.setToolTipText("Step through");
		btnStepThrough.setEnabled(false);
		btnStepThrough.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onStepThroughClicked();
			}
		});
		northPanel.add(btnStepThrough);
		
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
		xRegister = new JList<String>();
		xRegister.setCellRenderer(new DefaultListCellRenderer(){
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});
		xRegister.setEnabled(false);
		xRegister.setToolTipText("X register");
		xRegister.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		/*
		 * Y register
		 */
		yRegister = new JList<String>();
		yRegister.setCellRenderer(new DefaultListCellRenderer() {
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});
		yRegister.setEnabled(false);
		yRegister.setToolTipText("Y register");
		yRegister.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		/*
		 * Acc register
		 */
		accRegister = new JList<String>();
        accRegister.setCellRenderer(new DefaultListCellRenderer(){
			public int getHorizontalAlignment() {
				return CENTER;
			}
		});
        accRegister.setEnabled(false);
		accRegister.setToolTipText("Acc register");
		accRegister.setBorder(new BevelBorder(BevelBorder.LOWERED));

		
		/*
		 * Add the register panes
		 */
		xScroll = new JScrollPane(xRegister);
		xScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder(new EmptyBorder(0, 0, 0, 0), "X", TitledBorder.CENTER, TitledBorder.TOP, null, null)));
		xScroll.setOpaque(false);
		registerPanel.add(xScroll);
		
		yScroll = new JScrollPane(accRegister);
		yScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Accumulator", TitledBorder.CENTER, TitledBorder.TOP, null, null)));
		yScroll.setOpaque(false);
		registerPanel.add(yScroll);
		
		accScroll = new JScrollPane(yRegister);
		accScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(2, 2, 2, 2), new TitledBorder(new EmptyBorder(0, 0, 0, 0), "Y", TitledBorder.CENTER, TitledBorder.TOP, null, null)));
		accScroll.setOpaque(false);
		registerPanel.add(accScroll);
		
		/*
		 * Flags
		 */
		flagPanel = new JPanel();
		flagPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 5, 10, 10), new TitledBorder(null, "Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
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
		consolePanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 5, 10, 10), new TitledBorder(null, "Result", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
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
		final DefaultTableModel mdlInput = new DefaultTableModel();
		mdlInput.addColumn("#");
		mdlInput.addColumn("Label");
		mdlInput.addColumn("Instruction");
		mdlInput.addColumn("Data");
		mdlInput.addColumn("Comments");
		mdlInput.addRow(new Object[]{1,"","","",""});
		
		tblInput = new JTable(mdlInput);
		tblInput.getTableHeader().setReorderingAllowed(false);
		tblInput.getColumnModel().getColumn(0).setResizable(false);
		tblInput.getColumnModel().getColumn(0).setMinWidth(30);
		tblInput.getColumnModel().getColumn(0).setMaxWidth(30);
		
		setUpInstructionColumn(tblInput, tblInput.getColumnModel().getColumn(2));
		input = new JTextField();      
		DefaultTableCellRenderer aligncenter = new DefaultTableCellRenderer();
       	aligncenter.setHorizontalAlignment(JLabel.CENTER);
       	tblInput.getColumnModel().getColumn(0).setCellRenderer(aligncenter);
       	tblInput.setRowHeight(25);                
       	tblInput.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(input));
       	tblInput.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(input));
       	tblInput.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(input));
       	tblInput.setFillsViewportHeight(true);
		tblInput.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tblInput.getSelectedRow() + 1 == (tblInput.getRowCount())) {

						mdlInput.addRow(new Object[] {tblInput.getSelectedRow()+2, "", "", "",""});
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
		
		/*
		 * Memory
		 */
		DefaultTableModel mdlMemory = new DefaultTableModel();
		for (int i = 0; i < 1024; i++) {
			mdlMemory.addColumn(i, new Object[]{""});
		}
		
		tblMemory = new JTable(mdlMemory);
		tblMemory.getTableHeader().setReorderingAllowed(false);
		tblMemory.setEnabled(false);
		tblMemory.setFillsViewportHeight(true);
		tblMemory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane memoryScroll = new JScrollPane(tblMemory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		memoryScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		memoryScroll.setOpaque(true);
		southPanel.add(memoryScroll);
	}
	
	private void setupColours() {
		//Color background = new Color(248, 255, 125);
		//Color highlight = new Color(66, 227, 77);
		Color background = new Color(255, 230, 214);
		Color highlight = new Color(255, 148, 82);
		Color innerPanel = new Color(255, 255, 255);
		getContentPane().setBackground(background);
		menuBar.setColour(highlight);
		LineBorder border = new LineBorder(highlight, 3, true);
		UIManager.put("TitledBorder.border", border);
		UIManager.put("Label.disabledForeground", Color.BLACK);
		
		northPanel.setBackground(background);
		centerLeftPanel.setBackground(background);
		centerRightPanel.setBackground(background);
		southPanel.setBackground(background);

		btnCompile.setBackground(background);
		
		registerPanel.setBackground(background);
		xRegister.setBackground(innerPanel);
		yRegister.setBackground(innerPanel);
		accRegister.setBackground(innerPanel);
		
		accRegister.setSelectionBackground(highlight);
		xRegister.setSelectionBackground(highlight);
		yRegister.setSelectionBackground(highlight);

		flagPanel.setBackground(background);
		lblCarry.setBackground(innerPanel);
		lblZero.setBackground(innerPanel);
		lblNegative.setBackground(innerPanel);

		consolePanel.setBackground(background);
		txtConsole.setBackground(innerPanel);

		tblInput.setBackground(innerPanel);
		tblInput.setSelectionBackground(highlight);
		tblMemory.setBackground(innerPanel);
	}
	
	private void setupButtonIcons() {		
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
		
		//File
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-directory.png"));
			fileMenu.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-directory-colour.png"));
			fileMenu.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		fileMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-directory-colour.png"));
					fileMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					System.out.println("Error creating buttons: could not set button icon");
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-directory.png"));
					fileMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					System.out.println("Error creating buttons: could not set button icon");
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		//Settings
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-settings.png"));
			settingsMenu.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-settings-colour.png"));
			settingsMenu.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		settingsMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-settings-colour.png"));
					settingsMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					System.out.println("Error creating buttons: could not set button icon");
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-settings.png"));
					settingsMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					System.out.println("Error creating buttons: could not set button icon");
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		//Help
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-help.png"));
			helpMenu.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-help-colour.png"));
			helpMenu.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		helpMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-help-colour.png"));
					helpMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					System.out.println("Error creating buttons: could not set button icon");
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-help.png"));
					helpMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					System.out.println("Error creating buttons: could not set button icon");
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
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
	
	private void setupFonts(Font font) {
		UIManager.put("ToolTip.font", new FontUIResource(font));
        UIManager.put("Table.font", font);
        
		input.setFont(font);
		comboBox.setFont(font);
		
		btnCompile.setFont(font);
		btnRun.setFont(font);
		btnStepThrough.setFont(font);
		
		fileMenu.setFont(font);
		settingsMenu.setFont(font);
		helpMenu.setFont(font);
		menuOpen.setFont(font);
		menuSave.setFont(font);
		menuExit.setFont(font);
		menuPreferences.setFont(font);
		menuWelcome.setFont(font);
		menuDocumentation.setFont(font);
		menuAbout.setFont(font);
		
		((TitledBorder)((CompoundBorder) flagPanel.getBorder()).getInsideBorder()).setTitleFont(font);
		lblCarry.setFont(font);
		lblZero.setFont(font);
		lblNegative.setFont(font);
		((TitledBorder)((CompoundBorder) registerPanel.getBorder()).getInsideBorder()).setTitleFont(font);
		xRegister.setFont(font);
		((TitledBorder)((CompoundBorder) xScroll.getBorder()).getInsideBorder()).setTitleFont(font);
		yRegister.setFont(font);
		((TitledBorder)((CompoundBorder) yScroll.getBorder()).getInsideBorder()).setTitleFont(font);	
		accRegister.setFont(font);
		((TitledBorder)((CompoundBorder) accScroll.getBorder()).getInsideBorder()).setTitleFont(font);
		((TitledBorder)((CompoundBorder) consolePanel.getBorder()).getInsideBorder()).setTitleFont(font);
		txtConsole.setFont(font);
		((TitledBorder)((CompoundBorder) centerLeftPanel.getBorder()).getInsideBorder()).setTitleFont(font);
		tblInput.getTableHeader().setFont(font);
		tblInput.setRowHeight(40);
		tblInput.setFont(font);
		((TitledBorder)((CompoundBorder) southPanel.getBorder()).getInsideBorder()).setTitleFont(font);
		tblMemory.getTableHeader().setFont(font);
		tblMemory.setFont(font);
		tblMemory.setRowHeight(40);
	}
	
	private void setupProgramCode() {		
		if (currentProgram != null && currentProgram.size() > 0) {
			loadProgramCode(currentProgram);
		}
	}
	
	public void setUpInstructionColumn(JTable table, TableColumn instructionColumn) {
		comboBox = new Java2sAutoComboBox(instructionList);
		comboBox.setEditable(true);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				actionEvent.getSource();
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
	 
	private class MyComboBoxRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {	
				setBackground(list.getSelectionBackground());
				setForeground(Color.BLUE);
				if (-1 < index) {
					if (instructions != null && instructions.get(index) != null && instructions.get(index).getDescription() != null) {
						list.setToolTipText(instructions.get(index).getDescription());//get the tooltip according the selected item's index
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
	private class Java2sAutoComboBox extends JComboBox {
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
	 * Retrieves the program code from the input editor and passes it to the controller.
	 */
	private void onCompileClicked() {
		setButtonsEnabled(false);
		controller.compileClicked(getProgramCode());
	}
	
	/**
	 * Retrieve the program code from the input editor
	 * 
	 * @return The code in the input editor
	 */
	private ArrayList<ArrayList<String>> getProgramCode() {
		ArrayList<ArrayList<String>> code = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < tblInput.getRowCount(); i++) {
			ArrayList<String> line = new ArrayList<String>();
			for (int j = 1; j < tblInput.getColumnCount(); j++) {
				String value = (String) tblInput.getValueAt(i, j);
				if (value != null && !value.isEmpty()) {
					line.add((String) value);
				} else {
					line.add(" ");
				}
			}
			code.add(line);
		}
		return code;
	}
	
	/**
	 * Notifies the controller that the run button has been clicked.
	 */
	private void onRunClicked() {
		controller.runClicked();
	}
	
	/**
	 * Notifies the controller that the step through button has been clicked.
	 */
	private void onStepThroughClicked()  {
		controller.stepThroughClicked() ;
	}
	
	/**
	 * Opens a JFileChooser dialog to open a .cecil file.
	 */
	private void onOpenClicked() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CECIL File", "cecil");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			controller.fileOpened(fileChooser.getSelectedFile());
		}
	}
	
	/**
	 * Opens a JFileChooser dialog to save to a .cecil file.
	 */
	private void onSaveClicked() {
		ArrayList<ArrayList<String>> code = getProgramCode();
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CECIL File", "cecil");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showSaveDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			controller.saveToFile(code, fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * Loads a program into the input editor.
	 * 
	 * @param program An ArrayList of instruction lines. Each instruction is an ArrayList composed of three Strings.
	 */
	private void loadProgramCode(ArrayList<ArrayList<String>> program) {
		DefaultTableModel model = (DefaultTableModel) tblInput.getModel();
		model.setRowCount(0);
		if (program != null && program.size() > 0) {
			for (ArrayList<String> line : program) {
				Object[] data = new Object[line.size()+1];
				data[0] = program.indexOf(line)+1;
				for (int i = 0; i < line.size(); i++) {
					data[i+1] = line.get(i);
				}
				model.addRow(data);
			}
		}
		model.fireTableDataChanged();
	}
	
	/**
	 * Change the application font size.
	 * 
	 * @param font The new font.
	 * 
	 */
	public void setNewFont(Font font){
		setupFonts(font);
	}

	/*
	 * Interface methods
	 * 
	 */
	@Override
	public void setAccStack(ArrayList<String> values) {
		ArrayList<String> temp = new ArrayList<String>(values);
		Collections.reverse(temp);
		String[] list = new String[temp.size()];
		temp.toArray(list);
		accRegister.setListData(list);
		accRegister.setSelectedIndex(0);
	}

	@Override
	public void setXStack(ArrayList<String> values) {
		ArrayList<String> temp = new ArrayList<String>(values);
		Collections.reverse(temp);
		String[] list = new String[temp.size()];
		temp.toArray(list);
		xRegister.setListData(list);
		xRegister.setSelectedIndex(0);
	}

	@Override
	public void setYStack(ArrayList<String> values) {
		ArrayList<String> temp = new ArrayList<String>(values);
		Collections.reverse(temp);
		String[] list = new String[temp.size()];
		temp.toArray(list);
		yRegister.setListData(list);
		yRegister.setSelectedIndex(0);
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
	public void setMemoryAllocation(HashMap<String, String> values) {
		DefaultTableModel mdlMemory = new DefaultTableModel();
		for (Map.Entry<String, String> entry : values.entrySet()) {
			mdlMemory.addColumn(entry.getKey(), new Object[]{entry.getValue()});
		}
		tblMemory.setModel(mdlMemory);
	}

	@Override
	public void setInstructionList(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
		instructionList = new ArrayList<String>();
		for (Instruction instruction : instructions) {
			if (instruction != null && instruction.getName() != null) {
				instructionList.add(instruction.getName());
			}
		}
		drawFrame();
	}
	
	@Override
	public void setProgramCode(ArrayList<ArrayList<String>> program) {
		loadProgramCode(program);
	}

	@Override
	public void setButtonsEnabled(boolean enabled) {
		btnRun.setEnabled(enabled);
		btnStepThrough.setEnabled(enabled);
	}
}