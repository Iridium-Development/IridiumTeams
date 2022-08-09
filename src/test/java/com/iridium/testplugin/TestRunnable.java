package com.iridium.testplugin;

public class TestRunnable implements Runnable {
    private boolean run = false;

    @Override
    public void run() {
        run = true;
    }

    public boolean hasRan() {
        return run;
    }
}
