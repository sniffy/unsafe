package tools.unsafe;

public class Safe {

    private static volatile Safe INSTANCE;

    private Safe() {
    }

    public static Safe getInstance() {
        if (null == INSTANCE) {
            synchronized (Safe.class) {
                if (null == INSTANCE) {
                    INSTANCE = new Safe();
                }
            }
        }
        return INSTANCE;
    }
}
