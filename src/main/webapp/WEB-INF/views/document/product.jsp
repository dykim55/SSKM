<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%
@SuppressWarnings("unchecked")
List<HashMap<String, Object>> ctgList = (List<HashMap<String, Object>>)request.getAttribute("ctgList");

String sCtgSct = request.getParameter("gubun");
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
    	
		$("#product-grid-div").load("/file_pane", {"parent": this.getAttribute('data-tt-id')}, function() {
	    	console.log(this.getAttribute('data-tt-id'));
		});
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
                	fileUpload(t.getAttribute('data-tt-id'));
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

function createFolder(parent) {
    DIALOG.Open().load("/product/create_folder", {gubun : "<%=sCtgSct%>"}, function() {
        CREATE_FOLDER.init($(this), parent);
    });
}

function fileUpload(parent) {
    DIALOG.Open().load("/product/upload_file", {}, function() {
    	UPLOAD_FILE.init($(this), parent);
    });
}

</script>

<div id="product-tree-div" style="background: #fff;max-width: 360px;padding: 20px;" onselectstart="return false" ondragstart="return false">

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

</div>

<div id="product-grid-div" style="background: #fff;width: 800px;padding: 20px;" onselectstart="return false" ondragstart="return false">
</div>
  
<div class="contextMenu" id="contextFolder">
    <ul style="width: 100px;">
        <li id="createFolder">
            <i class="fa fa-folder-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">폴더 생성</span>
        </li>
        <li id="modifyFolder">
            <i class="fa fa-pencil-square-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">폴더 수정</span>
        </li>
        <li id="deleteFolder">
            <i class="fa fa-trash-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">폴더 삭제</span>
        </li>
        <li id="fileUpload">
            <i class="fa fa-upload"></i>
            <span style="font-size:12px; font-family:맑은 고딕">파일 등록</span>
        </li>
    </ul>
</div>


<%@ include file="../include/footer.jsp"%>