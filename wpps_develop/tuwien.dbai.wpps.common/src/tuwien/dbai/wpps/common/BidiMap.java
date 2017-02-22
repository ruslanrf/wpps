package tuwien.dbai.wpps.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//
/**
 * TODO use Guava (http://code.google.com/p/guava-libraries/)
 * Bi-directional hashmap
 *
 * @created: Unknown
 * @author Unknown (taken from the project weblearn at the DBAI Group, TUWien)
 *
 * @param <A>
 * @param <B>
 */
@Deprecated
public class BidiMap<A,B>
{

    Map<A,B> m1 = new HashMap<A,B>();
    Map<B,A> m2 = new HashMap<B,A>();

    public void clear() {
        m1.clear();
        m2.clear();
    }

    public Set<A> keySetA() {
    	return m1.keySet();
    }
    
    public Set<B> keySetB() {
    	return m2.keySet();
    }
    
    public boolean contains(A a, B b) {
        B bb = m1.get(a);
        return
            b!=null &&
            b.equals(bb);
    }
    
    public boolean containsKey(A a) {
    	return m1.containsKey(a);
    }
    
    public boolean containsValue(B b) {
    	return m2.containsKey(b);
    }

    public B get(A key) {
        return m1.get(key);
    }
    public A reverseGet(B key) {
        return m2.get(key);
    }

    public boolean isEmpty() {
        return m1.isEmpty();
    }

    public void put(A a, B b) {
        if (m1.containsKey(a) ||
            m2.containsKey(b))
            throw new RuntimeException("already exists");

        m1.put(a, b);
        m2.put(b, a);
    }

    public void remove(A a, B b) {
        m1.remove(a);
        m2.remove(b);
    }

    public int size() {
        return m1.size();
    }

    public Set<Map.Entry<A,B>> values() {
        return m1.entrySet();
    }

}
