package com.anxin.common.webcrawler;

import com.anxin.common.utils.ValidateHelper;
import com.anxin.common.utils.httputils.Okhhtp3Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬取url数据
 * Created by sun on 2018/10/17.
 */
public class WebCrawler {

    /**
     * 返回 本科，研究生，博士 学历信息
     * @param url 简历url
     * @return List<Edu>
     */
    public static List<Edu> getEduList(String url){
        List<Edu> list =null;
        String result = Okhhtp3Utils.httpGet(url);
        try {
            list = WebCrawler.getData(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析html标签中的内容
     * @param html 文本
     * @return
     * @throws Exception
     */
    public static List<Edu> getData (String html) throws Exception{
        List<Edu> list =new ArrayList<>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements1=doc.select("#form1 > div.resumeBox > div > table:nth-child(11) > tbody");
        Elements elements2=doc.select("#form1 > div.resumeBox > div > table:nth-child(12) > tbody");
        Elements elements3=doc.select("#form1 > div.resumeBox > div > table:nth-child(13) > tbody");
        Elements elements4=doc.select("#form1 > div.resumeBox > div > table:nth-child(14) > tbody");
        Elements elements5=doc.select("#form1 > div.resumeBox > div > table:nth-child(15) > tbody");
        Elements elements6=doc.select("#form1 > div.resumeBox > div > table:nth-child(16) > tbody");
        //最高学历信息
        getEdu(list, elements1);
        //第二学历信息
        getEdu(list, elements2);
        //第三学历信息
        getEdu(list, elements3);
        getEdu(list, elements4);
        getEdu(list, elements5);
        getEdu(list, elements6);
        //返回数据
        return list;
    }

    /**
     * 解析元素给edu赋值
     * @param list
     * @param elements
     */
    private static void getEdu(List<Edu> list, Elements elements) {
        if (ValidateHelper.isNotEmptyCollection(elements)) {
            for (Element ele: elements) {
                Edu e =new Edu();
                String school=ele.select("tr:nth-child(1) > th").text();
                String educa=ele.select("tr:nth-child(3) > td").text();
                String profession=ele.select("tr:nth-child(4) > td").text();
                String degree=ele.select("tr:nth-child(5) > td").text();
                e.setSchool(school);
                e.setEduca(educa);
                e.setProfession(profession);
                e.setDegree(degree);
                list.add(e);
            }
        }
    }

}

