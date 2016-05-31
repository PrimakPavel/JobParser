package com.javarush.test.level28.lesson15.big01.view;

import com.javarush.test.level28.lesson15.big01.Controller;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.util.List;

/**
 * Created by primak on 04.01.2016.
 */
public class HtmlView implements View
{
    private Controller controller;
    private final String filePath = this.getClass().getPackage().toString().replace('.', '/').replaceFirst("package ", "./src/")+"/" + "vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies)
    {
        try
        {
            updateFile(getUpdatedFileContent(vacancies));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;

    }
    public void userCitySelectEmulationMethod(){
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList)
    {
         String result;
    try
    {
        Document document = getDocument();
        Element element = document.getElementsByClass("template").first();
        Element copyElement = element.clone();
        copyElement.removeClass("template");
        copyElement.removeAttr("style");
        Elements elements = document.getElementsByClass("vacancy");
        for (Element elem : elements)
        {
            if (!elem.hasClass("template"))
            {
                elem.remove();
            }

        }
        for (Vacancy vacancy : vacancyList)
        {
            Element clone = copyElement.clone();
            clone.getElementsByClass("city").append(vacancy.getCity());
            clone.getElementsByClass("companyName").append(vacancy.getCompanyName());
            clone.getElementsByClass("salary").append(vacancy.getSalary());
            clone.getElementsByClass("title").select("a").attr("href", vacancy.getUrl()).append(vacancy.getTitle());
            element.before(clone);

        }
        result = document.html();
    }
    catch (IOException e) {
       e.printStackTrace();
       result = "Some exception occurred";
    }

        return result;
    }


    private void updateFile(String fileContent)
    {
        try {
            BufferedWriter fWriter = new BufferedWriter(new FileWriter(filePath));
            fWriter.write(fileContent);
            fWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected Document getDocument() throws IOException{
        return Jsoup.parse(new File(filePath),"utf-8");
    }
}
