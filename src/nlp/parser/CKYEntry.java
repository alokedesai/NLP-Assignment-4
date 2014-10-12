package nlp.parser;

import java.util.Hashtable;
import java.util.Set;

public class CKYEntry {
	private Hashtable<String, Double> rules = new Hashtable<String, Double>();
	private int i;
	private int j;

	public CKYEntry(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public Set<String> allRules() {
		return rules.keySet();
	}
	
	public boolean containsRule(String rule) {
		return rules.containsKey(rule);
	}
	
	public Double getWeight(String rule) {
		return rules.get(rule);
	}
	public void addRule(String rule, Double weight) {
		rules.put(rule, weight);
	}
	
	@Override
	public String toString() {
		return rules.toString();
	}
}
