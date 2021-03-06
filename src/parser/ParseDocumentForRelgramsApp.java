package parser;

import static extras.FileNames.N_A;
import static extras.FileNames.N_R_I;
import static extras.FileNames.N_M_A;

import java.io.PrintWriter;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class ParseDocumentForRelgramsApp {

	public static void main(String[] args) {
		ParseDocumentForRelgramsApp p = new ParseDocumentForRelgramsApp();
		try {
			p.parse(N_A, N_R_I);
			p.parseManualAnnotation(N_A, N_M_A);
			System.out.println("<<Document ready for relgramtuples-app>>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse(String source, String destination) throws Exception {
		String dummy = "dummy\tdummy\t0\t0\t";
		PrintWriter pw = new PrintWriter(destination);
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(source);
		for (List<HasWord> sentence : tokenizer) {
			String sentenceString = Sentence.listToString(sentence);
			// System.out.println(sentenceString);
			pw.println(dummy + sentenceString);
		}
		pw.close();
	}
	
	public void parseManualAnnotation(String source, String destination) throws Exception {
		//String dummy = "dummy\tdummy\t0\t0\t";
		int i=0;
		PrintWriter pw = new PrintWriter(destination);
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(source);
		for (List<HasWord> sentence : tokenizer) {
			String sentenceString = Sentence.listToString(sentence);
			// System.out.println(sentenceString);
			pw.println(++i+"\t"+ sentenceString);
		}
		pw.close();
	}
}
