package textual_entailment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import parser.ParseRelation;

public class TextEntail {
	ParseRelation parseRelation = null;
	public TextEntail() {
		parseRelation=new ParseRelation();
	}

	public static void main(String[] args) {
		TextEntail text_entail = new TextEntail();
		try {
			// String text = "hello ankush";
			// String hypothesis = "hi ankush";
			// System.out.println(text_entail.entail(text, hypothesis));

			text_entail.match_document_schema("document_relgram_Relations.txt",
					"manual_schema_Relations.txt");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void match_document_schema(String doc_file, String schema_file)
			throws Exception {
		BufferedReader document = new BufferedReader(new FileReader(doc_file));
		BufferedReader schema = new BufferedReader(new FileReader(schema_file));

		String schema_line = null;
		List<String> schema_list = new ArrayList<String>();
		while ((schema_line = schema.readLine()) != null)
			schema_list.add(schema_line);

		schema.close();
		String doc_line = null;
		while ((doc_line = document.readLine()) != null) {
			String max_match_pair = "";
			double confidence = 0.0, max_confidence = 0.0;
			String doc_relation=doc_line.split("\t")[2];
			doc_relation=parseRelation.parse(doc_relation);
			for (String s : schema_list) {
				String schema_relation=s.split("\t")[1];
				schema_relation=parseRelation.parse(schema_relation);
				
				confidence = entail(doc_relation, schema_relation);
				if (confidence > max_confidence) {
					max_confidence = confidence;
					max_match_pair = s + "\t<-schema--|"+ max_confidence +"|--document->\t" + doc_line;
				}

			}
			if (!max_match_pair.trim().isEmpty())
				System.out.println(max_match_pair);
		}
		document.close();
	}

	@SuppressWarnings("unchecked")
	private double entail(String text, String hypothesis) throws Exception {

		String url = "http://0.0.0.0:8191/api/entails";

		URL object = new URL(url);

		HttpURLConnection con = (HttpURLConnection) object.openConnection();

		con.setDoOutput(true);

		con.setDoInput(true);

		con.setRequestProperty("Content-Type", "application/json");

		con.setRequestProperty("Accept", "application/json");

		con.setRequestMethod("POST");

		JSONObject data = new JSONObject();

		data.put("text", getList(text));
		data.put("hypothesis", getList(hypothesis));

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(data.toString());
		wr.flush();

		// display what returns the POST request

		// StringBuilder sb = new StringBuilder();

		int HttpResult = con.getResponseCode();

		if (HttpResult == HttpURLConnection.HTTP_OK) {

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "utf-8"));

			// String line = null;
			//
			// while ((line = br.readLine()) != null) {
			// sb.append(line + "\n");
			// }
			//
			// br.close();

			// System.out.println("" + sb.toString());

			JSONParser parser = new JSONParser();

			JSONObject jsonObject = (JSONObject) parser.parse(br);

			double confidence = (double) jsonObject.get("confidence");
			return confidence;
			// System.out.println(confidence);

		} else {
			System.out.println(con.getResponseMessage());
			throw new Exception("Bad connection");

		}
	}

	private List<List<Object>> getList(String sentence) {

		List<List<Object>> list = new ArrayList<List<Object>>();
		int count = 0;
		for (String word : sentence.split(" ")) {
			List<Object> each = new ArrayList<Object>();
			each.add(word);
			each.add("Any");
			each.add(count);
			count += 1;
			list.add(each);
		}
		return list;

	}
}
