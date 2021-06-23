package fileReader;

import register.MapObjRegister;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Properties;

/**
 * EFileDecoder can decode the E-format file with the help of FileReader4EFormat
 * and store the data to an MapObjRegister
 */
public class EFileDecoder {

	fileReader.FileReader4EFormat reader = new fileReader.FileReader4EFormat();
	
	private String ePath = null;
	private String eFile = null;
	
	MapObjRegister obj = new MapObjRegister();
	
	
	public MapObjRegister getObjRegister(){
		return obj;
	}

	public void setConfigFile(String filename){
		reader.setConfigFile(filename);
	}
	
	public void setEPath(String path){
		this.ePath = path;
	}
	
	public void setEFile(String efile){
		this.eFile = efile;
	}
	
	public void decodeEfile() throws RemoteException {
		
		reader.setSourceFile(ePath + File.separator + eFile);
		Properties prop = reader.getConfigPro();
		if(prop.size()<1){
			System.out.println("Set a config file!");
			return;
		}
		String defaultId = prop.getProperty("storeid");
		if (defaultId == null)
			defaultId = "id";
		String tag = reader.getCurrentTag();
		while (tag != null) {
			System.out.println("eformat data tag : " + tag);
			HashMap<String, String> data = reader.readNextDataMap();
			String tagId = prop.getProperty(tag + ".storeid");
			if (tagId == null)
				tagId = defaultId;
			String[] idArray = tagId.split(";");
			while (data != null) {
				String objKey = "";
				for (String id : idArray) {
					objKey = objKey + data.get(id);
				}
				if (objKey != null) {
					obj.addDataObj(objKey, tag, data);
				}
				data = reader.readNextDataMap();
			}
			tag = reader.readToNextTag();
		}
	}


}
