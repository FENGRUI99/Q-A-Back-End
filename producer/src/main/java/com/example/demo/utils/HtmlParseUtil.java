package com.example.demo.utils;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 40826614460ae7d1d4b2a8af1af9d796fbf3b0f5

import com.example.demo.pojo.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {

    public static void main(String[] args) throws IOException {
        String url = "https://www.forbes.com/search/?q=java&sort=score&sh=691d94eb279f";
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("search-results__items");
        Elements elements = element.getElementsByTag("stream-item__text");
        for (Element el: elements){
            el.getElementsByTag("");
            String des = el.getElementsByClass("stream-item__description").eq(0).text();
        }
    }

    public List<Question> parseJD(String keyword) throws IOException {
        String url = "https://www.forbes.com/search/?q=java&sort=score&sh=691d94eb279f";
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("search-results__items");
        Elements elements = element.getElementsByTag("stream-item__text");
        ArrayList<Question> questions = new ArrayList<>();
        for (Element el: elements){
            el.getElementsByTag("");
            String des = el.getElementsByClass("stream-item__description").eq(0).text();
        }
        return questions;
    }
<<<<<<< HEAD
=======
=======
public class HtmlParseUtil {

>>>>>>> eba82a3c8c4d5f57a06c49f2d0c4ede21fe3f5e7
>>>>>>> 40826614460ae7d1d4b2a8af1af9d796fbf3b0f5
}
