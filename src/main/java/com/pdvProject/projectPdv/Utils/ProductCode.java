package com.pdvProject.projectPdv.Utils;

import java.util.Random;

public class ProductCode {

    public static String generateCode(){

        StringBuilder EAN13Code = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < 12; i++){
            int digit = random.nextInt(10);
            EAN13Code.append(digit);
        }
        
        EAN13Code.append(verificationCode(EAN13Code.toString()));
        return EAN13Code.toString();

    }

    private static String verificationCode(String digits){

        int sum = 0;
        int checkCode;

        for(int i = 0; i < digits.length(); i++){
            int digit = Integer.parseInt(digits.substring(i, i + 1));
            if(i%2 == 0){
                sum += digit;
            }else{
                sum += digit*3;
            }
        }

        int result = sum/10;
        checkCode = (((1 + result)*10) - sum)%10;

        return Integer.toString(checkCode);

    }
}
