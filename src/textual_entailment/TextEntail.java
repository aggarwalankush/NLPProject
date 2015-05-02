package textual_entailment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import parser.ParseRelation;
import static extras.FileNames.*;

public class TextEntail {
	ParseRelation parseRelation = null;
	HashSet<String> match_list = new HashSet<String>();
	HashMap<String, HashSet<String>> entities = new HashMap<String, HashSet<String>>();
	HashMap<String, Double> entailResult = new HashMap<String, Double>();
	HashSet<String> goodMatchDocument;

	public TextEntail() {
		goodMatchDocument=new HashSet<String>();
		parseRelation = new ParseRelation();
	}

	public static void main(String[] args) {
		
		
	}
	
	public HashSet<String> getGoodMatches(){
		try {
			// String text = "hello ankush";
			// String hypothesis = "hi ankush";
			// System.out.println(text_entail.entail(text, hypothesis));
			// System.out.println(text_entail.entail("kill","go off including"));
			match_document_schema(D_R_R, M_S_R);
			// // System.out.println("done");
			match_args_also();
			System.out
					.println("<<Program ended>> : Please see results in results.txt file");
			//System.out.println(goodMatchDocument.size());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return goodMatchDocument;
	}

	/*
	 * Treated equal >>>person == number >>>device == activity
	 * 
	 * >>>arg1 arg2 doc_arg3 doc_arg4
	 * 
	 * match 1-2 with 3-4 or 4-3 then good match
	 * 
	 * >>>arg1 arg2 doc_arg3
	 * 
	 * match 1-2 with 3, either match then good match
	 * 
	 * >>>arg1 arg2 nothing produced from document
	 * 
	 * not a good match
	 */
	private void match_args_also() throws Exception {
		PrintWriter result_file = new PrintWriter(new BufferedWriter(
				new FileWriter(RESULT, true)));
		result_file
				.println("\n=======================================================================Good Match=======================================================================\n");

		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m;

		for (String s : match_list) {
			String[] arrOfMatchList = s.split("\t");
			double conf = Double.parseDouble(arrOfMatchList[3].split("\\|")[1]);
			if (conf < conf_threshold)
				continue;
			StringBuilder sb = new StringBuilder();
			// System.out.println();
			m = p.matcher(s);
			while (m.find()) {
				// System.out.print(m.group(1) + "\t");
				sb.append(m.group(1) + "\t");
			}

			String[] args = sb.toString().split("\t");
			int len = args.length;
			for (int i = 0; i < len; i++) {
				args[i] = args[i].replaceAll("number", "person");
				args[i] = args[i].replaceAll("event", "activity");
				// args[i] = args[i].replaceAll("physical_object", "device");
				args[i] = args[i].replaceAll("device", "activity");
			}
			if (len < 3)
				continue; // no types detected for document line by relgrams
			else if (len == 3) {// match 0 with 2 or 1 with 2
				if (args[0].equalsIgnoreCase(args[2])) {
					result_file.println(s);
					goodMatchDocument.add(s.substring(s.indexOf(">>")+2));
					// result_file.println(args[0] + " : " + arrOfMatchList[4]);
					// result_file.println(args[1] + " : " + arrOfMatchList[8]);

					if (!entities.containsKey(args[0])) {
						HashSet<String> list = new HashSet<String>();
						list.add(arrOfMatchList[4]);
						entities.put(args[0], list);
					} else {
						entities.get(args[0]).add(arrOfMatchList[4]);
					}

					if (!entities.containsKey(args[1])) {
						HashSet<String> list = new HashSet<String>();
						list.add(arrOfMatchList[8]);
						entities.put(args[1], list);
					} else {
						entities.get(args[1]).add(arrOfMatchList[8]);
					}

				} else if (args[1].equalsIgnoreCase(args[2])) {
					result_file.println(s);
					goodMatchDocument.add(s.substring(s.indexOf(">>")+2));
					// result_file.println(args[1]
					// + " : " +
					// arrOfMatchList[4]);
					// result_file.println(args[0] + " : " + arrOfMatchList[8]);

					if (!entities.containsKey(args[1])) {
						HashSet<String> list = new HashSet<String>();
						list.add(arrOfMatchList[4]);
						entities.put(args[1], list);
					} else {
						entities.get(args[1]).add(arrOfMatchList[4]);
					}

					if (!entities.containsKey(args[0])) {
						HashSet<String> list = new HashSet<String>();
						list.add(arrOfMatchList[8]);
						entities.put(args[0], list);
					} else {
						entities.get(args[0]).add(arrOfMatchList[8]);
					}
				}

			} else if (len == 4) {// match 0-1 with 2-3 or 3-2
				if ((args[0].equalsIgnoreCase(args[2]) && args[1]
						.equalsIgnoreCase(args[3]))
						|| (args[0].equalsIgnoreCase(args[3]) && args[1]
								.equalsIgnoreCase(args[2]))) {
					result_file.println(s);
					goodMatchDocument.add(s.substring(s.indexOf(">>")+2));
					//System.out.println(s.substring(s.indexOf(">>")+2));
					
					// result_file.println(args[2]
					// + " : " +
					// arrOfMatchList[4]);
					// result_file.println(args[3] + " : " + arrOfMatchList[8]);

					if (!entities.containsKey(args[2])) {
						HashSet<String> list = new HashSet<String>();
						list.add(arrOfMatchList[4]);
						entities.put(args[2], list);
					} else {
						entities.get(args[2]).add(arrOfMatchList[4]);
					}

					if (!entities.containsKey(args[3])) {
						HashSet<String> list = new HashSet<String>();
						list.add(arrOfMatchList[8]);
						entities.put(args[3], list);
					} else {
						entities.get(args[3]).add(arrOfMatchList[8]);
					}
				}
			}

		}

		result_file
				.println("\n=======================================================================Entities=======================================================================\n");

		for (Map.Entry<String, HashSet<String>> entry : entities.entrySet()) {
			String key = entry.getKey();
			if (key.equalsIgnoreCase("activity"))
				key += "/device";
			result_file.println(key + " : " + entry.getValue());
		}

		result_file.close();

	}

	private void match_document_schema(String doc_file, String schema_file)
			throws Exception {
		BufferedReader document = new BufferedReader(new FileReader(doc_file));
		BufferedReader schema = new BufferedReader(new FileReader(schema_file));
		PrintWriter result_file = new PrintWriter(RESULT);
		String schema_line = null;
		List<String> schema_list = new ArrayList<String>();
		while ((schema_line = schema.readLine()) != null)
			schema_list.add(schema_line);

		schema.close();
		String doc_line = null;
		result_file
				.println("\n========================================Document-Schema pairs matched using only relation===============================================\n");
		while ((doc_line = document.readLine()) != null) {
			String max_match_pair = "";
			double confidence = 0.0, max_confidence = 0.0;
			String doc_relation = doc_line.split("\t")[2];
			doc_relation = parseRelation.parse(doc_relation);
			for (String s : schema_list) {

				String schema_relation = s.split("\t")[1];
				schema_relation = parseRelation.parse(schema_relation);
				if (entailResult.containsKey(doc_relation + schema_relation)) {
					confidence = entailResult.get(doc_relation
							+ schema_relation);
				} else {
					confidence = entail(doc_relation, schema_relation);
					entailResult
							.put(doc_relation + schema_relation, confidence);
				}
				if (entailResult.containsKey(schema_relation + doc_relation)) {
					confidence += entailResult.get(schema_relation
							+ doc_relation);
				} else {
					double c = entail(schema_relation, doc_relation);
					confidence += c;
					entailResult.put(schema_relation + doc_relation, c);
				}

				confidence /= 2;
				if (confidence > max_confidence) {
					max_confidence = confidence;
					max_match_pair = s + "\t<<-schema--|" + max_confidence
							+ "|--document->>\t" + doc_line;
				}
				if (confidence == 1.0) {
					max_match_pair = s + "\t<<-schema--|" + max_confidence
							+ "|--document->>\t" + doc_line;
					if (!match_list.contains(max_match_pair)) {
						match_list.add(max_match_pair);
						result_file.println(max_match_pair);
					}
				}

			}
			if (!max_match_pair.trim().isEmpty()) {
				// System.out.println(max_match_pair);
				if (!match_list.contains(max_match_pair)) {
					match_list.add(max_match_pair);
					result_file.println(max_match_pair);
				}
			}
		}
		result_file.close();
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
			try {
				double confidence = (double) jsonObject.get("confidence");
				// System.out.println(jsonObject);
				return confidence;
			} catch (Exception e) {
				return 0;
			}
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
