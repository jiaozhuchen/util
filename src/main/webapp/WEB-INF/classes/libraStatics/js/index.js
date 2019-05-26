(function(){
	var _instance = {};
	_instance.init = function() {
		init();
		bindEvents();
	};
	function init() {
		$("#methodType .on a").css('color', '#065f89');
	}
	function ajax (url, data, successCallback, errorCallback) {
        var options = {
            type: 'get',
            url: url,
            cache: true,
            success: function (response) {
                successCallback(response, data);
            },
            error: function (response) {
                if (errorCallback) {
                    errorCallback(data);
                }
            }
        };
        if (data) {
            options.type = 'post';
            options.data = data;
        }
        $.ajax(options);
    };
	
	function bindEvents() {
		$('#checkServiceValid').on('click', _getAllMethod);
		$('#methodValue').on('change', _changeParameter);
		$('#submit').on('click', _invokeMethod);
		$("#methodIdValue").keydown(function (events) {  
            if (events.which == 13) {  
            	var methodValue = $("#methodIdValue").val();
            	var methodId = _getMethodIdByValue(methodValue);
            	if (!methodId) {
            		alert("method不存在!");
            		return false;
            	}
            	$('#methodIdValue').data('method', methodId);
            	//判断方法是否存在
            	_submitFetchParameter(methodId);
            }  
        });  
		$("#methodType li").on('click', _switchMethodType);
	}
	function _switchMethodType() {//切换获取方法类型(动态方法和静态方法切换)
		var _this = $(this);
		var id = _this.find('a').prop('id');
		var labelName = (id == 'callDynamicMethod')? 'bean': 'class';
		var placeholder = (id == 'callDynamicMethod')? 'please input bean id': 'please input class path';
		var serviceValue = (id == 'callDynamicMethod')? 'testServiceLibra': 'com.yhd.libra.services.TestService';
		$("#service label").text(labelName);
		$('#serviceValue').val(serviceValue).attr('placeholder', placeholder);
		_this.addClass('on').siblings().removeClass('on');
		_this.find('a').css('color', '#065f89');
		_this.siblings().find('a').css('color', 'white');
		$('#methodIdValue').val('').addClass('none');
		$('#methodValue option').remove();
	}
	function _getMethodIdByValue(methodValue) {
		var result = null;
		$("#methodValue").find("option").each(function(i, e) {
			var $option = $(e);
			if ($option.val().startsWith(methodValue)) {
				result =  $option.data("id");
			}
		});
		return result;
	}
	
	function _invokeMethod() {//触发方法
		var methodId = $('#methodIdValue').data('method');
		var paramJson = $('#parameterValue').val();
		var beanName = $('#serviceValue').val();
		var paramArr = {'paramJson': paramJson, 'act': 'callMethod', 'methodId': methodId, 'beanName': beanName};
//		var url = './testProxyLibra?act=callMethod&methodId=' + methodId + '&beanName=' + beanName;
		var url = './testProxyLibra';
		$('#right #invokeResult').val("");
		var successFuc = function(data) {
			$('#right #invokeResult').val(data);
		};
		ajax(url, paramArr, successFuc);
	}
	
	function _changeParameter() {//修改方法参数
		var methodId = $('#methodValue option:checked').data('id');
		$("#methodIdValue").data("method", methodId);
		$("#methodIdValue").val($('#methodValue option:checked').val());
		_submitFetchParameter(methodId);
	}
	
	function _submitFetchParameter(methodIdValue) {
		var beanName = $('#serviceValue').val();
		var successFuc = function(data) {
			$('#parameterValue').val(data);
		};
		var url = './testProxyLibra?act=getParameter&methodId=' + methodIdValue + '&beanName=' + beanName;
		ajax(url, null, successFuc);
	}
	
	function _getAllMethod() {//获取所有方法
		var beanName = $('#serviceValue').val();
		if(!beanName) {
			alert('Please input bean id!');
			return false;
		}
		var successFunc = function(data) {
			var jsonObj = JSON.parse(data);
			if (!jsonObj || jsonObj['code'] != 0) {
				alert(jsonObj['msg']);
				return false;
			}
			$("#methodIdValue").removeClass("none");
			$('#methodValue').find('option.real').remove();
			var methodList = jsonObj['methodList'];
			if (methodList && methodList.length > 0) {
				$("#methodIdValue").val(methodList[0].methodName);
			}
			for(var i = 0; i < methodList.length; i++) {
				var method = methodList[i];
				$('#methodValue').append('<option class="real" data-id="' + method.methodId + '">' + method.methodName + '</option>');
			}
			$('#methodValue option').first().attr("checked", "checked");
			//更改下拉框样式
			$("#methodValue").css("border-color", "red");
			$("#methodIdValue").css("border-color", "red");
			setTimeout(function(){
				$("#methodValue").css("border-color", "black");
				$("#methodIdValue").css("border-color", "black");
			}, 1000);
			$('#methodValue').trigger('change');
		};
		var url = './testProxyLibra?act=getMethod&beanName=' + beanName;
		if ($("#methodType .on a").prop('id') == 'callStaticMethod') {
			url = url + '&isStatic=1&className=' + beanName;
		}
		ajax(url, null, successFunc);
	}	
	_instance.init();
})();