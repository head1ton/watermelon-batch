package ai.watermelonbatch;

public class CustomService<T> {

    private int cnt = 0;

    public T customRead() {
        return (T) ("item" + cnt++);
    }
}
