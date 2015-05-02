package extras;

import java.util.HashSet;

public final class FileNames {
	public static final String D_R_O = "document_relgram_original.txt";
	public static final String D_R_R = "document_relgram_Relations.txt";
	public static final String M_S_O = "manual_schema_original.txt";
	public static final String M_S_R = "manual_schema_Relations.txt";
	public static final String N_A = "news_article.txt";
	public static final String N_R_I = "news_relgram_input.txt";
	public static final String N_M_A = "news_manual_annotation.txt";
	public static final String RESULT = "results.txt";
	public static final String T_R_I="true_relgram_input.txt";
	public static final String T_R_O="true_relgram_original.txt";
	public static final String T_R_R="true_relgram_Relations.txt";
	public static final Double conf_threshold = 0.2;
	private static final int[] Manual_Matches = { 1, 2, 17, 18, 21, 23, 24, 40,
			56, 64, 99, 102, 103, 111, 112, 116, 127, 138, 139, 163, 183, 198,
			254, 310, 321, 344, 354, 358, 372, 373, 377, 381, 411, 412, 415,
			439, 441, 446, 477, 478, 491, 492, 495 };
	
	public static final HashSet<String> M_M_Y=new HashSet<String>();
	static{
		for(Integer i:Manual_Matches)
			M_M_Y.add(i.toString());
	}
	
}
