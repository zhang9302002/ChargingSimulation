package viewer;

import fileReader.EFileDecoder;
import register.MapObjRegister;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRelation;
import javax.accessibility.AccessibleRelationSet;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class RegisterViewer extends JFrame {
	JTable tableView;
	JScrollPane scrollpane;
	Dimension origin = new Dimension(0, 0);

	JComboBox selectDevType = null;

	JLabel filterInfoLabel;
	JLabel footerLabel;

	JTextField keywordTextField;
	JTextField footerTextField;

	JCheckBox fitWidth;
	JButton filterButton;

	JPanel controlPanel;
	JScrollPane tableAggregate;

	final int INITIAL_ROWHEIGHT = 15;

	MapObjRegister register = null;
	private boolean isDataAvailable;
	private HashMap<String, Object> tabDevs;
	private ArrayList<Entry<String, Object>> tableData;
	private String[] tableColNames;
	private MappedDataTableModel dataModel;
	private TableRowSorter sorter;
	private JLabel infoLabel;

	/**
	 * main method allows us to run as a standalone demo.
	 */
	public static void main(String[] args) {
		EFileDecoder decoder = new EFileDecoder();
		decoder.setConfigFile("lib" + File.separator + "NariEFormatReader.properties");
		decoder.setEPath(System.getProperty("user.dir"));
		decoder.setEFile("lib" + File.separator + "info.txt");
		try{
			decoder.decodeEfile();
			MapObjRegister register = decoder.getObjRegister();
			RegisterViewer demo = new RegisterViewer(register);
			demo.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * TableDemo Constructor
	 */
	public RegisterViewer(MapObjRegister reg) {
		register = reg;
		setLayout(new BorderLayout());
		controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
		JPanel comboPanel = new JPanel(new GridLayout(1, 2));
		JPanel filterPanel = new JPanel(new GridLayout(1, 2));

		getContentPane().add(controlPanel, BorderLayout.NORTH);
		Vector relatedComponents = new Vector();

		// ComboBox for selection modes.
		JPanel selectMode = new JPanel();
		selectMode.setLayout(new BoxLayout(selectMode, BoxLayout.X_AXIS));
		selectMode.setBorder(new TitledBorder("select data type"));

		selectDevType = new JComboBox() {
			public Dimension getMaximumSize() {
				return getPreferredSize();
			}
		};
		if (register != null) {
			Set<String> devTypes = register.getRegisteredDataObjTypes();
			if (devTypes != null) {
				for (String t : devTypes) {
					selectDevType.addItem(t);
				}
			}
		}
		selectDevType.setSelectedIndex(1);

		selectDevType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String devType = (String) cb.getSelectedItem();
				int objNum = register.getRegisteredDataObjs(devType).size();
				infoLabel.setText(objNum + " instances");
				dataModel.updateData(devType);
				dataModel.fireTableDataChanged();
				tableView.repaint();
			}
		});

		selectMode.add(Box.createHorizontalStrut(2));
		selectMode.add(selectDevType);
		selectMode.add(Box.createHorizontalGlue());
		infoLabel = new JLabel();
		infoLabel.setText("0000 instances");
		selectMode.add(infoLabel);
		comboPanel.add(selectMode);

		// Create the table.
		tableAggregate = createTable((String) selectDevType.getSelectedItem());
		getContentPane().add(tableAggregate, BorderLayout.CENTER);

		filterPanel
				.setBorder(new TitledBorder("enter key word for row filter"));
		keywordTextField = new JTextField("");
		filterButton = new JButton("Filter");
		filterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (keywordTextField.getText().trim().length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter(keywordTextField
							.getText()));
				}
			}
		});

		filterPanel.add(keywordTextField);
		filterPanel.add(filterButton);

		// add everything
		controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
		controlPanel.add(comboPanel);
		controlPanel.add(filterPanel);

		setSize(800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	} // TableDemo()

	public void repaintTable(String devType) {
		dataModel.updateData(devType);
		dataModel.fireTableDataChanged();
		tableView.repaint();
	}

	public void repaintFilter(String keyWords) {
		if (keyWords.trim().length() == 0) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter(keyWords));
		}
	}
	/**
	 * Sets the Accessibility MEMBER_OF property to denote that these components
	 * work together as a group. Each object is set to be a MEMBER_OF an array
	 * that contains all of the objects in the group, including itself.
	 * 
	 * @param components
	 *            The list of objects that are related
	 */
	void buildAccessibleGroup(Vector components) {

		AccessibleContext context = null;
		int numComponents = components.size();
		Object[] group = components.toArray();
		Object object = null;
		for (int i = 0; i < numComponents; ++i) {
			object = components.elementAt(i);
			if (object instanceof Accessible) {
				context = ((Accessible) components.elementAt(i))
						.getAccessibleContext();
				context.getAccessibleRelationSet().add(
						new AccessibleRelation(AccessibleRelation.MEMBER_OF,
								group));
			}
		}
	} // buildAccessibleGroup()

	/**
	 * Sets up accessibility relationships to denote that one object controls
	 * another. The CONTROLLER_FOR property is set on the controller object, and
	 * the CONTROLLED_BY property is set on the target object.
	 */
	private void setAccessibleController(JComponent controller,
			JComponent target) {
		AccessibleRelationSet controllerRelations = controller
				.getAccessibleContext().getAccessibleRelationSet();
		AccessibleRelationSet targetRelations = target.getAccessibleContext()
				.getAccessibleRelationSet();

		controllerRelations.add(new AccessibleRelation(
				AccessibleRelation.CONTROLLER_FOR, target));
		targetRelations.add(new AccessibleRelation(
				AccessibleRelation.CONTROLLED_BY, controller));
	} // setAccessibleController()

	public JScrollPane createTable(String devType) {
		dataModel = new MappedDataTableModel();
		dataModel.setUpData(register, devType);

		// Create the table
		tableView = new JTable(dataModel) {
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int column) {
				Component component = super.prepareRenderer(renderer, row,
						column);

				if (row % 2 == 0) {
					component.setBackground(Color.white);
				}
				if (row % 2 == 1) {
					component.setBackground(new Color(206, 231, 255));
				}

				return component;
			}
			public boolean isCellEditable(int row,int column){
				return true;
			}
		};

		sorter = new TableRowSorter(dataModel);
		tableView.setRowSorter(sorter);
		tableView.setRowHeight(INITIAL_ROWHEIGHT);
		scrollpane = new JScrollPane(tableView);
		return scrollpane;
	}


	void updateDragEnabled(boolean dragEnabled) {
		tableView.setDragEnabled(dragEnabled);
		keywordTextField.setDragEnabled(dragEnabled);
		footerTextField.setDragEnabled(dragEnabled);
	}

}