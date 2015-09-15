<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> ctgList = (List<HashMap<String, Object>>)request.getAttribute("ctgList");
%>

<script type="text/javascript">

$(document).ready(function() {
	
    $(".left-tree").treetable({ expandable: true });
    
    $('.left-tree').treetable('expandAll');
    
    // Highlight selected row
    $(".left-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    		$(".left-tree .selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    });

    $(".left-tree .folder").each(function() {
	});
    
    function initContextMenu() {    
        jQuery("tr", ".left-tree").contextMenu('contextFolder', {
            bindings: {
                'createFolder': function(t) {
                	createFolder($(t));
                },
                'fileUpload': function(t) {
                	fileUpload();
                }
            },
            onContextMenu : function(e, menu) {
               return true;                                    
            },
            onShowMenu: function(e, menu) {
                return menu;
            }        
        });             
    } 
    initContextMenu();
    
});

</script>

<table id="left-tree">
    <tbody>
<% if (ctgList.size() > 0) {
       for (HashMap<String, Object> map : ctgList) { %>
        <tr data-tt-id='<%=map.get("ctgId") %>' <% if ((Integer)map.get("ctgParent") > 0) { %> data-tt-parent-id='<%=map.get("ctgParent") %>' <% } %>>
            <td>
                <span class='folder'><%=StringUtil.convertString(map.get("ctgNm")) %></span>
            </td>
        </tr>
<%     } 
   } %>        
    </tbody>
</table>
