package com.exchangerate.app;

import com.exchangerate.base.Currency;
import com.exchangerate.utils.CurrencyCodes;
import com.exchangerate.utils.ExchangeApiCaller;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConverterApp {

    private final String separator = "***************************************";
    private final Scanner input = new Scanner(System.in);
    private List<String> currencyOptions = new ArrayList<>();

    public void run(){

        System.out.println("|| Bienvenido al conversor de monedas || \n");
        showMainMenu();

    }


    private void showMainMenu(){

        double option = -1;

        while(option != 0){

            currencyOptions = CurrencyCodes.getNameList();

            System.out.println(separator + "\n");
            System.out.println(" +[ Elija una moneda base para convertir ]+ ");

            listOptions();

            System.out.println(separator + "\n");

            System.out.println("> Escriba la opción o 0 para salir:");
            option = processInput(0, currencyOptions.size());

            if (option != 0){

                String currencyChoice = currencyOptions.get((int) option - 1);
                currencyOptions.remove((int) option - 1);

                showConvertMenu(currencyChoice);
            }
        }

        System.out.println("Gracias por utilizar el conversor de monedas");

    }

    private void showConvertMenu(String choice1) {

        System.out.printf("\n +[ Convirtiendo desde %s a... ]+ \n", choice1);

        listOptions();

        System.out.println(separator + "\n");
        System.out.println("> Escriba la opción: ");

        double option = processInput(1, currencyOptions.size());
        String choice2 = currencyOptions.get( (int) option - 1 );

        System.out.printf("> Escriba el monto de %s a convertir a %s : ", choice1, choice2);
        double amount = processInput(0.01f, 999999999.99f);

        var caller = new ExchangeApiCaller();

        var resultList = caller.requestPairConversion(choice1, CurrencyCodes.getCode(choice1), choice2, CurrencyCodes.getCode(choice2), String.valueOf(amount));

        showConversionResult(resultList);

    }

    private void showConversionResult(List<Currency> resultList) {

        System.out.println(separator + "\n");

        Currency c1 = resultList.get(0);
        Currency c2 = resultList.get(1);

        System.out.printf(" +[ %s %s (%s) son iguales a: %s %s (%s) ]+ \n\n",
                c1.value(), c1.code(), c1.name(),
                c2.value(), c2.code(), c2.name());

    }

    private void listOptions() {
        int i = 0;

        for ( String name : currencyOptions){
            System.out.println(++i + "- " + name);
        }
        System.out.println();
    }

    private double processInput(double min, double max) {
        double inputNumber;
        try {
            inputNumber = input.nextDouble();
            if(inputNumber < min || inputNumber > max) throw new IllegalArgumentException();

        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.printf("> Por favor ingrese un número valido del %.2f al %.2f: ", min, max);
            input.nextLine();
            inputNumber = processInput(min, max);
        }
        return inputNumber;
    }

}
