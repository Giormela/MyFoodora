package myFoodora.clui;

public enum Color {
    BLACK("\033[30m"),
    RED("\033[31m"),
    GREEN("\033[32m"),
    YELLOW("\033[33m"),
    BLUE("\033[34m"),
    PURPLE("\033[35m"),
    CYAN("\033[36m"),
    WHITE("\033[37m");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
    
    public static String colorText(String text, Color color) {
        return color.getColorCode() + text + "\033[0m";
    }
}

