package com.logicbig.example;

import javax.swing.*;
import java.awt.*;

public class StarvationDemo {
    private static Object sharedObj = new Object();

    public static void main(String[] args) {
        JFrame frame = createFrame();
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (int i = 0; i < 5; i++) {
            ProgressThread progressThread = new ProgressThread();
            frame.add(progressThread.getProgressComponent());
            progressThread.start();
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("Starvation Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(300, 200));
        return frame;
    }

    private static class ProgressThread extends Thread {
        JProgressBar progressBar;

        ProgressThread() {
            progressBar = new JProgressBar();
            String name = "";
            if (this.getName().contains("0")) {
                name = "Urgent & Important";
                setPriority(MAX_PRIORITY);
            } else if (this.getName().contains("1")) {
                name = "Important";
                setPriority(NORM_PRIORITY);
            } else {
                name = "Urgent";
                setPriority(MIN_PRIORITY);
            }
            progressBar.setString(name);
            progressBar.setStringPainted(true);
        }

        JComponent getProgressComponent() {
            return progressBar;
        }

        @Override
        public void run() {

            int c = 0;
            while (true) {
                synchronized (sharedObj) {
                    if (c == 100) {
                        if (getName().equals("Urgent & Important"))
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        c = 0;
                    }
                    progressBar.setValue(++c);
                    try {
                        if (getName().equals("Urgent & Important"))
                        //sleep the thread to simulate long running task
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
