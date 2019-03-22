import java.util.ArrayList;

public class ComparisonFunction {
	StemTrimmer st = new StemTrimmer();
	public boolean compare(ArrayList<InternalRepresentation> queryIRS, ArrayList<InternalRepresentation> docIRS) {
		InternalRepresentation query = queryIRS.get(0);
		ArrayList<String> QmodList = query.modifier;
		int qsize = query.size();
		if((query.subject.contains("Lincoln")||(query.subject.contains("lincoln")))
				&& !query.subject.contains("he") && !query.subject.contains("He")) {
			query.subject.add("he");

		}
		ArrayList<String> QsubList = query.subject;
		ArrayList<String> QobList = query.object;
		ArrayList<String> QverbList = query.verb;
		ArrayList<String> QprepList = query.preposition;
		for(int i = 0; i < docIRS.size(); i++) {
			int score = 0;
			InternalRepresentation docIr = docIRS.get(i);
			ArrayList<String> modList = docIr.modifier;
			ArrayList<String> subList = docIr.subject;
			ArrayList<String> obList = docIr.object;
			ArrayList<String> verbList = docIr.verb;
			ArrayList<String> prepList = docIr.preposition;
			
			int sameIdentifier = 5;
			score = score + stringMatch(modList, QmodList, sameIdentifier);
			score = score + stringMatch(modList, QsubList, 3);
			score = score + stringMatch(modList, QobList, 3);
			score = score + stringMatch(modList, QverbList, 4);
			score = score + stringMatch(modList, QprepList, 1);
			
			score = score + stringMatch(subList, QmodList, 3);
			score = score + stringMatch(subList, QsubList, sameIdentifier);
			score = score + stringMatch(subList, QobList, 0);
			score = score + stringMatch(subList, QverbList, 3);
			score = score + stringMatch(subList, QprepList, 1);
			
			score = score + stringMatch(obList, QmodList, 3);
			score = score + stringMatch(obList, QsubList, 0);
			score = score + stringMatch(obList, QobList, sameIdentifier);
			score = score + stringMatch(obList, QverbList, 3);
			score = score + stringMatch(obList, QprepList, 1);
			
			score = score + stringMatch(verbList, QmodList, 4);
			score = score + stringMatch(verbList, QsubList, 3);
			score = score + stringMatch(verbList, QobList, 3);
			score = score + stringMatch(verbList, QverbList, sameIdentifier);
			score = score + stringMatch(verbList, QprepList, 1);
			
			score = score + stringMatch(prepList, QmodList, 1);
			score = score + stringMatch(prepList, QsubList, 1);
			score = score + stringMatch(prepList, QobList, 1);
			score = score + stringMatch(prepList, QverbList, 1);
			score = score + stringMatch(prepList, QprepList, sameIdentifier);
			
			if(score>=(qsize*4)){
				return true;
			}
		}
		return false;
	}

	private int stringMatch(ArrayList<String> list, ArrayList<String> qList, int scoreIncrement) {
		int score = 0;
		boolean boo = false;
		for(int aa = 0; aa < qList.size(); aa++) {
			for(int a = 0; a < list.size(); a++) {
				if(st.trim(qList.get(aa)).equals(st.trim(list.get(a)))) {
					boo = true;
				}
			}
			if(boo) score = score + scoreIncrement;
			boo = false;
		}
		return score;
	}
}
