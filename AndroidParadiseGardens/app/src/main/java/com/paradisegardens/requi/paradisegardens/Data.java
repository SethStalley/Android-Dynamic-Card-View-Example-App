package com.paradisegardens.requi.paradisegardens;


import java.util.ArrayList;

public class Data {

    public ArrayList<String> getCardData(){
        ArrayList<String> cardsData = new ArrayList<>();

        String name = "Coffee Coaster\n";
        String description = "Descripción: Carritos de forma de granos de café en un recorrido de 2minutos con caídas libres y subidas de 90 grados.\n";
        String schedule = "Horario: 7am - 5:30 pm\n";
        String state = "Estado: Funcionando\n";
        String wait = "Tiempo de Espera:\n";

        String text = name + description + schedule +  state + wait;
        cardsData.add(String.format(text));
        return cardsData;
    }

}
