package com.javarush.test.level28.lesson15.big01.view;

import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

/**
 * Created by primak on 13.01.2016.
 */
public class SwingView implements View
{
    private Controller controller;
    @Override
    public void update(List<Vacancy> vacancies)
    {

        final List<Vacancy> vac=vacancies;
        System.out.println(vac.size());
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {

                JFrame vacancyWindow = new JFrame("Vacancy");
                vacancyWindow.setSize(1024, 768);
                vacancyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel headPanel = new JPanel();
                headPanel.setLayout(new GridLayout(1, 4, 1, 1));
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout((vac.size() + 1), 0, 1, 1));

                Border border = BorderFactory.createEtchedBorder();
                JLabel[] head = new JLabel[]{
                        new JLabel("Number"),
                        new JLabel("Title"),
                        new JLabel("City"),
                        new JLabel("Company Name"),
                        new JLabel("Salary")
                };
                for (JLabel label:head){
                    label.setBorder(border);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFont(new java.awt.Font("Arial", Font.ITALIC, 16));
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLUE);
                    headPanel.add(label, BorderLayout.CENTER);
                }
                int vacancyCount=0;
                for (Vacancy vacancy:vac){
                    vacancyCount++;
                    JLabel[] vacancyData = new JLabel[]{
                            new JLabel(Integer.toString(vacancyCount)),
                            new JLabel(vacancy.getTitle()),
                            new JLabel(vacancy.getCity()),
                            new JLabel(vacancy.getCompanyName()),
                            new JLabel(vacancy.getSalary())
                                                        };
                    for (JLabel label: vacancyData){
                        label.setBorder(border);
                        label.setFont(new java.awt.Font("Arial", Font.ITALIC, 10));
                        panel.add(label);
                    }
                }
                JScrollPane scrollPane = new JScrollPane(panel);
                scrollPane.setColumnHeaderView(headPanel);
                //vacancyWindow.add(headPanel,BorderLayout.NORTH);
                vacancyWindow.add(scrollPane,BorderLayout.CENTER);
                vacancyWindow.setVisible(true);
            }
        });

    }

    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Odessa");
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }
}
