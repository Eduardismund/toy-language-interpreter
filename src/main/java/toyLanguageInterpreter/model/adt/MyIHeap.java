package toyLanguageInterpreter.model.adt;

public interface MyIHeap<K,V> extends MyIDictionary<K,V> {
    int getNextFree();
    void updateNewFree();
}
