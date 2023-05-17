package hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapDemo {

	public static void main(String[] args) {
		HashMap hm = new HashMap();
		hm.put("Mr. Monopoly", new Double(3434.34));
		hm.put("Gabe newell", new Double(12389.22));
		hm.put("Mr. Pokerface", new Double(1378.00));
		hm.put("Dasiy", new Double(99.22));
		hm.put("Sir Buysalot", new Double(-1193051.08));

		Set set = hm.entrySet();
		Iterator i = set.iterator();
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + ":");
			System.out.println(me.getValue());
		}
		System.out.println();
		double balance = ((Double)hm.get("Mr. Monopoly")).doubleValue();
		hm.put("Mr. Monopoly", new Double(balance + 1000));
		System.out.println("Mr. Monopoly's new balance: "+hm.get("Mr. Monopoly"));
	}
	
}
