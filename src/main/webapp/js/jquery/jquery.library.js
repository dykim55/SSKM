/* @Author lee jun */

$(function(){
	windowSizeFix(); //window for doc size fix
	$(window).resize(function(){
		windowSizeFix();
	});
	function windowSizeFix(){
		var thisDoc = $(".section");
		var headerH = $(".header").outerHeight();
		var sumH = $(window).innerHeight()-headerH;
		thisDoc.css({"height":sumH});
	}
	
	idFix(); //id for focus event
	function idFix(){
		var urlPath = $(".login-box").find("input[type='text']");
		urlPath.bind({
			focus : function(){
				$(this).css({background:"url(/images/login/input_id_on.png) no-repeat",color:"white"});
			}, blur : function(){
				if(!$(this).val()){
					$(this).removeAttr("style");
				} else {
					$(this).css({background:"url(/images/login/input_id_off.png) no-repeat",color:"#004a91"});
				}
			}
		});
	}
	
	passFix(); //id for focus event
	function passFix(){
		var urlPath = $(".login-box").find("input[type='password']");
		urlPath.bind({
			focus : function(){
				$(this).css({background:"url(/images/login/input_pass_on.png) no-repeat",color:"white"});
			}, blur : function(){
				if(!$(this).val()){
					$(this).removeAttr("style");
				} else {
					$(this).css({background:"url(/images/login/input_pass_off.png) no-repeat",color:"#004a91"});
				}
			}
		});
	}

	navUi(); //top depth Ui
	function navUi(){
		var menuPath = $(".menu").find("dl");
		var depth2path = $(".menu").find("ol");
		var lightPath = $(".light-box").find("img");
		
		menuPath.find("dt,dd").bind({
			mouseenter : function(){
				lightPath.animate({width:$(this).outerWidth(),left:$(this).position().left},50);
				depth2path.hide();
				$(".location .left").hide();
				depth2path.eq($(this).index()).show();
				depth2path.eq($(this).index()).css({"margin-left":$(this).position().left-10});
			}, click : function(){
				window.location.href = $(this).attr("href");
			}
		});
		
		depth2path.find("li").bind({
			mouseenter : function(){
				$(this).css({background:"url(/images/common/bg_subnav_on.png)","color":"#f5f487"});
			}, mouseleave : function(){
				$(this).removeAttr("style");
			}, click : function(){
				window.location.href = $(this).attr("href");
			}
		});
		
		$(".nav").mouseleave(function(){
			depth2path.hide();
			$(".location .left").show();
		});		
	}

	listHover(); //board list for mouse focus
	function listHover(){
		var thisPath = $(".board").find("tbody").find("td");
		
		thisPath.bind({
			mouseenter : function(){
				$(this).parent().find(".option").show();
				$(this).parent().find("td").css({background:"#f2f2f2"});
			}, mouseleave : function(){
				$(this).parent().find(".option").hide();
				$(this).parent().find("td").removeAttr("style");
			}
		});
	}
	
	listfileview(); //board list for file view
	function listfileview(){
		var boxPath = $(".board").find(".filebox");
		
		boxPath.find(".clip").click(function(){
			$(this).parents(".board").find(".clip").removeAttr("style");
			$(this).parents(".board").find("ul").hide();
			$(this).css({background:"url(/images/detail/icon_clip_on.png) no-repeat"});
			$(this).parent().find("ul").show(200);
		});
		
		boxPath.find(".close").click(function(){
			$(this).parents(".board").find(".clip").removeAttr("style");
			$(this).parents(".board").find("ul").hide(100);
		});
	}
	
	treeMenuHover(); //tree menu type 1 of hover event
	function treeMenuHover(){
		$(".accordionHeaders").bind({
			mouseenter : function(){
				$(this).find(".option").show();
			}, mouseleave : function(){
				$(this).find(".option").hide();
			}
		});
		
		/*
		$(".accordionContent").bind({
			mouseenter : function(){
				$(this).find(".option").show();
				$(this).addClass("hover");
			}, mouseleave : function(){
				$(this).find(".option").hide();
				$(this).removeClass("hover");
			}
		});
		*/
	}
	
	$.ui.dialog.prototype._focusTabbable = function(){};	//dialog focus bug path
});