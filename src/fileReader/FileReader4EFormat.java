package fileReader;

import java.io.*;
import java.util.*;

/**
 * FileReader4EFormat can extract data from the E-format file "info.txt"
 */
public class FileReader4EFormat {

	final static String TAGSTARTER = "TagStarter";

	final static String TAGENDER = "TagEnder";

	final static String COMMENTSTARTER = "CommentStarter";

	final static String ATTRIBUTENAMESTARTER = "AttributeNameStarter";

	final static String DATALINESTARTER = "DataLineStarter";

	final static String ATTRIBUTEBREAKER = "AttributeBreaker";

	final static String DATABREAKER = "DataBreaker";

	private String configFile = null;

	private Properties configPro = new Properties();

	private String sourceFile = null;

	private BufferedReader br = null;

	private String currentTag = null;

	private String currentDataLine = null;

	private List<String> currentAttList = new ArrayList<String>();

	public Properties getConfigPro() {
		return configPro;
	}

	public boolean setConfigFile(String fileName) {
		this.configFile = fileName;
		if (fileName == null)
			return false;
		try {
			FileReader fr = new FileReader(this.configFile);
			configPro.load(fr);
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setSourceFile(String fileName) {
		this.sourceFile = fileName;
		if (fileName == null)
			return false;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					this.sourceFile));
			br = new BufferedReader(isr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("E File cannot be found.");
			return false;
		}
		return true;
	}

	public String getCurrentTag() {
		if (currentTag != null)
			return currentTag;
		String curLine = null;
		if (br == null)
			return null;
		try {
			curLine = br.readLine();
			currentTag = matchTagStarter(curLine);
			while (currentTag == null) {
				curLine = br.readLine();
				if (curLine == null) {
					System.out.println("end of file without any tag found");
					br.close();
					br = null;
					return null;
				}
				currentTag = matchTagStarter(curLine);
			}
			curLine = br.readLine();
			while (!matchAttributeNameLine(curLine)) {
				curLine = br.readLine();
			}
			if (configPro == null)
				return null;
			String attrbreaker = configPro.getProperty(ATTRIBUTEBREAKER);
			StringTokenizer st = null;
			if (attrbreaker.trim().equals("")) {
				st = new StringTokenizer(curLine, " ");
			} else {
				st = new StringTokenizer(curLine, attrbreaker);
			}
			currentAttList.clear();
			st.nextToken();
			while (st.hasMoreTokens()) {
				String sss = st.nextToken();
				currentAttList.add(sss);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return currentTag;
	}

	public List<String> getCurrentTagAtrributeName() {
		if (currentTag == null)
			return null;
		return currentAttList;
	}

	public String readToNextTag() {
		currentTag = null;
		return getCurrentTag();
	}

	public HashMap<String, String> readNextDataMap() {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		String dataLine = readNextDataLine();
		// System.out.println(dataLine);
		if (dataLine == null)
			return null;
		if (configPro == null)
			return null;
		String dataBreaker = configPro.getProperty(DATABREAKER);
		StringTokenizer st = null;
		if (dataBreaker.trim().equals("")) {
			st = new StringTokenizer(dataLine, " ");
		} else {
			st = new StringTokenizer(dataLine, dataBreaker);
		}
		st.nextToken();
		int i = 0;
		while (st.hasMoreTokens()) {
			dataMap.put(currentAttList.get(i), st.nextToken());
			i++;
		}
		return dataMap;
	}

	public String readNextDataLine() {
		if (currentTag == null)
			return null;
		if (br == null)
			return null;
		currentDataLine = null;
		try {
			String currentLine = br.readLine();
			while (!matchDataLine(currentLine) && !matchTagEnder(currentLine)) {
				currentLine = br.readLine();
				if (currentLine == null) {
					System.out
							.println("end of file without any data line found");
					br.close();
					br = null;
					return null;
				}
			}
			// System.out.println(currentLine);
			if (matchTagEnder(currentLine))
				return null;
			currentDataLine = currentLine;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currentDataLine;
	}

	public String getCurrentDataLine() {
		return currentDataLine;
	}

	public String matchTagStarter(String str) {
		if (configPro == null)
			return null;
		if (str == null)
			return null;
		String tagStarter = configPro.getProperty(TAGSTARTER);
		String tagEnder = configPro.getProperty(TAGENDER);
		String tagStartChar = tagStarter.substring(0, tagStarter.indexOf("xx"));
		String tagEndChar = tagEnder.substring(0, tagEnder.indexOf("xx"));
		if (str.startsWith(tagStartChar) && !str.startsWith(tagEndChar)) {
			int idxOfBlank = str.indexOf(' ');
			int idxOfEndChar = str.indexOf(tagStarter.charAt(tagStarter
					.length() - 1));
			if (idxOfBlank == -1 && idxOfEndChar == -1)
				return null;
			if (str.startsWith(tagStartChar + "!"))
				return null;
			if (idxOfBlank == -1)
				return str.substring(tagStartChar.length(), idxOfEndChar);
			return str.substring(tagStartChar.length(), idxOfBlank);
		}
		return null;
	}

	public boolean matchTagEnder(String str) {
		if (configPro == null)
			return false;
		if (str == null)
			return false;
		String tagEnder = configPro.getProperty(TAGENDER);
		String tagEndChar = tagEnder.substring(0, tagEnder.indexOf("xx"));
		// System.out.println(str);
		// System.out.println(tagEndChar);
		if (str.startsWith(tagEndChar)) {
			return true;
		}
		return false;
	}

	public boolean matchAttributeNameLine(String str) {
		if (configPro == null)
			return false;
		String attStarter = configPro.getProperty(ATTRIBUTENAMESTARTER);
		return str.startsWith(attStarter);
	}

	public boolean matchCommentNameLine(String str) {
		if (configPro == null)
			return false;
		String commStarter = configPro.getProperty(COMMENTSTARTER);
		return str.startsWith(commStarter);
	}

	public boolean matchDataLine(String str) {
		if (configPro == null)
			return false;
		String dataStarter = configPro.getProperty(DATALINESTARTER);
		return str.startsWith(dataStarter);
	}
}
