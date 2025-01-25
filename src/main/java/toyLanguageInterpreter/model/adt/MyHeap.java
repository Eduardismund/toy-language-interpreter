package toyLanguageInterpreter.model.adt;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.values.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHeap<K,V> extends MyDictionary<K,V> implements MyIHeap<K,V>{

    private int counter;

    public MyHeap(){
        this.counter = 0;
    }


    @Override
    public int getNextFree() {
        return counter + 1;
    }

    @Override
    public void updateNewFree() {
        List<Map.Entry<K,V>> l = new ArrayList<>(this.getMap().entrySet());
        if(l.isEmpty()){
            this.counter = 0;
        }
        else{
            Map.Entry<K,V> last = l.getLast();
            this.counter = (int)(last.getKey()) + 1;
        }
        this.counter++;
    }
}
