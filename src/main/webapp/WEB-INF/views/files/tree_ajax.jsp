<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.sskm.model.UserInfo
            , com.cyberone.sskm.utils.StringUtil"
    
%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> ctgList = (List<HashMap<String, Object>>)request.getAttribute("ctgList");
String sSct = request.getParameter("gubun");
%>

<script type="text/javascript">

$(document).ready(function() {
	
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
    		$(".location .left").html($('.accordion').find('.accordionHeaders.ac_selected')[0].textContent.trim() + "&nbsp;&nbsp;>&nbsp;&nbsp;" + $(".left-tree:visible").treetable('pathName', $(".left-tree:visible").find(".selected").attr('data-tt-id')));	
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

});

</script>

<table class="left-tree">
    <tbody>
<%  
    if (ctgList.size() > 0) {
       for (HashMap<String, Object> ctgMap : ctgList) { 
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
		} 
   } %>        
    </tbody>
</table>
