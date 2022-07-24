let bottomLeftOption = {

tooltip: {
    show: true,
    
},
series: [{
    type: "wordCloud",
    gridSize: 1,
    sizeRange: [20, 55],
    rotationRange: [-45, 0, 45, 90],
    
    textStyle: {
        fontFamily: 'sans-serif',
        fontWeight:'bold',
        color: function () {
            // Random color
                return 'rgb(' + [
                Math.round(Math.random() * 150+100),
                Math.round(Math.random() * 150+100),
                Math.round(Math.random() * 150+100)
            ].join(',') + ')';
        }
    },
    right: null,
    bottom: null,
    data: []
}]
}

function initBottomLeftOptionData(ddd) {
bottomLeftOption.series[0].data = ddd;         
return bottomLeftOption;
}

export { initBottomLeftOptionData as default };