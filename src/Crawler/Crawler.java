package Crawler;

import DTO.CategoryNews;
import DTO.News;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.io.IOException;
import java.util.ArrayList;

public class Crawler {

    public JSONObject searchArticleContext(String url){
        JSONArray jsonArray = new JSONArray();
        //WebDriver
        WebDriver driver;
        WebElement element;

        //Properties
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "C:\\Users\\82108\\Documents\\chromedriver.exe";


        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        //Driver SetUp
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(options);

        try {
            //get방식으로 url 요청
            driver.get(url);

            Thread.sleep(1000);

            //기사 본문 크롤링
            element = driver.findElement(By.className("article_body"));
            JSONObject jsonObject = new JSONObject();

            if( element != null  ) {
                String value = element.getText().replaceAll("이미지 원본보기", "");

                jsonObject.put("fullcontext", value );
                jsonObject.put("url", url);

                System.out.println( element.getText() );

                return jsonObject;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
        return null;
    }

    public JSONObject searchCategoryArticleContext(String url){
        JSONArray jsonArray = new JSONArray();
        //WebDriver
        WebDriver driver;
        WebElement element;

        //Properties
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "C:\\Users\\82108\\Documents\\chromedriver.exe";


        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        //Driver SetUp
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        driver = new ChromeDriver(options);

        try {
            //get방식으로 url 요청
            driver.get(url);

            Thread.sleep(1000);

            //기사 본문 크롤링
            element = driver.findElement(By.className("_article_body_contents"));
            JSONObject jsonObject = new JSONObject();

            if( element != null  ) {
                jsonObject.put("fullcontext", element.getText() );
                jsonObject.put("url", url);

                System.out.println( element.getText() );

                return jsonObject;
            }
            Thread.sleep(1000);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
        return null;
    }


    public JSONArray realtimeSearchWord() throws IOException {
        JSONArray jsonArray = new JSONArray();
        int rank = 1;
        Document doc = Jsoup.connect("https://datalab.naver.com/keyword/realtimeList.naver").get();

        //1~20위 태그에 접근
        Elements elements = doc.select("ul.ranking_list > li.ranking_item > div.item_box");

        //span.item_num, item_title
        for (Element e : elements) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("idx", rank);
            jsonObject.put("word", e.select("span.item_title").get(0).html());
            jsonArray.add(jsonObject);
            jsonObject = null;
            rank++;
        }

        return jsonArray;
    }



    public JSONArray freeSearch(String searchWord) {
        JSONArray jsonArray = new JSONArray();

        Document doc = null;
        try {
            doc = Jsoup.connect("https://search.naver.com/search.naver?where=news&sm=tab_jum&query=" + searchWord).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("ul.type01 >  li");
        int n = 0;
        //span.item_num, item_title
        for (Element e : elements) {
            n++;
            String url = e.select("dd.txt_inline > a._sp_each_url").attr("href");
            String aid = "";
            if (!url.isBlank()) {
                aid = e.select("dd.txt_inline > a._sp_each_url").attr("href").split("aid=")[1].replaceAll("[^0-9]", "");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fullcontext", e.select("dl > dd:nth-child(3)").text());
                jsonObject.put("context", e.select("dl > dd:nth-child(3)").text());
                jsonObject.put("title", e.select("a._sp_each_title").text());
                jsonObject.put("url", e.select("dd.txt_inline > a._sp_each_url").attr("href"));
                jsonObject.put("aid", aid);
                jsonObject.put("word", searchWord);
                jsonObject.put("imgurl",e.select("div.thumb > a > img").attr("src"));
                //System.out.println("imgurl: "+e.select("div.thumb > a > img").attr("src"));
                //System.out.println("context: "+e.select("dl > dd:nth-child(3)").text());

                jsonArray.add(jsonObject);
                jsonObject = null;
            }


        }
        //출력

        System.out.println(n);

        return jsonArray;
    }

    public JSONArray categorySearch() {
        ArrayList<CategoryNews> arrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://news.naver.com/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = doc.select("div.com_list");

        //경제
        for (Element e : elements.get(0).select("li")) {
            System.out.println(e);
            String aid = e.select("a").attr("href").split("aid=")[1].replaceAll("[^0-9]", "");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("context", "");
            jsonObject.put("category", 2);
            jsonObject.put("title", e.select("strong").text());
            jsonObject.put("url", e.select("a").attr("href"));
            jsonObject.put("aid", aid);

            jsonArray.add(jsonObject);
            jsonObject = null;
        }

        //정치
        for (Element e : elements.get(1).select("li")) {
            System.out.println(e);
            String aid = e.select("a").attr("href").split("aid=")[1].replaceAll("[^0-9]", "");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("context", "");
            jsonObject.put("category", 1);
            jsonObject.put("title", e.select("strong").text());
            jsonObject.put("url", e.select("a").attr("href"));
            jsonObject.put("aid", aid);

            jsonArray.add(jsonObject);
            jsonObject = null;
        }
        //사회
        for (Element e : elements.get(2).select("li")) {
            System.out.println(e);
            String aid = e.select("a").attr("href").split("aid=")[1].replaceAll("[^0-9]", "");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("context", "");
            jsonObject.put("category", 3);
            jsonObject.put("title", e.select("strong").text());
            jsonObject.put("url", e.select("a").attr("href"));
            jsonObject.put("aid", aid);

            jsonArray.add(jsonObject);
            jsonObject = null;
        }
        //생활/문화
        for (Element e : elements.get(3).select("li")) {
            System.out.println(e);
            String aid = e.select("a").attr("href").split("aid=")[1].replaceAll("[^0-9]", "");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("context", "");
            jsonObject.put("category", 4);
            jsonObject.put("title", e.select("strong").text());
            jsonObject.put("url", e.select("a").attr("href"));
            jsonObject.put("aid", aid);

            jsonArray.add(jsonObject);
            jsonObject = null;
        }
        //세계
        for (Element e : elements.get(4).select("li")) {
            System.out.println(e);
            String aid = e.select("a").attr("href").split("aid=")[1].replaceAll("[^0-9]", "");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("context", "");
            jsonObject.put("category", 5);
            jsonObject.put("title", e.select("strong").text());
            jsonObject.put("url", e.select("a").attr("href"));
            jsonObject.put("aid", aid);

            jsonArray.add(jsonObject);
            jsonObject = null;
        }
        //IT/과학
        for (Element e : elements.get(5).select("li")) {
            System.out.println(e);
            String aid = e.select("a").attr("href").split("aid=")[1].replaceAll("[^0-9]", "");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("context", "");
            jsonObject.put("category", 6);
            jsonObject.put("title", e.select("strong").text());
            jsonObject.put("url", e.select("a").attr("href"));
            jsonObject.put("aid", aid);

            jsonArray.add(jsonObject);
            jsonObject = null;
        }

        return jsonArray;
    }
}
