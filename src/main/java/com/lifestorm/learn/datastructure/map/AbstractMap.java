package com.lifestorm.learn.datastructure.map;

import java.util.*;

/**
 * Created by life_storm on 2018/9/26.
 */
public abstract class AbstractMap<K,V>  implements Map<K,V> {

    protected AbstractMap(){

    }

    public int size() {
        return entrySet().size();
    }

    public boolean isEmpty(){
        return size() == 0 ;
    }

    public boolean containsValue(Object value) {

        Iterator<Entry<K,V>> i = entrySet().iterator();
        if( value == null ){
            while ( i.hasNext() ){
                Entry<K,V> e = i.next();
                if( e.getValue() == null  ){
                    return true;
                }
            }
        }else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if( value.equals( e.getValue() )){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if( key == null ){
            while ( i.hasNext() ){
                Entry<K,V> e = i.next();
                if( e.getKey() == null  ){
                    return true;
                }
            }
        }else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if( key.equals( e.getKey() )){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public V get(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if( key == null ){
            while ( i.hasNext() ){
                Entry<K,V> e = i.next();
                if( e.getKey() == null  ){
                    return e.getValue();
                }
            }
        }else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if( key.equals( e.getKey() )){
                    return e.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public V remove(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        Entry<K,V> correctEntry = null;
        if( key == null ){
            while( correctEntry==null && i.hasNext()) {
                Entry<K,V> e = i.next();
                if( e.getKey() == null ){
                    correctEntry = e;
                }
            }
        }else {
                while( correctEntry==null && i.hasNext()) {
                    Entry<K,V> e = i.next();
                    if( key.equals(e.getKey())){
                        correctEntry = e;
                    }
                }
        }
        V oldValue = null ;
        if( correctEntry != null ){
            oldValue = correctEntry.getValue();
            i.remove();
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(),e.getValue());
        }
    }

    @Override
    public void clear() {
        entrySet().clear();
    }

    //views

    transient volatile Set<K> keySet = null;
    transient volatile Collection<V> values = null;

    @Override
    public Set<K> keySet() {
       if ( keySet == null ){
           keySet = new AbstractSet<K>() {
               @Override
               public Iterator<K> iterator() {

                   return new Iterator<K>() {
                       private Iterator<Entry<K,V>> i = entrySet().iterator();
                       @Override
                       public boolean hasNext() {
                           return i.hasNext();
                       }

                       @Override
                       public K next() {
                           return i.next().getKey();
                       }

                       @Override
                       public void remove() {
                            i.remove();
                       }
                   };
               }

               @Override
               public int size() {
                   return AbstractMap.this.size();
               }

               @Override
               public boolean contains(Object k) {
                   return AbstractMap.this.containsKey(k);
               }
           };
       }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        if( values == null ){
            values = new AbstractCollection<V>() {
                @Override
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();
                        @Override
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        @Override
                        public V next() {
                            return i.next().getValue();
                        }

                        @Override
                        public void remove() {
                            i.remove();
                        }
                    };
                }

                @Override
                public int size() {
                    return AbstractMap.this.size();
                }

                @Override
                public boolean contains(Object v) {
                    return AbstractMap.this.containsValue(v);
                }
            };

        }
        return values;
    }

    public abstract Set<Entry<K,V>> entrySet();

    @Override
    public boolean equals(Object o) {
        if( o == this) {
            return true;
        }

        if( !(o instanceof Map) ){
            return false;
        }
        Map<K,V> m = (Map<K,V>) o;
        if( m.size() != size() ){
            return  false;
        }
        try {
            Iterator<Entry<K,V>> i = entrySet().iterator();
            while ( i.hasNext() ) {
                Entry<K,V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if( value == null ){
                    if( !(m.get(key) == null && m.containsKey(key))){
                        return false;
                    }else {
                        if( !value.equals(m.get(key))){
                            return false;
                        }
                    }
                }
            }
        }catch (Exception ex){
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
       int h = 0 ;
        Iterator<Entry<K,V>> i = entrySet().iterator();
        while(i.hasNext())
            h+=i.next().hashCode();
        return h;
    }

    private static boolean eq(Object o1, Object o2 ){
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    public static class SimleEntry<K,V>
    implements Entry<K,V>,java.io.Serializable {

        private static final long serialVersionUID = 1L;

        private final K key;
        private V value;

        public SimleEntry(K key,V value){
            this.key = key;
            this.value = value;
        }

        public SimleEntry(Entry<? extends  K, ? extends  V> entry){
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
           if( !(o instanceof Entry) ){
               return false;
           }
           Entry e =  (Entry) o;
           return eq(key,e.getKey()) && eq(value,e.getValue());

        }

        @Override
        public int hashCode() {
          return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }

        @Override
        public String toString() {
            return "SimleEntry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public static class SimpleImmutableEntry<K,V>
    implements Entry<K,V>,java.io.Serializable {
        private static final long serialVersionUID = 1L;

        private final K key;
        private V value;

        public SimpleImmutableEntry(K key,V value){
            this.key = key;
            this.value = value;
        }

        public SimpleImmutableEntry(Entry<? extends  K, ? extends  V> entry){
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if( !(o instanceof Entry) ){
                return false;
            }
            Entry e =  (Entry) o;
            return eq(key,e.getKey()) && eq(value,e.getValue());

        }

        @Override
        public int hashCode() {
            return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }

        @Override
        public String toString() {
            return "SimpleImmutableEntry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
