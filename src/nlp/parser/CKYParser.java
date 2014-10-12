package nlp.parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class CKYParser {
	private Hashtable<ArrayList<String>, GrammarRule> binaryRules = new Hashtable<ArrayList<String>, GrammarRule>();
	private Hashtable<String, GrammarRule> unaryRules = new Hashtable<String, GrammarRule>();
	private Hashtable<String, ArrayList<GrammarRule>> lexicalRules = new Hashtable<String, ArrayList<GrammarRule>>();
	
	
	public CKYParser(String filename) {
		File file = new File(filename);
		Scanner sc;

		try {
			sc = new Scanner(file);			
			
			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				GrammarRule rule = new GrammarRule(currentLine);
				
				// depending on the type of rule, we update the proper hashtable with the rule
				if (rule.isLexical()) {
					if (lexicalRules.containsKey(rule.getRhs().get(0))) {
						lexicalRules.get(rule.getRhs().get(0)).add(rule);
					} else {
						ArrayList<GrammarRule> rules = new ArrayList<GrammarRule>();
						rules.add(rule);
						lexicalRules.put(rule.getRhs().get(0), rules);
					}
				} else {
					if (rule.getRhs().size() > 1) {
						// binary rule
						binaryRules.put(rule.getRhs(), rule);
					} else {
						unaryRules.put(rule.getRhs().get(0), rule);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String parse(String sentence) {
		String[] words = sentence.split(" ");
		int numWords = words.length;
		// build cky table
		CKYEntry[][] theTable = new CKYEntry[numWords][numWords];
		
		//populate table for lexical rules
		for(int j=0, i=0; i < theTable.length; i++, j++) {
			theTable[i][j] = new CKYEntry(i, j);

			//find all the lexical Rules with our current word
			ArrayList<GrammarRule> allRules = lexicalRules.get(words[j]);

			// add all the rules to the entry
			for (GrammarRule gr: allRules) {
				add(gr, theTable[i][j]);
			}
			System.out.println(i + " , " + j + " " + theTable[i][j]);
		}
		
		// fill in the rest of the table
		for (int j = 0; j < numWords; j++) {
			for (int i = j-1; i >= 0; i--) {
				theTable[i][j] = new CKYEntry(i,j);
				
				
			}
		}
		
		return "";
	}
	
	private void possibleGrammar(CKYEntry original, CKYEntry left, CKYEntry right) {
		for (GrammarRule leftSide : left.allRules()) {
			for (GrammarRule rightSide : right.allRules()) {
				
			}
		}
	}
	private void add(GrammarRule rule, CKYEntry entry){
		Double sum = rule.weight;
		GrammarRule currentRule = rule;
		entry.addRule(rule, sum);
		while (unaryRules.containsKey(currentRule.getLhs())) {
			GrammarRule newRule = unaryRules.get(currentRule.getLhs());
			sum += newRule.weight;
			
			if (entry.containsRule(newRule)) {
				if (sum > entry.getWeight(rule)) {
					entry.addRule(newRule, sum);
				}
			} else {
				entry.addRule(newRule, sum);
			}
			currentRule = newRule;
		}
	}

	public static void main(String[] args) {
		CKYParser p = new CKYParser("example.pcfg");
		p.parse("Mary likes giant programs .");
	}
}
