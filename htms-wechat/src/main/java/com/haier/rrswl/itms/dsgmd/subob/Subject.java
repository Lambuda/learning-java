package com.haier.rrswl.itms.dsgmd.subob;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    /**
     * 观察者
     */
    List<Observer> observers = new ArrayList<>();


     void addObs(Observer observer) {
        observers.add(observer);
    }

     void deleteObs(Observer observer){
        observers.remove(observer);
    }


    void notifyObserver(String msg){
         observers.forEach(e -> {
             e.update(msg);
         });
    }
}
