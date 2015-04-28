import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;


public class DummyTesting {

	public static void main(String[] args) throws Exception{
		BufferedReader br=new BufferedReader(new FileReader("manual_schema_Relations.txt"));
		String line="";
		while((line=br.readLine())!=null){
			System.out.println(line.split("\t")[1]);
		}
	}

}
