import requests
import json

def get_list(sen):
	"""
	Converts the sentence into a List such that it can be used as an POST request json blob data.

	Args:
		sen : a string

	Returns:
		res: a list of list containg words and tags.
	"""
	res = list()
	count = 0
	for word in sen.split():
	    l = [word, "Any", count]
	    count += 1
	    res.append(l)
	return res

def get_ai2_textual_entailment(t, h):
	"""
	Returns the output of POST request to AI2 textual entailment service

	Args:
		t, h : text and hypothesis (two strings)

	Returns:
		req : A text version of json response.
	"""
	text = get_list(t)
	hypothesis = get_list(h)

	data = { "text" : text, "hypothesis": hypothesis}

	headers = {'Content-type': 'application/json', 'Accept': 'application/json'}

	url = 'http://0.0.0.0:8191/api/entails'
	print json.dumps(data)
	req = requests.post(url, headers=headers, data=json.dumps(data))
	
	return req.json()

def main():

	file2 = open("schema_tuple_rel.txt")
	file1 = open("document_tuple_rel.txt")
	file3 = open("score_tuples.txt","w+")
	arr1 = []
	arr2 = []
	
	max_conf = 0.3

	for x in file1:
		arr1.append(x)
	
	for y in file2:
		arr2.append(y)

	for i in arr1:
		max_conf = 0
		for j in arr2:
			d = get_ai2_textual_entailment(i,j)
			confidence = json.dumps(d[u'confidence'])
			file3.write(i)
			file3.write(j)
			file3.write(confidence)
			file3.write("\n")
			if confidence > max_conf:
				max_conf=confidence
		print max_conf	
	 	
	file1.close()
	file2.close()
	file3.close()

if __name__ == "__main__":
	main()
