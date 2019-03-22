import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class BasicPipelineExample {

    public static void main(String[] args) throws IOException {

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse");
        props.setProperty("ssplit.newlineIsSentenceBreak", "two");
        props.setProperty("parse.maxlen", "70");
        props.setProperty("ner.useSUTime", "false");
        props.setProperty("ner.applyNumericClassifiers", "false");

       StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
       
       String query = new Query().read();

        // read some text in the text variable
       	LincolnParser lincoln = new LincolnParser("Abraham Lincoln.txt");
		lincoln.read();
		String text = lincoln.getPlainText();

        // create an empty Annotation just with the given text
        Annotation queryAnnotation = new Annotation(query);
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(queryAnnotation);
        pipeline.annotate(document);
        
        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> querySentences = queryAnnotation.get(SentencesAnnotation.class);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        ArrayList<InternalRepresentation> docIRS = new ArrayList<InternalRepresentation>();
        ArrayList<InternalRepresentation> queryIRS = new ArrayList<InternalRepresentation>();
     
        for(CoreMap sentence: sentences) {
       // traversing the words in the current sentence
       // a CoreLabel is a CoreMap with additional token-specific methods
    	ArrayList<TokenData> tokenDataList = new ArrayList<TokenData>();
       for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
    	  TokenData tokenData = new TokenData(token);
    	  tokenDataList.add(tokenData);
       }
       // this is the parse tree of the current sentence
       Tree tree = sentence.get(TreeAnnotation.class);
       ParseTree pt = new ParseTree(tree);
       pt.parse();
       for(int i = 0; i < pt.irList.size(); i++) {
    	   docIRS.add(pt.irList.get(i));
       }
     }
     
     for(CoreMap sentence: querySentences) {
         // traversing the words in the current sentence
         // a CoreLabel is a CoreMap with additional token-specific methods
      	ArrayList<TokenData> tokenDataList = new ArrayList<TokenData>();
         for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
      	  TokenData tokenData = new TokenData(token);
      	  tokenDataList.add(tokenData);
         }

         // this is the parse tree of the current sentence
         Tree tree = sentence.get(TreeAnnotation.class);
         ParseTree pt = new ParseTree(tree);
         pt.parse();
         queryIRS = pt.irList;
       }
     
	     boolean answer = new ComparisonFunction().compare(queryIRS, docIRS);
	     if(answer) System.out.println("Yes");
	     else System.out.println("No");
    }
}

// this is the Stanford dependency graph of the current sentence
//SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

// This is the coreference link graph
// Each chain stores a set of mentions that link to each other,
// along with a method for getting the most representative mention
// Both sentence and token offsets start at 1!
//Map<Integer, CorefChain> graph = 
//  document.get(CorefChainAnnotation.class);

