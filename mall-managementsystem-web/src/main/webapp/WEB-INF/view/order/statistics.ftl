<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>订单统计</title>
    <script src="../../../js/echarts.simple.min.js"></script>
    <script type="application/javascript">
        function statisticsForDay(){
            window.location.href="/order/statistics?timeFormat=DAY";
        }
        function statisticsForHour(){
            window.location.href="/order/statistics?timeFormat=HOUR";
        }
        function statisticsForMinute(){
            window.location.href="/order/statistics?timeFormat=MINUTE";
        }
        function statisticsForSecond(){
            window.location.href="/order/statistics?timeFormat=SECOND";
        }
    </script>
</head>
<body>
<button onclick="statisticsForDay()">按日统计</button>&nbsp;<button onclick="statisticsForHour()">按小时统计</button>&nbsp;<button onclick="statisticsForMinute()">按分钟统计</button>&nbsp;<button onclick="statisticsForSecond()">按秒统计</button>
<div id="main" style="width: 100%;height:400px;"></div>
<script type="application/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    //data = [["2000-06-05",116],["2000-06-06",129],["2000-06-07",135],["2000-06-08",86],["2000-06-09",73],["2000-06-10",85],["2000-06-11",73],["2000-06-12",68],["2000-06-13",92],["2000-06-14",130],["2000-06-15",245],["2000-06-16",139],["2000-06-17",115],["2000-06-18",111],["2000-06-19",309],["2000-06-20",206],["2000-06-21",137],["2000-06-22",128],["2000-06-23",85],["2000-06-24",94],["2000-06-25",71],["2000-06-26",106],["2000-06-27",84],["2000-06-28",93],["2000-06-29",85],["2000-06-30",73],["2000-07-01",83],["2000-07-02",125],["2000-07-03",107],["2000-07-04",82],["2000-07-05",44],["2000-07-06",72],["2000-07-07",106],["2000-07-08",107],["2000-07-09",66],["2000-07-10",91],["2000-07-11",92],["2000-07-12",113],["2000-07-13",107],["2000-07-14",131],["2000-07-15",111],["2000-07-16",64],["2000-07-17",69],["2000-07-18",88],["2000-07-19",77],["2000-07-20",83],["2000-07-21",111],["2000-07-22",57],["2000-07-23",55],["2000-07-24",60]];
    data = ${statisticData};

    var dataList = data.map(function (item) {
        return item[0];
    });
    var valueList = data.map(function (item) {
        return item[1];
    });

    option = {

        // Make gradient line here
        visualMap: [{
            show: true,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 400
        }, {
            show: true,
            type: 'continuous',
            seriesIndex: 1,
            dimension: 0,
            min: 0,
            max: dataList.length - 1
        }],


        title: [{
            left: 'center',
            text: 'Gradient along the y axis'
        }, {
            top: '55%',
            left: 'center',
            text: 'Gradient along the x axis'
        }],
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [{
            data: dataList
        }, {
            data: dataList,
            gridIndex: 1
        }],
        yAxis: [{
            splitLine: {show: true}
        }, {
            splitLine: {show: true},
            gridIndex: 1
        }],
        grid: [{
            bottom: '60%'
        }, {
            top: '60%'
        }],
        series: [{
            type: 'line',
            showSymbol: true,
            data: valueList
        }, {
            type: 'line',
            showSymbol: true,
            data: valueList,
            xAxisIndex: 1,
            yAxisIndex: 1
        }]
    };

    myChart.setOption(option);
</script>
</body>
</html>