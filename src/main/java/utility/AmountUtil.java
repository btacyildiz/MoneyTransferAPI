package utility;

public class AmountUtil {

    public static int getFloatDigitCount(double value){
        return String.valueOf(value).split("\\.")[1].length();
    }

}
