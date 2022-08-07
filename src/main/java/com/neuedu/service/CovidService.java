package com.neuedu.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.neuedu.Entity.Detail;
import com.neuedu.Entity.Patient;
import com.neuedu.Entity.Province;

import java.util.List;



public interface CovidService {

    public List<Detail> getDetail() ;

    public List<Patient> getPa();

    public List<Province> getMap();
}
