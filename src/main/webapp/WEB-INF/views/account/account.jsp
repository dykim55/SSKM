<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> acctList = (List<HashMap<String, Object>>)request.getAttribute("acctList");
%>

<script type="text/javascript">

$(document).ready(function() {
	
    $(".account-tree").treetable({ expandable: true });
    
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

    if(confirm("삭제 하시겠습니까?")) {
        $.ajax({
            url: "/account/delete_auth",
            data : {grp : $(t).closest('tr').attr('data-tt-id')},
            type : "post",
            success : function(data){
                if (data.status == 'success') {
					$("#account-tree-div").load("/account/tree_ajax", {}, function() {
					});
                } else {
                    alert(data.message);
                }
            }
        });
    }

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

    if(confirm("삭제 하시겠습니까?")) {
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
                    alert(data.message);
                }
            }
        });
    }

}

function createAcct(t) {
    DIALOG.Open().load("/account/create_account", {auth_grp : $(t).closest('tr').attr('data-tt-parent-id'), acct_grp : $(t).closest('tr').attr('data-tt-id')}, function() {
    	CREATE_ACCOUNT.init($(this), $(t).closest('tr'));
    });
}

function fileUpload(parent) {
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
						<div class="left-set">계정관리</div>
						<div class="right-set">
							<div class="list-search">
								<input type="text">
								<button type="button"><img src="/images/detail/icon_normal_search.png"></button>
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
									<a href="#" onclick="javascript:createAuthGrp();" class="add_r_folder" title="권한그룹추가"><i class="fa fa-plus-square-o"></i></a>
								</div>
							</div>
							<div id="account-tree-div" class="contentHolder" style="display: block;">
							    <table class="account-tree">
							        <tbody>
							    <% if (acctList.size() > 0) {
							           for (HashMap<String, Object> map : acctList) { 
							           		if ((Long)map.get("level") > 1) { %>
									            <tr class="accordionContent" level='<%=map.get("level") %>' data-tt-id='<%=map.get("acctGrpCd") %>' <% if ((Integer)map.get("acctPrntCd") > 0) { %> data-tt-parent-id='<%=map.get("acctPrntCd") %>' <% } %>>
									                <td>
									                    <span class=''><%=StringUtil.convertString(map.get("acctGrpNm")) %></span>
									                    <div class="option">
									                 <% if ((Long)map.get("level") == 2) { %>
									                    	<a href="#" onclick="javascript:createAcctGrp(this);" title="계정그룹추가"><i class="fa fa-users"></i></a>
									                    	<a href="#" onclick="javascript:modifyAuthGrp(this);" title="권한그룹수정"><i class="fa fa-pencil-square-o"></i></a>
															<a href="#" onclick="javascript:deleteAuthGrp(this);" title="권한그룹삭제"><i class="fa fa-trash-o"></i></a>
													 <% } else { %>
									                    	<a href="#" onclick="javascript:createAcct(this);" title="계정추가"><i class="fa fa-user-plus"></i></a>
									                    	<a href="#" onclick="javascript:modifyAcctGrp(this);" title="계정그룹수정"><i class="fa fa-pencil-square-o"></i></a>
															<a href="#" onclick="javascript:deleteAcctGrp(this);" title="계정그룹삭제"><i class="fa fa-trash-o"></i></a>
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
							<col width="15%">
							<col width="15%">
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
								<th>등록일시</th>
								<th>최근접속일시</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>756</td>
								<td>mr.hong</td>
								<td>홍길동</td>
								<td>데이터센터</td>
								<td>대외정보관리부</td>
								<td>과장</td>
								<td>2015-06-30 12:30:30</td>
								<td>2015-06-30 12:30:30</td>
							</tr>
							<tr>
								<td>753</td>
								<td>naveron115</td>
								<td>장나라</td>
								<td>데이터센터</td>
								<td>대외정보관리부</td>
								<td>과장</td>
								<td>2015-06-30 12:30:30</td>
								<td>2015-06-30 12:30:30</td>
							</tr>
							<tr>
								<td>754</td>
								<td>admin</td>
								<td>관리자</td>
								<td>데이터센터</td>
								<td>대외정보관리부</td>
								<td>과장</td>
								<td>2015-06-30 12:30:30</td>
								<td>2015-06-30 12:30:30</td>
							</tr>
						</tbody>
					</table>
					
					<!-- paging -->
					<div class="paging">
						<div>
							<a href="#none" class="first"></a>
							<a href="#none" class="prev"></a>
						</div>
						
						<div class="pl15 pr15">
							<u class="selected">1</u>
							<a href="#" class="page">2</a>
							<a href="#" class="page">3</a>
							<a href="#" class="page">4</a>
							<a href="#" class="page">5</a>
						</div>
						
						<div>
							<a href="#none" class="next"></a>
							<a href="#none" class="last"></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

  
<div class="contextMenu" id="contextFolder">
    <ul style="width: 160px;">
        <li id="createAuthGrp">
            <i class="fa fa-folder-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">접근권한 그룹 생성</span>
        </li>
        <li id="modifyAuthGrp">
            <i class="fa fa-pencil-square-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">접근권한 그룹 수정</span>
        </li>
        <li id="deleteAuthGrp">
            <i class="fa fa-trash-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">접근권한 그룹 삭제</span>
        </li>
        <li id="createAcctGrp">
            <i class="fa fa-pencil-square-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">계정그룹 생성</span>
        </li>
        <li id="modifyAcctGrp">
            <i class="fa fa-pencil-square-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">계정그룹 수정</span>
        </li>
        <li id="deleteAcctGrp">
            <i class="fa fa-trash-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">계정그룹 삭제</span>
        </li>
        <li id="createAcct">
            <i class="fa fa-trash-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">계정 생성</span>
        </li>
    </ul>
</div>


<%@ include file="../include/footer.jsp"%>