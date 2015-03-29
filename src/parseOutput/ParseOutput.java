package parseOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ParseOutput {

	public static void main(String[] args) {

		try {
			new ParseOutput().parse("test-output.txt", "parse-output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void parse(String input, String output) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
		String line = "";
		int count = 1;
		br.readLine();
		while ((line = br.readLine()) != null) {
			if (line.trim().length() == 0) {
				count = 1;
				bw.write("\n");
				//System.out.println();
				continue;
			}
			count++;
			String[] elements = line.split("\t");
			if (elements.length >= 4) {
				bw.write(elements[3] + " ||| ");
			//	System.out.print(elements[3] + " ||| ");
			}
			
			if (count % 3 != 0) {
				if (elements.length >= 5) {
					bw.write(elements[4] + " ||| ");
				//	System.out.print(elements[4] + " ||| ");
				}
			}

		}
		br.close();
		bw.close();
	}
}
