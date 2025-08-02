package gui;

import javax.swing.JFrame;


/*
 * Creating an abstract class helps us setup the blueprint that our GUIs will follow, for example
 * in each of the GUIs they will have the same size and will need to invoke their own addGuiComponents()
 * which will be unique to each subclass
 */
public abstract class BaseFrame extends JFrame {
    public BaseFrame(String title) {
        init(title);
    }

    private void init(String title) {
        setTitle(title);
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        addGuiComponents();
    }

    protected abstract void addGuiComponents();
}