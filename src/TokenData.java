import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;

public class TokenData {
	String word;
	String pos;
	String ne;
	public TokenData(CoreLabel token) {
		// this is the text of the token
	    this.word = token.get(TextAnnotation.class);
	    // this is the POS tag of the token
	    this.pos = token.get(PartOfSpeechAnnotation.class);
	    // this is the NER label of the token
	    //this.ne = token.get(NamedEntityTagAnnotation.class);
	}
}
