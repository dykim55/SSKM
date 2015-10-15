<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , java.util.Date
        , com.cyberone.sskm.model.UserInfo
        , com.cyberone.sskm.utils.StringUtil"
%>

<%
	UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

    @SuppressWarnings("unchecked")
    HashMap<String, Object> category = (HashMap<String, Object>)request.getAttribute("category");
    if (StringUtil.isEmpty(category)) {
    	category = new HashMap<String, Object>(); 
    }
%>

<script type="text/javascript">

CREATE_FOLDER = (function() {
	var _Dlg;
	var _bProcessing = false;
	
    return {
        init: function(Dlg, loc) {
        	_bProcessing = false;
        	_Dlg = Dlg;
        	
        	_Dlg.find("#path_name").html($(".location .left").html());
        	
        	var t = $('.accordion').find('.accordionHeaders.ac_selected')[0].textContent.trim();
        	if ( $(".left-tree .selected").closest("table").length > 0) {
        		t += "&nbsp;&nbsp;>&nbsp;&nbsp;" + $(".left-tree .selected").closest("table").treetable('pathName', $(".left-tree .selected").attr('data-tt-id'))
        	}
        	_Dlg.closest('.ui-dialog').find('.ui-dialog-title').html(t);
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 600,
                modal: true,
                /*title: <% if (!category.containsKey("ctgId")) { %> "폴더 생성" <% } else { %> "폴더 수정" <% } %>,*/
                buttons: {
<% if (!category.containsKey("ctgId")) { %> "생성" <% } else { %> "수정" <% } %> : function() {                	
					  	var flag=false;
						_Dlg.find(".important:visible").each(function() {
					        if ($(this).val().length == 0) {
					            $(this).select();
					            flag = true;
					            _alert($(this).attr("alt")+" 필수 입력값입니다.");
					            return false;
					        }
					    });
					    if (flag) return false;

					    if (!_bProcessing) {
					    	_bProcessing = true;
	                        $.ajax({
	                            url: "/files/folder_register",
	                            dataType: 'json',
	                            data : {
	                            	id: '<%=StringUtil.convertString(category.get("ctgId")) %>',
	                            	gubun: $('.accordion').find('.accordionHeaders.ac_selected').attr("ref-sct"), 
	                            	parent: $(".left-tree .selected").attr('data-tt-id') ? $(".left-tree .selected").attr('data-tt-id') : $('.accordion').find('.accordionHeaders.ac_selected').attr("ref-id"), 
	                            	name: $("#folder_name").val(),
	                            	desc: $("#folder_desc").val()
	                            },
	                            success: function(data, text, request) {
	                            	$('.accordion').find('.accordionHeaders.ac_selected').next().load("/files/tree_ajax", {"gubun": data.gubun}, function() {
	                            		try { $(this).find(".left-tree").treetable("reveal", data.parent); } catch(e) {}
									});
									_Dlg.dialog("close");
	                            }
	                        });
					    }                    	
                    },
                    "취소": function() {
                        $(this).dialog("close");
                    }
                },
                close: function( event, ui ) {
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
        }
    };
    
})();

</script>

<div id="dir">
	<div class="dia-insert">
		<table class="board-insert">
			<colgroup>
				<col width="100">
				<col width="*">
			</colgroup>
			<tr>
				<th>만든 사람</th>
				<td>
					<%=category.containsKey("ctgId") ? StringUtil.convertString(category.get("regrNm")) : userInfo.getAcct().getAcctNm() %>
				</td>
				<th>만든 날짜</th>
				<td>
					<%=category.containsKey("ctgId") ? StringUtil.convertDate(category.get("regDtime"),"yyyy-MM-dd HH:mm:ss") : StringUtil.convertDate(new Date(),"yyyy-MM-dd HH:mm:ss") %>
				</td>
			</tr>
			<tr>
				<th>폴더명</th>
				<td colspan="3">
					<input type="text" class="important" name="folder_name" id="folder_name" value="<%=StringUtil.convertString(category.get("ctgNm")) %>" alt="폴더명은 ">
				</td>
			</tr>
			<tr>
				<th>폴더 설명</th>
				<td colspan="3">
					<textarea name="folder_desc" id="folder_desc"><%=StringUtil.convertString(category.get("ctgDesc")) %></textarea>
				</td>
			</tr>
		</table>
	</div>
</div>
