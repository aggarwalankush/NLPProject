package parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Ankush
 *Input: document output from relgram-app 
 *Output: arg-rel-arg pair with entities name also
 */

public class ParseDocument {

	public static void main(String[] args) {

		try {
			new ParseDocument().parse("document_relgram_original.txt",
					"document_relgram_Relations.txt");
			System.out.println("done parsing document");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void parse(String input, String output) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		String line = "";
		int count = 1;
		br.readLine();// ignore the first header line
		String arg1 = "", arg2 = "", rel = "", type1 = "", type2 = "";
		while ((line = br.readLine()) != null) {
			// reinitialize count when new arg-rel-arg pair starts
			if (line.trim().length() == 0) {
				count = 1;
				bw.write(arg1 + "\tA1:[" + type1 + "]\t" + rel + "\tA2:["
						+ type2 + "]\t" + arg2);
				bw.write("\n");

				continue;
			}
			// for each set of arg-rel-arg
			count++;

			String[] elements = line.split("\t");
			int len = elements.length;

			if (count == 2) { // for first line
				arg1 = elements[2];
				if (elements[len - 1].contains(":")) {
					type1 = elements[len - 1].substring(0,
							elements[len - 1].indexOf(':'));
				}

			} else if (count == 3) { // for middle line
				rel = elements[len - 1];
			} else if (count == 4) { // for last line
				if (elements[len - 1].contains(":")) {
					type2 = elements[len - 1].substring(0,
							elements[len - 1].indexOf(':'));
				}
				arg2 = elements[2];
			}

		}
		br.close();
		bw.close();
	}
}
