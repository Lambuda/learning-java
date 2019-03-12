package com.haier.rrswl.itms.dsgmd.subob;

public class Wolf extends Subject {

    public  void invoke(){
        System.out.println("大灰狼来啦");
        notifyObserver("大灰狼来啦");
    }


    public static void main(String[] args) {
        Wolf wolf = new Wolf();
        XiyyObserver xiyyObserver = new XiyyObserver();
        wolf.addObs(xiyyObserver);

        wolf.invoke();

    }
}
