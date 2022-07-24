import initChart from '../charts/initChart.js';
import initBottomLeftOptionData from '../charts/bottomLeft.js';

let ddd = [
    { 'name': '方舱医院', 'value': '1273670' }, { 'name': '动态清零', 'value': '1273670' },
    { 'name': '新型', 'value': '1273670' }, { 'name': '灵活赋红码', 'value': '1273440' },
    { 'name': '常态化', 'value': '1234670' },{ 'name': '精准防疫', 'value': '1270460' },
    { 'name': '搬起石头', 'value': '1273670' },{ 'name': '清零', 'value': '1273470' },
    { 'name': '无上访社区', 'value': '1734670' },{ 'name': '唐山', 'value': '1273670' },
    { 'name': '坚持清零', 'value': '1234670' },
    
]

let bottomLeftOption = initBottomLeftOptionData(ddd);
let bottomLeftChart = initChart('.line .chart', bottomLeftOption);

export { bottomLeftChart, bottomLeftOption, ddd };