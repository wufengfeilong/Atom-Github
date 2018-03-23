var LOGIN = {
	APPURL:$("#appUrl").val() || "https://sso.dinghuo123.com",
	getVerifyCode:function(){
		var _self = this,
			timestamp = Date.parse(new Date());
		var s = _self.APPURL + '/verifyCode' + '?timestamp=' + timestamp;
		return s;
	},
    clearCrossDomainCookie:function(){
        //$.cookie("cdc","",{domain:"dinghuo123.com"});//用于清除cdc，便于在登陆到系统之后，系统判断无这个cookie对请求列表发出跨域名的登录
        $.cookie("cdc",null);//用于清除cdc，便于在登陆到系统之后，系统判断无这个cookie对请求列表发出跨域名的登录
    },
	/**
	*
	*callback.success
	*callback.error
	*/
	auth:function(postData,callback){
		var _self = this;
		$.ajax({
			url:_self.APPURL+"/authentication",
			data:postData,
			dataType:"JSONP",
			success:function(responseText){
				if(responseText.indexOf("OK_") != -1){
					var lt = responseText.split("OK_")[1];
					!!callback.success && callback.success(lt);
				}else{
					var t = '';
					if(responseText == "login_error"){
						t = '用户名密码错误';
					}
					else if(responseText == "user_disabled"){
						t = '账号被禁用或者已删除, 请联系厂商管理员';
					}
					else if(responseText == "need_verfCode"){
						t = '用户名密码输入错误累计三次，请输入验证码';
					}
					else if(responseText == "wrong_verfCode"){
						t = '验证码错误，请重新输入';
					}
					else if(responseText.indexOf("limit") != -1){
						t = '连续输错密码五次,请30分钟后再试!';
					}
					else{
						/*t = '网络异常，请联系管理员';*/
						t = '账号或者密码错误，登录失败';
					}
					!!callback.error && callback.error(responseText,t);
				}
			},
			error:function(){
				/*var t = '网络异常，请联系管理员';*/
				var t = '账号或者密码错误，登录失败';
				!!callback.error && callback.error('error',t);
			}
		});
	},

	sendCode : function(postData,callback){
		var _self = this;
		$.ajax({
			url:_self.APPURL+'/activeCode?action=sendActiveCode',
             type: 'POST',
             data: postData,
             dataType: 'json',
             success:function(responseText){
             	if(200 === responseText.code){
             		!!callback.success && callback.success(responseText.data);
             	}else{
             		var t = responseText.message;
             		!!callback.error && callback.error(t);
             	}
             },
             error:function(){
             	/*var t = '网络异常，请联系管理员';*/
            	 var t = '账号或者密码错误，登录失败';
             	!!callback.error && callback.error(t);
             }
         });
	},
	checkActiveCode:function(postData,callback){
		var _self = this;
		 $.ajax({
	        type: "POST",
	        url: _self.APPURL+"/register?action=checkActiveCode",
	        dataType: "json",
	        data: postData,
	        success: function(responseText){
	            if(200 === responseText.code && !responseText.data.error){
             		!!callback.success && callback.success(responseText.data);
             	}else{
             		var t = responseText.message;
             		!!callback.error && callback.error(t);
             	}
	        },
	        error: function(xhr, textStatus, errorThrown){
	           /* var t = '网络异常，请联系管理员';*/
	        	 var t = '账号或者密码错误，登录失败';
             	!!callback.error && callback.error(t);
	        }
	    });
	},
	register:function(postData,callback){
		var _self = this;
		$.ajax({
	        type: "POST",
	        url: _self.APPURL+"/register",
	        dataType: "json",
	        data: postData,
	        success: function(responseText){
	            if(200 === responseText.code){
             		!!callback.success && callback.success(responseText.data);
             	}else{
             		var t = responseText.message;
             		!!callback.error && callback.error(t);
             	}
	        },
	        error: function(xhr, textStatus, errorThrown){
	          /*  var t = '网络异常，请联系管理员';*/
	        	 var t = '账号或者密码错误，登录失败';
             	!!callback.error && callback.error(t);
	        }
	    });

	}
}