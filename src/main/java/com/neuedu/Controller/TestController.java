package com.neuedu.Controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neuedu.Entity.*;
//import com.sun.deploy.cache.BaseLocalApplicationProperties;
import com.neuedu.service.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.TreeMap;

@CrossOrigin
@RestController
public class TestController {

    @Autowired
    CovidService covidService;

    @RequestMapping("/getdetail")
    public List<Detail> getDetail()
    {
        return covidService.getDetail();
    }


    @RequestMapping("/getpatient")
    public List<Patient> getPa()
    {
        return covidService.getPa();




    }


    @RequestMapping("/getmap")
    public List<Province> getMap()
    {
        return covidService.getMap();


    }








}