##Natural Language Processing Course Project

####Problem Description
The problem is to extract information about events mentioned in sentences. `Input` includes articles describing events and a schema for that domain such as bombing domain. `Output` is the information about the events in a tabular form of triples where each tuple denotes the relation between two actors and a second table for each actor and all its mentions. `Event extraction` is a key prerequisite for generating structured, machine-readable representations of natural language. Such representations can aid various tasks like question answering, machine translation, novelty detection.

####Required Files
1. Jars
  - json-simple-1.1.1
  - stanford-corenlp-3.5.1-models
  - stanford-corenlp-3.5.1
2. relgramtuples-app
3. textual-entailment running as service
  - go to textual-entailment directory and run following from terminal
    - sbt
    - service/reStart


####Instructions to run
1. Copy required schema lines in `manual_schema_original.txt`
2. Steps to run relgramtuples-app on news articles and store output in `document_relgram_original.txt`
  - save news article in `news_articles.txt`
  - run `ParseDocumentForRelgramsApp.java`, it will output `news_relgram_input.txt`
  - copy `news_relgram_input.txt` in relgramtuples-app directory
  - go to relgramtuples-app directory from terminal and run below command
    - `java -cp lib/relgramtuples-1.0.0-SNAPSHOT-jar-with-dependencies.jar edu.washington.cs.knowitall.relgrams.apps.RelgramTuplesApp --wnHome resources/wordnet3.0 --wnTypesFile resources/wordnet-selected-classes.txt --ne7ModelFile resources/english.muc.7class.nodistsim.crf.ser.gz --ne3ModelFile resources/english.all.3class.nodistsim.crf.ser.gz -mpp resources/engmalt.linear-1.7.mco news_relgram_input.txt document_relgram_original.txt`
  - copy generated `document_relgram_original.txt` in workspace
3. Run following parsers to parse above files
  - `ParseDocument.java`
  - `ParseSchema.java`
4. Run `TextEntail.java` to see desired output

####Output
- results.txt file
- `Schema_tuple - confidence - Document_tuple`

