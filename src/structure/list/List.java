package structure.list;

import java.util.Comparator;

public interface List<E> extends Iterable<E> {

    void add(E element);

    void add(int index, E element);

    E get(int index);

    void remove(int index);

    boolean contains(E element);

    int size();


    int indexOf(E element);

    int lastIndexOf(E element);

    void set(int index, E element);

    void sort(Comparator<? super E> comparator);

    List<E> subList(int start, int end);
    static <E> ArrayList<E> of(E... elements) {
        if (elements == null) {
            throw new NullPointerException("Elements cannot be null");
        }
        return new ArrayList<>(elements);
    }

}