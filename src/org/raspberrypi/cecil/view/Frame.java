package org.raspberrypi.cecil.view;

import java.awt.ItemSelectable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.Component;

import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JComboBox;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ComboBoxCellEditor;
import org.raspberrypi.cecil.controller.Controller;
import org.raspberrypi.cecil.model.outputstream.OutputError;
import org.raspberrypi.cecil.pojo.Instruction;

/**
 * View class for creating and displaying the GUI and handling user inputs.
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

	public static final Color[] ORANGE_THEME = {new Color(255, 230, 214), new Color(255, 148, 82), new Color(255, 255, 255)};
	public static final Color[] BLUE_THEME = {new Color(152, 221, 255), new Color(67, 178, 233), new Color(255, 255, 255)};
	public static final Color[] GREEN_THEME = {new Color(191, 252, 172),new Color(6, 209, 46),new Color(255, 255, 255)};
	public static final Color[] DEFAULT_THEME = null;

	private static final FontUIResource FONT_SMALL = new FontUIResource("Arial", Font.PLAIN, 12);
	private static final FontUIResource FONT_MEDIUM = new FontUIResource("Arial", Font.PLAIN, 18);
	private static final FontUIResource FONT_LARGE = new FontUIResource("Arial", Font.PLAIN, 24);

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
	private JList<String> txtConsole;

	//Input
	private JTable tblInput;

	//Memory
	JTable tblMemory;

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
	private JMenu ioMenu;
	private JMenuItem menuNew;
	private JMenuItem menuOpen;
	private JMenuItem menuSave;
	private JMenuItem menuExit;
	private JMenuItem menuPreferences;
	private JMenuItem menuWelcome;
	private JMenuItem menuAbout;
	private JMenuItem menuDocumentation;

	//Misc
	private JTextField input;
	private JComboBox<String> comboBox;
	private FontUIResource currentFont;
	private ArrayList<ArrayList<String>> currentProgram;
	private Color[] currentTheme;
	private ArrayList<OutputError> errors;
	private boolean ioPortsEnabled;

	/**
	 * Creates the view with default fonts, colours, and values.
	 */
	public Frame(Controller controller) {
		this.controller = controller;
		currentTheme = ORANGE_THEME;
		currentFont = FONT_MEDIUM;
		drawFrame();
	}

	/**
	 * Draw the application GUI.
	 */
	private void drawFrame() {
		setupDefaultFrame();
		setupColours();
		setupButtonIcons();
		setupFlagIcons();
		setupFonts();
		setupProgramCode();
		repaint();
		setVisible(true);
	}

	/**
	 * Setup the frame layout and contents.
	 */
	private void setupDefaultFrame() {
		/*
		 * Setup main frame and panels
		 */
		getContentPane().removeAll();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		ToolTipManager.sharedInstance().setDismissDelay(20000);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			System.out.println("ERROR: "+e1.getMessage());
		}
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0};
		getContentPane().setLayout(gridBagLayout);
		setTitle("CECIL");
		if (System.getProperty("os.name").toLowerCase().equals("linux") && System.getProperty("os.arch").toLowerCase().equals("arm")) {
			setTitle("CECIL RPi");
		}

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
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/cecil_title.png"));
			ImageIcon icon = new ImageIcon(img);
			JMenu iconItem = new JMenu();
			iconItem.setBorder(new EmptyBorder(5, 5, 5, 5));
			iconItem.setOpaque(false);
			iconItem.setEnabled(false);
			iconItem.setDisabledIcon(icon);
			iconItem.setIcon(icon);
			Dimension iconSize = new Dimension(170, 120);
			iconItem.setMaximumSize(iconSize);
			iconItem.setMinimumSize(iconSize);
			menuBar.add(iconItem);
		} catch (IOException e1) {
			System.out.println("Error creating logo: could not set logo image");
		}
		repaint();

		fileMenu = new JMenu("File");
		fileMenu.setToolTipText("File");
		fileMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fileMenu.requestFocus();
			}
		});
		menuBar.add(fileMenu);

		menuNew = new JMenuItem("New");
		menuNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNewClicked();
			}
		});

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
		menuExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onExitClicked();
			}
		});

		fileMenu.add(menuNew);
		fileMenu.add(menuOpen);
		fileMenu.add(menuSave);
		fileMenu.addSeparator();
		fileMenu.add(menuExit);

		settingsMenu = new JMenu("Settings");
		settingsMenu.setToolTipText("Settings");
		settingsMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				settingsMenu.requestFocus();
			}
		});
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
		helpMenu.setToolTipText("Help");
		helpMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				helpMenu.requestFocus();
			}
		});
		menuBar.add(helpMenu);

		menuWelcome = new JMenuItem("Welcome");
		menuDocumentation = new JMenuItem("Documentation");
		menuAbout = new JMenuItem("About CECIL");

		helpMenu.add(menuWelcome);
		helpMenu.add(menuDocumentation);
		helpMenu.addSeparator();
		helpMenu.add(menuAbout);
		
		ioMenu = new JMenu("Output to IO ports");
		ioMenu.setToolTipText("Check to enable output to physical IO ports (only available on Raspberry Pi)");
		ioPortsEnabled = false;
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-unchecked.png"));
			ioMenu.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		ioMenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ioMenu.requestFocus();
				if (ioMenu.isEnabled()) {
					try {
						ioPortsEnabled = !ioPortsEnabled;
						if (ioPortsEnabled) {
							Image img = ImageIO.read(getClass().getResource("/resources/vdk-checked.png"));
							ioMenu.setIcon(new ImageIcon(img));
						} else {
							Image img = ImageIO.read(getClass().getResource("/resources/vdk-unchecked.png"));
							ioMenu.setIcon(new ImageIcon(img));
						}
						ioMenu.setSelected(false);
						onIOCheckClicked();
					} catch (IOException e1) {
						System.out.println("Error creating buttons: could not set button icon");
					}
				}
			}
		});
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(ioMenu);

		setJMenuBar(menuBar);

		/*
		 * Panels
		 */
		northPanel = new JPanel();	
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northPanel.setBorder(new EmptyBorder(5, 10, 0, 10));

		centerLeftPanel = new JPanel();
		centerLeftPanel.setLayout(new GridLayout(1,1));
		centerLeftPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 10, 5), new TitledBorder(null, "Program - Untitled", TitledBorder.LEADING, TitledBorder.TOP, null, null)));

		centerRightPanel = new JPanel();
		centerRightPanel.setLayout(new GridLayout(2,1));

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);

		southPanel = new JPanel();
		southPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 20, 10), new TitledBorder(null, "Memory", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		//southPanel.setToolTipText("Each cell in the memory is an address."
		//+ " Addresses between 0-905 store user input data 906-1028 store other housekeeping data like registers, ports etc");
		southPanel.setLayout(new GridBagLayout());

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
				tblInput.clearSelection();
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
		xRegister.setToolTipText("Internal memory store for data within CPU.");
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
		yRegister.setToolTipText("Internal memory store for data within CPU.");
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
		accRegister.setToolTipText("Memory store for performing arithmetic and logical operations.");
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
		//yScroll.setToolTipText("Register in which intermediate arithmetic and logic results are stored.");
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
		lblCarry.setToolTipText("This flag is switched on when the value at any of the registers exceeds 1024 (max buffer size)");
		lblCarry.setBorder(BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5)));
		lblCarry.setOpaque(true);
		carryPanel.add(lblCarry);
		flagPanel.add(carryPanel);

		JPanel zeroPanel = new JPanel(new GridLayout(1,1));
		zeroPanel.setOpaque(false);
		zeroPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblZero = new JLabel("Zero", SwingConstants.CENTER);
		lblZero.setToolTipText("This flag is switched on when the value at any of the registers is equal to 0");
		lblZero.setBorder(BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5)));
		lblZero.setOpaque(true);
		zeroPanel.add(lblZero);
		flagPanel.add(zeroPanel);

		JPanel negativePanel = new JPanel(new GridLayout(1,1));
		negativePanel.setOpaque(false);
		negativePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblNegative = new JLabel("Negative", SwingConstants.CENTER);
		lblNegative.setToolTipText("this flag is switched on when the value at any of the registers is < 0");
		lblNegative.setBorder(BorderFactory.createCompoundBorder(new BevelBorder(BevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5)));
		lblNegative.setOpaque(true);
		negativePanel.add(lblNegative);
		flagPanel.add(negativePanel);

		/*
		 * Console
		 */		
		consolePanel = new JPanel();
		consolePanel.setLayout(new GridLayout(1,1));
		consolePanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 5, 10, 10), new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null)));
		centerRightPanel.add(consolePanel);

		txtConsole = new JList<String>();
		txtConsole.setToolTipText("Output console");

		txtConsole.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (errors != null && errors.size() > 0) {
					txtConsole.setToolTipText("Click on the error to see where the problem is");
				}
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (errors != null && errors.size() > 0) {
					highlightError();

				} else {
					txtConsole.removeSelectionInterval(txtConsole.getSelectedIndex(), txtConsole.getSelectedIndex());
				}
			}
		});
		JScrollPane consoleScroll = new JScrollPane(txtConsole);
		consoleScroll.setOpaque(false);
		consoleScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		consolePanel.add(consoleScroll);

		/*
		 * Input editor
		 */
		final InputTableModel mdlInput = new InputTableModel();
		mdlInput.addColumn("#");
		mdlInput.addColumn("Label");
		mdlInput.addColumn("Instruction");
		mdlInput.addColumn("Data");
		mdlInput.addColumn("Comments");
		mdlInput.addRow(new Object[]{0,"","","",";"});
		tblInput = new JTable(mdlInput) {
			/**
			 * Serial version UID to stop the warning.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void changeSelection(int row, int column, boolean toggle, boolean extend) {
				super.changeSelection(row, column, toggle, extend);

				if (editCellAt(row, column)) {
					Component editor = getEditorComponent();
					editor.requestFocusInWindow();
				}
			}
		};
		tblInput.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tblInput.getTableHeader().setReorderingAllowed(false);
		tblInput.getColumnModel().getColumn(0).setResizable(false);
		tblInput.getColumnModel().getColumn(0).setMinWidth(35);
		tblInput.getColumnModel().getColumn(0).setMaxWidth(35);
		JTableHeader headerforinput = tblInput.getTableHeader();

		ToolTipsforinput tips = new ToolTipsforinput();
		for (int column = 0; column < tblInput.getColumnCount(); column++) {
			TableColumn colNo = tblInput.getColumnModel().getColumn(column);
			if (column == 0) {
				tips.setToolTip(colNo, "Line Number");
			}
			if (column == 1) {
				tips.setToolTip(colNo, "Reference point to the next instruction");
			}
			if (column == 2) {
				tips.setToolTip(colNo, "Machine operation to be executed");
			}
			if (column == 3) {
				tips.setToolTip(colNo, "Reference to labelfield");
			}
			if (column == 4) {
				tips.setToolTip(colNo, "Comments");
			}
		}
		headerforinput.addMouseMotionListener(tips);
		/*JLabel headerRenderer = new DefaultTableCellRenderer();
	    String columnName = tblInput.getModel().getColumnName(0);
	    headerRenderer.setText(columnName);
	    headerRenderer.setBackground(Color.GRAY);
	    headerRenderer.setToolTipText("Wave");
	    TableColumnModel columnModel = tblInput.getColumnModel();
	    TableColumn englishColumn = columnModel.getColumn(0);
	    englishColumn.setHeaderRenderer((TableCellRenderer) headerRenderer);*/

		String[] temp = new String[instructionList.size()];
		instructionList.toArray(temp);
		comboBox = new JComboBox(temp);
		comboBox.setEditable(true);
		AutoCompleteDecorator.decorate(comboBox);

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				actionEvent.getSource();
			}
		};
		comboBox.addActionListener(actionListener);				
		comboBox.setRenderer(new MyComboBoxRenderer()); 				
		input = new JTextField();
		DefaultTableCellRenderer aligncenter = new DefaultTableCellRenderer();
		aligncenter.setHorizontalAlignment(JLabel.CENTER);
		tblInput.getColumnModel().getColumn(0).setCellRenderer(aligncenter);
		tblInput.setRowHeight(25);
		tblInput.setFillsViewportHeight(true);

		for (int i = 0; i < tblInput.getColumnCount(); i++) {
			TableColumn column = tblInput.getColumnModel().getColumn(i);

			if (i != 2) {
				column.setCellEditor(new DefaultCellEditor(input));
			} else {
				column.setCellEditor(new ComboBoxCellEditor(comboBox));
			}
			column.getCellEditor().addCellEditorListener(new CellEditorListener() {
				@Override
				public void editingStopped(ChangeEvent arg0) {
					if (currentProgram != null && !currentProgram.equals(getProgramCode())) {
						setButtonsEnabled(false);
					}
				}
				@Override
				public void editingCanceled(ChangeEvent arg0) {}
			});
		}

		tblInput.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int selectedRow = tblInput.getSelectedRow();
				int selectedColumn = tblInput.getSelectedColumn();

				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					mdlInput.insertRow(tblInput.getSelectedRow()+1, new Object[] {0, "", "", "",";"});
					if (currentProgram != null && !currentProgram.equals(getProgramCode())) {
						setButtonsEnabled(false);
					}
					break;
				case KeyEvent.VK_BACK_SPACE:
					if (selectedRow > 0) {
						mdlInput.removeRow(selectedRow);
						if (currentProgram != null && !currentProgram.equals(getProgramCode())) {
							setButtonsEnabled(false);
						}
						tblInput.setRowSelectionInterval(selectedRow-1, selectedRow-1);
						tblInput.getCellEditor().cancelCellEditing();
					} else if (selectedRow == 0 && selectedRow+1 == tblInput.getRowCount()){
						mdlInput.removeRow(selectedRow);
						if (currentProgram != null && !currentProgram.equals(getProgramCode())) {
							setButtonsEnabled(false);
						}
						mdlInput.addRow(new Object[] {0, "", "", "", ";"});
						tblInput.setRowSelectionInterval(selectedRow, selectedRow);
						tblInput.getCellEditor().cancelCellEditing();
					} else if (selectedRow == 0 && selectedRow+1 < tblInput.getRowCount()) {
						mdlInput.removeRow(selectedRow);
						if (currentProgram != null && !currentProgram.equals(getProgramCode())) {
							setButtonsEnabled(false);
						}
						tblInput.setRowSelectionInterval(selectedRow, selectedRow);
						tblInput.getCellEditor().cancelCellEditing();
					}
					break;
				case KeyEvent.VK_TAB:
					if (selectedColumn == 1) {
						System.out.println("Hello");
						tblInput.setRowSelectionInterval(selectedRow, selectedRow);
						tblInput.setColumnSelectionInterval(selectedColumn, selectedColumn);
						tblInput.requestFocus();
					}
					break;
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
		tblMemory.setToolTipText("Each cell in the memory is an address."
				+ " Addresses between 0-905 store user input data and 906-1028 store other housekeeping data like registers, ports etc");

		//		JScrollPane memoryScroll = new JScrollPane(tblMemory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//		memoryScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		//		memoryScroll.setOpaque(false);
		//		southPanel.add(memoryScroll);

		JScrollPane memoryScroll = new JScrollPane(tblMemory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		memoryScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		memoryScroll.setOpaque(false);

		GridBagConstraints gbc_south_table = new GridBagConstraints();
		gbc_south_table.fill = GridBagConstraints.BOTH;
		gbc_south_table.gridx = 0;
		gbc_south_table.gridy = 0;
		gbc_south_table.weightx = 1;
		gbc_south_table.weighty = 1;
		southPanel.add(memoryScroll, gbc_south_table);
	}

	/**
	 * Set the colours to the current theme.
	 * A theme consists of three colours; background, highlight, and inner panel colours.
	 */
	private void setupColours() {
		if (currentTheme != null && currentTheme[0] != null && currentTheme[1] != null && currentTheme[2] != null) {
			Color background = currentTheme[0];
			Color highlight = currentTheme[1];
			Color inner = currentTheme[2];
			getContentPane().setBackground(background);
			menuBar.setColour(highlight);
			LineBorder border = new LineBorder(highlight, 3, true);
			UIManager.put("TitledBorder.border", border);
			UIManager.put("Label.disabledForeground", Color.BLACK);

			northPanel.setBackground(background);
			centerLeftPanel.setBackground(background);
			centerRightPanel.setBackground(background);
			southPanel.setBackground(background);

			registerPanel.setBackground(background);
			xRegister.setBackground(inner);
			yRegister.setBackground(inner);
			accRegister.setBackground(inner);

			accRegister.setSelectionBackground(highlight);
			xRegister.setSelectionBackground(highlight);
			yRegister.setSelectionBackground(highlight);

			flagPanel.setBackground(background);
			lblCarry.setBackground(inner);
			lblZero.setBackground(inner);
			lblNegative.setBackground(inner);

			consolePanel.setBackground(background);
			txtConsole.setBackground(inner);
			txtConsole.setSelectionBackground(highlight);

			tblInput.setBackground(inner);
			tblInput.setSelectionBackground(highlight);
			tblMemory.setBackground(inner);
		} else {
			getContentPane().setBackground(UIManager.getColor("Panel.background"));
			menuBar.setColour(UIManager.getColor("Panel.background"));
			EtchedBorder border = new EtchedBorder();
			UIManager.put("TitledBorder.border", border);
			UIManager.put("Label.disabledForeground", Color.BLACK);

			northPanel.setBackground(UIManager.getColor("Panel.background"));
			centerLeftPanel.setBackground(UIManager.getColor("Panel.background"));
			centerRightPanel.setBackground(UIManager.getColor("Panel.background"));
			southPanel.setBackground(UIManager.getColor("Panel.background"));

			registerPanel.setBackground(UIManager.getColor("Panel.background"));
			xRegister.setBackground(Color.WHITE);
			yRegister.setBackground(Color.WHITE);
			accRegister.setBackground(Color.WHITE);

			accRegister.setSelectionBackground(UIManager.getColor("List.selectionBackground"));
			xRegister.setSelectionBackground(UIManager.getColor("List.selectionBackground"));
			yRegister.setSelectionBackground(UIManager.getColor("List.selectionBackground"));

			flagPanel.setBackground(UIManager.getColor("Panel.background"));
			lblCarry.setBackground(Color.WHITE);			
			lblZero.setBackground(Color.WHITE);
			lblNegative.setBackground(Color.WHITE);

			consolePanel.setBackground(UIManager.getColor("Panel.background"));
			txtConsole.setBackground(Color.WHITE);
			txtConsole.setSelectionBackground(UIManager.getColor("List.selectionBackground"));

			tblInput.setBackground(Color.WHITE);
			tblInput.setSelectionBackground(UIManager.getColor("List.selectionBackground"));
			tblMemory.setBackground(Color.WHITE);
		}
	}

	/**
	 * Set the icons for the menu bar buttons and action buttons.
	 * These icons change colour when hovered over.
	 */
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

	/**
	 * Set the icons for the status flags.
	 * These icons change colour depending on the flag state.
	 */
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

	/**
	 * Set font of all text components.
	 * The font sizes can be changed from small, medium, and large.
	 */
	private void setupFonts() {
		UIManager.put("ToolTip.font", new FontUIResource(currentFont));
		UIManager.put("Table.font", currentFont);

		input.setFont(currentFont);
		comboBox.setFont(currentFont);

		btnCompile.setFont(currentFont);
		btnRun.setFont(currentFont);
		btnStepThrough.setFont(currentFont);

		fileMenu.setFont(currentFont);
		settingsMenu.setFont(currentFont);
		helpMenu.setFont(currentFont);
		ioMenu.setFont(currentFont);
		menuNew.setFont(currentFont);
		menuOpen.setFont(currentFont);
		menuSave.setFont(currentFont);
		menuExit.setFont(currentFont);
		menuPreferences.setFont(currentFont);
		menuWelcome.setFont(currentFont);
		menuDocumentation.setFont(currentFont);
		menuAbout.setFont(currentFont);

		((TitledBorder)((CompoundBorder) flagPanel.getBorder()).getInsideBorder()).setTitleFont(currentFont);
		lblCarry.setFont(currentFont);
		lblZero.setFont(currentFont);
		lblNegative.setFont(currentFont);
		((TitledBorder)((CompoundBorder) registerPanel.getBorder()).getInsideBorder()).setTitleFont(currentFont);
		xRegister.setFont(currentFont);
		((TitledBorder)((CompoundBorder) xScroll.getBorder()).getInsideBorder()).setTitleFont(currentFont);
		yRegister.setFont(currentFont);
		((TitledBorder)((CompoundBorder) yScroll.getBorder()).getInsideBorder()).setTitleFont(currentFont);	
		accRegister.setFont(currentFont);
		((TitledBorder)((CompoundBorder) accScroll.getBorder()).getInsideBorder()).setTitleFont(currentFont);
		((TitledBorder)((CompoundBorder) consolePanel.getBorder()).getInsideBorder()).setTitleFont(currentFont);
		txtConsole.setFont(new Font("Consolas", Font.PLAIN, currentFont.getSize()));
		((TitledBorder)((CompoundBorder) centerLeftPanel.getBorder()).getInsideBorder()).setTitleFont(currentFont);
		tblInput.getTableHeader().setFont(currentFont);
		tblInput.setRowHeight(40);
		tblInput.setFont(currentFont);
		((TitledBorder)((CompoundBorder) southPanel.getBorder()).getInsideBorder()).setTitleFont(currentFont);
		tblMemory.getTableHeader().setFont(currentFont);
		tblMemory.setFont(currentFont);
		tblMemory.setRowHeight(30);
	}

	/**
	 * Set the current program code to display in the input editor.
	 */
	private void setupProgramCode() {		
		if (currentProgram != null && currentProgram.size() > 0) {
			loadProgramCode(currentProgram);
		}
	}

	//TODO Javadocing needed?
	static private String selectedString(ItemSelectable is) {
		Object selected[] = is.getSelectedObjects();
		return ((selected.length == 0) ? "null" : (String)selected[0]);
	} 

	//TODO Javadocing needed
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
	class ToolTipsforinput extends MouseMotionAdapter {
		HashMap tooltips = new HashMap();
		public void setToolTip(TableColumn col, String tooltip) {		    
			tooltips.put(col, tooltip);		    
		}
		public void mouseMoved(MouseEvent evt) {
			JTableHeader headerInput = (JTableHeader) evt.getSource();
			JTable inputTable = headerInput.getTable();
			int columnIndex = inputTable.getColumnModel().getColumnIndexAtX(evt.getX());
			if (columnIndex >= 0) {
				headerInput.setToolTipText((String) tooltips.get(inputTable.getColumnModel().getColumn(columnIndex)));
			}

		}
	}


	/**
	 * Custom JMenuBar class which allows the background colour to be changed.
	 * Call setColour to change the background colour.
	 * 
	 * MIT Open Source License
	 * @author Cathy Jin (cj8g10)
	 * Southampton University, United Kingdom
	 * @version 1.1
	 * 
	 * @date 07/11/2013
	 *
	 */
	private class BackgroundMenuBar extends JMenuBar {
		/**
		 * Serial version UID to stop the warning.
		 */
		private static final long serialVersionUID = 1L;
		private Color bgColour = Color.WHITE;

		/**
		 * Set the background colour.
		 * 
		 * @param colour Background colour.
		 */
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
	 * Custom DefaultTableModel class which displays the row numbers in the first column.
	 * 
	 * MIT Open Source License
	 * @author Cathy Jin (cj8g10)
	 * Southampton University, United Kingdom
	 * @version 1.1
	 * 
	 * @date 11/11/2013
	 *
	 */
	private class InputTableModel extends DefaultTableModel {
		/**
		 * Serial version UID to stop the warning.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getValueAt(int row, int column) {
			if (column == 0) {
				return row+1;
			}
			return super.getValueAt(row, column);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if (column == 0) {
				return false;
			}
			return true;
		}
	}

	/**
	 * Retrieves the program code from the input editor and passes it to the controller.
	 */
	private void onCompileClicked() {
		if (tblInput.getCellEditor() != null) {
			tblInput.getCellEditor().stopCellEditing();
		}
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
		tblInput.clearSelection();
	}

	/**
	 * Notifies the controller that the step through button has been clicked.
	 */
	private void onStepThroughClicked()  {
		controller.stepThroughClicked(tblInput.getSelectedRow());
	}

	/**
	 * Loads a new program and sets filename to Untitled.
	 * The user is also shown a warning if there are unsaved changes.
	 */
	private void onNewClicked() {
		if (!currentProgram.equals(getProgramCode())) {
			int returnValue = JOptionPane.showConfirmDialog(this, "You have not saved your program! Do you want to continue anyway?", "Warning", JOptionPane.YES_NO_OPTION);
			if (returnValue == JOptionPane.OK_OPTION) {
				clearVisualisations();
				loadNewProgram();
				setFilename("Untitled");
			}
		} else {
			clearVisualisations();
			loadNewProgram();
			setFilename("Untitled");
		}
	}

	/**
	 * Loads the input editor with an empty row.
	 */
	private void loadNewProgram() {
		ArrayList<ArrayList<String>> code = new ArrayList<ArrayList<String>>();
		ArrayList<String> newLine = new ArrayList<String>();
		newLine.add(".start");
		newLine.add("");
		newLine.add("");
		newLine.add(";");
		code.add(newLine);
		setProgramCode(code);
	}

	/**
	 * Opens a JFileChooser dialog to open a .cecil file.
	 */
	private void onOpenClicked() {
		if (!currentProgram.equals(getProgramCode())) {
			int returnValue = JOptionPane.showConfirmDialog(this, "You have not saved your program! Do you want to continue anyway?", "Warning", JOptionPane.YES_NO_OPTION);
			if (returnValue == JOptionPane.OK_OPTION) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CECIL File", "cecil");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					controller.fileOpened(fileChooser.getSelectedFile());
				}
			}
		} else {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CECIL File", "cecil");
			fileChooser.setFileFilter(filter);
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				controller.fileOpened(fileChooser.getSelectedFile());
			}
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
			currentProgram = getProgramCode();
		}
	}

	/**
	 * Exits the application.
	 */
	private void onExitClicked() {
		if (!currentProgram.equals(getProgramCode())) {
			int returnValue = JOptionPane.showConfirmDialog(this, "You have not saved your program! Do you want to continue anyway?", "Warning", JOptionPane.YES_NO_OPTION);
			if (returnValue == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}

	/**
	 * Notify the controller of the new IO port checkbox state.
	 */
	private void onIOCheckClicked() {
		controller.ioCheckClicked(ioPortsEnabled);
	}
	
	/**
	 * Loads a program into the input editor.
	 * 
	 * @param program An ArrayList of instruction lines. Each instruction is an ArrayList composed of three Strings.
	 */
	private void loadProgramCode(ArrayList<ArrayList<String>> program) {
		InputTableModel model = (InputTableModel) tblInput.getModel();
		model.setRowCount(0);
		if (program != null && program.size() > 0) {
			for (ArrayList<String> line : program) {
				Object[] data = new Object[line.size()+1];
				data[0] = program.indexOf(line)+1;
				for (int i = 0; i < line.size(); i++) {
					if (!line.get(i).isEmpty()) {
						line.set(i, line.get(i).trim());
						data[i+1] = line.get(i);
					} else {
						data[i+1] = "";
					}
				}
				model.addRow(data);
			}
		}
		model.fireTableDataChanged();
		currentProgram = getProgramCode();
	}

	/**
	 * Highlight and jump to the error line in the input editor.
	 * 
	 * @param errorNo Error index in errors.
	 */
	private void highlightError() {
		int errorNo = txtConsole.getSelectedIndex();
		if (errors != null && errors.size() > 0) {
			int lineNo = errors.get(errorNo).getLine() - 1;
			tblInput.setRowSelectionInterval(lineNo, lineNo);
			txtConsole.removeSelectionInterval(errorNo, errorNo);
			tblInput.scrollRectToVisible(new Rectangle(tblInput.getCellRect(lineNo, 0, true)));
		}
	}

	/**
	 * 
	 */
	public void highlightStepThrough(int currNo) {
		tblInput.clearSelection();
		
		tblInput.setRowSelectionInterval(currNo-1, currNo-1);
		tblInput.scrollRectToVisible(new Rectangle(tblInput.getCellRect(currNo-1, 0, true)));
	}
	
	/**
	 * Change the application font size.
	 * 
	 * @param font The new font.
	 * 
	 */
	public void setNewFont(String font){
		if(font == "Small"){
			currentFont = FONT_SMALL;
		} else if(font == "Medium"){
			currentFont = FONT_MEDIUM;
		} else if(font == "Large"){
			currentFont = FONT_LARGE;
		}
		setupFonts();
	}

	public void setNewColour(String color) {
		if (color == "Default") {
			currentTheme = DEFAULT_THEME;
		} else if (color == "Orange") {
			currentTheme = ORANGE_THEME;
		} else if (color == "Blue") {
			currentTheme = BLUE_THEME;
		} else if (color == "Green") {
			currentTheme = GREEN_THEME;
		}
		setupColours();
		repaint();
	}

	/*
	 * Interface methods
	 * 
	 */
	@Override
	public void setAccStack(ArrayList<String> values) {
		accRegister.setListData(new String[0]);
		if (values != null) {
			ArrayList<String> temp = new ArrayList<String>(values);
			Collections.reverse(temp);
			String[] list = new String[temp.size()];
			temp.toArray(list);
			accRegister.setListData(list);
			accRegister.setSelectedIndex(0);
		}
	}

	@Override
	public void setXStack(ArrayList<String> values) {
		xRegister.setListData(new String[0]);
		if (values != null) {
			ArrayList<String> temp = new ArrayList<String>(values);
			Collections.reverse(temp);
			String[] list = new String[temp.size()];
			temp.toArray(list);
			xRegister.setListData(list);
			xRegister.setSelectedIndex(0);
		}
	}

	@Override
	public void setYStack(ArrayList<String> values) {
		yRegister.setListData(new String[0]);
		if (values != null) {
			ArrayList<String> temp = new ArrayList<String>(values);
			Collections.reverse(temp);
			String[] list = new String[temp.size()];
			temp.toArray(list);
			yRegister.setListData(list);
			yRegister.setSelectedIndex(0);
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
	public void setConsoleResult(ArrayList<String> text) {
		errors = null;
		txtConsole.setListData(new String[0]);
		txtConsole.setForeground(Color.BLACK);
		if (text != null) {
			String[] list = new String[text.size()];
			text.toArray(list);
			txtConsole.setListData(list);
		}
	}

	@Override
	public void setConsoleError(ArrayList<OutputError> errors) {
		txtConsole.setListData(new String[0]);
		txtConsole.setForeground(Color.RED);
		if (errors != null) {
			String[] list = new String[errors.size()];
			for (int i = 0; i < errors.size(); i++) {
				list[i] = "Error at line "+errors.get(i).getLine()+": "+errors.get(i).getMessage();
			}
			txtConsole.setListData(list);
			this.errors = errors;
		}
	}

	@Override
	public void setMemoryAllocation(int[] memory) {
		DefaultTableModel mdlMemory = new DefaultTableModel();
		if (memory != null && memory.length > 0) {
			for(int i = 0; i < memory.length; i++) {
				if (memory[i] > -1) {
					mdlMemory.addColumn(i, new Object[]{memory[i]});
				} else {
					mdlMemory.addColumn(i, new Object[]{""});
				}
			}
		} else {
			for (int i = 0; i < 1024; i++) {
				mdlMemory.addColumn(i, new Object[]{""});
			}
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
		if (program != null && program.size() > 0) {
			currentProgram = program;
			loadProgramCode(currentProgram);
		} else {
			loadNewProgram();
		}
	}

	@Override
	public void setButtonsEnabled(boolean enabled) {
		txtConsole.setToolTipText("Output Console");
		btnRun.setEnabled(enabled);
		btnStepThrough.setEnabled(enabled);
	}

	@Override
	public void clearVisualisations() {
		setProgramCode(null);
		setFilename(null);
		setXStack(null);
		setYStack(null);
		setAccStack(null);
		setConsoleResult(null);
		setConsoleError(null);
		setCarryFlag(false);
		setZeroFlag(false);
		setNegativeFlag(false);
		setMemoryAllocation(null);
		repaint();
	}

	@Override
	public void setFilename(String filename) {
		if (filename != null) {
			((TitledBorder)((CompoundBorder) centerLeftPanel.getBorder()).getInsideBorder()).setTitle("Program - "+filename);
		} else {
			((TitledBorder)((CompoundBorder) centerLeftPanel.getBorder()).getInsideBorder()).setTitle("Program - Untitled");
		}
		repaint();
	}

	@Override
	public void setIOEnabled(boolean isEnabled) {
		ioMenu.setEnabled(isEnabled);
	}

}
