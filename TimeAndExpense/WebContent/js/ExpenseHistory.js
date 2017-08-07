		var store = null;
	function displayExpenseHistoryList() {
		var jsonResponseStr = dojo.byId('jsonResponse_HistoryHidden').value;
		var expHistory = eval('(' + jsonResponseStr + ')');
		var resultsGridStore = new dojo.data.ItemFileReadStore(
							{data: {identifier: 'expaIdentifier',
								items: expHistory}
							}
						);
		dojo.forEach(expHistory, function(item){
			if(item.revisionNo == undefined) item.revisionNo='';
			if(item.expActionCode == undefined) item.expActionCode='';
			if(item.comments == undefined) item.comments='';
			if(item.modifiedUserId == undefined) item.modifiedUserId='';
			if(item.modifiedDate == undefined) item.modifiedDate='';
		});
		/*dijit.byId('historyGrid').noDataMessage = "No Data Found.. Please modify your search criteria";*/
		dijit.byId('historyGrid').setStore(resultsGridStore);	
		dijit.byId('historyGrid').resize();
		dojo.connect(dijit.byId('historyGrid'),'onCellMouseOver', 'showToolTip');
		dojo.connect(dijit.byId('historyGrid'),'onCellMouseOut', 'hideToolTip');
		var treqId = dojo.byId(treqEventId).innerHTML;
		if (parseInt(treqId ) <= 0)
			dojo.byId('spanTreqEventId').style.display = "none";
		

		var cloneExpenseEventId = dojo.byId('hrefCloneExpenseId').innerHTML;
		
		if (parseInt(cloneExpenseEventId) <= 0)		
			dojo.byId('spanCloneExpense').style.display = "none";
		
	}
	function showToolTip(event){
		
		var historyGrid =  dijit.byId('historyGrid');
		var comments = historyGrid._by_idx[event.rowIndex].item.comments; 
		if(event.cellIndex==2){
			if (comments != undefined && trimStr(comments[0]) != ""){
				var msg = comments;			
    			dijit.showTooltip(msg, event.cellNode);
	   		}
		}
	}

	function hideToolTip (e) {
		dijit.hideTooltip(e.cellNode);
	}