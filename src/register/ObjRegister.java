package register;

import java.util.HashMap;
import java.util.Set;

/**
 * Obj Register is the interface
 */
public interface ObjRegister {

	public boolean registDataObjType(String objType);
	
	public boolean registDataObjType(Class objClass);
	
	public Set<String> getRegisteredDataObjTypes();
	
	public boolean setDataObj(String objKey, Class objType, Object objMap);
	
	public boolean setDataObj(String objKey, String objType, Object objMap);
	
	public boolean addDataObj(String objKey, Class objType, Object objMap);
	
	public boolean addDataObj(String objKey, String objType, Object objMap);
	
	public boolean removeDataObj(String objKey, Class objType);
	
	public boolean removeDataObj(String objKey, String objType);
	
	public Object getRegisteredDataObj(String objKey, String objType);
	
	public Object getRegisteredDataObj(String objKey);
	
	public String getTypeofRegisteredDataObj(String objKey);
	
	public HashMap<String, Object> getRegisteredDataObjs(String objType);
	
	public boolean compare(ObjRegister reg);
	
}
