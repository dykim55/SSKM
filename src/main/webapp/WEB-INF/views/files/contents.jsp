<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%

@SuppressWarnings("unchecked")
List<HashMap<String, Object>> subMenuList = (List<HashMap<String, Object>>)request.getAttribute("subMenuList");

@SuppressWarnings("unchecked")
HashMap<String, Object> sideMap = (HashMap<String, Object>)request.getAttribute("sideMap");

String sCtgSct = StringUtil.convertString(request.getAttribute("gubun"));

String sSubMenu = StringUtil.convertString(request.getParameter("subMenu"));
if (StringUtil.isEmpty(sSubMenu)) {
	sSubMenu = StringUtil.convertString(subMenuList.get(0).get("menuCd"));
}

%>

<script type="text/javascript">

$(document).ready(function() {
	
	$('#btn_test').click(function() {
		console.log(location.href);
		location.href="/files/security_control?A=1&B=2";
	});
	
	$('.accordionHeaders').click(function() {
		$(".location").html($(this)[0].textContent.trim());
		$(".left-tree .selected").not(this).removeClass("selected");
		
		if ($(this).hasClass('ac_selected')) {
			return;
		}
		$('.accordionHeaders').removeClass('ac_selected');
		$('.contentHolder').slideUp();
		$(this).addClass('ac_selected').next('.contentHolder').slideToggle();
		
		if ($(".content-head").find("button").length > 1) $(".content-head").find("button")[0].remove(); 
		
	});
	
    $(".left-tree").treetable({ expandable: true });
    
    // Highlight selected row
    $(".left-tree tbody").off("mousedown", "tr");
    $(".left-tree tbody").off("mouseenter", "tr");
    $(".left-tree tbody").off("mouseleave", "tr");
    
    $(".left-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".left-tree .selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    	$(".location").html($('.accordion').find('.accordionHeaders.ac_selected')[0].textContent.trim() + "&nbsp;&nbsp;>&nbsp;&nbsp;" + $(this).closest('table').treetable('pathName', $(this).attr('data-tt-id')));

    	PG.reload($(this).attr('data-tt-id'), function() {});
    });
    
    $(".left-tree tbody").on("mouseenter", "tr", function() {
		$(this).find(".option").show();
		$(this).addClass("hover");
    });

    $(".left-tree tbody").on("mouseleave", "tr", function() {
		$(this).find(".option").hide();
		$(this).removeClass("hover");
    });
    
    $(".location").html($('.accordion').find('.accordionHeaders.ac_selected')[0].textContent.trim());
    
});

function createFolder() { //folder create
    DIALOG.Open().load("/files/create_folder", {}, function() {
        CREATE_FOLDER.init($(this));
    });
}

function modifyFolder() {
    DIALOG.Open().load("/files/create_folder", {id:  $(".left-tree .selected").attr('data-tt-id')}, function() {
        CREATE_FOLDER.init($(this));
    });
}

function deleteFolder(tr) {

	if(confirm("삭제 하시겠습니까?")) {
	    $.ajax({
	        url: "/files/delete_folder",
	        data : {id:  $(".left-tree .selected").attr('data-tt-id')},
	        type : "post",
	        success : function(data){
	            if (data.status == 'success') {
                	$('.accordion').find('.accordionHeaders.ac_selected').next().load("/files/tree_ajax", {"gubun": $('.accordion').find('.accordionHeaders.ac_selected').attr("ref-sct")}, function() {
                		$(this).find(".left-tree").treetable("reveal", data.parent);
                		$(this).find(".left-tree").treetable("node", data.parent).expand();
					});
	            } else {
	                alert(data.message);
	            }
	        }
	    });
	}

}

function fileUpload(id) {
    DIALOG.Open().load("/files/file_upload", {id: id ? id : ""}, function() {
    	FILE_UPLOAD.init($(this));
    });
}

function deleteFile(id) {
    if(confirm("삭제 하시겠습니까?")) {
        $.ajax({
        	url : "/files/file_delete",
            data : {id: id},
            type : "post",
            success : function(data){
                if (data.status == 'success') {
                } else {
                    alert(data.message);
                }
                PG.reload(false, function() {});
            }
        });
    }
}

function fileBox(id) {
	if ($('[data-tt-id=' + id + ']').find(".filebox ul").length == 0) {
		$.get("/files/appx_list", {id: id}, function( data ) {
			$('[data-tt-id=' + id + ']').find(".filebox").append(data);
		});
	}
}

</script>


<!-- depth 1. content -->
<div class="content">

	<!-- depth 2. sub menu ui -->
	<div class="sub">

		<!-- depth 3. sub menu search -->
		<div class="dir-search">
			<input type="text">
			<button type="button"><img src="/images/detail/icon_search.png"></button>
			<div class="cl"><!-- clear fix --></div>
		</div>

		<!-- depth3. sub menu -->
		<div class="sub-nav" onselectstart="return false" ondragstart="return false">
		
			<div class="accordion">

	<% if (subMenuList.size() > 0) {
			int idx=0;
			for (HashMap<String, Object> map : subMenuList) { 
				List<HashMap<String, Object>> ctgList = (List<HashMap<String, Object>>)sideMap.get(StringUtil.convertString(map.get("menuCd")));
				for (HashMap<String, Object> ctgMap : ctgList) {
					if ((Integer)ctgMap.get("ctgParent") == 0) {
			%>
						<div ref-sct="<%=StringUtil.convertString(ctgMap.get("ctgSct")) %>" ref-id="<%=(Integer)ctgMap.get("ctgId") %>" class="accordionHeaders <%= (sSubMenu.equals(StringUtil.convertString(ctgMap.get("ctgSct"))) ? "ac_selected" : "") %>">
							<%=StringUtil.convertString(ctgMap.get("ctgNm")) %>
							<div class="option">
								<a href="javascript:createFolder();"><i class="fa fa-plus-square-o"></i></a>
							</div>
						</div>
						<div class="contentHolder" style="display: <%= (sSubMenu.equals(StringUtil.convertString(ctgMap.get("ctgSct"))) ? "block" : "none") %>;">
						    <table class="left-tree">
						        <tbody>
			<%      }   
					
	           		if ((Integer)ctgMap.get("ctgParent") > 0) { %>
						            <tr class="accordionContent" level='<%=ctgMap.get("level") %>' ref-sct='<%=ctgMap.get("ctgSct") %>' data-tt-id='<%=ctgMap.get("ctgId") %>' <% if ((Integer)ctgMap.get("ctgParent") > 0) { %> data-tt-parent-id='<%=ctgMap.get("ctgParent") %>' <% } %> style="display: none;">
						                <td>
						                    <span class='folder'><%=StringUtil.convertString(ctgMap.get("ctgNm")) %></span>
						                    <div class="option">
						                    	<a href="javascript:createFolder();"><i class="fa fa-plus-square-o"></i></a>
						                    	<a href="javascript:modifyFolder();"><i class="fa fa-pencil-square-o"></i></a>
												<a href="javascript:deleteFolder();"><i class="fa fa-trash-o"></i></a>
						                    </div>
						                </td>
						            </tr>
	    <%          }
	    		} %>
						        </tbody>
						    </table>
						</div>				
		<%  } %>        

	<% } %>
			</div>

		</div>
	</div>


	<!-- depth 2. section -->
	<div class="section" id="files-grid-div" onselectstart="return false" ondragstart="return false">
		<div class="detail">
			<div class="content-head">
				<div class="head-end">
					<div class="set-table">				
				
						<div class="left-set">
						</div>
						<div class="right-set">
							<button type="button" onclick="javascript:createFolder();">새 폴더</button>
						</div>
				
					</div>
				</div>
			</div>
	
			<div id="emptycontent" style="font-size: 16px;    color: #888;    position: absolute;    text-align: center;    top: 30%;    width: 85%;">
				<div class="icon-folder"></div>
				<h2>파일이 없습니다.</h2>
			</div>
	
		</div>	

	</div>
	
</div>

<%@ include file="../include/footer.jsp"%>