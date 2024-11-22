package model.adt;

import exceptions.ADTException;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    private Map<K, V> map;

    public MyDictionary() {
        map = new LinkedHashMap<>();
    }

    @Override
    public void put(K key, V value) throws ADTException {

        try{
            map.put(key, value);
        } catch (Exception e) {
            throw new ADTException("The null value cannot be added to the dictionary!");
        }
    }

    public void setMap(Map<K, V> dict) {
        this.map = dict;
    }

    @Override
    public Collection<V> values(){
        return this.map.values();
    }


    @Override
    public void remove(K key) throws ADTException {
        try {
            this.map.remove(key);
        } catch (Exception e) {
            throw new ADTException("The key doesn't exist in the dictionary!");
        }
    }

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        try {
            return map.get(key);
        } catch (Exception e) {
            throw new ADTException("The key doesn't exist in the dictionary!");
        }
    }

    @Override
    public void update(K key, V value) throws ADTException {
        try {
            map.put(key, value);
        } catch (Exception e) {
            throw new ADTException("The key doesn't exist in the dictionary!");
        }
    }

    @Override
    public Map<K,V> getMap(){
        return map;
    }

    @Override
    public String toString() {
        String result = "{";
        for(K key : this.map.keySet()){
            result += key.toString() + " -> " + this.map.get(key).toString() + ", ";
        }
        if(result.length() > 1)
            result = result.substring(0, result.length() - 2);
        result += "}";
        return result;
    }
}
