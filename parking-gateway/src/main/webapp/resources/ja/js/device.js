$(function(){
	$.ajax({
		url:'/gtw/rest/sensors?areaId=1',
		type:'get',
		dataType:'json',
		success:function(res){
			new Vue({
				el:'#data',
				data:{
					datas:res.params
				}
			})
		}
	})
})

function _send(_e,status){
	var _mac = $(_e).attr('num');
	if(confirm('您确定要发送推送：'+_mac+'吗？')){
        $.ajax({
            url:'/gtw/rest/ja/setStatus?mac='+_mac+'&status='+status,
            type:'post',
            dataType:'json',
            success:function(res){
                if('200'==res.code){
                    alert('推送成功！');
                    location.reload();
                }else{
                    alert('推送失败！');
                }
            }
        })
	}

}