<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import= "java.util.List
            , java.util.HashMap
            , com.cyberone.scourt.model.UserInfo
            , com.cyberone.scourt.utils.StringUtil"
    
%>

<%@ include file="../include/header.jsp"%>

<%

@SuppressWarnings("unchecked")
List<HashMap<String, Object>> subMenuList = (List<HashMap<String, Object>>)request.getAttribute("subMenuList");

@SuppressWarnings("unchecked")
HashMap<String, Object> sideMap = (HashMap<String, Object>)request.getAttribute("sideMap");

String sCtgSct = StringUtil.convertString(request.getAttribute("gubun"));
%>

<style type="text/css">
			
			.accordion {
				position: relative;
				margin: auto;
				width: 220px;
				top: 150px;
				-webkit-box-shadow: 5px 5px 60px 1px #000;
				-moz-box-shadow: 5px 5px 60px 1px #000;
				box-shadow: 5px 5px 60px 1px #000;
			}
			
			.accordionHeaders {
				background: rgb(111,119,130); /* Old browsers */
				background: -moz-linear-gradient(top,  rgba(111,119,130,1) 0%, rgba(81,88,98,1) 100%); /* FF3.6+ */
				background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(111,119,130,1)), color-stop(100%,rgba(81,88,98,1))); /* Chrome,Safari4+ */
				background: -webkit-linear-gradient(top,  rgba(111,119,130,1) 0%,rgba(81,88,98,1) 100%); /* Chrome10+,Safari5.1+ */
				background: -o-linear-gradient(top,  rgba(111,119,130,1) 0%,rgba(81,88,98,1) 100%); /* Opera 11.10+ */
				background: -ms-linear-gradient(top,  rgba(111,119,130,1) 0%,rgba(81,88,98,1) 100%); /* IE10+ */
				background: linear-gradient(top,  rgba(111,119,130,1) 0%,rgba(81,88,98,1) 100%); /* W3C */
				filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#6f7782', endColorstr='#515862',GradientType=0 ); /* IE6-9 */
				height: 35px;
				width: 220px;
				border: 1px solid #313842;
				overflow: auto;
				line-height: 35px;
				color: #fafdff;
				text-shadow: 1px 1px 3px #444;
				filter: dropshadow(color=#444, offx=1, offy=1);
				padding: 3px 0 3px 10px;
				position: relative;
				border-top: none;
				cursor: pointer;
			}
			
			.accordionHeaders:after {
				content: attr(data-amount);
				width: 30px;
				height: 25px;
				border-radius: 12.5px;
				border: 1px solid #48515a;
				display: none;
				position: absolute;
				right: 10px;
				top: 7px;
				line-height: 25px;
				text-align: center;
				background: rgb(70,76,88); /* Old browsers */
				background: -moz-linear-gradient(top,  rgba(70,76,88,1) 0%, rgba(76,84,95,1) 100%); /* FF3.6+ */
				background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(70,76,88,1)), color-stop(100%,rgba(76,84,95,1))); /* Chrome,Safari4+ */
				background: -webkit-linear-gradient(top,  rgba(70,76,88,1) 0%,rgba(76,84,95,1) 100%); /* Chrome10+,Safari5.1+ */
				background: -o-linear-gradient(top,  rgba(70,76,88,1) 0%,rgba(76,84,95,1) 100%); /* Opera 11.10+ */
				background: -ms-linear-gradient(top,  rgba(70,76,88,1) 0%,rgba(76,84,95,1) 100%); /* IE10+ */
				background: linear-gradient(top,  rgba(70,76,88,1) 0%,rgba(76,84,95,1) 100%); /* W3C */
				filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#464c58', endColorstr='#4c545f',GradientType=0 ); /* IE6-9 */
			}
			
			.accordionContent {
				background: #727882;
				height: 35px;
				width: 220px;
				line-height: 35px;
				position: relative;
				padding: 3px 0 3px 12px;
				border-bottom: 1px solid #989898;
				font-size: 9pt;
			}
			
			.accordionContent:after {
				content: attr(data-amount);
				width: 30px;
				height: 20px;
				border-radius: 12.5px;
				border: 1px solid #ebebed;
				display: none;
				position: absolute;
				right: 10px;
				top: 10px;
				line-height: 20px;
				text-align: center;
			}
			
			.accordionContent:first-child {
				-webkit-box-shadow: inset 0px 5px 5px -2px #c0bcbb;
				-moz-box-shadow: inset 0px 5px 5px -2px #c0bcbb;
				box-shadow: inset 0px 5px 5px -2px #c0bcbb;
			}
			
			.accordionContent span {
				padding-right: 10px;
			}
			
			.contentHolder {
				position: relative;
				display: none;
				overflow-x: hidden;
				width: 230px;
				padding-left: 1px;
			}
			
			.accordionHeaders.selected {
				background: rgb(64,193,235); /* Old browsers */
				background: -moz-linear-gradient(top,  rgba(64,193,235,1) 0%, rgba(40,154,216,1) 100%); /* FF3.6+ */
				background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(64,193,235,1)), color-stop(100%,rgba(40,154,216,1))); /* Chrome,Safari4+ */
				background: -webkit-linear-gradient(top,  rgba(64,193,235,1) 0%,rgba(40,154,216,1) 100%); /* Chrome10+,Safari5.1+ */
				background: -o-linear-gradient(top,  rgba(64,193,235,1) 0%,rgba(40,154,216,1) 100%); /* Opera 11.10+ */
				background: -ms-linear-gradient(top,  rgba(64,193,235,1) 0%,rgba(40,154,216,1) 100%); /* IE10+ */
				background: linear-gradient(top,  rgba(64,193,235,1) 0%,rgba(40,154,216,1) 100%); /* W3C */
				filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#40c1eb', endColorstr='#289ad8',GradientType=0 ); /* IE6-9 */

			}
												
</style>

<script type="text/javascript">

$(document).ready(function() {
	
	$('.accordionHeaders:first').addClass('selected').next('.contentHolder').slideDown();
	
	$('.accordionHeaders').click(function() {
		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
			$(this).next('.contentHolder').slideUp();
			return;
		}
		
		$('.accordionHeaders').removeClass('selected');
		$('.contentHolder').slideUp();
		$(this).addClass('selected').next('.contentHolder').slideToggle();
	});
	
    $(".left-tree").treetable({ expandable: true });
    
    $('.left-tree').treetable('expandAll');
    
    // Highlight selected row
    $(".left-tree tbody").on("mousedown", "tr", function() {
    	if (!$(this).hasClass("selected")) {
    	    $(".left-tree .selected").not(this).removeClass("selected");
    	    $(this).toggleClass("selected");
    	}
    	
		$("#product-grid-div").load("/file_pane", {"parent": this.getAttribute('data-tt-id')}, function() {
	    	console.log(this.getAttribute('data-tt-id'));
		});
    });

    $(".left-tree .folder").each(function() {
	});
    
    function initContextMenu() {    
        jQuery("tr", ".left-tree").contextMenu('contextFolder', {
            bindings: {
                'createFolder': function(tr) {
                	createFolder($(tr));
                },
                'fileUpload': function(tr) {
                	fileUpload($(tr));
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

function createFolder(tr) {
    DIALOG.Open().load("/files/create_folder", {gubun : "<%=sCtgSct%>"}, function() {
        CREATE_FOLDER.init($(this), tr);
    });
}

function fileUpload(tr) {
    DIALOG.Open().load("/files/file_upload", {}, function() {
    	FILE_UPLOAD.init($(this), tr);
    });
}

</script>



<% if (subMenuList.size() > 0) { %>
	
<%     for (HashMap<String, Object> map : subMenuList) { %>

			
			    <table class="left-tree">
			        <tbody>
			    <%  
			    	List<HashMap<String, Object>> ctgList = (List<HashMap<String, Object>>)sideMap.get(StringUtil.convertString(map.get("menuCd")));
			        if (ctgList.size() > 0) {
			           for (HashMap<String, Object> ctgMap : ctgList) { %>
			            <tr data-tt-id='<%=ctgMap.get("ctgId") %>' <% if ((Integer)ctgMap.get("ctgParent") > 0) { %> data-tt-parent-id='<%=ctgMap.get("ctgParent") %>' <% } %>>
			                <td>
			                    <span class='folder'><%=StringUtil.convertString(ctgMap.get("ctgNm")) %></span>
			                </td>
			            </tr>
			    <%     } 
			       } %>        
			        </tbody>
			    </table>
			

<%     } %>
</div>
<% } %>





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