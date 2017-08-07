var numOfElements = document.all.length;

for(var i=0;i<= numOfElements; i++)
{
var node = document.all[i];
if (node != null) node.title = "";
}

/**
 * date check for the reports 
 * dates should be equal to or later than
 * The value of system code TE01
 * value='1/01/2010'
 */

function validTeStDt(f_date)
{
	
	if (f_date.value.length == 0 )
		return true;
	
	var compareDT='01/01/2010'; 
	var sysDT = Date.parse(compareDT);
	var tDate = Date.parse(f_date.value); 
	if(sysDT.compareTo(tDate)>0)
		{		 
		return false;
		}else
			{
			return true;
			}
	
}

//check presence of both expense from & to dates
function validateExpenseDate(errorMsg){

	var expenseFromDate = dojo.byId('f_fromDateField').value;
	var expenseToDate = dojo.byId('f_toDateField').value;
	
	if((expenseFromDate=='' && expenseToDate != '')){
		errorMsg.msg = "Please select Expense 'From' date";
		return (errorMsg.msg.length >0)? false: true;	
	}else if((expenseToDate=='' && expenseFromDate != '')){
		errorMsg.msg = "Please select Expense 'To' date";
		return (errorMsg.msg.length >0)? false: true;
	}
	
	if( !validTeStDt(dojo.byId('f_fromDateField'))){
  		errorMsg.msg = "Expense From date cannot be before 01/01/2010 ";
		return (errorMsg.msg.length >0)? false: true;
  	} 
	
  	if( !validTeStDt(dojo.byId('f_toDateField'))){
  		errorMsg.msg = "Expense To date cannot be before 01/01/2010 ";
		return (errorMsg.msg.length >0)? false: true;	
	}
  	
	return true;
}



function validateFromDate(errorMsg){
	var fDate = dojo.byId('f_fromDateField').value;
	if( !validTeStDt(dojo.byId('f_fromDateField')))
	  	{
  		errorMsg.msg = "Expense From date cannot be before 01/01/2010 ";
		return (errorMsg.msg.length >0)? false: true;	
		  } 
	errorMsg.msg = (fDate == '') ? "Expense 'From' date missing":'';
	return (errorMsg.msg.length >0)? false: true;	
}


function validateToDate(errorMsg){
	var tDate = dojo.byId('f_toDateField').value;
	if( !validTeStDt(dojo.byId('f_toDateField')))
  		{
  		errorMsg.msg = "Expense To date cannot be before 01/01/2010 ";
		return (errorMsg.msg.length >0)? false: true;	
		  }	
	errorMsg.msg = (tDate == '') ? "Expense 'To' date missing":'';
	return (errorMsg.msg.length >0)? false: true;	
}