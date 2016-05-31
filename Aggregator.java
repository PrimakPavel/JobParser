package com.javarush.test.level28.lesson15.big01;

import com.javarush.test.level28.lesson15.big01.model.HHStrategy;
import com.javarush.test.level28.lesson15.big01.model.Model;
import com.javarush.test.level28.lesson15.big01.model.MoikrugStrategy;
import com.javarush.test.level28.lesson15.big01.model.Provider;
import com.javarush.test.level28.lesson15.big01.view.HtmlView;
import com.javarush.test.level28.lesson15.big01.view.SwingView;


/**
 * Created by primak on 29.12.2015.
 */
public class Aggregator
{
    public static void main(String[] args){
        SwingView swingView = new SwingView();
        Model model = new Model(swingView,new Provider[]{new Provider(new HHStrategy()),new Provider(new MoikrugStrategy()) });
        Controller controller = new Controller(model);
        swingView.setController(controller);
        swingView.userCitySelectEmulationMethod();
    }

}
