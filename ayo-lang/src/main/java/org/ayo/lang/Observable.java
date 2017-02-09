package org.ayo.lang;

import java.util.ArrayList;

/**
 *   protected Observable observable = new Observable();
     public Observable getObservable(){
            return observable;
     }
 */

public class Observable {
    private ArrayList<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void notifyDataChanged(Object source, Object data){
        for(Observer observer: observers){
            observer.update(this, source, data);
        }
    }
}
