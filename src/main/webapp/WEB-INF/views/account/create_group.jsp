<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        import= "java.util.List
        , java.util.HashMap
        , java.util.Date
        , java.lang.Integer
        , com.cyberone.sskm.model.UserInfo
        , com.cyberone.sskm.model.AcctGrp
        , com.cyberone.sskm.utils.StringUtil"
%>

<%
UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

AcctGrp acctGrpInfo = (AcctGrp)request.getAttribute("acctGrpInfo");

@SuppressWarnings("unchecked")
List<HashMap<String, Object>> menuList = (List<HashMap<String, Object>>)request.getAttribute("menuList");

%>

<script type="text/javascript">

CREATE_GROUP = (function() {
	var _Dlg, _Parent;
	var _bProcessing = false;
	
    return {
    	auth: function() {
    		var menuCodes=',';
    		$(".board-insert input:checked").each(function() {
    			 menuCodes += $(this).attr('menu-id') + ",";
    		});
			return menuCodes;    		
    	},
        init: function(Dlg, t) {
        	_bProcessing = false;
        	_Dlg = Dlg;

        	_Parent = t.attr('data-tt-id');
        	
        	_Dlg.dialog({
                autoOpen: false, 
                resizable: false, 
                width: 1020,
                modal: true,
                title: <% if (acctGrpInfo.getAcctPrntCd() == 0) { %> "계정 그룹 등록" <% } else { %> "계정 그룹 수정" <% } %>,
                buttons: {
<% if (acctGrpInfo.getAcctPrntCd() == 0) { %> "등록" <% } else { %> "수정" <% } %> : function() {
    
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
	                            	if (data.status=="success") {
										$("#account-tree-div").load("/account/tree_ajax", {}, function() {
											$(this).find(".account-tree").treetable("reveal", data.id);
										});
										_Dlg.dialog("close");
	                            	} else {
	                            		_bProcessing = false;
	                            		_alert(data.message);	
	                            	}
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


$(document).ready(function() {

	$(".board-insert dd input:checkbox").on("change", function(e) {
		$(this).parent().siblings('dt').find('input').prop("checked", $(this).closest('dl').find('dd input:checked').length);
		
	});

	$(".board-insert dt input:checkbox").on("change", function(e) {
		$(this).parent().siblings('dd').find('input').prop("checked", $(this).is(":checked"));
	});
	
	$(".board-insert dl").each(function() {
		$(this).find('dt').find('input').prop("checked", $(this).find('dd input:checked').length);
	});
});


</script>

<div id="accountGroup">
	<div class="dia-insert">
		<table class="board-insert">
			<colgroup>
				<col width="10%">
				<col width="*">
				<col width="10%">
				<col width="15%">
			</colgroup>
			<tr>
				<th>계정그룹명</th>
				<td>
					<input type="text" class="important" name="group_name" id="group_name" value="<%=StringUtil.convertString(acctGrpInfo.getAcctGrpNm()) %>" alt="계정그룹명은">
				</td>
				<th>만든 사람</th>
				<td>
					<%=acctGrpInfo.getAcctPrntCd() == 0 ? userInfo.getAcct().getAcctNm() : StringUtil.convertString(acctGrpInfo.getRegrNm()) %>
				</td>
			</tr>
            <tr>
                <th>계정그룹 설명</th>
                <td class="left">
                    <input type="text" name="group_desc" id="group_desc" value="<%=StringUtil.convertString(acctGrpInfo.getAcctGrpDesc()) %>">
                </td>
				<th>만든 날짜</th>
				<td>
					<%=acctGrpInfo.getAcctPrntCd() == 0 ? StringUtil.convertDate(new Date(),"yyyy-MM-dd HH:mm:ss") : StringUtil.convertDate(acctGrpInfo.getRegDtime(),"yyyy-MM-dd HH:mm:ss") %>
				</td>
            </tr>
			<tr>
				<th>접근권한 설정</th>
				<td colspan="3">
					<div class="rows1">
						<dl style="width: 16%;">
							<dt><input type="checkbox" id="b0" menu-id="1000"><label for="b0">보안 관제/운영</label></dt>
							<dd><input type="checkbox" id="b1" menu-id="1001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("1001") >= 0) { %> checked <% } %>><label for="b1">업무 보고서</label></dd>
							<dd><input type="checkbox" id="b2" menu-id="1002" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("1002") >= 0) { %> checked <% } %>><label for="b2">오류 보고서</label></dd>
							<dd><input type="checkbox" id="b3" menu-id="1003" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("1003") >= 0) { %> checked <% } %>><label for="b3">보안성 검토 보고서</label></dd>
							<dd><input type="checkbox" id="b4" menu-id="1004" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("1004") >= 0) { %> checked <% } %>><label for="b4">보안 분석 보고서</label></dd>
							<dd><input type="checkbox" id="b5" menu-id="1005" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("1005") >= 0) { %> checked <% } %>><label for="b5">구성 관리</label></dd>
							<dd><input type="checkbox" id="b6" menu-id="1006" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("1006") >= 0) { %> checked <% } %>><label for="b6">모의 훈련</label></dd>
						</dl>
						<dl style="width: 18%;">
							<dt><input type="checkbox" id="c0" menu-id="2000"><label for="c0">보안 진단</label></dt>
							<dd><input type="checkbox" id="c1" menu-id="2001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("2001") >= 0) { %> checked <% } %>><label for="c1">보안 진단(센터)</label></dd>
							<dd><input type="checkbox" id="c2" menu-id="2002" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("2002") >= 0) { %> checked <% } %>><label for="c2">보안 진단(사업자)</label></dd>
							<dd><input type="checkbox" id="c3" menu-id="2003" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("2003") >= 0) { %> checked <% } %>><label for="c3">사법부 보안컨설팅</label></dd>
							<dd><input type="checkbox" id="c4" menu-id="2004" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("2004") >= 0) { %> checked <% } %>><label for="c4">사이버 보안의날</label></dd>
							<dd><input type="checkbox" id="c5" menu-id="2005" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("2005") >= 0) { %> checked <% } %>><label for="c5">보안점검 체크리스트</label></dd>
						</dl>
						<dl style="width: 19%;">
							<dt><input type="checkbox" id="d0" menu-id="3000"><label for="d0">정보보호 관리체계</label></dt>
							<dd><input type="checkbox" id="d1" menu-id="3001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("3001") >= 0) { %> checked <% } %>><label for="d1">정보보호 조직</label></dd>
							<dd><input type="checkbox" id="d2" menu-id="3002" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("3002") >= 0) { %> checked <% } %>><label for="d2">정보보호 관리체계 인증</label></dd>
							<dd><input type="checkbox" id="d3" menu-id="3003" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("3003") >= 0) { %> checked <% } %>><label for="d3">서비스 수준관리</label></dd>
							<dd><input type="checkbox" id="d4" menu-id="3004" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("3004") >= 0) { %> checked <% } %>><label for="d4">보안 운영 감리</label></dd>
							<dd><input type="checkbox" id="d5" menu-id="3005" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("3005") >= 0) { %> checked <% } %>><label for="d5">보안 교육</label></dd>
						</dl>
						<dl style="width: 17%;">
							<dt><input type="checkbox" id="e0" menu-id="4000"><label for="e0">정보보호 정책/지침</label></dt>
							<dd><input type="checkbox" id="e1" menu-id="4001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("4001") >= 0) { %> checked <% } %>><label for="e1">정보보호 관련 법규</label></dd>
							<dd><input type="checkbox" id="e2" menu-id="4002" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("4002") >= 0) { %> checked <% } %>><label for="e2">사이버 안전 메뉴얼</label></dd>
							<dd><input type="checkbox" id="e3" menu-id="4003" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("4003") >= 0) { %> checked <% } %>><label for="e3">정보보호 정책서</label></dd>
							<dd><input type="checkbox" id="e4" menu-id="4004" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("4004") >= 0) { %> checked <% } %>><label for="e4">정보보호 지침</label></dd>
							<dd><input type="checkbox" id="e5" menu-id="4005" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("4005") >= 0) { %> checked <% } %>><label for="e5">정보보호 가이드</label></dd>
						</dl>
						<dl style="width: 16%;">
							<dt><input type="checkbox" id="f0" menu-id="5000"><label for="f0">정보보호 동향</label></dt>
							<dd><input type="checkbox" id="f1" menu-id="5001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("5001") >= 0) { %> checked <% } %>><label for="f1">주간 보안 동향</label></dd>
							<dd><input type="checkbox" id="f2" menu-id="5002" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("5002") >= 0) { %> checked <% } %>><label for="f2">상황 전파문</label></dd>
						</dl>
						<dl style="width: 16%;">
							<dt><input type="checkbox" id="g0" menu-id="6000"><label for="g0">보안 뉴스</label></dt>
							<dd><input type="checkbox" id="g1" menu-id="6001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("6001") >= 0) { %> checked <% } %>><label for="g1">일일 보안 뉴스</label></dd>
						</dl>
					</div>
					
					<div class="rows2">
						<dl>
							<dt><input type="checkbox" id="a0" menu-id="A000"><label for="a0">공지사항</label></dt>
							<dd><input type="checkbox" id="a1" menu-id="A001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("A001") >= 0) { %> checked <% } %>><label for="a1">공지사항</label></dd>
						</dl>
						<dl>
							<dt><input type="checkbox" id="h0" menu-id="B000"><label for="h0">인수 인계</label></dt>
							<dd><input type="checkbox" id="h1" menu-id="B001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("B001") >= 0) { %> checked <% } %>><label for="h1">인수 인계</label></dd>
						</dl>
						<dl>
							<dt><input type="checkbox" id="i0" menu-id="C000"><label for="i0">일정 관리</label></dt>
							<dd><input type="checkbox" id="i1" menu-id="C001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("C001") >= 0) { %> checked <% } %>><label for="i1">일정 관리</label></dd>
						</dl>
						<dl>
							<dt><input type="checkbox" id="j0" menu-id="Z000"><label for="j0">계정 관리</label></dt>
							<dd><input type="checkbox" id="j1" menu-id="Z001" <% if (!StringUtil.isEmpty(acctGrpInfo.getAcctPrmsMenus()) && acctGrpInfo.getAcctPrmsMenus().indexOf("Z001") >= 0) { %> checked <% } %>><label for="j1">계정 관리</label></dd>
						</dl>
					</div>
				</td>
			</tr>
		</table>
  	</div>
</div>

