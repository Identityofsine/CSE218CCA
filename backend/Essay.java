package backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Essay{
    private final String underlyingString;

    public Essay(String underlyingString) {
        this.underlyingString = underlyingString;
    }
    
    public static int wordCount(String temp) {
    	String trim = temp.trim();
    	if (trim.isEmpty())
    	    return 0;
    	return trim.split("\\s+").length; // separate string around spaces
    }
    
    public static int getSentence(String temp) {
    	String trim = temp.trim();
    	if(trim.isEmpty())
    		return 0;
    	return trim.split("[\\	.\\?!]").length;
    }
    
    public static double fletchScore(String essay) {
		//206.835 - 1.015(totalwords/totalsetences) - 84.6(total syllables/total words);
    	int _wordCount = wordCount(essay);
    	int _sentenceConut = getSentence(essay);
    	int _syllableCount = SyllableCount(essay);
    	System.out.println(String.format("words: %s, sentences: %s, syllables : %s", _wordCount, _sentenceConut, _syllableCount));
    	System.out.println("fletch score : " + (206.835 - (1.015 * ((double)_wordCount / _sentenceConut)) - (84.6 * ((double)_syllableCount / _wordCount))));
    	return 206.835 - (1.015 * ((double)_wordCount / _sentenceConut)) - (84.6 * ((double)_syllableCount / _wordCount));
    }
    
    public static double typos(String essay, int perWord) { // 
    	//check each word, if it exists in a hashtable-- load hashtable on program launch...
    	int typos = 0;
    	int iterations = 1;
    	double tpc = 0;
    	String[] splitWords = essay.trim().replaceAll("[^a-zA-Z]", "").split("\\s+");
    	int adjPerWord = perWord < splitWords.length ? splitWords.length : perWord;
    	for(int i = 0; i < splitWords.length; i++) {
    		if(!UserCenter.dictionary.contains(splitWords[i].toLowerCase())){
    			typos++;
    		}
    	}
    	return typos / (double)(perWord < essay.length() ? perWord : essay.length()) * 100;
    }
    
    
	public static int SyllableCount(String sentence) {
		String[] split = sentence.trim().split("\\s+");
		int num = 0;
		int tt = 0;
		for(String word : split) {
		String pattern = "[AEIOUYaeiouy]+";
		Pattern tokenSplitter = Pattern.compile(pattern);
		Matcher m = tokenSplitter.matcher(word);
		String lastToken = "";
		while (m.find()) {
			num++;
			lastToken = m.group();
			tt++;
		}
		if (lastToken.equals("e") && num > 1 && word.charAt(word.length() - 1) == 'e') {
			num--;
			tt--;
		} else
		num = 0;
	}
		return tt;
	}
    // Override equals however you want here
}