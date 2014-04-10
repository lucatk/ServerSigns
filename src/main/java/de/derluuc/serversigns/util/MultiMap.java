package de.derluuc.serversigns.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class MultiMap<A, B, C> implements Cloneable {

	@SuppressWarnings("rawtypes")
	private final HashMap<A, ArrayList> map = new HashMap<A, ArrayList>();
	
	public void put(A key, final B firstValue, final C secondValue) {
		map.put(key, new ArrayList<Object>(2)
			{
				private static final long serialVersionUID = -8841921592042474055L;
				{
					add(firstValue);
					add(secondValue);
				}
			});
	}
	
	public Set<A> keySet() {
		return map.keySet();
	}
	
	public void remove(A key) {
		map.remove(key);
	}
	
	public void clear() {
		map.clear();
	}
	
	public int size() {
		return map.size();
	}
	
	public int hashCode() {
		return (map.hashCode() * map.values().hashCode() + map.keySet().hashCode());
	}
	
	public boolean equals(Object object) {
		return map.equals(object);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public boolean containsKey(A key) {
		return map.containsKey(key);
	}
	
	public boolean containsValue(A key, Object value) {
		return map.get(key).contains(value);
	}
	
	@SuppressWarnings("unchecked")
	public B getFirstValue(A key) {
		return (B) map.get(key).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public C getSecondValue(A key) {
		return (C) map.get(key).get(1);
	}
	
	@SuppressWarnings("unchecked")
	public void setFirstValue(A key, B value) {
		map.get(key).set(0, value);
	}
	
	@SuppressWarnings("unchecked")
	public void setSecondValue(A key, C value) {
		map.get(key).set(1, value);
	}
	
	public String toString() {
		return "({[%hashCode$" + hashCode() + "$%][%size$" + size() + "$%][%keys$" + keySet().toString() + "$%]})";
	}
	
	@SuppressWarnings("unchecked")
	public MultiMap<A, B, C> clone() throws CloneNotSupportedException {
		return (MultiMap<A, B, C>) super.clone();
	}
	
}