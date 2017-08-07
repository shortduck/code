var timeOutMilliSeconds = 10000;//1000 Milliseconds = 1 Second

function formatGridLink(value){
	return value.replace(/&lt;/g, '<');
}

function rightAlignGridCellText(value){
	var cellHtml = '<div style="text-align:right;">'+value+'</div>';
	return cellHtml.replace(/&lt;/g, '<');
}
function centerAlignGridCellText(value){
	var cellHtml = '<div style="text-align:center;">'+value+'</div>';
	return cellHtml.replace(/&lt;/g, '<');
}


function rightAlignGridCellNumber(value){
	var cellHtml = value;
	
	var number = dojo.number.parse(value);
	if(!isNaN(number)){
		var formattedNo = dojo.number.format(value,{places:2});
		cellHtml = (number < 0) ? 
						'<div style="text-align:right;color:red">'+formattedNo+'</div>'
						:'<div style="text-align:right;">'+formattedNo+'</div>';
	}
	
	return cellHtml.replace(/&lt;/g, '<');
}
function formatCurrency(num) {
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
		num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	cents = num%100;
	num = Math.floor(num/100).toString();
	if(cents<10)
		cents = "0" + cents;
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3))+','+
		num.substring(num.length-(4*i+3));
	return (((sign)?'':'-') + num + '.' + cents);
}

function formatCurrencyWithNoDollarSign(value) {
	var cellHtml = "<span style='float:right'>" + formatCurrency(value) + "</span>";
	return cellHtml.replace(/&lt;/g, '<');
}

/* Returns the position of the element in array. Returns -1 if element not present. */
function findElementPositionInArray(a, obj){
	for(var i = 0; i < a.length; i++) {
	    if(a[i] == obj) return i;
	}
	return -1;
}
/* Used to call PreviousExpenseRevision or NextExpenseRevision to get Prev/Next Revision  */
function handlePrevNext(from,id,action){
	dojo.byId(id).value = from;
	dojo.byId(from).action  = action;
	dojo.byId(from).submit();
}

/**
 * Used for character count in text areas such as comments or reason fields
 * @return
 */

function processTextFieldCounters(){
	dojo.query(".counter").forEach(function(node, index, arr){ 
		
		var charLimit = 255;
		dojo.forEach(node.className.split(/\s/), function(classEL){
			if(classEL.indexOf('charLimit.') > -1){
				charLimit = parseInt(classEL.substring(classEL.indexOf('.')+1));
			}
		});
	
		dojo.query('.count', node.parentNode)[0].innerHTML = charLimit - node.value.length;
	});

	dojo.query(".counter").onkeyup(function(e){
		counterUpdate(e);
	});
	dojo.query(".counter").onchange(function(e){
		counterUpdate(e);
	});
}

function counterUpdate(e){
		var inputEL = e.target;
		var countEL = dojo.query('.count', e.target.parentNode)[0];

		var charLimit = 255;
		dojo.forEach(inputEL.className.split(/\s/), function(classEL){
			if(classEL.indexOf('charLimit.') > -1){
				charLimit = parseInt(classEL.substring(classEL.indexOf('.')+1));
			}
		});

		var charsLeft = charLimit - inputEL.value.length;
		countEL.innerHTML = charsLeft;
		(charsLeft < 0 ) ? countEL.style.color = 'red' : countEL.style.color = 'gray';
}

function isNumeric(strString)
//  check for valid numeric strings	
{
var strValidChars = "0123456789";
var strChar;
var blnResult = true;

if (strString.length == 0) return false;

//  test strString consists of valid characters listed above
for (i = 0; i < strString.length && blnResult == true; i++)
   {
   strChar = strString.charAt(i);
   if (strValidChars.indexOf(strChar) == -1)
      {
      blnResult = false;
      }
   }
return blnResult;
}

function isDecimal(element)
// tests if the input data represents a valid decimal amount
{
return (/^\d+(\.\d)?$/.test(element.value) || /^\d+(\.\d\d)?$/.test(element.value)
		 || /^(\.\d\d)?$/.test(element.value) || /^\d+(\.)?$/.test(element.value));
}

function isNotNull(obj){
	return (obj != null)?true:false;
}

function helpFunction(type) {
	if (type == "contactus") {
		window.open((document.getElementById("contactusurl").value));
	} else if (type == "faq") {
		window.open((document.getElementById("faqurl").value));	
	} else if (type == "training") {
		window.open((document.getElementById("trainingurl").value));
	}
}

function resizeGrid() {
	var tables = document.getElementsByTagName("Div");
	for ( var i = 0; i < tables.length; i++) {
		var table = dijit.byId(tables[i].id);
		if (table != undefined) {
			if (table.declaredClass == 'dojox.grid.EnhancedGrid') {
				table.resize();
				table.update();
			}
		}
	}
}

/**
 * Adds or removes a transaction from the approval queue
 * @param selectedCell
 * @return
 */
function getCheckedRows(selectedCell){

   	if (!selectedCell.checked){
   		removeFromApprovalQueue(selectedCell);   	
   		return false;
   	}
  		
	var grid = dijit.byId('resultsGrid');
	var queryString;
	               grid.store.fetch({
                  			query:{requestId: selectedCell.id},
                  		    onError: function(){},
                  		    onComplete: function(items, request){                
                  		    	dojo.forEach(items, function(item){
									queryString = item;
                  		    	});
                  		    }
                  		});
	
	var nextTran = {};
	nextTran.department = queryString.department[0];
	nextTran.agency = queryString.agency[0];
	nextTran.tku =  queryString.tku[0];
	nextTran.empIdentifier = queryString.empIdentifier[0];
	nextTran.apptIdentifier = queryString.apptIdentifier[0];
	nextTran.requestId =  queryString.requestId[0];
	nextTran.masterId =  queryString.masterId[0];
	nextTran.transactionType =  queryString.transactionType[0];
	
	dojo.xhrPost({
		url: 'ApprovalQueueAction.action',
		content: nextTran,
		handleAs: "json",
		handle: function(data,args){
			if(typeof data == "error"){
				//if errors, do not pursue the effect of call!
				console.warn("error!",args);
			}else{
				//alert('added');
			}
		}
	});
}
	
function removeFromApprovalQueue (selectedCell){
		dojo.xhrPost({
		url: 'RemoveApprovalTransaction.action',
		content: {'requestId': selectedCell.id},
		handleAs: "json",
		handle: function(data,args){
			if(typeof data == "error"){
				//if errors, do not pursue the effect of call!
				console.warn("error!",args);
			}
		}
	});
}

function clearApprovalQueue (){
	// clear approval queue
	dojo.xhrPost({
	url: 'ClearApprovalQueue.action',
	handleAs: "json",
	handle: function(data,args){
		if(typeof data == "error"){
			//if errors, do not pursue the effect of call!
			console.warn("error!",args);
		}
	}
});
}

function loadApprovalPage(moduleId){
	// reset form status, in case comments were added before approval
	formModified = false;
	// launch approval page
	if (moduleId == 'APRW003'){
		window.location = "ManagerApprove.action?moduleId=APRW003";
	} else {
		window.location = "StateWideApproval.action?moduleId=APRW004";
	}
}
/**
 * Returns the difference between 2 Calendar dates
 * @param d1
 * @param d2
 * @return
 */
function dateDifferenceInDays (d1, d2){
	var t2 = new Date(d2).getTime();
    var t1 = new Date(d1).getTime();

    return parseInt((t2-t1)/(24*3600*1000));
}
