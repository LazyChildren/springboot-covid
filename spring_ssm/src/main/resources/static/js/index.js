//  时间、疫情确诊数量、点击事件
import './public/time.js';
import './public/diagnosisNum.js';
import addClick from './public/chartClick.js';

addClick(bottomLeftChart,function(params) {
    console.log(params);
  
      //window.open("https://www.google.com/search?q="+params.name);
      window.open("https://www.baidu.com/s?ie=UTF-8&wd="+params.name);
  }
)    


// 地图初始化
import './chartsInitData/centerMapInit.js';
import './chartsInitData/rightInit.js';
import './chartsInitData/topLeftInit.js';
import './chartsInitData/bottomLeftInit.js';
import { bottomLeftChart } from './chartsInitData/bottomLeftInit.js';


