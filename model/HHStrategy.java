package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by primak on 29.12.2015.
 */
public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";


    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        ArrayList<Vacancy> vacancies = new ArrayList<>();

        try
        {
            Document document;
            int pageCount=0;
            while (true){
                document = getDocument(searchString,pageCount++);
                if (document == null){break;}
                Elements elements = document.select("[data-qa=vacancy-serp__vacancy]");
                if (elements.size() == 0){break;}
                for(Element element: elements){
                    //title
                 Element titleElement = element.select("[data-qa=vacancy-serp__vacancy-title]").first();
                 String title = titleElement.text();



                    //salary
                 Element salaryElement = element.select("[data-qa=vacancy-serp__vacancy-compensation]").first();
                 String salary = "";
                 if (salaryElement!=null){
                        salary = salaryElement.text();
                 }

                    //city
                 String city = element.select("[data-qa=vacancy-serp__vacancy-address]").first().text();

                    //companyName
                 String companyName = element.select("[data-qa=vacancy-serp__vacancy-employer]").first().text();


                    //siteName
                 String siteName = "http://hh.ua/";

                    //url
                 String url = titleElement.attr("href");

                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(title);
                    vacancy.setSalary(salary);
                    vacancy.setCity(city);
                    vacancy.setCompanyName(companyName);
                    vacancy.setSiteName(siteName);
                    vacancy.setUrl(url);
                    vacancies.add(vacancy);
                }

            }


        }

        catch (IOException e){/*NOP*/}

        return vacancies;
    }


    protected Document getDocument(String searchString, int page) throws IOException{
        String formatStr = String.format(URL_FORMAT,searchString,page);
        Document doc = Jsoup.connect(formatStr).userAgent("Mozilla/5.0 (Windows NT 6.1) Chrome/47.0.2526.106").referrer("none").get();
        return doc;
    }

}
