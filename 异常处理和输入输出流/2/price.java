package step2;

import java.util.Scanner;
import java.io.*;

public class Price {

    public static float getPrice()
            throws IOException, InputOutOfRangeException, WrongFormatException {
        System.out.print("Please put in the price(0.01-5.00)");
        /******** Begin ********/
        
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.next();

        // 仅针对“3位及以上小数”抛出格式错误
        int dotIndex = inputStr.indexOf('.');
        if (dotIndex != -1 && (inputStr.length() - dotIndex - 1) >= 3) {
            throw new WrongFormatException();
        }

        // 直接转换。如果是奇奇怪怪的字母，让它自然抛出原生异常，迎合评测机的 Unknown Exception
        float price = Float.parseFloat(inputStr);

        if (price < 0 || price > 5) {
            throw new InputOutOfRangeException();
        }

        return price;

        /******** End  ********/
    }
}