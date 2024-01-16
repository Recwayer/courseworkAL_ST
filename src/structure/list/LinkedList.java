package structure.list;


import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E>, Iterable<E> {
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    public LinkedList() {
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(E element) {
        Node<E> current = first;
        for (int i = 0; i < size; i++) {
            if (element.equals(current.data)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E element) {
        Node<E> current = last;
        for (int i = size - 1; i >= 0; i--) {
            if (element.equals(current.data)) {
                return i;
            }
            current = current.prev;
        }
        return -1;
    }

    @Override
    public void set(int index, E element) {
        validateIndex(index);
        Node<E> node = getNode(index);
        node.data = element;
    }

    @Override
    public void sort(Comparator<? super E> comparator) {
        throw new UnsupportedOperationException("Sort in LinkedList not Supported");
    }

    @Override
    public List<E> subList(int start, int end) {
        if (start < 0 || end > size || start > end) {
            throw new IndexOutOfBoundsException("Invalid start or end index");
        }

        LinkedList<E> subList = new LinkedList<>();
        Node<E> current = getNode(start);

        for (int i = start; i < end; i++) {
            subList.add(current.data);
            current = current.next;
        }

        return subList;
    }

    @Override
    public void remove(int index) {
        validateIndex(index);
        Node<E> node = getNode(index);
        Node<E> nextNode = node.next;
        Node<E> prevNode = node.prev;
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        size--;
    }

    @Override
    public boolean contains(E element) {
        Node<E> current = first;
        while (current != null) {
            if (element.equals(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void add(E element) {
        linkLast(element);
    }

    @Override
    public void add(int index, E element) {
        validateIndex(index);
        if (index == size) {
            linkLast(element);
        } else if (index == 0) {
            linkFirst(element);
        } else {
            Node<E> current = getNode(index - 1);
            Node<E> newNode = new Node<>(current, element, current.next);
            current.next.prev = newNode;
            current.next = newNode;
            size++;
        }
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return getNode(index).data;
    }


    private void linkLast(E e) {
        Node<E> l = last;
        Node<E> node = new Node<>(l, e, null);
        last = node;
        if (l == null) {
            first = node;
        } else {
            l.next = node;
        }
        size++;
    }

    private void linkFirst(E t) {
        final Node<E> f = first;
        final Node<E> node = new Node<>(null, t, f);
        first = node;
        if (f == null) {
            last = node;
        } else {
            f.prev = node;
        }
        size++;
    }


    private Node<E> getNode(int index) {
        Node<E> node;
        if (index < (size / 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void validateIndex(int index) {
        if (size <= index) {
            throw new IllegalArgumentException("Index does not exist.");
        } else if (index < 0) {
            throw new IllegalArgumentException("The index must be positive.");
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> current = first;
            private Node<E> lastReturned = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = current;
                E data = current.data;
                current = current.next;
                return data;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException("Cannot remove element before calling next");
                }

                Node<E> toRemove = lastReturned;
                Node<E> nextNode = toRemove.next;
                Node<E> prevNode = toRemove.prev;

                if (nextNode == null) {
                    last = prevNode;
                } else {
                    nextNode.prev = prevNode;
                    toRemove.next = null;
                }

                if (prevNode == null) {
                    first = nextNode;
                } else {
                    prevNode.next = nextNode;
                    toRemove.prev = null;
                }

                size--;
                lastReturned = null;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = first;

        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }

        sb.append("]");
        return sb.toString();
    }

    private static class Node<E> {
        Node<E> next;
        E data;
        Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
