<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!-- Included the dojoInclude.jsp as a part of Dojo 1.6.1 upgrade : KP du --> 
<jsp:include page="/jsp/dojoInclude.jsp"></jsp:include>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/CodingBlock.js"></script>

<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.form.ComboBox");
	dojo.require("dijit.form.CheckBox"); 
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit.Tooltip"); //kp
	 
	
	
</script>
</head>

<script type="text/javascript">
	dojo.addOnLoad(function()
	{
	createTable();
	setAttributes();
	});
	
	setAttributes = function(){
    dojo.attr('pc1_1', {
              tabIndex: 1
               
    });
    dojo.attr('ay_1', {
              tabIndex: 2 
    });
      dojo.attr('index_1', {
              tabIndex: 3  
    });
         dojo.attr('pca_1', {
              tabIndex: 4 
    });
           dojo.attr('grant_1', {
              tabIndex: 5 
    });
         dojo.attr('grantPhase_1', {
              tabIndex: 6  
    });
         dojo.attr('ag1_1', {
              tabIndex: 7 
    });
     dojo.attr('project_1', {
              tabIndex: 8 
    });
     dojo.attr('projectPhase_1', {
              tabIndex: 9  
    });
      dojo.attr('ag2_1', {
              tabIndex: 9  
    });
      dojo.attr('ag3_1', {
              tabIndex: 9  
    });
    dojo.attr('multi_1', {
              tabIndex: 9  
    });
    dojo.attr('std_1', {
              tabIndex: 9  
    });
}
	</script>
<body>

<div style="display:none">
<s:textfield theme="simple" id="no_of_coding_blocks_hidden" value="%{noOfCodingBlocks}"/>
<s:textfield theme="simple" id="cb_options_hidden" value="%{cbOptions}"/>
<s:textfield theme="simple" id="json_result_hidden" value="%{result}"/>
<s:textfield theme="simple" id="selected_Coding_Block_data" value="%{selectedCodingBlockData}"/>
</div>

<div id="dept_tab" class="tundra">

<form id="cb_form" action="CodingBlockAction.action" method="post" onsubmit="return submitUsingAjax(this);">
<table id="cbTable">
</table>
<input type="submit" value="Submit" />
</form>
</div>
</body>
</html>
