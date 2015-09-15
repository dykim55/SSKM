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
	
    $("#product-tree").treetable({ expandable: true });
    
    $('#product-tree').treetable('expandAll');
    
    // Highlight selected row
    $("#product-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    });

    $("#product-tree .folder").each(function() {
	});
    
    function initContextMenu() {    
        jQuery("tr", "#product-tree").contextMenu('contextFolder', {
            bindings: {
                'createFolder': function(t) {
                	createFolder(t.getAttribute('data-tt-id'));
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

<table id="product-tree">
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
