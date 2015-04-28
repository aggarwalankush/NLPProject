package parseOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ankush
 *Input: schema original form 
 *Output: unique arg-rel-arg pairs
 */
public class ParseSchema {

	public static void main(String[] args) {
		try {
			new ParseSchema().parse("manual_schema.txt",
					"manual_schema_mainContent.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void parse(String input, String output) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));

		Pattern pattern = Pattern.compile("A\\d+:\\[.*\\]");
		Matcher matcher = null;
		HashSet<String> schemas_set = new HashSet<String>();
		String line = "";
		while ((line = br.readLine()) != null) {
			matcher = pattern.matcher(line);
			if (matcher.find()) {
				//System.out.println(matcher.group());
				schemas_set.add(matcher.group());
			}

		}
		System.out.println("\n");
		for (String s : schemas_set){
			bw.write(s+"\n");
		}
		br.close();
		bw.close();
	}
}
