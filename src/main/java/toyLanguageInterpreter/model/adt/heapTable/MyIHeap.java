package toyLanguageInterpreter.model.adt.heapTable;

import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;

public interface MyIHeap<K,V> extends MyIDictionary<K,V> {
    int getNextFree();
    void updateNewFree();
}
