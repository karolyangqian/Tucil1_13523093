package iqpuzzlerpro;

public class Ansi {
    public static final String coloredText(String text, int color) {
        return "\033[38;5;" + color + "m" + text + "\033[0m";
    }

    public static final String coloredText(String text, int color, boolean bold) {
        return "\033[" + (bold ? "1" : "0") + ";38;5;" + color + "m" + text + "\033[0m";
    }

    public static final void printColoredText(String text, int color) {
        System.out.print(coloredText(text, color));
    }
}
