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
 * Created by primak on 05.01.2016.
 */
public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=java+%s";


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
                Elements elements = document.select(".job") ;
                if (elements.size() == 0){break;}
                for(Element element: elements){
                    //title
                    Element titleElement = element.select(".title").first();
                    String title = titleElement.select("a").text();



                    //salary
                    Element salaryElement = element.select(".salary").first();
                    String salary = "";
                    if (salaryElement!=null){
                        salary = salaryElement.text();
                    }

                    //city
                    Element cityElement = element.select(".meta").first();
                    Element locationElement = cityElement.select(".location").first();
                    String city = "";
                    if (locationElement!=null){
                        city = locationElement.select("a").text();
                    }


                    //companyName
                    Element companyNameElement = element.select(".company_name").first();
                    String companyName = companyNameElement.select("a").text();


                    //siteName
                    String siteName = "https://moikrug.ru/";

                    //url
                    String url = "https://moikrug.ru"+titleElement.select("a").attr("href");

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

    protected Document getDocument(String searchString, int page) throws IOException
    {
        String formatStr = String.format(URL_FORMAT,page,searchString);
        Document doc = Jsoup.connect(formatStr).userAgent("Mozilla/5.0 (Windows NT 6.1) Chrome/47.0.2526.106").referrer("none").get();
        return doc;
    }
}
