package gomoku;

class Debug {

    static Boolean print = false;
    private static Boolean debug = false;

    static void print(Object x) {
        if (print)
            System.out.println(x);
    }

    static void debug(Object x) {
        if (debug)
            print(x);
    }

    static void on() {
        print = debug = true;
    }
}
