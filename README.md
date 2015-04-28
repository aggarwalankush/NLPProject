##Natural Language Processing Course Project

####Problem Description
The problem is to extract information about events mentioned in sentences. `Input` includes articles describing events and a schema for that domain such as bombing domain. `Output` is the information about the events in a tabular form of triples where each tuple denotes the relation between two actors and a second table for each actor and all its mentions. `Event extraction` is a key prerequisite for generating structured, machine-readable representations of natural language. Such representations can aid various tasks like question answering, machine translation, novelty detection.

####Instructions to run
1. Copy required schema lines in `manual_schema_original.txt`
2. Run relgramtuples-app on document lines and store output in `document_relgram_original.txt`
3. Run following parsers to parse above files
  - `ParseDocument.java`
  - `ParseSchema.java`
4. Run `TextEntail.java` to see required results

####Output
- `Schema_tuple - confidence - Document_tuple`

