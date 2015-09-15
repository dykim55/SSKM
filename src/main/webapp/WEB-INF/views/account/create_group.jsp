<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , java.lang.Integer
        , com.cyberone.scourt.model.AcctGrp
        , com.cyberone.scourt.utils.StringUtil"
%>

<%
@SuppressWarnings("unchecked")
AcctGrp acctGrpInfo = (AcctGrp)request.getAttribute("acctGrpInfo");

List<HashMap<String, Object>> menuList = (List<HashMap<String, Object>>)request.getAttribute("menuList");

%>

<script type="text/javascript">

CREATE_GROUP = (function() {
	var _Dlg, _Parent;
	var bProcessing = false;
	
    return {
    	auth: function() {
    		var menuCodes='';
    		$("#menu-tree").find("input:checked").each(function() {
    			 menuCodes += $(this).closest('tr').attr('data-tt-id') + ",";
    		});
			return menuCodes;    		
    	},
        init: function(Dlg, tr) {
        	_Dlg = Dlg;

        	_Parent = tr.attr('data-tt-id');
        	
        	$("#menu-tree").treetable({ expandable: true });
        	
            $("#menu-tree tbody").on("mousedown", "tr", function() {
            	if (!$(this).hasClass("selected")) {
            	    $(".selected").not(this).removeClass("selected");
            	    $(this).toggleClass("selected");
            	}
            });
            $("#menu-tree tbody").on("click", "input:checkbox", function() {
            	var tt = $(this).closest('tr').attr('data-tt-parent-id');
            	if (tt == "0") {
            		$("[data-tt-parent-id="+$(this).closest('tr').attr('data-tt-id')+"]").find("input").prop("checked", $(this).is(':checked'));	
            	} else {
            		if ($("[data-tt-parent-id="+$(this).closest('tr').attr('data-tt-parent-id')+"]").find(':checked').length > 0) {
            			$("[data-tt-id="+$(this).closest('tr').attr('data-tt-parent-id')+"]").find("input").prop("checked", true);
            		} else {
            			$("[data-tt-id="+$(this).closest('tr').attr('data-tt-parent-id')+"]").find("input").prop("checked", false);
            		}
            	}
            });
            
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                title: <% if (acctGrpInfo.getAcctPrntCd() == 0) { %> "계정 그룹 등록" <% } else { %> "계정 그룹 수정" <% } %>,
                buttons: {
<% if (acctGrpInfo.getAcctPrntCd() == 0) { %> "등록" <% } else { %> "수정" <% } %> : function() {
                    	
                        $.ajax({
                            url: "/account/register_acct_group",
                            dataType: 'json',
                            data : { 
                            	id : <%=acctGrpInfo.getAcctGrpCd()%>,
                            	prts : _Parent,
                            	name : $("#group_name").val(), 
                            	desc : $("#group_desc").val(),
                            	auth : CREATE_GROUP.auth()
                            },
                            success: function(data, text, request) {
								$("#account-tree-div").load("/account/tree_ajax", {}, function() {
								    $("#account-tree tr").each(function() {
								    	console.log($(this)[0].getAttribute('data-tt-id'));
								    	if ($(this)[0].getAttribute('data-tt-id')==data.id) {
								    	    $(".selected").not(this).removeClass("selected");
								    	    $(this).addClass("selected");
								    		return false;
								    	}
									});
								});
								_Dlg.dialog("close");
                            }
                        });

                    },
                    "취소": function() {
                        $(this).dialog("close");
                    }
                },
                close: function( event, ui ) {
                	bProcessing = false;
                    $(this).children().remove();
                },
                open: function( event, ui ) {
                    var t = $(this).parent(), w = window;
                    t.offset({top: (w.innerHeight / 2) - (t.height() / 2),left: (w.innerWidth / 2) - (t.width() / 2)});
                    $(this).attr('tabindex',-1).css('outline',0).focus();
                }            
            });
            _Dlg.dialog("open");    
            _Dlg.dialog('option', 'position', 'center');
            
            $('#menu-tree').treetable('expandAll');
        }
    };
    
})();



</script>

<div class="contents">
    <div class="dialogContent">
        <div class="contents">
            <table class="popTable mt20" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="160"><col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>계정그룹 명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="group_name" id="group_name" style="width:200px;" value="<%=StringUtil.convertString(acctGrpInfo.getAcctGrpNm()) %>">
                        </td>
                    </tr>
                    <tr>
                        <th>계정그룹 설명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="group_desc" id="group_desc" style="width:200px;" value="<%=StringUtil.convertString(acctGrpInfo.getAcctGrpDesc()) %>">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        
		<div id="menu-tree-div" style="background: #fff;max-width: 360px;padding: 20px; overflow-y:auto; height: 300px;" onselectstart="return false" ondragstart="return false">
		
		    <table id="menu-tree">
		        <tbody>
		    <% if (menuList.size() > 0) {
		           for (HashMap<String, Object> map : menuList) { %>
		            <tr data-tt-id='<%=map.get("menuCd") %>' data-tt-parent-id='<%=map.get("prtsCd") %>'>
		                <td>
		                    <span class='folder'><input type="checkbox" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf(StringUtil.convertString(map.get("menuCd"))) >= 0) { %> checked <% } %>><%=StringUtil.convertString(map.get("menuNm")) %></span>
		                </td>
		            </tr>
		    <%     } 
		       } %>        
		        </tbody>
		    </table>
		
		</div>
        
    </div>
</div>


<!-- 
<div class="contents">
    <div class="dialogContent">
        <div class="contents">
            <table class="popTable mt20" cellpadding="0" cellspacing="0">
                <colgroup>
                    <col width="160"><col width="*">
                </colgroup>
                <tbody>
                    <tr>
                        <th>계정 그룹명</th>
                        <td class="left">
                            <input type="text" class="normal focus_e" name="group_name" id="group_name" style="width:200px;">
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

 -->