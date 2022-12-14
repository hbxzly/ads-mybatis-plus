<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="js/echarts.js"></script>
<div id="main" style="width: 600px;height: 400px"></div>

<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
        title: {
        text: 'ECharts 入门示例'
    },
        tooltip: {},
        legend: {
        data: ['销量']
    },
        xAxis: {
        data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
    },
        yAxis: {},
        series: [
    {
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
    }
        ]
    };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
</script>

