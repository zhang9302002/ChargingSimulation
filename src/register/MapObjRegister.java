package register;


import java.util.*;

/**
 * Map Obj Register is to store the data extracted from the "info.txt"
 * using HashMap<String, HashMap<String, HashMap<String, String>>> data structure
 */
public class MapObjRegister implements ObjRegister {
	private HashMap<String, HashMap<String, Object>> dataMap = new HashMap<String, HashMap<String, Object>>();

	@Override
	public boolean addDataObj(String objKey, Class objType, Object obj) {
		String objTypeStr = objType.getSimpleName();
		if (!dataMap.containsKey(objTypeStr)) {
			dataMap.put(objTypeStr, new HashMap<String, Object>());
		}
		HashMap<String, Object> objMap = dataMap.get(objType);
		if (objMap == null) {
			objMap = new HashMap<String, Object>();
			dataMap.put(objTypeStr, objMap);
		}
		if (objMap.containsKey(objKey))
			return false;
		objMap.put(objKey, obj);
		return true;
	}

	@Override
	public boolean addDataObj(String objKey, String objType, Object objMap) {
		if (!dataMap.containsKey(objType)) {
			dataMap.put(objType, new HashMap<String, Object>());
		}
		HashMap<String, Object> objMaps = dataMap.get(objType);
		if (objMaps == null) {
			objMaps = new HashMap<String, Object>();
			objMaps.put(objType, objMaps);
		}
		if (objMaps.containsKey(objKey))
			return false;
		objMaps.put(objKey, objMap);
		return true;
	}


	@Override
	public boolean setDataObj(String objKey, Class objType, Object objMap) {
		String objTypeStr = objType.getSimpleName();
		if (!dataMap.containsKey(objTypeStr)) return false;
		HashMap<String, Object> objMaps = dataMap.get(objTypeStr);
		if (objMaps == null) return false;
		if (!objMaps.containsKey(objKey)) return false;
		objMaps.put(objKey, objMap);
		return true;
	}

	@Override
	public boolean setDataObj(String objKey, String objType, Object objMap) {
		if (!dataMap.containsKey(objType)) return false;
		HashMap<String, Object> objMaps = dataMap.get(objType);
		if (objMaps == null) return false;
		if (!objMaps.containsKey(objKey)) return false;
		objMaps.put(objKey, objMap);
		return true;
	}

	public boolean setLoadData(String ID, String attr, String type, String val) {
		if(type == null || ID == null || val == null)
			return false;
		Object d = getRegisteredDataObj(ID, type);
		HashMap<String, Object> dMap = (HashMap<String, Object>)d;
		if(dMap == null)
			return false;
		dMap.put(attr, val);
		return true;
	}

	@Override
	public Object getRegisteredDataObj(String objKey, String objType) {
		if (!dataMap.containsKey(objType))
			return null;
		HashMap<String, Object> objMap = dataMap.get(objType);
		if (objMap == null)
			return null;
		return objMap.get(objKey);
	}

	@Override
	public Set<String> getRegisteredDataObjTypes() {
		return dataMap.keySet();
	}

	@Override
	public HashMap<String, Object> getRegisteredDataObjs(String objType) {
		return dataMap.get(objType);
	}

	@Override
	public boolean registDataObjType(String objType) {
		if (!dataMap.containsKey(objType))
			return false;
		dataMap.put(objType, new HashMap<String, Object>());
		return true;
	}

	@Override
	public boolean registDataObjType(Class objClass) {
		if (!dataMap.containsKey(objClass.getSimpleName()))
			return false;
		dataMap.put(objClass.getSimpleName(), new HashMap<String, Object>());
		return true;
	}

	@Override
	public boolean removeDataObj(String objKey, Class objType) {
		String objTypeStr = objType.getSimpleName();
		if (!dataMap.containsKey(objTypeStr))
			return false;
		HashMap<String, Object> objMaps = dataMap.get(objTypeStr);
		if (objMaps == null)
			return false;
		if (!objMaps.containsKey(objKey))
			return false;
		objMaps.remove(objKey);
		return true;
	}

	@Override
	public boolean removeDataObj(String objKey, String objType) {
		if (!dataMap.containsKey(objType))
			return false;
		HashMap<String, Object> objMaps = dataMap.get(objType);
		if (objMaps == null)
			return false;
		if (!objMaps.containsKey(objKey))
			return false;
		objMaps.remove(objKey);
		return true;
	}

	@Override
	public Object getRegisteredDataObj(String objKey) {
		Collection<HashMap<String, Object>> c = dataMap.values();
		for (HashMap<String, Object> map : c) {
			if (map.containsKey(objKey)) {
				return map.get(objKey);
			}
		}
		return null;
	}

	@Override
	public String getTypeofRegisteredDataObj(String objKey) {
		Set<String> keySet = dataMap.keySet();
		for (String tag : keySet) {
			if ((dataMap.get(tag)).containsKey(objKey)) {
				return tag;
			}
		}
		return null;
	}


	public boolean compare(ObjRegister reg) {
		boolean isSame = true;
		Set<String> keySet = dataMap.keySet();
		Set<String> keySet2 = reg.getRegisteredDataObjTypes();
		for (String key : keySet) {
			if (!keySet2.contains(key)) {
				System.out.println("dst reg dose not contains data type as "
						+ key);
				isSame = false;
				continue;
			}
			HashMap<String, Object> dataMap = getRegisteredDataObjs(key);
			Set<String> idSet = dataMap.keySet();
			for (String id : idSet) {
				HashMap<String, Object> dataObj = (HashMap<String, Object>) dataMap
						.get(id);
				HashMap<String, Object> dataObj2 = (HashMap<String, Object>)reg.getRegisteredDataObj(id, key);
				if (dataObj2 == null) {
					System.out
							.println("dst reg dos not contains data obj id as "
									+ id);
					isSame = false;
					continue;
				}
				Set<String> kSet = dataObj.keySet();
				Set<String> kSet2 = dataObj2.keySet();
				for (String k : kSet) {
					if (!kSet2.contains(k)) {
						System.out.println("dst reg obj id " + id
								+ " does not contains key as " + k);
						isSame = false;
						continue;
					}
					Object obj = dataObj.get(k);
					Object obj2 = dataObj2.get(k);
					if (obj instanceof String && obj2 instanceof String) {
						String s1 = (String) obj;
						String s2 = (String) obj2;
						if (!s1.trim().equalsIgnoreCase(s2.trim())) {
							System.out.println("dst reg obj id " + id
									+ "  key as " + k + " value different v1 "
									+ s1 + " v2 " + s2);
							isSame = false;
						}
					}
					if (obj instanceof List
							&& obj2 instanceof List) {
						List<String> s1 = (List<String>) obj;
						List<String> s2 = (List<String>) obj2;
						if (!s1.equals(s2)) {
							System.out.println("dst reg obj id " + id
									+ "  key as " + k + " value different v1 "
									+ s1 + " v2 " + s2);
							isSame = false;
						}
					}
				}
			}

		}
		return isSame;
	}

}
