import java.util.ArrayList;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;

public class InternalRepresentation {
	ArrayList<String> subject;
	ArrayList<String> verb;
	ArrayList<String> object;
	ArrayList<String> preposition;
	ArrayList<String> modifier;
	ArrayList<String> time;
	
	public InternalRepresentation() {
		this.subject = new ArrayList<String>();
		this.verb = new ArrayList<String>();
		this.object = new ArrayList<String>();
		this.preposition = new ArrayList<String>();
		this.modifier = new ArrayList<String>();
		this.time = new ArrayList<String>();
	}
	
	public boolean isNull() {
		return this.subject.isEmpty() && this.verb.isEmpty() && this.object.isEmpty() 
				&& this.preposition.isEmpty() && this.modifier.isEmpty() && this.time.isEmpty();
	}
	
	public int size() {
		return this.subject.size() + this.verb.size() + this.object.size() 
				+ this.preposition.size() + this.modifier.size() + this.time.size();
	}
	
	public void print() {
		System.out.println("Subject: " + this.subject.toString() + 
				"\nVerb: " + this.verb.toString() + 
				"\nObject: " + this.object.toString() + 
				"\nPreposition: " + this.preposition.toString() + 
				"\nAdjective: " + this.modifier.toString() + 
				"\nTime: " + this.time.toString()+"\n");
	}
}
