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

    // Drag & Drop Example Code
    $("#product-tree .file, #product-tree .folder").draggable({
		helper: "clone",
		opacity: .75,
		refreshPositions: true, // Performance?
		revert: "invalid",
		revertDuration: 300,
		scroll: true
    });

    $("#product-tree .folder").each(function() {
        $(this).parents("#product-tree tr").droppable({
			accept: ".file, .folder",
			drop: function(e, ui) {
				var droppedEl = ui.draggable.parents("tr");
				$("#product-tree").treetable("move", droppedEl.data("ttId"), $(this).data("ttId"));
			},
			hoverClass: "accept",
			over: function(e, ui) {
				var droppedEl = ui.draggable.parents("tr");
				if(this != droppedEl[0] && !$(this).is(".expanded")) {
					$("#product-tree").treetable("expandNode", $(this).data("ttId"));
				}
			}
		});
	});
    
    $("form#reveal").submit(function() {
		var nodeId = $("#revealNodeId").val();
	    try {
	        $("#product-tree").treetable("reveal", nodeId);
	    } catch(error) {
	        alert(error.message);
	    }
	    return false;
	});

    function initContextMenu() {    
        jQuery("tr", "#product-tree").contextMenu('contextFolder', {
            bindings: {
                'createFolder': function(t) {
                	console.log(t.getAttribute('data-tt-id'));
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

function createFolder(parent) {
    console.log('createFolder');
    DIALOG.Open().load("/product/create_folder", {}, function() {
        CREATE_FOLDER.init($(this), parent);
    });

}

function fileUpload() {
    console.log('fileUpload');
}

function searchSub() {
    document.searchForm.submit();
}

</script>

<div id="main" style="background: #fff;max-width: 360px;padding: 20px;" onselectstart="return false" ondragstart="return false">

    <table id="product-tree">
        <tbody>
    <% if (ctgList.size() > 0) {
           for (HashMap<String, Object> map : ctgList) { %>
            <tr data-tt-id='<%=map.get("ctgId") %>' data-tt-parent-id='<%=map.get("ctgParent") %>' >
                <td>
                    <span class='folder'><%=StringUtil.convertString(map.get("ctgNm")) %></span>
                </td>
            </tr>
    <%     } 
       } %>        
        </tbody>
    </table>

</div>
  
<div class="contextMenu" id="contextFolder">
    <ul style="width: 100px;">
        <li id="createFolder">
            <i class="fa fa-folder-o"></i>
            <span style="font-size:12px; font-family:맑은 고딕">폴더 생성</span>
        </li>
        <li id="fileUpload">
            <i class="fa fa-upload"></i>
            <span style="font-size:12px; font-family:맑은 고딕">파일 등록</span>
        </li>
    </ul>
</div>


<%@ include file="../include/footer.jsp"%>