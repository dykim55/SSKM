<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.sskm.model.UserInfo
            , com.cyberone.sskm.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%
	String sBbsSct = StringUtil.convertString(request.getAttribute("bbsSct"));

    @SuppressWarnings("unchecked")
    List<HashMap<String, Object>> bbsList = (List<HashMap<String, Object>>)request.getAttribute("list");
    
    int nRows =  (Integer)request.getAttribute("rows");
    int nTotalRecord =  (Integer)request.getAttribute("record");
    int nTotalPage = (Integer)request.getAttribute("total");
    int nGroupPos = (Integer)request.getAttribute("group");
    int nGroupEndPos = (Integer)request.getAttribute("groupEnd");
    int nGroupStartPos = (Integer)request.getAttribute("groupStart");
    int nPrevPageNo = (Integer)request.getAttribute("prev");
    int nNextPageNo = (Integer)request.getAttribute("next");
    int nPage = (Integer)request.getAttribute("page");
    
    String sSearchWord = StringUtil.convertString(request.getParameter("searchWord"));
%>

<script type="text/javascript">

ARTICLE_PG = (function() {
	var rows=15, page=1, bbsSct, searchSel=1, searchWord="";
	return {
		reload : function(b, func) { if(b) { bbsSct = b; searchSel=1; searchWord=""; } else { searchSel = $("#nt_selbox").val(); searchWord = $("#searchWord").val(); } $(".dual-right").load("/article/article_list", { rows: rows, bbsSct: bbsSct, searchSel : searchSel, searchWord : searchWord, page : page }, func); },
		move : function(p) { if(p) page = p; this.reload(); },
		rows : function(r) { if(r) { rows = r; page = 1; } this.reload(); }
	};
})();

$(document).ready(function() {
	ARTICLE_PG.reload(<%=sBbsSct%>, function() {});
	$(".location .left").html("공지사항");
});

function articleWrite(s, id) {
    DIALOG.Open().load("/article/article_write", {bbsSct : s, id: id ? id : ""}, function() {
    	ARTICLE_DLG.init($(this));
    });
}

function fileBox(id) {
	if ($(".detail ul").length == 0) {
		$(".detail").append('<ul style="white-space: nowrap;left:'+event.clientX+'px;top:'+event.clientY+'px;"></ul>');
		$.get("/article/appx_list", {id: id}, function( data ) {
			$(".detail ul").append(data);
		});
	}
	event.stopPropagation();
}

function deleteArticle(id) {
	_confirm("삭제 하시겠습니까?", function() {
        $.ajax({
        	url : "/article/article_delete",
            data : {id: id},
            type : "post",
            success : function(data){
                if (data.status == 'success') {
                } else {
                    _alert(data.message);
                }
                ARTICLE_PG.reload(false, function() {});
            }
        });
	});
    event.stopPropagation();
}

function search() {
	ARTICLE_PG.reload(false, function() {});
}

</script>

<div class="content">		
	<!-- depth 2. section -->
	<div class="section">
		<div class="detail" style="margin: 20px auto 0px auto;width: 70%;">
			<div class="content-head">
				<div class="head-end">
					<div class="set-table">
						<div class="left-set">
							<button type="button" onclick="javascript:articleWrite(<%=sBbsSct %>);">등  록</button>
						</div>
						<div class="right-set">
							<div class="list-search">
								<select id="nt_selbox" style="float:left;margin-left:10px;padding:2px;height: 26px;font-family:&quot;nanum&quot;;font-size:12px;color:#555;border:1px solid #AAA;vertical-align:middle;margin-top:5px;">
									<option value="1" <%=StringUtil.convertString(request.getParameter("searchSel")).equals("1") ? "selected" : "" %>>제목</option>
									<option value="2" <%=StringUtil.convertString(request.getParameter("searchSel")).equals("2") ? "selected" : "" %>>작성자</option>
								</select>							
								<input type="text" onclick="this.select()" onKeyDown="if(event.keyCode==13){javascript:search(); return false;}" id="searchWord" name="searchWord" value="<%=StringUtil.convertString(request.getParameter("searchWord"))%>">
								<button type="button" onclick="javascript:search();"><img src="/images/detail/icon_normal_search.png"></button>
								<div class="cl"><!-- Clear Fix --></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="inner-side">	
				<div class="dual-right">
				</div>
			</div>	
		</div>
	</div>
</div>		

<%@ include file="../include/footer.jsp"%>