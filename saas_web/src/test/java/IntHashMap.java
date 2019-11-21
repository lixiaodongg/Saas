


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class IntHashMap<E> implements Map {
    private Entry[] table;
    private int count;
    private int threshold;
    private float loadFactor;

    public IntHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity > 0 && (double)loadFactor > 0.0D) {
            this.loadFactor = loadFactor;
            this.table = new IntHashMap.Entry[initialCapacity];
            this.threshold = (int)((float)initialCapacity * loadFactor);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public IntHashMap(int initialCapacity) {
        this(initialCapacity, 0.75F);
    }

    public IntHashMap() {
        this(101, 0.75F);
    }

    protected void rehash() {
        int oldCapacity = this.table.length;
        IntHashMap.Entry[] oldTable = this.table;
        int newCapacity = oldCapacity * 2 + 1;
        IntHashMap.Entry[] newTable = new IntHashMap.Entry[newCapacity];
        this.threshold = (int)((float)newCapacity * this.loadFactor);
        this.table = newTable;
        int i = oldCapacity;

        IntHashMap.Entry e;
        int index;
        while(i-- > 0) {
            for(IntHashMap.Entry old = oldTable[i]; old != null; newTable[index] = e) {
                e = old;
                index = (old.hash & 2147483647) % newCapacity;
                old = old.next;
                e.next = newTable[index];
            }
        }

    }

    public final boolean containsKey(int key) {
        int index = (key & 2147483647) % this.table.length;

        for(IntHashMap.Entry e = this.table[index]; e != null; e = e.next) {
            if (e.hash == key && e.key == key) {
                return true;
            }
        }

        return false;
    }

    public final Object get(int key) {
        int index = (key & 2147483647) % this.table.length;

        for(IntHashMap.Entry e = this.table[index]; e != null; e = e.next) {
            if (e.hash == key && e.key == key) {
                return e.value;
            }
        }

        return null;
    }

    public final Object put(int key, E value) {
        int index = (key & 2147483647) % this.table.length;
        if (value == null) {
            throw new IllegalArgumentException();
        } else {
            IntHashMap.Entry<E> e;
            for(e = this.table[index]; e != null; e = e.next) {
                if (e.hash == key && e.key == key) {
                    Object old = e.value;
                    e.value = value;
                    return old;
                }
            }

            if (this.count >= this.threshold) {
                this.rehash();
                return this.put(key, value);
            } else {
                e = new IntHashMap.Entry();
                e.hash = key;
                e.key = key;
                e.value = value;
                e.next = this.table[index];
                this.table[index] = e;
                ++this.count;
                return null;
            }
        }
    }

    public final Object remove(int key) {
        int index = (key & 2147483647) % this.table.length;
        IntHashMap.Entry e = this.table[index];

        for(IntHashMap.Entry prev = null; e != null; e = e.next) {
            if (e.hash == key && e.key == key) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    this.table[index] = e.next;
                }

                --this.count;
                return e.value;
            }

            prev = e;
        }

        return null;
    }

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public Object get(Object key) {
        if (!(key instanceof Number)) {
            throw new IllegalArgumentException("key is not an Number subclass");
        } else {
            return this.get(((Number)key).intValue());
        }
    }

    public Object put(Object key, Object value) {
        if (!(key instanceof Number)) {
            throw new IllegalArgumentException("key cannot be null");
        } else {
            return this.put(((Number)key).intValue(), value);
        }
    }

    public void putAll(Map otherMap) {
        Iterator it = otherMap.keySet().iterator();

        while(it.hasNext()) {
            Object k = it.next();
            this.put(k, otherMap.get(k));
        }

    }

    public Object remove(Object key) {
        if (!(key instanceof Number)) {
            throw new IllegalArgumentException("key cannot be null");
        } else {
            return this.remove(((Number)key).intValue());
        }
    }

    public void clear() {
        IntHashMap.Entry[] tab = this.table;
        int index = tab.length;

        while(true) {
            --index;
            if (index < 0) {
                this.count = 0;
                return;
            }

            tab[index] = null;
        }
    }

    public boolean containsKey(Object key) {
        if (!(key instanceof Number)) {
            throw new InternalError("key is not an Number subclass");
        } else {
            return this.containsKey(((Number)key).intValue());
        }
    }

    public boolean containsValue(Object value) {
        IntHashMap.Entry[] tab = this.table;
        if (value == null) {
            throw new IllegalArgumentException();
        } else {
            int i = tab.length;

            while(i-- > 0) {
                for(IntHashMap.Entry e = tab[i]; e != null; e = e.next) {
                    if (e.value.equals(value)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public Set keySet() {
        Set<Object> result = new HashSet<>();
        IntHashMap.IntHashMapIterator it = new IntHashMap.IntHashMapIterator<E>(this.table, true);

        while(it.hasNext()) {
            result.add(it.next());
        }

        return result;
    }

    public Collection<E> values() {
        List<E> result = new ArrayList<E>();
        IntHashMap.IntHashMapIterator<E> it = new IntHashMap.IntHashMapIterator<E>(this.table, false);

        while(it.hasNext()) {
            result.add(it.next());
        }

        return result;
    }

    public Set entrySet() {
        throw new UnsupportedOperationException("entrySet");
    }

    public static class Entry<E> {
        int hash;
        int key;
        E value;
        IntHashMap.Entry next;

        public Entry() {
        }
    }

    private static class IntHashMapIterator<E> implements Iterator<E> {
        boolean keys;
        int index;
        IntHashMap.Entry<E>[] table;
        IntHashMap.Entry<E> entry;

        IntHashMapIterator(IntHashMap.Entry<E>[] table, boolean keys) {
            this.table = table;
            this.keys = keys;
            this.index = table.length;
        }

        public boolean hasNext() {
            if (this.entry != null) {
                return true;
            } else {
                do {
                    if (this.index-- <= 0) {
                        return false;
                    }
                } while((this.entry = this.table[this.index]) == null);

                return true;
            }
        }

        public E next() {
            if (this.entry == null) {
                while(this.index-- > 0 && (this.entry = this.table[this.index]) == null) {
                }
            }

            if (this.entry != null) {
                IntHashMap.Entry<E> e = this.entry;
                this.entry = e.next;
                return this.keys ? null : e.value;
            } else {
                throw new NoSuchElementException("IntHashMapIterator");
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
}
