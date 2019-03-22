import java.util.HashMap;
import java.util.Iterator;

public class StemTrimmer {
	private HashMap<String,String> modRules;
	private HashMap<String,String> fixRules;
	private int checkLen = 3;
	public StemTrimmer(){
		modRules = new HashMap<String,String>();
		modRules.put("s","");
		modRules.put("ion","");
		modRules.put("ing", "");
		modRules.put("ed", "");
		modRules.put("es", "");
		modRules.put("esses", "ess");
		
		fixRules = new HashMap<String,String>();
		fixRules.put("at","ate");
		fixRules.put("bl","ble");
		fixRules.put("iz", "ize");
		fixRules.put("nc", "nce");
		fixRules.put("ie", "y");
	}
	
	public void setCheckLen(int len){
		checkLen = len;
	}

	public String trim(String word){
		word = word.toLowerCase().trim();
		Iterator<String> ruleIter = modRules.keySet().iterator();
		String next;
		int len = word.length();
		boolean isModApplied = false;
		while(ruleIter.hasNext()&& len > checkLen && !isModApplied){
			next = ruleIter.next();
			int ruleLen = next.length();
			int diffLen = len - ruleLen;
			if(word.contains(next)){
				String chk = word.substring(diffLen, len);
				if(chk.equals(next)){
					word = word.substring(0,diffLen) + modRules.get(next);						
					isModApplied = true;
					len = word.length();
				}
			}

		}
		
		Iterator<String> fixIter = fixRules.keySet().iterator();
		String fixNext;
		boolean isFixApplied = false;
		while(fixIter.hasNext() && !isFixApplied && len > checkLen && isModApplied){
			fixNext = fixIter.next();
			int fixLen = fixNext.length();
			int chaLen = len - fixLen;
			String chk = word.substring(chaLen,len);
			if(chk.equals(fixNext)){
				word = word.substring(0,chaLen) + fixRules.get(fixNext);
				isFixApplied = true;
				len = word.length();
			}
		}
		return word;
	}
	
}
