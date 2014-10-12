package nlp.parser;

import java.util.Hashtable;
import java.util.Set;

public class CKYEntry {
	private Hashtable<GrammarRule, Double> rules = new Hashtable<GrammarRule, Double>();
	private int i;
	private int j;

	public CKYEntry(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public Set<GrammarRule> allRules() {
		return rules.keySet();
	}
	
	public boolean containsRule(GrammarRule rule) {
		return rules.containsKey(rule);
	}
	
	public Double getWeight(GrammarRule rule) {
		return rules.get(rule);
	}
	public void addRule(GrammarRule rule, Double weight) {
		rules.put(rule, weight);
	}
	
	@Override
	public String toString() {
		return rules.toString();
	}
}
