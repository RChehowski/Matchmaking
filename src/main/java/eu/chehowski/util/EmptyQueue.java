package eu.chehowski.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;

public class EmptyQueue<T> implements Queue<T>
{
    public static final Queue<?> EMPTY_QUEUE = new EmptyQueue<>();


    private EmptyQueue()
    {
    }

    @Override
    public boolean add(T t)
    {
        return false;
    }

    @Override
    public boolean offer(T t)
    {
        return false;
    }

    @Override
    public T remove()
    {
        return null;
    }

    @Override
    public T poll()
    {
        return null;
    }

    @Override
    public T element()
    {
        return null;
    }

    @Override
    public T peek()
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public boolean contains(Object o)
    {
        return false;
    }

    @Override
    public Iterator<T> iterator()
    {
        return Collections.emptyIterator();
    }

    @Override
    public Object[] toArray()
    {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a)
    {
        return null;
    }

    @Override
    public boolean remove(Object o)
    {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return false;
    }

    @Override
    public void clear()
    {
    }

    @SuppressWarnings("unchecked")
    public static <T> Queue<T> emptyQueue()
    {
        return (Queue<T>)EMPTY_QUEUE;
    }
}
