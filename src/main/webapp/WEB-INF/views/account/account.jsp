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
	
    $("#account-tree").treetable({ expandable: true });
    
    $('#account-tree').treetable('expandAll');
    
    // Highlight selected row
    $("#account-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    	
    	console.log($(this).attr('data-tt-id'));
    	console.log($(this));
    	
		$("#account-grid-div").load("/account_ajax", {"grpCd": $(this).attr('data-tt-id'), "prtsCd": $(this).attr('data-tt-parent-id')}, function() {
	    	console.log(this.getAttribute('data-tt-id'));
		});
    });

    $("#account-tree .folder").each(function() {
	});
    
    function initContextMenu() {    
        jQuery("tr", "#account-tree").contextMenu('contextFolder', {
            bindings: {
                'createAuthGrp': function(t) {
                	createAuthGrp($(t));
                },
                'modifyAuthGrp': function(t) {
                	modifyAuthGrp($(t));
                },
                'createAcctGrp': function(t) {
	               	createAcctGrp($(t));
                },
                'modifyAcctGrp': function(t) {
                	modifyAcctGrp($(t));
                },
                'createAcct': function(t) {
	               	createAcct($(t));
                },
                'fileUpload': function(t) {
                	fileUpload($(t));
                }
            },
            onContextMenu : function(e, menu) {
               return true;                                    
            },
            onShowMenu: function(e, menu) {
            	 var level = $('#account-tree').treetable('node', e.target.closest('tr').getAttribute('data-tt-id')).level();
            	 if (level == 0) {
            		 $('#modifyAuthGrp', menu).remove();
            		 $('#deleteAuthGrp', menu).remove();
            		 $('#createAcctGrp', menu).remove();
            		 $('#modifyAcctGrp', menu).remove();
            		 $('#deleteAcctGrp', menu).remove();
            		 $('#createAcct', menu).remove();
            	 }
            	 if (level == 1) {
                     $('#createAuthGrp', menu).remove();
                     
                     $('#modifyAcctGrp', menu).remove();
                     $('#deleteAcctGrp', menu).remove();
                     $('#createAcct', menu).remove();
            	 }
            	 if (level == 2) {
            		 $('#createAuthGrp', menu).remove();
            		 $('#modifyAuthGrp', menu).remove();
            		 $('#deleteAuthGrp', menu).remove();
            		 $('#createAcctGrp', menu).remove();
            	 }
                return menu;
            }        
        });             
    } 
    
    initContextMenu();
});

function createAuthGrp(t) {
    DIALOG.Open().load("/account/create_auth", {auth_grp : t[0].getAttribute('data-tt-id')}, function() {
    	CREATE_AUTH_GROUP.init($(this), t);
    });
}

function modifyAuthGrp(t) {
    DIALOG.Open().load("/account/create_auth", {auth_grp : t[0].getAttribute('data-tt-id')}, function() {
    	CREATE_AUTH_GROUP.init($(this), t);
    });
}

function createAcctGrp(t) {
    DIALOG.Open().load("/account/create_group", {}, function() {
    	CREATE_GROUP.init($(this), t);
    });
}

function modifyAcctGrp(t) {
    DIALOG.Open().load("/account/create_group", {acct_grp : t.attr('data-tt-id')}, function() {
    	CREATE_GROUP.init($(this), t);
    });
}

function createAcct(t) {
    DIALOG.Open().load("/account/create_account", {auth_grp : t[0].getAttribute('data-tt-parent-id'), acct_grp : t[0].getAttribute('data-tt-id')}, function() {
    	CREATE_ACCOUNT.init($(this), t);
    });
}

function fileUpload(parent) {
}

</script>

<div id="account-tree-div" style="background: #fff;max-width: 360px;padding: 20px;" onselectstart="return false" ondragstart="return false">

    <table id="account-tree">
        <tbody>
    <% if (acctList.size() > 0) {
           for (HashMap<String, Object> map : acctList) { %>
            <tr level='<%=map.get("level") %>' data-tt-id='<%=map.get("acctGrpCd") %>' <% if ((Integer)map.get("acctPrntCd") > 0) { %> data-tt-parent-id='<%=map.get("acctPrntCd") %>' <% } %>>
                <td>
                    <span class='folder'><%=StringUtil.convertString(map.get("acctGrpNm")) %></span>
                </td>
            </tr>
    <%     } 
       } %>        
        </tbody>
    </table>

</div>

<div id="account-grid-div" style="background: #fff;width: 800px;padding: 20px;" onselectstart="return false" ondragstart="return false">
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