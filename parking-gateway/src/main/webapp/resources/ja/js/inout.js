$(function(){
	$('#datepicker').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		lang : 'ch'
	});
	$('#datepicker').val(new Date().Format('yyyy-MM-dd'));
	_getData();
	setInterval(function() {

		_getData();

	}, 15000);
})
var _data = '';
function _getData(){
	var dateStr = $('#datepicker').val(),areaId=1;
	$.ajax({
		url:'/gtw/rest/sensor/inOutLog?areaId='+areaId+'&dateStr='+dateStr,
		type:'get',
		dataType:'json',
		success:function(res){
			for(var i in res.params){
				res.params[i].changeTime = jsDateTime(res.params[i].changeTime);
			}
			if(_data ==''){
				_data = new Vue({
					el:'#datas',
					data:{
						datas:res.params
					}
				})
			}else{
				_data.datas = res.params;
			}
		}
	})
}
function jsDateTime(unixtime)  {  
	 var date = new Date(unixtime);
	 return  date.Format("yyyy-MM-dd hh:mm:ss"); 
}  
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};