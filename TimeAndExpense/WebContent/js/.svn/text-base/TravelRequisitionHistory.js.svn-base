		var store = null;
	function displayTravelRequisitionHistoryList() {
		var jsonResponseStr = dojo.byId('jsonResponse_HistoryHidden').value;
		var treqHistory = eval('(' + jsonResponseStr + ')');
		var resultsGridStore = new dojo.data.ItemFileReadStore(
							{data: {identifier: 'treqaIdentifier',
								items: treqHistory}
							}
						);
		dojo.forEach(treqHistory, function(item){
			if(item.revisionNo == undefined) item.revisionNo='';
			if(item.treqActionCode == undefined) item.treqActionCode='';
			if(item.comments == undefined) item.comments='';
			if(item.modifiedUserId == undefined) item.modifiedUserId='';
			if(item.modifiedDate == undefined) item.modifiedDate='';
		});
		/*dijit.byId('historyGrid').noDataMessage = "No Data Found.. Please modify your search criteria";*/
		dijit.byId('historyGrid').setStore(resultsGridStore);	
		dijit.byId('historyGrid').resize();
		dojo.connect(dijit.byId('historyGrid'),'onCellMouseOver', 'showToolTip');
		dojo.connect(dijit.byId('historyGrid'),'onCellMouseOut', 'hideToolTip');		
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