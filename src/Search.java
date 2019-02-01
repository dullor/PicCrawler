
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
	public String Patterndemo(StringBuffer name, String key) {
		Pattern p = Pattern.compile(key);
		Matcher m = p.matcher(name);
		StringBuffer answer = new StringBuffer();
		if (!m.find()) {
			System.out.println("Unable to match: " + key);
			return null;
		}
			answer.append(m.group());
		return answer.toString();

	}

	public String[] Patterndemo(String name, String key) {
		Pattern p = Pattern.compile(key);
		Matcher m = p.matcher(name);
		String[] answer = new String[1000];
		int i = 0;
		if (!m.find()) {
			System.out.println("Unable to match: " + key);
			return null;
		}
		else
			answer[i++] = m.group();
		while (m.find())
			answer[i++] = m.group();
		return answer;
	}
}
