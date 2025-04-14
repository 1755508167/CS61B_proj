package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    //默认的大小
    private static final int DEFAULT_SIZE = 16;
    //负载因子的阈值
    private static final double MAX_LF = 0.75;
    //储存桶的列表
    private ArrayMap<K, V>[] buckets;
    //总的元素的数量
    private int size;

    //计算负载因子
    private int loadFactor() {
        return size / buckets.length;
    }
    //构造函数，初始化哈希表
    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        //把列表里的每个桶设置为空的
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    //计算哈希码，返回索引
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int numBuckets = buckets.length;
        //求模，得到索引
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index=hash(key);
        if (containKey(buckets[index],key)){
            return buckets[index].get(key);
        }else {
            return null;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    //如果键已存在，则更新值
    @Override
    public void put(K key, V value) {
        //计算索引
        int index=hash(key);
        ArrayMap<K,V> map=buckets[index];
        if (!containsKey(key)){
            size++;
        }
        map.put(key,value);

    }
    //检查整个HashMap里是否存在这个key
    @Override
    public boolean containsKey(K key){
        int index=hash(key);
        if (containKey(buckets[index],key)){
            return true;
        }else {
            return false;
        }
    }
    //检查桶里是否存在某个键
    private boolean containKey(ArrayMap<K,V> map,K key){

        for (K k:map){
            if (k.equals(key)){
                return true;
            }
        }
        return false;
    }
    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
