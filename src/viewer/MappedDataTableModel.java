package viewer;

import register.MapObjRegister;

import javax.swing.table.DefaultTableModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

@SuppressWarnings("serial")
public class MappedDataTableModel extends DefaultTableModel {
	
	private MapObjRegister register = null;
	
	private String dataType = null;
	
	private boolean isDataAvailable;
	
	private Vector<Vector> dataV;

	private Vector<String> colV;

	private Vector<String> colVname;
	
	@SuppressWarnings("unchecked")
	public void setUpData(MapObjRegister reg, String type) {
		register = reg;
		dataType = type;
		if (reg == null || type == null) {
			isDataAvailable = false;
			return;
		}
		updateData(type);
	}
	
	@SuppressWarnings("unchecked")
	public void updateData(String type) {
		HashMap<String, Object> tabDevs = null;
		if (dataType != null) {
			tabDevs = register.getRegisteredDataObjs(type);
			if (tabDevs != null && tabDevs.size() > 0) {
				isDataAvailable = true;
			}else {
				return;
			}
		}
		boolean isColSet = false;
		colV = new Vector<String>();
		dataV = new Vector<Vector>();
		Collection<Object> dataC = tabDevs.values();
		for (Object d : dataC) {
			if (d == null) continue;
			HashMap<String, Object> dMap = (HashMap<String, Object>)d;
			if (!isColSet) {
				colV.addAll(dMap.keySet());
				isColSet = true;
			}
			Vector tmpV = new Vector();
			for (String s : colV) {
				tmpV.add(dMap.get(s));
			}
			dataV.add(tmpV);
		}
		setDataVector(dataV, colV);
		fireTableDataChanged();
	}

	public void loadDataToReg(String ID, String attr, String type, String val) {
		register.setLoadData(ID, attr, type, val);
	}

	public int getColumnCount() {
		if (isDataAvailable == false) {
			return 0;
		}
		return colV.size();
	}

	public int getRowCount() {
		if (isDataAvailable == false) {
			return 0;
		}
		return dataV.size();
	}

	public Object getValueAt(int row, int col) {
		if (!isDataAvailable)
			return null;
		Object dO = (dataV.get(row)).get(col);
		if (dO == null) return "not setted";
		return dO.toString();
	}

	public String getColumnName(int column) {
		if (!isDataAvailable)
			return null;
		return colV.get(column);
	}

	public Class getColumnClass(int c) {
		if (!isDataAvailable)
			return null;
		return String.class;
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

}
