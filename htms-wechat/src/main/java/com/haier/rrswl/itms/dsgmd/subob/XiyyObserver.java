package com.haier.rrswl.itms.dsgmd.subob;

public class XiyyObserver implements Observer {
    @Override
    public String getName() {
        return "喜洋洋";
    }

    @Override
    public void update(String msg) {
        System.out.println("通知喜洋洋：" + msg);
    }
}
