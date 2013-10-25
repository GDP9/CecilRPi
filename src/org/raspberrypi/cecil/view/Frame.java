package org.raspberrypi.cecil.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.ItemSelectable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.JMenuItem;
import java.awt.Component;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.AbstractTableModel;
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
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;



public class Frame extends JFrame {

	private JPanel contentPane;
	private JTable table_1;
	DefaultTableModel model;
	DefaultTableModel model_1;
	private	JScrollPane scrollPane;

	  String[] tooltips = { "American", "Japanese ", "Latin ", "English"};


	/**
	 * Create the frame.
	 */
	public Frame() {
		getContentPane().setBackground(Color.GREEN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 900);
		getContentPane().setLayout(new GridBagLayout());
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.CYAN);
		JPanel centerPanel = new JPanel();
		JPanel centerleftPanel = new JPanel();
		centerleftPanel.setBackground(Color.MAGENTA);
		centerleftPanel.setBorder(new TitledBorder(null, "Input Editor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JPanel centerrightPanel = new JPanel();
		centerrightPanel.setBackground(Color.BLUE);
		JPanel southPanel = new JPanel();
		southPanel.setBorder(new TitledBorder(null, "Memory Allocation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		southPanel.setLayout(new GridLayout(1,1));
		
		
		GridBagConstraints gbc_north = new GridBagConstraints();
		//gbc_register.anchor = GridBagConstraints.WEST;
		gbc_north.fill = GridBagConstraints.BOTH;
		//gbc_register.insets = new Insets(0, 0, 5, 5);
		//gbc_register.insets = new Insets(0, 0, 0, 5);
		gbc_north.gridx = 0;
		gbc_north.gridy = 0;
		gbc_north.weightx = 1;
		gbc_north.weighty = 0.05;
		getContentPane().add(northPanel, gbc_north);
		
		
		GridBagConstraints gbc_center = new GridBagConstraints();
		//gbc_register.anchor = GridBagConstraints.WEST;
		gbc_center.fill = GridBagConstraints.BOTH;
		//gbc_register.insets = new Insets(0, 0, 5, 5);
		//gbc_register.insets = new Insets(0, 0, 0, 5);
		gbc_center.gridx = 0;
		gbc_center.gridy = 1;
		gbc_center.weightx = 1;
		gbc_center.weighty = 0.75;
		getContentPane().add(centerPanel, gbc_center);
		
		GridBagConstraints gbc_south = new GridBagConstraints();
		//gbc_register.anchor = GridBagConstraints.WEST;
		gbc_south.fill = GridBagConstraints.BOTH;
		//gbc_register.insets = new Insets(0, 0, 5, 5);
		//gbc_register.insets = new Insets(0, 0, 0, 5);
		gbc_south.gridx = 0;
		gbc_south.gridy = 2;
		gbc_south.weightx = 1;
		gbc_south.weighty = 0.2;
		getContentPane().add(southPanel, gbc_south);
		
		
		
	
		JButton btnRun = new JButton("Run");
		northPanel.add(btnRun);
		
		JButton btnCompile = new JButton("Compile");
		northPanel.add(btnCompile);
		
		JButton btnStepThrough = new JButton("Step through");
		northPanel.add(btnStepThrough);
		southPanel.setLayout(new GridLayout(1,1));
	
		centerPanel.setLayout(new GridLayout(1,2));
		centerPanel.add(centerleftPanel);
		centerPanel.add(centerrightPanel);
		centerrightPanel.setLayout(new GridLayout(2,1));

		
		JPanel centerrighttopPanel = new JPanel();
		centerrighttopPanel.setLayout(new GridBagLayout());
		centerrightPanel.add(centerrighttopPanel);
		
		/*Registers view*/
		JPanel registerpanel = new JPanel();
		registerpanel.setBackground(Color.GREEN);
		registerpanel.setBorder(new TitledBorder(null, "Registers", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_register = new GridBagConstraints();
		//gbc_register.anchor = GridBagConstraints.WEST;
		gbc_register.fill = GridBagConstraints.BOTH;
		//gbc_register.insets = new Insets(0, 0, 5, 5);
		//gbc_register.insets = new Insets(0, 0, 0, 5);
		gbc_register.gridx = 0;
		gbc_register.gridy = 0;
		gbc_register.weightx = 1;
		gbc_register.weighty = 0.75;
		centerrighttopPanel.add(registerpanel, gbc_register);
		
	
			
			registerpanel.setLayout(new GridLayout(1,3));
			JTextArea xregister = new JTextArea();
			xregister.setSelectionColor(Color.MAGENTA);
			xregister.setSelectedTextColor(Color.YELLOW);
			xregister.setForeground(Color.PINK);
			xregister.setDisabledTextColor(Color.YELLOW);
			 Color color=new Color(255,0,0); 
			xregister.setBackground(color);
			xregister.setBorder(new TitledBorder(null, "X", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			xregister.setLineWrap(true);
			xregister.setText("           X");
			JTextArea yregister = new JTextArea();
			yregister.setBackground(Color.BLUE);
			yregister.setText("     Y\r\n");
			yregister.setLineWrap(true);
			yregister.setToolTipText("");
			yregister.setBorder(new TitledBorder(null, "Y", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			JTextArea accumulator = new JTextArea();
			accumulator.setBackground(Color.BLUE);
			accumulator.setBorder(new TitledBorder(null, "Accumulator", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			accumulator.setText("          A");
			registerpanel.add(xregister);
			registerpanel.add(yregister);
			registerpanel.add(accumulator);
			
			JPanel flagpanel = new JPanel();
			flagpanel.setBorder(new TitledBorder(null, "Flags", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			flagpanel.setLayout(new FlowLayout());
			GridBagConstraints gbc_flagpanel = new GridBagConstraints();
			gbc_flagpanel.fill = GridBagConstraints.BOTH;
			gbc_flagpanel.gridx = 0;
			gbc_flagpanel.gridy = 1;
			gbc_flagpanel.weightx = 1;
			gbc_register.weighty = 0.25;
			centerrighttopPanel.add(flagpanel, gbc_flagpanel);
			
			JButton btnCarry = new JButton("Carry");
			flagpanel.add(btnCarry);
			
			JButton btnZero = new JButton("Zero");
			flagpanel.add(btnZero);
			
			JButton btnNegative = new JButton("Negative");
			flagpanel.add(btnNegative);
			
			JPanel consolepanel = new JPanel();
			GridBagConstraints gbc_consolepanel = new GridBagConstraints();
			gbc_consolepanel.gridwidth = 12;
			gbc_consolepanel.insets = new Insets(0, 0, 0, 5);
			gbc_consolepanel.fill = GridBagConstraints.BOTH;
			gbc_consolepanel.gridx = 0;
			gbc_consolepanel.gridy = 7;
			centerrightPanel.add(consolepanel, gbc_consolepanel);
					
					/*Console view*/
					
					
				
					consolepanel.setBorder(new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					consolepanel.setForeground(Color.WHITE);
					GridBagLayout gbl_panel_1 = new GridBagLayout();
					gbl_panel_1.columnWidths = new int[]{287, 0};
					gbl_panel_1.rowHeights = new int[]{151, 1, 0};
					gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
					gbl_panel_1.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
					consolepanel.setLayout(gbl_panel_1);
					
					JTextArea textArea = new JTextArea();
					textArea.setBackground(Color.red);
					JScrollPane scrollPane_1 = new JScrollPane(textArea);
					GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
					gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
					gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
					gbc_scrollPane_1.gridx = 0;
					gbc_scrollPane_1.gridy = 0;
					consolepanel.add(scrollPane_1, gbc_scrollPane_1);
					
				
		/*Input editor view*/
		model = new DefaultTableModel();
		model.addColumn("Lable");
		model.addColumn("Instruction");
		model.addColumn("Data");
		model.addRow(new Object[]{"","",""});
		model.setRowCount(10);		
		GridBagLayout gbl_centerleftPanel = new GridBagLayout();
		gbl_centerleftPanel.columnWidths = new int[]{142, 0, 0, 0, 0};
		gbl_centerleftPanel.rowHeights = new int[]{242, 0, 0, 0, 0, 0, 0, 0};
		gbl_centerleftPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_centerleftPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centerleftPanel.setLayout(gbl_centerleftPanel);
		table_1 = new JTable(model);
		table_1.setBackground(Color.YELLOW);
		
		//table_1.setRowSelectionAllowed(false);
	
		setUpSportColumn(table_1, table_1.getColumnModel().getColumn(1));
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		//table_1.setBounds(15, 15, 365, 200);		
		table_1.setRowHeight(25);		
		table_1.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_1.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER) {			
				if(table_1.getSelectedRow()+1 == (table_1.getRowCount())){		
				
			model.addRow(new Object[]{"", "",""}); } }
			}
			public void keyReleased(KeyEvent e) { }
			public void keyTyped(KeyEvent e) { } } );

		scrollPane = new JScrollPane(table_1);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		centerleftPanel.add( scrollPane, gbc_scrollPane );
		
		
		
	model_1 = new DefaultTableModel();
	model_1.setColumnCount(100);
	JTable memory = new JTable(model_1);


	JScrollPane memoryscroll = new JScrollPane(memory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	memory.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	southPanel.add(memoryscroll);
	
	
		

		
		
			
	}

	

	 public void setUpSportColumn(JTable table,TableColumn sportColumn) {
		 ArrayList<String> listSomeString = new ArrayList<String>();        
	        listSomeString.add("LOAD");
	        listSomeString.add("JUMP");
	        listSomeString.add("Interrup");
	        listSomeString.add("None");
	        Java2sAutoComboBox comboBox = new Java2sAutoComboBox(listSomeString);
	        comboBox.setEditable(true);
	        ActionListener actionListener = new ActionListener() {
	            public void actionPerformed(ActionEvent actionEvent) {
	              ItemSelectable is = (ItemSelectable)actionEvent.getSource();
	              System.out.println(", Selected: " + selectedString(is));
	            }
	          };
	          comboBox.addActionListener(actionListener);
	       	  comboBox.setRenderer(new MyComboBoxRenderer());
	        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

			
			

}
	  static private String selectedString(ItemSelectable is) {
		    Object selected[] = is.getSelectedObjects();
		    return ((selected.length == 0) ? "null" : (String)selected[0]);
		  } 
	 
	 class MyComboBoxRenderer extends BasicComboBoxRenderer {
		    public Component getListCellRendererComponent(JList list, Object value,
		        int index, boolean isSelected, boolean cellHasFocus) {
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

		    public void replace(int i, int j, String s, AttributeSet attributeset)
		        throws BadLocationException {
		      super.remove(i, j);
		      insertString(i, s, attributeset);
		    }

		    public void insertString(int i, String s, AttributeSet attributeset)
		        throws BadLocationException {
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
		        if (!isCaseSensitive
		            && s1.toLowerCase().startsWith(s.toLowerCase()))
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
		      fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder,
		          1));
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
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");//setting a Napkin like look
						UIManager.put("ToolTip.background", new ColorUIResource(255, 140, 0));//setting the background of the tooltip
						Frame frame = new Frame();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}


