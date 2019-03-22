import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.Tree;
public class ParseTree {
	private Tree tree;
	ArrayList<String> subject;
	ArrayList<InternalRepresentation> irList;
	public ParseTree(Tree tree) {
		this.tree = tree;
		this.subject = new ArrayList<String>();
		this.irList = new ArrayList<InternalRepresentation>();
	}
	public void parse() {
		List<Tree> childList = tree.firstChild().getChildrenAsList();
		int sentenceBranches = childList.size();
		for(int index = 0; index < sentenceBranches; index++) {
			Tree branch = childList.get(index);
			dealWithSentenceBranch(branch);
		}
	}
	private void dealWithSentenceBranch(Tree branch) {
		if(branch.label().toString().equals("NP")) {
			String wordsWithBrackets = branch.getLeaves().toString();
			String words = wordsWithBrackets.substring(1, wordsWithBrackets.length() - 1);
			String[] wordsList = words.split(",");
			for(int i = 0; i < wordsList.length; i++) {
				this.subject.add(wordsList[i]);
			}
		}
		else if(branch.label().toString().equals("VP")) {
			dealWithVerbPhrase(branch);
		}
		else {
			//fullstop 
			//do nothing
		}
		
	}
	private void dealWithVerbPhrase(Tree branch) {
		List<Tree> childList = branch.getChildrenAsList();
		int branches = childList.size();
		boolean hasSomeVPBranch = false;
		int index = 0;
		while(index < branches) {
			Tree innerBranch = childList.get(index);
			String innerBranchString = innerBranch.label().toString();
			if(innerBranchString.equals("VP")) {
				hasSomeVPBranch = true;
				break;
			}
			index++;
		}
		if(!hasSomeVPBranch) {
			makeFrame(childList);
		}
		else {
			for(int i = 0; i < branches; i++) {
				Tree innerBranch = childList.get(i);
				String innerBranchString = innerBranch.label().toString();
				if(innerBranchString.equals("VP")) {
					dealWithVerbPhrase(innerBranch);
				}
			}
		}
	}
		public InternalRepresentation makeFrame(List<Tree> childList) {
		InternalRepresentation ir = new InternalRepresentation();
		ir.subject = this.subject;
		ir = dealWithFrame(childList, ir);
		irList.add(ir);
		return ir;
	}
		private InternalRepresentation dealWithFrame(List<Tree> childList, InternalRepresentation ir) {
			for(int i = 0; i < childList.size(); i++) {
				Tree innerBranch = childList.get(i);
				String innerBranchString = innerBranch.label().toString();
				if(innerBranchString.equals("VBD")
					|| innerBranch.label().toString().equals("VBN")
					|| innerBranch.label().toString().equals("VBZ")
					|| innerBranch.label().toString().equals("VB")) {
					String wordsWithBrackets = innerBranch.getLeaves().toString();
					ir.verb.add(wordsWithBrackets.substring(1, wordsWithBrackets.length()-1));
				}
				else if(innerBranchString.equals("NP")) {
					String wordsWithBrackets = innerBranch.getLeaves().toString();
					String words = wordsWithBrackets.substring(1, wordsWithBrackets.length()-1);
					String[] wordList = words.split(",");
					for(int index = 0; index < wordList.length; index++) {
						ir.object.add(wordList[index]);
					}
				}
				else if(innerBranchString.equals("PP")) {
					dealWithFrame(innerBranch.getChildrenAsList(), ir);
				}	
				else if(innerBranchString.equals("ADJP")
						|| innerBranchString.equals("JJ")) {
					String wordsWithBrackets = innerBranch.getLeaves().toString();
					ir.modifier.add(wordsWithBrackets.substring(1, wordsWithBrackets.length()-1));
				}
				else if(innerBranchString.equals("IN")) {
					String wordsWithBrackets = innerBranch.getLeaves().toString();
					ir.preposition.add(wordsWithBrackets.substring(1, wordsWithBrackets.length()-1));
				}
				else if(innerBranchString.equals("S")) {
					dealWithFrame(innerBranch.getChildrenAsList(), ir);
				}
			}
			return ir;
		}
}
