package com.lin.ds.itms.thread.flush;

import java.util.List;

public interface Processor<T> {
    void process(List<T> list);
}
