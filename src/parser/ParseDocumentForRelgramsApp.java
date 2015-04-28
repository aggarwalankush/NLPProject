package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class ParseDocumentForRelgramsApp {
	
	public static void main(String[] args) {
		ParseDocumentForRelgramsApp p=new ParseDocumentForRelgramsApp();
		try {
			p.parse("news_article.txt", "news_relgram_input.txt");
			System.out.println("Document ready for relgramtuples-app");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse(String source,String destination) throws Exception {
		String dummy="dummy\tdummy\t0\t0\t";
		PrintWriter pw=new PrintWriter(destination);
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(
				source);
		for (List<HasWord> sentence : tokenizer) {
			String sentenceString = Sentence.listToString(sentence);
			//System.out.println(sentenceString);
			pw.println(dummy+sentenceString);
		}
		pw.close();
	}
}
