package com.example.manager.controller;

import com.example.manager.aspect.SystemLogger;
import com.example.manager.entity.Result;
import com.example.manager.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/NewsCrawler")
@CrossOrigin("*")
@EnableAsync
public class NewsCrawlerController {
    private static final Logger log = LogManager.getLogger(NewsCrawlerController.class);
    @Autowired
    private NewsService newsService;

    @Scheduled(cron = "0 0 */1 * * ?")//每小时运行一次 0（秒） 0（分） */1（时） *（日） *（月） ?（星期）
    @GetMapping("/crawlNews")//爬取新闻
    public Result crawlNews() {
        log.info("爬取新闻");
        Map<String, Object> map = new HashMap<>();
        String url = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/news_1.jsonp?cb=news";//央视新闻
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; SpringBootCrawler/1.0)")
                    .timeout(10000)
                    .get();

            Element script = doc.select("body").first();
            String jsonStr = script.text().replaceAll("news\\(", "").replaceAll("\\);", "");
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray newsList = jsonObj.getJSONObject("data").getJSONArray("list");
            for (int i = 0; i < newsList.length(); i++) {
                JSONObject newsItem = newsList.getJSONObject(i);
                String id = newsItem.getString("id");//新闻id
                String title = newsItem.getString("title");//新闻标题
                String detailUrl = newsItem.getString("url");//新闻详情页
                String time = newsItem.getString("focus_date");//新闻时间
                String text = newsItem.getString("brief");//新闻简介
                String image = newsItem.getString("image");//新闻图片
                map.put("num", newsList.length());
                //存储新闻信息
                newsService.saveNews(id, title, detailUrl, time, text,image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(map);
    }

//    @Scheduled(cron = "0 0 */1 * * ?")//每小时运行一次 0（秒） 0（分） */1（时） *（日） *（月） ?（星期）
    @GetMapping("/crawlNewsA")//爬取新闻
    public Result crawlNewsA() {
        Map<String, Object> map = new HashMap<>();
        String url = "https://news.cctv.com/2019/07/gaiban/cmsdatainterface/page/news_1.jsonp?cb=news";//央视新闻
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; SpringBootCrawler/1.0)")
                    .timeout(10000)
                    .get();

            Element script = doc.select("body").first();
            String jsonStr = script.text().replaceAll("news\\(", "").replaceAll("\\);", "");
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray newsList = jsonObj.getJSONObject("data").getJSONArray("list");
            for (int i = 0; i < newsList.length(); i++) {
                JSONObject newsItem = newsList.getJSONObject(i);
                String id = newsItem.getString("id");//新闻id
                String title = newsItem.getString("title");//新闻标题
                String detailUrl = newsItem.getString("url");//新闻详情页
                String time = newsItem.getString("focus_date");//新闻时间
                String text = newsItem.getString("brief");//新闻简介
                String image = newsItem.getString("image");//新闻图片
                map.put("num", newsList.length());
                //存储新闻信息
                newsService.saveNews(id, title, detailUrl, time, text,image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(map);
    }

    @GetMapping("/count")
    public Result<Map<String,Object>> getCount(){
        return newsService.getCount();
    }
}
