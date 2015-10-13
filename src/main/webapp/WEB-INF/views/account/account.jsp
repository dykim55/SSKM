<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.sskm.model.UserInfo
            , com.cyberone.sskm.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%
//계정그룹 트리 데이타
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> acctGrpList = (List<HashMap<String, Object>>)request.getAttribute("acctGrpList");
%>

<script type="text/javascript">

ACCT_PG = (function() {
	var rows=15, page=1, parent, group;
	return {
		reload : function(g, p, func) { if(g) group = g; if(p) parent = p; $("#account-grid-div").load("/account/account_ajax", { rows: rows, grp: group, prts: parent, page: page, searchSel: $("#ac_selbox").val(), searchWord: $("#searchWord").val() }, func); },
		move : function(p) { if(p) page = p; this.reload(); },
		rows : function(r) { if(r) { rows = r; page = 1; } this.reload(); }
	};
})();

$(document).ready(function() {
	
    $(".account-tree").treetable({ expandable: true });
    
	$('.accordionHeaders').click(function() {
		$(".account-tree .selected").removeClass("selected");
		ACCT_PG.reload("", "0", function() {});
	});
    
    // Highlight selected row
    $(".account-tree tbody").off("mousedown", "tr");
    $(".account-tree tbody").off("mouseenter", "tr");
    $(".account-tree tbody").off("mouseleave", "tr");
    
    $(".account-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    	
    	ACCT_PG.reload($(this).attr('data-tt-id'), $(this).attr('data-tt-parent-id'), function() {});
    });

    $(".account-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".account-tree .selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    });
    
    $(".account-tree tbody").on("mouseenter", "tr", function() {
		$(this).find(".option").show();
		$(this).addClass("hover");
    });

    $(".account-tree tbody").on("mouseleave", "tr", function() {
		$(this).find(".option").hide();
		$(this).removeClass("hover");
    });
    
    $(".location .left").html("계정관리");
    
    ACCT_PG.reload("", "0", function() {});
    
});

function createAuthGrp() {
    DIALOG.Open().load("/account/create_auth", {}, function() {
    	CREATE_AUTH_GROUP.init($(this));
    });
}

function modifyAuthGrp(t) {
    DIALOG.Open().load("/account/modify_auth", {grp : $(t).closest('tr').attr('data-tt-id')}, function() {
    	CREATE_AUTH_GROUP.init($(this), $(t).closest('tr'));
    });
}

function deleteAuthGrp(t) {

	_confirm("삭제 하시겠습니까?", function() {
        $.ajax({
            url: "/account/delete_auth",
            data : {grp : $(t).closest('tr').attr('data-tt-id')},
            type : "post",
            success : function(data){
                if (data.status == 'success') {
					$("#account-tree-div").load("/account/tree_ajax", {}, function() {});
                } else {
                    _alert(data.message);
                }
            }
        });
    });

}

function createAcctGrp(t) {
    DIALOG.Open().load("/account/create_group", {}, function() {
    	CREATE_GROUP.init($(this), $(t).closest('tr'));
    });
}

function modifyAcctGrp(t) {
    DIALOG.Open().load("/account/modify_group", {acct_grp : $(t).closest('tr').attr('data-tt-id')}, function() {
    	CREATE_GROUP.init($(this), $(t).closest('tr'));
    });
}

function deleteAcctGrp(t) {
	_confirm("삭제 하시겠습니까?", function() {
        $.ajax({
            url: "/account/delete_group",
            data : {prts : $(t).closest('tr').attr('data-tt-parent-id'), grp : $(t).closest('tr').attr('data-tt-id')},
            type : "post",
            success : function(data){
                if (data.status == 'success') {
					$("#account-tree-div").load("/account/tree_ajax", {}, function() {
                		$(this).find(".account-tree").treetable("reveal", data.parent);
					});
                } else {
                    _alert(data.message);
                }
            }
        });
    });
}

function createAcct(t) {
	var a=g=0;
	if ($(".account-tree tr.selected").length) {
		if ($(".account-tree tr.selected").hasClass("leaf")) {
			a=$(".account-tree tr.selected").attr('data-tt-parent-id');
			g=$(".account-tree tr.selected").attr('data-tt-id');
		} else {
			a=$(".account-tree tr.selected").attr('data-tt-id');
			g=0;
		}
	} 
    DIALOG.Open().load("/account/create_account", {auth_grp : a, acct_grp : g}, function() {
    	CREATE_ACCOUNT.init($(this), undefined);
    });
}


function acctDetail(id) {
    DIALOG.Open().load("/account/create_account", {id : id}, function() {
    	CREATE_ACCOUNT.init($(this), undefined);
    });
}

function search() {
	ACCT_PG.reload();
}

</script>

<!-- depth 1. content -->
<div class="content">		
	<!-- depth 2. section -->
	<div class="section">
		<div class="detail">
			<div class="content-head">
				<div class="head-end">
					<div class="set-table">
						<div class="left-set">
							<button type="button" onclick="javascript:createAcct();">계정 등록</button>
						</div>
						<div class="right-set">
							<div class="list-search">
								<select id="ac_selbox" style="float:left;margin-left:10px;padding:2px;height: 26px;font-family:&quot;nanum&quot;;font-size:12px;color:#555;border:1px solid #AAA;vertical-align:middle;margin-top:5px;">
									<option value="1" selected>성명</option>
									<option value="2" >부서</option>
									<option value="3" >직급</option>
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
				<!-- 3. treemenu type2-->
				<div class="dual-left">
					<div class="sub-nav">
						<div class="membership">
							<div class="accordionHeaders selected">접근권한그룹
								<div class="option">
									<a href="#" onclick="javascript:createAuthGrp();" class="addfolder" title="권한그룹추가"></a>
								</div>
							</div>
							<div id="account-tree-div" class="contentHolder" style="display: block;">
							    <table class="account-tree">
							        <tbody>
							    <% if (acctGrpList.size() > 0) {
							           for (HashMap<String, Object> map : acctGrpList) { 
							           		if ((Long)map.get("level") > 1) { %>
									            <tr class="accordionContent" level='<%=map.get("level") %>' data-tt-id='<%=map.get("acctGrpCd") %>' <% if ((Integer)map.get("acctPrntCd") > 0) { %> data-tt-parent-id='<%=map.get("acctPrntCd") %>' <% } %>>
									                <td>
									                    <span class=''><%=StringUtil.convertString(map.get("acctGrpNm")) %></span>
									                    <div class="option">
									                 <% if ((Long)map.get("level") == 2) { %>
									                    	<a class="add_r_folder" href="#" onclick="javascript:createAcctGrp(this);" title="계정그룹추가"></a>
									                    	<a class="edit_r_folder" href="#" onclick="javascript:modifyAuthGrp(this);" title="권한그룹수정"></a>
															<a class="del_r_folder" href="#" onclick="javascript:deleteAuthGrp(this);" title="권한그룹삭제"></a>
													 <% } else { %>
									                    	<a class="add_p_folder" href="#" onclick="javascript:createAcct();" title="계정추가"></a>
									                    	<a class="edit_r_folder" href="#" onclick="javascript:modifyAcctGrp(this);" title="계정그룹수정"></a>
															<a class="del_r_folder" href="#" onclick="javascript:deleteAcctGrp(this);" title="계정그룹삭제"></a>
													 <% } %>
									                    </div>
									                </td>
									            </tr>
							    <%     		}
							           } 
							       } %>        
							        </tbody>
							    </table>
							</div>
						</div>
					</div>
				</div>

				<!-- 3. list -->
				<div id="account-grid-div" class="dual-right">
					<table class="board">
						<colgroup>
							<col width="5%">
							<col width="12%">
							<col width="12%">
							<col width="15%">
							<col width="15%">
							<col width="14%">
							<col width="12%">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>계정아이디</th>
								<th>성명</th>
								<th>접근계정그룹</th>
								<th>부서</th>
								<th>직급</th>
								<th>등록일</th>
								<th>최근접속일시</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp"%>