package eventExtraction;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import parser.ParseDocument;
import parser.ParseDocumentForRelgramsApp;
import parser.ParseSchema;
import textual_entailment.TextEntail;

public class EventExtraction {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out
					.print("textual-entailment is running as service (Y-YES, N-NO)\t");
			if (!br.readLine().equalsIgnoreCase("y"))
				throw new Exception("--Please run textual-entailment--");
			System.out
					.print("Copy required schema lines in manual_schema_original.txt (Y-YES, N-NO)\t");
			if (!br.readLine().equalsIgnoreCase("y"))
				throw new Exception(
						"--Please copy required schema lines in manual_schema_original.txt--");
			ParseSchema.main(args);
			System.out
					.print("Copy news article in news_article.txt (Y-YES, N-NO)\t");
			if (!br.readLine().equalsIgnoreCase("y"))
				throw new Exception("--Please copy news article--");
			ParseDocumentForRelgramsApp.main(args);
			System.out
					.println("copy news_relgram_input.txt in relgramtuples-app directory");
			System.out.println("<<Run below command from Terminal:>>");
			System.out
					.println("java -cp lib/relgramtuples-1.0.0-SNAPSHOT-jar-with-dependencies.jar "
							+ "edu.washington.cs.knowitall.relgrams.apps.RelgramTuplesApp"
							+ " --wnHome resources/wordnet3.0 --wnTypesFile resources/wordnet-selected-classes.txt "
							+ "--ne7ModelFile resources/english.muc.7class.nodistsim.crf.ser.gz "
							+ "--ne3ModelFile resources/english.all.3class.nodistsim.crf.ser.gz "
							+ "-mpp resources/engmalt.linear-1.7.mco "
							+ "news_relgram_input.txt document_relgram_original.txt");
			
			System.out.print("copy generated document_relgram_original.txt in workspace (Y-YES, N-NO)\t");
			if (!br.readLine().equalsIgnoreCase("y"))
				throw new Exception("--Please copy relgram output in workspace--");
			ParseDocument.main(args);
			
			TextEntail.main(args);
			br.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
