package com.neuedu.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.Entity.Brief;
import com.neuedu.Entity.Detail;
import com.neuedu.Entity.Patient;
import com.neuedu.Entity.Province;
import com.neuedu.cache.CovidCache;
import com.neuedu.service.CovidService;
import com.neuedu.util.CovidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CovidServiceImpl implements CovidService {

//    @Autowired
//    private CovidCache covidCache;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Detail> getDetail() {
        HashOperations<String, String, Object> operations = stringRedisTemplate.opsForHash();
        Map<String, Object> details = operations.entries(CovidUtil.COVID_DETAIL);
        ObjectMapper mapper = CovidUtil.mapper;
        List<Detail> res=new ArrayList<>();
        if(!details.isEmpty())
        {

            try {
                for(Object cur:details.values())
                {
                     Detail curDate = mapper.readValue(cur.toString(),Detail.class);
                     res.add(curDate);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        else
        {


            String url=CovidUtil.URL_163;
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
                    try {
                        operations.put(CovidUtil.COVID_DETAIL,date,mapper.writeValueAsString(detail));

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    res.add(detail);
                }
                stringRedisTemplate.expire(CovidUtil.COVID_DETAIL,CovidUtil.TIME_OUT, TimeUnit.SECONDS);

            }



        }


        Collections.sort(res, CovidUtil.detailComparator);

        return res;
    }

    @Override
    public List<Patient> getPa() {
        HashOperations<String, String, Object> operations = stringRedisTemplate.opsForHash();
        Map<String, Object> patients = operations.entries(CovidUtil.COVID_PATIENT);
        ObjectMapper mapper = CovidUtil.mapper;
        List<Patient> res=new ArrayList<>();
        if(!patients.isEmpty())
        {
            try {
                for(Object cur:patients.values())
                {
                    Patient curPatient = mapper.readValue(cur.toString(),Patient.class);
                    res.add(curPatient);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        else {
            String url = CovidUtil.URL_163;
            String json = HttpUtil.createGet(url).execute().body();
            //转化请求的 json 数据
            JSONObject jsonObject = JSONObject.parseObject(json);
            //获取 error 返回状态码
            String error = jsonObject.getString("error");
            if (((String) jsonObject.get("msg")).equals("成功")) {
                JSONObject data = jsonObject.getObject("data", JSONObject.class);
                JSONObject chinaTotal = data.getObject("chinaTotal", JSONObject.class).getObject("total", JSONObject.class);
                Patient inputpa = new Patient(chinaTotal.getInteger("input"), "境外输入患者");
                Patient severepa = new Patient(chinaTotal.getInteger("severe"), "严重患者");
                Patient deadpa = new Patient(chinaTotal.getInteger("dead"), "死亡人数");
                Patient healpa = new Patient(chinaTotal.getInteger("heal"), "治愈患者");
                Patient nonseverepa = new Patient(chinaTotal.getInteger("confirm") - chinaTotal.getInteger("severe"), "轻微患者");
                try{
                    res.add(inputpa);
                    operations.put(CovidUtil.COVID_PATIENT,inputpa.getSituation(),mapper.writeValueAsString(inputpa));
                    res.add(severepa);
                    operations.put(CovidUtil.COVID_PATIENT,severepa.getSituation(),mapper.writeValueAsString(severepa));

                    res.add(deadpa);
                    operations.put(CovidUtil.COVID_PATIENT,deadpa.getSituation(),mapper.writeValueAsString(deadpa));

                    res.add(healpa);
                    operations.put(CovidUtil.COVID_PATIENT,healpa.getSituation(),mapper.writeValueAsString(healpa));

                    res.add(nonseverepa);
                    operations.put(CovidUtil.COVID_PATIENT,nonseverepa.getSituation(),mapper.writeValueAsString(nonseverepa));

                }catch(JsonProcessingException e){
                    e.printStackTrace();
                }


                stringRedisTemplate.expire(CovidUtil.COVID_PATIENT,CovidUtil.TIME_OUT, TimeUnit.SECONDS);
            }

        }
        return res;
    }


    @Override
    public List<Province> getMap() {
        HashOperations<String, String, Object> operations = stringRedisTemplate.opsForHash();
        Map<String, Object> provinceList = operations.entries(CovidUtil.COVID_MAP);
        ObjectMapper mapper = CovidUtil.mapper;
        List<Province> res=new ArrayList<>();
        if(!provinceList.isEmpty())
        {
            try {
                for(Object cur:provinceList.values())
                {
                    Province curProvince = mapper.readValue(cur.toString(),Province.class);
                    res.add(curProvince);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        else {
            String url = CovidUtil.URL_tencent;
            String json = HttpUtil.createGet(url).execute().body();
            //转化请求的 json 数据
            JSONObject jsonObject = JSONObject.parseObject(json);
            //获取 error 返回状态码
            String error = jsonObject.getString("error");
            if (jsonObject.getInteger("ret") == 0) {
                JSONArray provinces = jsonObject.getJSONObject("data").getJSONObject("diseaseh5Shelf").getJSONArray("areaTree").getJSONObject(0).getJSONArray("children");
                for (int i = 0; i < provinces.size(); i++) {
                    JSONObject cur_pro = provinces.getJSONObject(i).getJSONObject("today");
                    JSONObject cur_pro2 = provinces.getJSONObject(i).getJSONObject("total");
                    int newnum = cur_pro.getInteger("confirm");
                    int curnum = cur_pro2.getInteger("nowConfirm");
                    int sumnum = cur_pro2.getInteger("confirm");
                    String name = provinces.getJSONObject(i).getString("name");
                    Province province = new Province(name, newnum, curnum, sumnum);
                    res.add(province);
                    try {
                        operations.put(CovidUtil.COVID_MAP,name,mapper.writeValueAsString(province));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }

            stringRedisTemplate.expire(CovidUtil.COVID_MAP,CovidUtil.TIME_OUT, TimeUnit.SECONDS);



        }
        Collections.sort(res,CovidUtil.provinceComparator);
        return res;
    }

}
