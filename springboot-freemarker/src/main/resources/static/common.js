Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

Date.prototype.preDay = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate()-1, //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var Utils = {
    topWindow: function(){
        var win = window;
        var count = 0;
        for (;;) {
            if (win.parent === win.self || !win.parent) {
                break;
            }
            if(window.console){
                console.log(++count);                
            }
            win = win.parent;
        }
        return win;
    },
    abtainWindowByIndex: function(ind){
        var win = window;
        var count = 0;
        for (var i = 0; i < ind; i++) {
            if (win.parent === win.self || !win.parent) {
                break;
            }
            if(window.console){
                console.log(++count);                
            }
            win = win.parent;
        }
        return win;
    },
	/**
	 * 获取顶层窗口，并绑定窗口大小变化事件
	 */
	bindTopWinResize: function(fun){
		var topWin = Utils.topWindow();
		Utils.bindWinResize(topWin, fun);
	},
	/**
	 * 
	 */
	bindWinResize: function(win, fun){
		if(win.addEventListener){
			win.addEventListener('resize',function(){
	        	fun();
	        });
	    }else{
	        if(win.attachEvent){
	        	win.attachEvent('onresize',function(){
	            	fun();
	            });
	        }
	    }
	    if(win.addEventListener){
	    	win.addEventListener('resize',function(){
	    		fun();
	        });
	    }else{
	        if(win.attachEvent){
	        	win.attachEvent('onresize',function(){
	        		fun();
	            });
	        }
	    }
	}
};
jQuery.extend({
	Utils : Utils,
	ajaxlock: function(){
		$.ajaxSetup({
			async: false
		});
	},
	nullAndUndefined2Str: function(obj){
		if(typeof obj == 'undefined' || obj == null){
			return '';
		}
		return obj;
	},
	checkNullOrEmpty: function(it, message){
		if(typeof obj == 'undefined' || obj == null || obj == ''){
			if(message){
				$.tip(message);
			}
			return true;
		}
		return false;
	},
	ajaxunlock: function(){
		$.ajaxSetup({
			async: true
		});
	},
	ajaxw : function(options) {
		if(options.isAlert !== true){
			options.isAlert = false;
		}
		if(options.async !== false){
			options.async = true;
		}
		$.ajax({
			url : options.url,
			data : options.data || {},
			type : 'POST',
			async : options.async,
			dataType : 'json',
			success : function(data) {
				if(data['session_time_is_out'] == 'true'){// 登录超时，退出
					 Utils.topWindow().showLoginPage();
					 return;
				}
				if(typeof data.code != 'undefined' && options.isAlert){
					var ff = function(){
						if(typeof options.success == 'function'){
							options.success(data);
						}
					};
					if(data.code==0){
						$.messager.alert('注意', '操作成功！', 'info', ff);
					}else{
						$.messager.alert('注意', '操作失败！失败原因：'+data.message, 'warning', ff);
					}
				}else{
					options.success(data);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if(!options.error || options.isAlert){
					$.messager.alert('注意', '后台异常，请联系系统管理员！', 'error');
				}else{
					options.error(XMLHttpRequest, textStatus, errorThrown);
				}
			}
		});
	},
	ajaxwsync: function(options){
		var responseText = $.ajax({url:options.url,dataType:"json",data:options.data,async:false,cache:false,type:"post",error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('注意', '后台异常，请联系系统管理员！', 'error');
		}}).responseText;
		if(responseText == "{\"session_time_is_out\":\"true\"}"){// 登录超时，退出
			 Utils.topWindow().showLoginPage();
			 return false;
		}
		return responseText;
	},
	isMyEmpty: function(attr){
    	if(typeof attr == 'undefined' || attr == null || attr == ''){
    		return true;
    	}else{
    		return false;
    	}
    },
    mask: {
        show: function(title){
    		$(document).mask('<div id="p" class="easyui-progressbar" data-options="value:60" style="width:400px;"></div>', undefined, function(){
				$('#p').progressbar({
					text: title,
				    value: 0
				});
			});
    		$.mask.interval = setInterval(function(){
				var val = $('#p').progressbar('getValue');
				if(val < 80){
					$('#p').progressbar('setValue', val+2);
				}else{
					clearInterval($.mask.interval);
				}
			}, 500);
        },
        hide: function(fun){
        	clearInterval($.mask.interval);
        	$.mask.interval = setInterval(function(){
				var val = $('#p').progressbar('getValue');
				if(val < 100){
					$('#p').progressbar('setValue', val+2);
				}else{
					clearInterval($.mask.interval);
					$(document).unmask();
					if(fun){
						fun();
					}
				}
			}, 10);
        },
        lmhide: function(){
        	clearInterval($.mask.interval);
        	$(document).unmask();
        }
    },
    tip: function(msg, title, seconds){
    	if(!title){
    		title = '注意';
    	}
    	if(!seconds){
    		seconds = 3000;
    	}
    	$.messager.show({
			title : title,
			msg : msg,
			timeout : seconds,
			showType : 'slide'
		});
    },
    href: function(url){
    	if(url.indexOf('?') != -1){
    		url += '&isTagPage=true';
    	}else{
    		url += '?isTagPage=true';
    	}
    	window.location.href = url;
    },
    getTimeStr: function(time){
    	var hours = time.getHours();
    	hours = hours<10?'0'+hours:hours;
    	var minutes = time.getMinutes();
    	minutes = minutes<10?'0'+minutes:minutes;
    	var seconds = time.getSeconds();
    	seconds = seconds<10?'0'+seconds:seconds;
    	var years = time.getFullYear();
    	var months = time.getMonth()+1;
    	months = months<10?'0'+months:months;
    	var days = time.getDate();
    	days = days<10?'0'+days:days;
    	return years+'-'+months+'-'+days +' '+hours+':'+minutes+':'+seconds;
    },
    /**
     * 阻止浏览器默认事件
     */
    stopEvent: function(e){
    	if(e.preventDefault){
			e.preventDefault();// 取消事件的默认行为  
			e.stopPropagation(); // 阻止事件的传播
		}else{
			window.event.cancelBubble = true;//停止冒泡
			window.event.returnValue = false;//阻止事件的默认行为
		}
    },
    roundStr : function(number, num){
    	if(isNaN(number)){
    		number = parseFloat(number);
    		if(isNaN(number)){
        		return '';
        	}
    	}else{
    		if(number == null){
    			number = 0;
    		}
    	}
    	if(isNaN(num)){
    		num = 0;
    	}
    	var power = Math.pow(10, num);
    	var result = Math.round(number*power)/power+'';
    	
    	var start = result.indexOf('.') == -1 ? 0 : result.length - result.indexOf('.') - 1;
    	if(start == 0 && num != 0){
			result = result.concat('.');
		}
    	for ( var int = start; int < num; int++) {
    		result = result.concat('0');
		}
    	return result;
    },
    getPosition : function(id){
    	var dom = document.getElementById(id);
    	var top = 0;
    	var left = 0;
    	while(dom != null){
    		top += dom.offsetTop;
    		left += dom.offsetLeft;
    		
    		dom = dom.offsetParent;
    	}
    	return {
    		left: left,
    		top: top
    	};
    }
});

(function($){
	$.extend($.fn.validatebox.defaults.rules, {
	    remoteValid: {
	        validator: function(value, param){
	        	if(param && param.length != 0 && param[0] != ''){
	        		var responseText = $.ajaxwsync({url: param[0], data: {val: value}});
	        		if(responseText === 'true' || (param.length > 1 && responseText == $('#'+param[2]).val())){
	        			return true;
	        		}else{
	        			if(param.length > 1){
	        				this.message = param[1];	        				
	        			}
	        			return false;
	        		}
	        	}
	        },
	        message: '验证失败，请按要求重新填写！'
	    },
	    regularValid: {
	        validator: function(value, param){
	        	if(param && param.length > 0){
	        		eval('var re = /' + param[0] +'/;');
	        		if(re.test(value)){
	        			return true;
	        		}else{
	        			if(param.length > 1)
	        				this.message = param[1];
	        			return false;
	        		}
	        	}
	        	return false;
	        },
	        message: '验证失败，请按要求重新填写！'
	    },
	    ipvalid: {
	    	validator: function(value, param){
        		if(/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(value)){
        			return true;
        		}
	        	return false;
	        },
	        message: '请填写合法的IP地址！'
	    },
	    portvalid: {
	    	validator: function(value, param){
        		if(/^(0|([1-9]{1}[0-9]{0,4}))$/.test(value) && Number(value) <= 0xffff){
        			return true;
        		}
	        	return false;
	        },
	        message: '请填写合法的端口号！'
	    },
	    textvalid: {
	    	validator: function(value, param){
	    		if(!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)){
	    			return false;
	    		}
	    		return true;
	    	},
	    	message: '不能包含特殊字符！'
	    }
	});
	/**
	 * easyui时间插件添加 清空 按钮
	 */
	$.fn.datebox.defaults.buttons.push({
		text: '清空',
		handler: function(target){
			$(target).datebox('clear');
			$(target).datebox('panel').panel('close');
		}
	});
})(jQuery);

function pagerFilter(data){
	if(data['session_time_is_out'] == 'true'){
		 $.Utils.topWindow().showLoginPage();
		 return;
	}
	if(data.rows){
		return data;
	}
    data = {
        total: data.totalRow,
        rows: data.list
    };
    return data;
}
/**
 * 一次查询，然后无前后台交互的分页函数
 * @param data
 * @returns
 */
function pagerLocaleDataFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}


/**
 * 在线预览文件
 * @param url
 */
function previewFile(url){
	if(url ==null || url ==''){
		$.messager.alert("提示","文件不存在");
		return;
	}
	$.messager.progress({ 
		title:'请稍候', 
		text:'在线预览文档转码中.....'
	});
	
	$.ajax({
		url:'preview/getPreviewURL.do',
		dataType:'json',
		data:{url:url},
		type:'post',
		success:function(data){
			$.messager.progress('close');
			if(data.status =='101'){
				window.open('preview/geturl.do?url='+encodeURIComponent(data.data));
			}else{
				$.messager.alert(data.msg,data.desc);
			}
			
		},
		error:function(data){
			$.messager.progress('close');
		}
	});
}


// 方法作用：解决Id的值带点，attr方法为undefined问题
// 时间：2017-12-06
// 例如：$("#wypt01.dt_lq01_ba000p0001z").attr("checked")
// author：zhangying
function getSplitTranstion(keyId) {
	var lastValue = '';//返回处理结果值
	if(keyId.indexOf(".") > 0) {
		key = keyId.split(".");
		for (var i = 0; i< key.length; i++) {
			if (i < key.length -1) {
				lastValue = key[i] + "\\.";
			} else {
				lastValue += key[i];
			}
		}
	}else{
		lastValue = keyId;
	}
	return lastValue;
}



function getUrlParam(name){
	var urlParams = decodeURIComponent(window.location.search);
	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)","i");
	var r = urlParams.substr(1).match(reg);
	return r!=null?unescape(r[2]):null;
}







