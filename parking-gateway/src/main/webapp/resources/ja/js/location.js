$(function() {
	_setMap();
})

function _setMap() {
	for ( var i in _data) {
		var _item = _data[i];
		var _htmlStr = '';
		_htmlStr = '<img id="'
				+ _item.lot
				+ '"  src="/gtw/resources/ja/img/B-car.png" width="20px;" style="position: absolute;top: '
				+ _item._top + 'px;left: ' + _item._left
				+ 'px;" class=" car none" role="button" type="button"   />';
		$('.location').append(_htmlStr);
	}
	run();

	setInterval(function() {

		run();

	}, 15000);
}
function run() {
	$.get('/gtw/rest/sensors?areaId=1', function(res) {
		var _num = 0;
		var params = res.params;
		for (idx in params) {
			var sensorVo = params[idx];
			if (sensorVo.available == 1) {
				$('#' + sensorVo.desc).attr('data-toggle','popover');
				$('#' + sensorVo.desc).attr('data-trigger','hover');
				$('#' + sensorVo.desc).attr('data-container','body');
				if(parseInt(sensorVo.desc)>=24){
					$('#' + sensorVo.desc).attr('data-placement','right');
				}else if(parseInt(sensorVo.desc)<=11){
					$('#' + sensorVo.desc).attr('data-placement','left');
				}else{
					$('#' + sensorVo.desc).attr('data-placement','bottom');
				}
				var _detail = '<b>驶入时间：</b>'+sensorVo.lastSeenTime+'</br><b>车位号：</b>'+sensorVo.desc+'</br><b>Mac地址：</b>'+sensorVo.mac;
				$('#' + sensorVo.desc).attr('title','基本信息');
				$('#' + sensorVo.desc).attr('data-content',_detail);
				$('#' + sensorVo.desc).show();
				$("[data-toggle='popover']").popover({html: true});
			}else{
				if($('#' + sensorVo.desc).length>0){
					$('#' + sensorVo.desc).removeAttr('data-toggle');
					_num ++;
				}
				$('#' + sensorVo.desc).hide();
			}
			$('#leftLot').html(_num);
		}
	})
}