package org.raspberrypi.cecil.view;

import java.awt.ItemSelectable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
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

import org.raspberrypi.cecil.controller.CecilController;
import org.raspberrypi.cecil.pojo.CecilInstruction;

public class Frame extends JFrame implements CecilViewInterface {
	private static final int WIDTH = 1100;
	private static final int HEIGHT = 750;
	private Font font = null;
	
	private ArrayList<CecilInstruction> instructions;
	private ArrayList<String> instructionList;
	
	private JPanel northPanel;
	private JPanel centerRightPanel;
	private JPanel centerLeftPanel;
	private JPanel southPanel;
	private JPanel consolePanel;
	private JPanel registerPanel;
	private JPanel flagPanel;
	
	private JList<String> xRegister;
	private JList<String> yRegister;
	private JList<String> accRegister;
	private JScrollPane xScroll;
	private JScrollPane yScroll;
	private JScrollPane accScroll;
	
	private JTextPane txtConsole;
	
	private JTable tblInput;
	private JTable tblMemory;
	
	private JButton btnCompile;
	private JButton btnRun;
	private JButton btnStepThrough;
	private JLabel lblCarry;
	private JLabel lblZero;
	private JLabel lblNegative;
	
	private BackgroundMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu settingsMenu;
	private JMenu helpMenu;
	
	private JTextField input;
	private Java2sAutoComboBox comboBox;
	
	private CecilController controller;
	
	/**
	 * Create the frame.
	 */
	public Frame(CecilController controller) {
		this.controller = controller;
		
		setupDefaultFrame();
		setupColours();
		setupButtonIcons();
		setupFlagIcons();
		setupFonts();
		
		ArrayList<String> examples = new ArrayList<String>();
		examples.add("0");
//		examples.add("2");
//		examples.add("3");
		
		setXStack(examples);
		setAccStack(examples);
		setYStack(examples);
		
		setVisible(true);
	}

	private void setupDefaultFrame() {
		/*
		 * Setup main frame and panels
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getContentPane().setLayout(new GridBagLayout());
		setTitle("CECIL");
		
		menuBar = new BackgroundMenuBar();
//		menuBar.setOpaque(true);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/resources/cecil_title.png").getPath());
		JMenuItem iconItem = new JMenuItem();
		iconItem.setBorder(new EmptyBorder(5, 5, 5, 5));
		iconItem.setIcon(icon);
		menuBar.add(iconItem);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem menuItem = new JMenuItem("A text only item");
		fileMenu.add(menuItem);
		
		settingsMenu = new JMenu("Settings");
		settingsMenu.setRolloverEnabled(true);
		menuBar.add(settingsMenu);
		
		JMenuItem fontchooser = new JMenuItem("Font chooser");
		settingsMenu.add(fontchooser);
		fontchooser.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent ae) {
//				   System.out.println("Hello");
//				   Frame frame = new Frame();
//				    (new FontChooser(frame)).setVisible(true);
				   }
				 });

		helpMenu = new JMenu("Help");
		helpMenu.setRolloverEnabled(true);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
		
		if (instructionList == null) {
			//Default instructions
			instructionList = new ArrayList<String>();
			instructionList.add("load");
			instructionList.add("print");
			instructionList.add("stop");
			instructionList.add("add");
			instructionList.add("insert");
		}
		
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
		  yRegister.setCellRenderer(new DefaultListCellRenderer(){
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
//		accRegister = new JTextPane();
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
		mdlInput.addRow(new Object[]{1,"","",""});
		
		tblInput = new JTable(mdlInput);
		tblInput.getTableHeader().setReorderingAllowed(false);
		tblInput.getColumnModel().getColumn(0).setResizable(false);
		tblInput.getColumnModel().getColumn(0).setMinWidth(30);
		tblInput.getColumnModel().getColumn(0).setMaxWidth(30);
		
		setUpInstructionColumn(tblInput, tblInput.getColumnModel().getColumn(2));
//       table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		input = new JTextField();      
		DefaultTableCellRenderer aligncenter = new DefaultTableCellRenderer();
       	aligncenter.setHorizontalAlignment(JLabel.CENTER);
       	tblInput.getColumnModel().getColumn(0).setCellRenderer(aligncenter);
       	tblInput.setRowHeight(25);                
       	tblInput.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(input));
       	tblInput.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(input));
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
		for (int i = 0; i < 1024; i++) {
			mdlMemory.addColumn(i, new Object[]{""});
		}
		
		tblMemory = new JTable(mdlMemory);
		tblMemory.getTableHeader().setReorderingAllowed(false);
		tblMemory.setEnabled(false);
		tblMemory.setFillsViewportHeight(true);
		tblMemory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		tblMemory.setOpaque(true);
		
		JScrollPane memoryScroll = new JScrollPane(tblMemory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		memoryScroll.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(5, 5, 5, 5), new BevelBorder(BevelBorder.LOWERED)));
		memoryScroll.setOpaque(true);
		//memoryScroll.setBackground(Color.BLUE);
//		tblMemory.setSize(memoryScroll.WIDTH, memoryScroll.HEIGHT);
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-directory.png"));
					fileMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-settings.png"));
					settingsMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				try {
					Image img = ImageIO.read(getClass().getResource("/resources/vdk-help.png"));
					helpMenu.setIcon(new ImageIcon(img));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
	
	private void setupFonts() {
		font = new Font("Arial", Font.PLAIN, 18);

		UIManager.put("ToolTip.font", new FontUIResource(font));
		FontUIResource font = new FontUIResource("Arial", Font.PLAIN, 18);
        UIManager.put("Table.font", font);
        
		input.setFont(font);
		comboBox.setFont(font);
		
		btnCompile.setFont(font);
		btnRun.setFont(font);
		btnStepThrough.setFont(font);
		
		fileMenu.setFont(font);
		settingsMenu.setFont(font);
		helpMenu.setFont(font);
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
	
	public void setUpInstructionColumn(JTable table, TableColumn instructionColumn) {
		comboBox = new Java2sAutoComboBox(instructionList);
		comboBox.setEditable(true);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				ItemSelectable is = (ItemSelectable)actionEvent.getSource();
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

	private void onCompileClicked() {
		ArrayList<ArrayList<String>> code = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < tblInput.getRowCount(); i++) {
			ArrayList<String> line = new ArrayList<String>();
			for (int j = 1; j < tblInput.getColumnCount(); j++) {
				line.add((String) tblInput.getValueAt(i, j));
			}
			code.add(line);
		}
		controller.compileClicked(code);
	}
	
	private void onRunClicked() {
		controller.runClicked();
	}
	
	private void onStepThroughClicked()  {
		controller.stepThroughClicked() ;
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
	public void setInstructionList(ArrayList<CecilInstruction> instructions) {
		this.instructions = instructions;
		instructionList = new ArrayList<String>();
		for (CecilInstruction instruction : instructions) {
			if (instruction != null && instruction.getInstructionName() != null) {
				instructionList.add(instruction.getInstructionName());
			}
		}
	}

	@Override
	public void setButtonsEnabled(boolean enabled) {
		btnRun.setEnabled(enabled);
		btnStepThrough.setEnabled(enabled);
	}
}