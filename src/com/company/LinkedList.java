package com.company;

import java.util.NoSuchElementException;

// двусвязный список
public class LinkedList<Type> implements List<Type>{

    private Element<Type> firstElement;
    private Element<Type> lastElement;

    private int size = 0;

    //конструктор
    public LinkedList(){
        lastElement = new Element<>(null, null, null);
        firstElement = new Element<>(null, null, lastElement);
        lastElement.setPrevElement(firstElement);
    }

    // функция добавления элемента, вставляет элемент в конец списка
    @Override
    public void add(Type e) {
        addLast(e);
    }

    // добавление элемента в конец списка
    @Override
    public void addLast(Type e) {
        Element<Type> prev = lastElement;
        prev.setElementValue(e);
        lastElement = new Element<>(null, prev, null);
        prev.setNextElement(lastElement);
        size++;
    }

    // добавление элемента в начало списка
    @Override
    public void addFirst(Type e) {
        Element<Type> next = firstElement;
        next.setElementValue(e);
        firstElement = new Element<>(null, null, next);
        next.setPrevElement(firstElement);
        size++;
    }

    // возвращает размер списка
    @Override
    public int size() {
        return size;
    }

    public void unlinkElement(Element<Type> removingElement) {
        removingElement.prevElement = null;
        removingElement.nextElement = null;
        removingElement.value = null;
    }

    // удаление элемента по его индексу
    @Override
    public void remove(int index) {
        if (index > size - 1 || index < 0){
            throw new NoSuchElementException();
        }
        Element<Type> removingElement;

        if (index < size/2){
            removingElement = firstElement.getNextElement();
            for (int i = 0; i < index; i++){
                removingElement = removingElement.getNextElement();
            }
            if (removingElement != null){
                Element<Type> nextElement = removingElement.nextElement;
                Element<Type> prevElement = removingElement.prevElement;
                nextElement.prevElement = prevElement;
                prevElement.nextElement = nextElement;
                unlinkElement(removingElement);
                size--;
            }
        } else {
            removingElement = lastElement.getPrevElement();
            for (int i = size - 1; i > index; i--){
                removingElement = removingElement.getPrevElement();
            }
            if (removingElement != null){
                Element<Type> nextElement = removingElement.nextElement;
                Element<Type> prevElement = removingElement.prevElement;
                nextElement.prevElement = prevElement;
                prevElement.nextElement = nextElement;
                unlinkElement(removingElement);
                size--;
            }
        }
    }


    // удаление последнего в списке элемента
    @Override
    public void removeLast() {
        Element<Type> element = lastElement;
        if (element == null)
            throw new NoSuchElementException();

        element = lastElement.prevElement.prevElement;
        if (element == null){
            Element<Type> removingElement = lastElement.prevElement;
            unlinkElement(removingElement);
        } else {
            element.nextElement = lastElement;
            Element<Type> removingElement = lastElement.prevElement;
            unlinkElement(removingElement);
            lastElement.prevElement = element;
            size--;
        }
    }


    // удаление первого в списке элемента
    @Override
    public void removeFirst() {
        Element<Type> element = firstElement;
        if (element == null)
            throw new NoSuchElementException();

        element = firstElement.nextElement.nextElement;
        if (element == null){
            Element<Type> removingElement = firstElement.nextElement;
            unlinkElement(removingElement);
        } else {
            element.prevElement = firstElement;
            Element<Type> removingElement = firstElement.nextElement;
            unlinkElement(removingElement);
            firstElement.nextElement = element;
            size--;
        }
    }

    // возвращение значения элемента списка по его индексу
    @Override
    synchronized public Type getElementByIndex(int index) {
        if (index > size - 1 || index < 0){
            throw new NoSuchElementException();
        }
        Element<Type> currentElement;

        if (index < size/2){
            currentElement = firstElement.getNextElement();
            for (int i = 0; i < index; i++){
                currentElement = currentElement.getNextElement();
            }
        } else {
            currentElement = lastElement.getPrevElement();
            for (int i = size - 1; i > index; i--){
                currentElement = currentElement.getPrevElement();
            }
        }
        if (currentElement == null){
            return null;
        }
        return currentElement.getValue();
    }

    // описание элемента списка
    private class Element<Type>{
        private Type value;
        private Element<Type> nextElement;
        private Element<Type> prevElement;

        private Element(Type value, Element<Type> prevElement, Element<Type> nextElement){
            this.value = value;
            this.prevElement = prevElement;
            this.nextElement = nextElement;
        }

        public Type getValue (){
            return this.value;
        }

        public void setElementValue(Type e) {
            this.value = e;
        }

        public void setNextElement(Element<Type> nextElement) {
            this.nextElement = nextElement;
        }

        public void setPrevElement(Element<Type> prevElement){
            this.prevElement = prevElement;
        }

        public Element<Type> getNextElement(){
            return this.nextElement;
        }

        public Element<Type> getPrevElement(){
            return this.prevElement;
        }
    }

}

