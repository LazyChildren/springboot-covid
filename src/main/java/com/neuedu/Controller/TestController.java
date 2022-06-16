package com.neuedu.Controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.Entity.*;
import com.sun.deploy.cache.BaseLocalApplicationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class TestController {

    @RequestMapping("/userlist")
    public List<User> userList()
    {
        List<User> userList = new ArrayList<>();

        User user1 = new User(1,"first",10);
        User user2=new User(2,"second",20);
        User user3=new User(3,"third",30);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }

    @RequestMapping("/gettemperature")
    public List<Daily> dayList() throws IOException {
        List<Daily> dayList = new ArrayList<>();
        //从类路径下获取文件对象
        File file = new ClassPathResource("temperature.txt").getFile();

        //构造缓冲字符流
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line="";
        while((line = br.readLine())!=null)
        {
            String[] temperatures=line.split(",");
            int low=Integer.parseInt(temperatures[0]);
            int high=Integer.parseInt(temperatures[1]);
            dayList.add(new Daily(low,high));
        }
        return dayList;
    }

    @RequestMapping("/getway")
    public List<Way> wayList() throws IOException {
        List<Way> wayList = new ArrayList<>();
        //从类路径下获取文件对象
        File file = new ClassPathResource("getway.txt").getFile();

        //构造缓冲字符流
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line="";
        while((line = br.readLine())!=null)
        {
            String[] ways=line.split(",");
            String name=ways[0];
            int count=Integer.parseInt(ways[1]);
            wayList.add(new Way(name,count));
        }
        return wayList;
    }

    @RequestMapping("/getmonth")
    public List<Month> monthList() throws IOException {
        List<Month> monthList = new ArrayList<>();
        //从类路径下获取文件对象
        File file = new ClassPathResource("month.txt").getFile();

        //构造缓冲字符流
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line="";
        while((line = br.readLine())!=null)
        {
            String[] months=line.split(",");
            int index=Integer.parseInt(months[0]);
            double evaporation=Double.parseDouble(months[1]);
            double precipitation=Double.parseDouble(months[2]);
            monthList.add(new Month(index,evaporation,precipitation));
        }
        return monthList;
    }

    @RequestMapping("/getdetail")
    public List<Detail> getDetail()
    {
        List<Detail> detList=new ArrayList<>();
        String url="https://c.m.163.com/ug/api/wuhan/app/data/list-total";
        String json= HttpUtil.createGet(url).execute().body();
        //转化请求的 json 数据
        JSONObject jsonObject = JSONObject.parseObject(json);
        //获取 error 返回状态码
        String error = jsonObject.getString("error");
        if(((String)jsonObject.get("msg")).equals("成功"))
        {
            JSONObject data=jsonObject.getObject("data",JSONObject.class);
            JSONObject chinaTotal=data.getObject("chinaTotal",JSONObject.class).getObject("total",JSONObject.class);
            JSONObject chinaToday=data.getObject("chinaTotal",JSONObject.class).getObject("today",JSONObject.class);
            Brief brief=new Brief(chinaTotal.getInteger("confirm"),chinaToday.getInteger("confirm"),
                    chinaTotal.getInteger("heal"),chinaToday.getInteger("heal"),
                    chinaTotal.getInteger("dead"),chinaToday.getInteger("dead"));
            JSONArray dateList=data.getObject("chinaDayList",JSONArray.class);
            for(int i=0;i<dateList.size();i++) {
                JSONObject cur=dateList.getJSONObject(i);
                String date=cur.getString("date");
                String[] ymd=date.split("-");
                int year=Integer.parseInt(ymd[0]);
                int month=Integer.parseInt(ymd[1]);
                int day=Integer.parseInt(ymd[2]);
                JSONObject curTotal=cur.getJSONObject("total");
                JSONObject curToday=cur.getJSONObject("today");
                int todaytotal=curTotal.getInteger("confirm");
                int todaynew=curToday.getInteger("confirm");
                Detail detail=new Detail(year,month,day,todaynew,todaytotal);
                detList.add(detail);
            }
        }



        return detList;
    }


    @RequestMapping("/getbrief")
    public Brief getBrief()
    {

        String url="https://c.m.163.com/ug/api/wuhan/app/data/list-total";
        String json= HttpUtil.createGet(url).execute().body();
        //转化请求的 json 数据
        JSONObject jsonObject = JSONObject.parseObject(json);
        //获取 error 返回状态码
        String error = jsonObject.getString("error");
        if(((String)jsonObject.get("msg")).equals("成功"))
        {
            JSONObject data=jsonObject.getObject("data",JSONObject.class);
            JSONObject chinaTotal=data.getObject("chinaTotal",JSONObject.class).getObject("total",JSONObject.class);
            JSONObject chinaToday=data.getObject("chinaTotal",JSONObject.class).getObject("today",JSONObject.class);
            Brief brief=new Brief(chinaTotal.getInteger("confirm"),chinaToday.getInteger("confirm"),
                    chinaTotal.getInteger("heal"),chinaToday.getInteger("heal"),
                    chinaTotal.getInteger("dead"),chinaToday.getInteger("dead"));
            return brief;
        }
        return null;


    }


    @RequestMapping("/getmap")
    public Brief getMap()
    {

        String url="https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=statisGradeCityDetail,diseaseh5Shelf";
        String json= HttpUtil.createGet(url).execute().body();
        //转化请求的 json 数据
        JSONObject jsonObject = JSONObject.parseObject(json);
        //获取 error 返回状态码
        String error = jsonObject.getString("error");
        if(((String)jsonObject.get("msg")).equals("成功"))
        {

        }
        return null;


    }
}
