package parser;

import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ParseRelation {
	MaxentTagger tagger;

	public ParseRelation() {
		String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
		tagger = new MaxentTagger(taggerPath);
	}

	public static void main(String[] args) {
		ParseRelation p = new ParseRelation();
		System.out.println(p.parse("be kill by"));
		System.out.println(p.parse("he killed injured by"));
		System.out.println(p.parse("be kil"));
		System.out.println(p.parse("be rocked"));
	}

	public String parse(String relation) {

		DocumentPreprocessor tokenizer = new DocumentPreprocessor(
				new StringReader(relation));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			StringBuilder sb = new StringBuilder();
			for (TaggedWord tw : tagged) {
				if (tw.tag().equalsIgnoreCase("IN"))
					continue;
				if (tw.tag().equalsIgnoreCase("TO"))
					continue;
				if (tw.word().equalsIgnoreCase("be"))
					continue;
				if (tw.word().equalsIgnoreCase("go"))
					continue;
				sb.append(tw.word() + " ");
			}
			return sb.toString();
		}
		return "";
	}
}
