(function(){
	var _instance = {};
	_instance.init = function() {
		init();
		bindEvents();
	};
	function init() {
		//$("#methodType .on a").css('color', '#065f89');
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
		$('#submit').on('click', _invokeMethod);
	}
	
	function _invokeMethod() {//触发方法
		var userName = $('#userName').val();
		var password = $('#password').val();
		// var paramArr = {'userName': userName, 'password': 'password', 'act': 'sso'};
//		var url = './testProxyLibra?act=callMethod&methodId=' + methodId + '&beanName=' + beanName;
// 		var url = './ssoLogin';
		// $('#right #invokeResult').val("");
		// ajax(url, paramArr, successFuc);
        var url = window.location.href;
        // var returnUrlIndex = url.indexOf("returnUrl");
        // var returnUrl  = url.substr(returnUrlIndex, url.length -1);
		window.location.href=url + '&userName=' + userName + '&password=' + password + '&act=' + 'sso';
	}
	_instance.init();
})();