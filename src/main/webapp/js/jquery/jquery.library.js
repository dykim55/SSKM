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
		
		menuPath.find("dt").bind({
			mouseenter : function(){
				depth2path.hide();
				depth2path.eq($(this).index()).show();
			}, click : function(){
				window.location.href = $(this).attr("href");
			}
		});
		
		depth2path.find("li").bind({
			mouseenter : function(){
			}, click : function(){
				window.location.href = $(this).attr("href");
			}
		});
		
		$(".nav").mouseleave(function(){
			depth2path.hide();
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
});