package eventExtraction;

import static extras.FileNames.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;

import parser.ParseDocument;


public class Analysis {
	
	HashSet<String> manual_Yes = new HashSet<String>();
	HashSet<String> manual_No = new HashSet<String>();

	public static void main(String[] args) {
		Analysis analysis = new Analysis();
		try {
			analysis.analyze(N_M_A);
			System.out.println("Analysis done");
			
//			EventExtraction event=new EventExtraction();
//			HashSet<String> goodMathesResult=event.getGoodMatches(args);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void analyze(String document) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(document));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] str = line.split("\t");
			if (M_M_Y.contains(str[0])) {
				//System.out.println(line);
				//System.out.println(str[0]);
				manual_Yes.add(str[1]);
			} else {
				//System.out.println(line);
				manual_No.add(str[1]);
			}
		}
//		System.out.println(manual_Yes.size());
		System.out.println(manual_Yes);
//		System.out.println(manual_No.size());
//		for(String s:manual_Yes)
//			System.out.println(s);
		
		br.close();
		
		PrintWriter pw=new PrintWriter("true_relgram_input.txt");
		for(String s:manual_Yes){
			String dummy = "dummy\tdummy\t0\t0\t";
			pw.println(dummy + s);

		}
		pw.close();
		new ParseDocument().parse(T_R_O, T_R_R);
	}

}
