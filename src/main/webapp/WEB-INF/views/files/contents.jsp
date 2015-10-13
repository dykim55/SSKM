<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.sskm.model.UserInfo
            , com.cyberone.sskm.utils.StringUtil"
    
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

PG = (function() {
	var rows=15, page=1, parent, searchSel=1, searchWord="";
	return {
		reload : function(p, func) { if(p) { parent = p; searchSel=1; searchWord=""; } else { searchSel = $("#nt_selbox").val(); searchWord = $("#searchWord").val(); } $("#files-grid-div").load("/files/file_ajax", { rows: rows, parent: parent, searchSel : searchSel, searchWord : searchWord, page : page }, func); },
		move : function(p) { if(p) page = p; this.reload(); },
		rows : function(r) { if(r) { rows = r; page = 1; } this.reload(); }
	};
})();

ALL_PG = (function() {
	var rows=15, page=1, parent, searchWord="";
	return {
		reload : function(p, s, func) { if(p) parent = p; if(s) searchWord = s; $("#files-grid-div").load("/files/allover_ajax", { rows: rows, searchWord : $(".dir-search input").val(), page : page }, func); },
		move : function(p) { if(p) page = p; this.reload(); },
		rows : function(r) { if(r) { rows = r; page = 1; } this.reload(); }
	};
})();

$(document).ready(function() {
	
	$('.accordionHeaders').click(function() {
		$(".location .left").html($(".menu dt[code=<%=subMenuList.get(0).get("prtsCd")%>]")[0].textContent.trim() + "&nbsp;&nbsp;>&nbsp;&nbsp;" + $(this)[0].textContent.trim());
		
		$(".left-tree .selected").not(this).removeClass("selected");

		if ($(".content-head .left-set").find("button").length > 1) $(".content-head").find("button").eq(0).remove();
		
		if ($(this).hasClass('ac_selected')) {
			return;
		}
		$('.accordionHeaders').removeClass('ac_selected');
		$('.contentHolder').slideUp();
		$(this).addClass('ac_selected').next('.contentHolder').slideToggle();
		
	});
	
    $(".left-tree").treetable({ expandable: true });
    
    // Highlight selected row
    $(".left-tree tbody").off("mousedown", "tr");
    $(".left-tree tbody").off("mouseenter", "tr");
    $(".left-tree tbody").off("mouseleave", "tr");
    
    $(".left-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    		$(this).removeClass("hover");
    	    $(".left-tree .selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}

    	PG.reload($(this).attr('data-tt-id'), function() {
    		$(".location .left").html($(".menu dt[code=<%=subMenuList.get(0).get("prtsCd")%>]")[0].textContent.trim() + "&nbsp;&nbsp;>&nbsp;&nbsp;" + $('.accordion').find('.accordionHeaders.ac_selected')[0].textContent.trim() + "&nbsp;&nbsp;>&nbsp;&nbsp;" + $(".left-tree:visible").treetable('pathName', $(".left-tree:visible").find(".selected").attr('data-tt-id')));	
    	});

    });
    
    $(".left-tree tbody").on("mouseenter", "tr", function() {
		$(this).find(".option").show();
		$(this).addClass("hover");
    });

    $(".left-tree tbody").on("mouseleave", "tr", function() {
		$(this).find(".option").hide();
		$(this).removeClass("hover");
    });
    
    $(".location .left").html($(".menu dt[code=<%=subMenuList.get(0).get("prtsCd")%>]")[0].textContent.trim() + "&nbsp;&nbsp;>&nbsp;&nbsp;" + $('.accordion').find('.accordionHeaders.ac_selected')[0].textContent.trim());
    
    $("#searchWord").keydown(function(e) {
        if (e.keyCode==13) {
        	search();
            return false;
        }
    });
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
	_confirm("삭제 하시겠습니까?", function() {
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
	                _alert(data.message);
	            }
	        }
	    });
	});
}

function fileUpload(id) {
    DIALOG.Open().load("/files/file_upload", {id: id ? id : ""}, function() {
    	FILE_UPLOAD.init($(this));
    });
}

function deleteFile(id) {
	_confirm("삭제 하시겠습니까?", function() {
        $.ajax({
        	url : "/files/file_delete",
            data : {id: id},
            type : "post",
            success : function(data){
                if (data.status == 'success') {
                } else {
                    _alert(data.message);
                }
                PG.reload(false, function() {});
            }
        });
	});
    event.stopPropagation();
}

function fileBox(id) {
	if ($(".detail ul").length == 0) {
		$(".detail").append('<ul style="white-space: nowrap;left:'+event.clientX+'px;top:'+event.clientY+'px;"></ul>');
		$.get("/files/appx_list", {id: id}, function( data ) {
			$(".detail ul").append(data);
		});
	}
	event.stopPropagation();
}

function search() {
	PG.reload(false, function() {});
}

function allOverSearch() {
	ALL_PG.reload(false, function() {});
}

</script>


<!-- depth 1. content -->
<div class="content">

	<!-- depth 2. sub menu ui -->
	<div class="sub">

		<!-- depth 3. sub menu search -->
		<div class="dir-search">
			<input type="text" onclick="this.select()" onKeyDown="if(event.keyCode==13){javascript:allOverSearch(); return false;}">
			<button type="button" onclick="javascript:allOverSearch();"><img src="/images/detail/icon_search.png"></button>
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
							<span><%=StringUtil.convertString(ctgMap.get("ctgNm")) %></span>
							<div class="option">
								<a href="javascript:createFolder();" class="addfolder" title="추가"></a>
							</div>
						</div>
						<div class="contentHolder" style="display: <%= (sSubMenu.equals(StringUtil.convertString(ctgMap.get("ctgSct"))) ? "block" : "none") %>;">
						    <table class="left-tree">
						        <tbody>
			<%      }   
					
	           		if ((Integer)ctgMap.get("ctgParent") > 0) { %>
						            <tr class="accordionContent" level='<%=ctgMap.get("level") %>' ref-sct='<%=ctgMap.get("ctgSct") %>' data-tt-id='<%=ctgMap.get("ctgId") %>' <% if ((Integer)ctgMap.get("ctgParent") > 0) { %> data-tt-parent-id='<%=ctgMap.get("ctgParent") %>' <% } %> style="display: none;">
						                <td>
						                    <span><%=StringUtil.convertString(ctgMap.get("ctgNm")) %></span>
						                    <div class="option">
						                    	<a href="javascript:createFolder();" class="addfolder" title="추가"></a>
						                    	<a href="javascript:modifyFolder();" class="editfolder" title="수정"></a>
												<a href="javascript:deleteFolder();" class="delfolder" title="삭제"></a>
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
		<div class="detail" style="max-width: inherit;">
			<div class="content-head" style="max-width: inherit;">
				<div class="head-end">
					<div class="set-table">				
				
						<div class="left-set">
							<button type="button" onclick="javascript:createFolder();">새 폴더</button>
						</div>
						<div class="right-set">
							<div class="list-search">
								<select id="nt_selbox" style="float:left;margin-left:10px;padding:2px;height: 26px;font-family:&quot;nanum&quot;;font-size:12px;color:#555;border:1px solid #AAA;vertical-align:middle;margin-top:5px;">
									<option value="1" <%=StringUtil.convertString(request.getParameter("searchSel")).equals("1") ? "selected" : "" %>>제목</option>
									<option value="2" <%=StringUtil.convertString(request.getParameter("searchSel")).equals("2") ? "selected" : "" %>>등록자</option>
								</select>							
								<input type="text" onclick="this.select()" onKeyDown="if(event.keyCode==13){javascript:search(); return false;}" id="searchWord" name="searchWord" value="<%=StringUtil.convertString(request.getParameter("searchWord"))%>">
								<button type="button" onclick="javascript:search();"><img src="/images/detail/icon_normal_search.png"></button>
								<div class="cl"><!-- Clear Fix --></div>
							</div>
						</div>
				
					</div>
				</div>
			</div>
	
			<table class="board" style="max-width: inherit;">
				<colgroup>
					<col width="80">
					<col width="*">
					<col width="100">
					<col width="150">
					<col width="250">
					<col width="100">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>첨부파일</th>
						<th>등록자</th>
						<th>등록일자</th>
						<th>조회</th>
					</tr>
				</thead>
				<!-- 
				<tbody>
					<tr>
						<td colspan="6">
							<h2>파일이 없습니다.</h2>
						</td>
					</tr>
				</tbody>
				 -->
			</table>
	
		</div>	

	</div>
	
</div>

<%@ include file="../include/footer.jsp"%>