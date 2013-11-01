package org.raspberrypi.cecil.view;

import java.awt.ItemSelectable;

import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.border.TitledBorder;

public class Frame extends JFrame implements CecilViewInterface {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 700;
	private Font schoolbellFont = null;
	
	private DefaultTableModel mdlInput;
	String[] tooltips = { "American", "Japanese ", "Latin ", "English"};
	private ArrayList<String> instructionSet;
	
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
	
	private JButton btnFile;
	private JButton btnCompile;
	private JButton btnRun;
	private JButton btnStepThrough;
	private JButton btnSettings;
	private JButton btnHelp;
	private JButton btnCarry;
	private JButton btnZero;
	private JButton btnNegative;
	
	/**
	 * Create the frame.
	 */
	public Frame() {
		setupDefaultFrame();
		setupColours();
		setupButtonIcons();
		setupFlagIcons();
		setupFonts();
	}

	private void setupDefaultFrame() {
		try {
			schoolbellFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/Schoolbell.ttf"));
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * Setup main frame and panels
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().setLayout(new GridBagLayout());
		
		if (instructionSet == null) {
			//Default instructions
			instructionSet = new ArrayList<String>();
			instructionSet.add("Instruction 1");
			instructionSet.add("Instruction 2");
			instructionSet.add("Instruction 3");
		}
		
		northPanel = new JPanel();	
		northPanel.setLayout(new GridLayout(1,3));
		
		centerLeftPanel = new JPanel();
		centerLeftPanel.setLayout(new GridLayout(1,1));
		centerLeftPanel.setBorder(new TitledBorder(null, "Input Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		centerRightPanel = new JPanel();
		centerRightPanel.setLayout(new GridLayout(2,1));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(centerLeftPanel);
		centerPanel.add(centerRightPanel);
		
		southPanel = new JPanel();
		southPanel.setBorder(new TitledBorder(null, "Memory", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		btnFile = new JButton("File");
		btnFile.setToolTipText("File");
		System.out.println(UIManager.getDefaults().getFont("Button.font").getFontName());
		topLeft.add(btnFile);
		
		btnCompile = new JButton();
		btnCompile.setToolTipText("Compile");
		topCentre.add(btnCompile);
		
		btnRun = new JButton();
		btnRun.setToolTipText("Run");
		topCentre.add(btnRun);
		
		btnStepThrough = new JButton();
		btnStepThrough.setToolTipText("Step through");
		topCentre.add(btnStepThrough);
		
		btnSettings = new JButton("Settings");
		btnSettings.setToolTipText("Settings");
		topRight.add(btnSettings);
		
		btnHelp = new JButton("Help");
		btnHelp.setToolTipText("Help");
		topRight.add(btnHelp);
		
		/*
		 * Registers
		 */
		JPanel centerRightTopPanel = new JPanel();
		centerRightTopPanel.setLayout(new GridBagLayout());
		centerRightPanel.add(centerRightTopPanel);
		
		registerPanel = new JPanel();
		registerPanel.setLayout(new GridLayout(1,3));
		registerPanel.setBorder(new TitledBorder(null, "Registers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
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
		xRegister.setBorder(new TitledBorder(null, "X", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		xRegister.setText("0");
		//Center the text
		StyledDocument doc = xRegister.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		/*
		 * Y register
		 */
		yRegister = new JTextPane();
		yRegister.setText("0");
		yRegister.setToolTipText("Y register");
		yRegister.setBorder(new TitledBorder(null, "Y", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		doc = yRegister.getStyledDocument();
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		/*
		 * Acc register
		 */
		accRegister = new JTextPane();
		accRegister.setBorder(new TitledBorder(null, "Accumulator", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		accRegister.setText("0");
		doc = accRegister.getStyledDocument();
		center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		/*
		 * Add the register panes
		 */
		registerPanel.add(xRegister);
		registerPanel.add(yRegister);
		registerPanel.add(accRegister);
		
		/*
		 * Flags
		 */
		flagPanel = new JPanel();
		flagPanel.setBorder(new TitledBorder(null, "Status Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		flagPanel.setLayout(new FlowLayout());
		
		GridBagConstraints gbc_flagpanel = new GridBagConstraints();
		gbc_flagpanel.fill = GridBagConstraints.BOTH;
		gbc_flagpanel.gridx = 0;
		gbc_flagpanel.gridy = 1;
		gbc_flagpanel.weightx = 1;
		gbc_flagpanel.weighty = 0.1;
		centerRightTopPanel.add(flagPanel, gbc_flagpanel);
		
		btnCarry = new JButton("Carry");
		btnCarry.setFocusable(false);
		
		btnZero = new JButton("Zero");
		btnZero.setFocusable(false);
		
		btnNegative = new JButton("Negative");
		btnNegative.setFocusable(false);
		
		flagPanel.add(btnCarry);
		flagPanel.add(btnZero);
		flagPanel.add(btnNegative);
		
		/*
		 * Console
		 */		
		consolePanel = new JPanel();
		consolePanel.setLayout(new GridLayout(1,1));
		consolePanel.setBorder(new TitledBorder(null, "Output console", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerRightPanel.add(consolePanel);
					
		txtConsole = new JTextPane();
		consolePanel.add(new JScrollPane(txtConsole));
		
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
		centerLeftPanel.add(new JScrollPane(tblInput), gbc_scrollPane);
		
		DefaultTableModel mdlMemory = new DefaultTableModel();
		mdlMemory.setColumnCount(100);
		
		tblMemory = new JTable(mdlMemory);
		tblMemory.setFillsViewportHeight(true);

		tblMemory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		southPanel.add(new JScrollPane(tblMemory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
	
//	private void setupColours() {
//		northPanel.setBackground(new Color(204,229,255));
//		centerLeftPanel.setBackground(new Color(255, 255, 102));
//		southPanel.setBackground(new Color(204,229,255));
//		
//		registerPanel.setBackground(new Color(226,255,147));
//		xRegister.setBackground(new Color(255,204,178));
//		yRegister.setBackground(new Color(182,252,207));
//		accRegister.setBackground(new Color(229,204,255));
//		
//		flagPanel.setBackground(new Color(226,255,147));
//		btnCarry.setBackground(new Color(255,255,204));
//		btnZero.setBackground(new Color(255,255,204));
//		btnNegative.setBackground(new Color(255,255,204));
//		
//		consolePanel.setBackground(new Color(226,255,147));
//		txtConsole.setBackground(new Color(226,255,147));
//		
//		tblInput.setBackground(new Color(255, 255, 102));
//		tblMemory.setBackground(new Color(204,229,255));
//	}
	
	private void setupColours() {
		northPanel.setBackground(new Color(204, 229, 255));
		centerLeftPanel.setBackground(new Color(255, 255, 102));
		southPanel.setBackground(new Color(204, 229, 255));

		registerPanel.setBackground(new Color(255, 243, 66));
		xRegister.setBackground(new Color(246, 238, 10));
		yRegister.setBackground(new Color(255, 251, 105));
		accRegister.setBackground(new Color(255, 255, 0));

		flagPanel.setBackground(new Color(255, 243, 66));
		btnCarry.setBackground(new Color(255, 255, 204));
		btnZero.setBackground(new Color(255, 255, 204));
		btnNegative.setBackground(new Color(255, 255, 204));

		consolePanel.setBackground(new Color(255, 243, 66));
		txtConsole.setBackground(new Color(255, 243, 66));

		tblInput.setBackground(new Color(255, 243, 93));
		// tblInput.setBackground(new Color(255, 255, 102));
		tblMemory.setBackground(new Color(204, 229, 255));
	}
	
	private void setupButtonIcons() {
		//File
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-directory.png"));
			btnFile.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-directory-colour.png"));
			btnFile.setRolloverIcon(new ImageIcon(img));
			
			
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
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
		
		//Settings
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-settings.png"));
			btnSettings.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-settings-colour.png"));
			btnSettings.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		//Help
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-help.png"));
			btnHelp.setIcon(new ImageIcon(img));
			
			img = ImageIO.read(getClass().getResource("/resources/vdk-help-colour.png"));
			btnHelp.setRolloverIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
	}
	
	private void setupFlagIcons() {
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-light-colour.png"));
			btnCarry.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-light-colour.png"));
			btnZero.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
		
		try {
			Image img = ImageIO.read(getClass().getResource("/resources/vdk-light.png"));
			btnNegative.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println("Error creating buttons: could not set button icon");
		}
	}
	
	private void setupFonts() {
		schoolbellFont = schoolbellFont.deriveFont(20f);
		UIManager.put("ToolTip.font", new FontUIResource(schoolbellFont));
		
		btnFile.setFont(schoolbellFont);
		btnCompile.setFont(schoolbellFont);
		btnRun.setFont(schoolbellFont);
		btnStepThrough.setFont(schoolbellFont);
		btnSettings.setFont(schoolbellFont);
		btnHelp.setFont(schoolbellFont);
		((TitledBorder) flagPanel.getBorder()).setTitleFont(schoolbellFont);
		btnCarry.setFont(schoolbellFont);
		btnZero.setFont(schoolbellFont);
		btnNegative.setFont(schoolbellFont);
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
		
//		for (int i = 0; i < tblInput.getColumnCount(); i ++) {
//		    TableColumn col = tblInput.getColumnModel().getColumn(i);
//		    col.setCellEditor(new MyTableCellEditor());
//		}
//		
//		for (int i = 0; i < tblMemory.getColumnCount(); i ++) {
//		    TableColumn col = tblMemory.getColumnModel().getColumn(i);
//		    col.setCellEditor(new MyTableCellEditor());
//		}
	}
	
//	public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
//	    JComponent component = new JTextField();
//	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
//	        ((JTextField)component).setText((String)value);
//	        ((JTextField)component).setFont(schoolbellFont);
//	        return component;
//	    }
//		@Override
//		public Object getCellEditorValue() {
//			return null;
//		}
//	}
	
	public void setUpInstructionColumn(JTable table, TableColumn instructionColumn) {
		Java2sAutoComboBox comboBox = new Java2sAutoComboBox(instructionSet);
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
					list.setToolTipText(tooltips[index]);//get the tooltip according the selected item's index
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
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("ToolTip.background", new ColorUIResource(255, 140, 0));//setting the background of the tooltip
			Frame frame = new Frame();
			UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");//setting a Napkin like look
			SwingUtilities.updateComponentTreeUI(frame);
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
	public void setInstructionList(ArrayList<String> instructions) {
		instructionSet = instructions;
	}

	@Override
	public void setAccStack(ArrayList<String> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setXStack(ArrayList<String> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setYStack(ArrayList<String> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCarryFlag(boolean flagIsOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZeroFlag(boolean flagIsOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNegativeFlag(boolean flagIsOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConsoleText(ArrayList<String> text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMemoryAllocation(ArrayList<String> values) {
		// TODO Auto-generated method stub
		
	}
}