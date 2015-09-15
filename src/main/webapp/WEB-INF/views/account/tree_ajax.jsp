<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

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

</script>

<table id="account-tree">
    <tbody>
<% if (acctList.size() > 0) {
       for (HashMap<String, Object> map : acctList) { %>
        <tr data-tt-id='<%=map.get("acctGrpCd") %>' <% if ((Integer)map.get("acctPrntCd") > 0) { %> data-tt-parent-id='<%=map.get("acctPrntCd") %>' <% } %>>
            <td>
                <span class='folder'><%=StringUtil.convertString(map.get("acctGrpNm")) %></span>
            </td>
        </tr>
<%     } 
   } %>        
    </tbody>
</table>
