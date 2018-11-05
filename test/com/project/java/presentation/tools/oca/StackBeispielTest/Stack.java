package com.project.java.presentation.tools.oca.StackBeispielTest;

/**
 * Created by john on 27.10.2015.
 */
public class Stack<T> {
    private T[] elements;
    private int size;

    public Stack(int capacity){

    }

    public boolean isEmpty(){
        return true;
    }

    public boolean isFull(){
        return false;
    }

    public void push(T element) {
        this.elements[this.size] = element;
        this.size++;
    }
}
