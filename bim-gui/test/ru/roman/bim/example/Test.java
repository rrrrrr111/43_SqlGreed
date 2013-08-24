package ru.roman.bim.example;

import ru.roman.bim.util.ExceptionHandler;

/** @author Roman 27.07.13 17:35 */
public class Test {




    public static void main(String[] args) {


        try {
            throw new Exception("dfgd dsfg sdf dsf ");
        } catch (Exception e) {
            ExceptionHandler.showErrorMessageAndExit(e);
        }


    }
}
