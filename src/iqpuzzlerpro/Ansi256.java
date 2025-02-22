package iqpuzzlerpro;

public class Ansi256 {
    public static final String coloredText(String text, int color, int background) {
        return "\033[" + "38;5;" + color + "m" + "\033[48;5;" + background + "m" + text + "\033[0m";
    }

    public static final String coloredText(String text, int color, boolean bold) {
        return "\033[" + (bold ? "1" : "0") + ";38;5;" + color + "m" + text + "\033[0m";
    }

    public static final void printColoredText(String text, int color, int background) {
        System.out.print(coloredText(text, color, background));
    }
}
