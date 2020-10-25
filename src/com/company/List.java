package com.company;

public interface List<Type> {
    void add(Type e);
    void addLast(Type e);
    void addFirst(Type e);
    int size();
    void remove(int index);
    void removeLast();
    void removeFirst();
    Type getElementByIndex(int index);
}
