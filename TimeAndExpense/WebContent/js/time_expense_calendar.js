 var MA_From = [];
 var MA_To = [];
 var prevFromDate = "";

function initSingleDateCalendar(field_id, button_id){
	if (field_id == null) {
		field_id = "f_singleDateField";
	}
	if (button_id == null) {
		button_id = "f_singleDateTrigger";
	}
	Calendar.setup({
	    inputField     :    field_id,     // id of the input field
	    //ifFormat       :    "%B %e, %Y",      // format of the input field
	    dateFormat       :    "%m/%d/%Y",      
	    trigger         :    button_id,  // trigger for the calendar (trigger ID)
	    align          :    "Br",           // alignment (defaults to "Bl")
	    fdow           : 0,
	    onSelect    : 		singleFromCalClose,
	    weekNumbers    : true	    
	});
	return true;
}

function initTwoSingleDateCalendars(fromField_id, fromButton_id, toField_id, toButton_id){
	if (fromField_id == null) {
		fromField_id = "f_fromDateField";
	}
	if (fromButton_id == null) {
		fromButton_id = "f_fromDateTrigger";
	}
	Calendar.setup({
	    inputField     :    fromField_id,     // id of the input field
	    //ifFormat       :    "%B %e, %Y",      // format of the input field
	    dateFormat       :    "%m/%d/%Y",      
	    trigger         :    fromButton_id,  // trigger for the calendar (trigger ID)
	    align          :    "Br",           // alignment (defaults to "Bl")
	    fdow           : 0,
	    weekNumbers    : true,
	    onSelect    : 		singleFromCalClose
	});
	if (toField_id == null) {
		toField_id = "f_toDateField"; 
	}
	if (toButton_id == null ) {
		toButton_id = "f_toDateTrigger";
	}
	Calendar.setup({
	    inputField     :    toField_id,     // id of the input field
	    //ifFormat       :    "%B %e, %Y",      // format of the input field
	    dateFormat       :    "%m/%d/%Y",      
	    trigger         :    toButton_id,  // trigger for the calendar (trigger ID)
	    align          :    "Br",           // alignment (defaults to "Bl")
	    fdow           : 0,
	    weekNumbers    : true,
	    onSelect    : 		singleFromCalClose
	});
	return true;
}

//function created to accommodate reports that need 4 calendars
function initTwoSingleDateCalendars_2(){
	Calendar.setup({
	    inputField     :    "f_fromDateField_2",     // id of the input field
	    //ifFormat       :    "%B %e, %Y",      // format of the input field
	    dateFormat       :    "%m/%d/%Y",      
	    trigger         :    "f_fromDateTrigger_2",  // trigger for the calendar (trigger ID)
	    align          :    "Br",           // alignment (defaults to "Bl")
	    fdow           : 0,
	    weekNumbers    : true,
	    onSelect    : 		singleFromCalClose
	});
	Calendar.setup({
	    inputField     :    "f_toDateField_2",     // id of the input field
	    //ifFormat       :    "%B %e, %Y",      // format of the input field
	    dateFormat       :    "%m/%d/%Y",      
	    trigger         :    "f_toDateTrigger_2",  // trigger for the calendar (trigger ID)
	    align          :    "Br",           // alignment (defaults to "Bl")
	    fdow           : 0,
	    weekNumbers    : true,
	    onSelect    : 		singleFromCalClose
	});
	return true;
}


function initSingleMultiDateSelectionCalendar(){
    MA_From = [];

    Calendar.setup({
      inputField :    "fromDate",     // id of the input field
      dateFormat   :    "%m/%d/%Y",      
      trigger     : "fromDateSelector",
      selectionType: Calendar.SEL_MULTIPLE,
      selection : MA_From,
      fdow           : 0,
      weekNumbers    : true,
      align          :    "Br",           // alignment (defaults to "Bl")
      onBlur    : fromClosed,
      onFocus : syncFromDate
      
    });
}

function initMultipleDateCalendar(){
    MA_From = [];
	MA_To = [];

    Calendar.setup({
      align      : "Br",
      showOthers : true,
      selectionType: Calendar.SEL_MULTIPLE,
      selection   : MA_From, // pass the initial or computed array of multiple dates
      onBlur    : fromClosed,
      fdow           : 0,
      weekNumbers    : true,
      trigger     : "fromDateSelector"
    });
	
    Calendar.setup({
      align      : "Br",
      showOthers : true,
      selectionType: Calendar.SEL_MULTIPLE,
      selection   : MA_To, // pass the initial or computed array of multiple dates
      onBlur    : toClosed,
      fdow           : 0,
      weekNumbers    : true,
      trigger     : "toDateSelector"
    });
	return true;
}

function singleFromCalClose(cal){
	fireEvent(dojo.byId(cal.inputField.id), 'change');
	
	cal.hide();
    return true;
}

function fireEvent(element,event){
    if (document.createEventObject){
        // dispatch for IE
        var evt = document.createEventObject();
        return element.fireEvent('on'+event,evt)
    }
    else{
        // dispatch for firefox + others
        var evt = document.createEvent("HTMLEvents");
        evt.initEvent(event, true, true ); // event type,bubbling,cancelable
        return !element.dispatchEvent(evt);
    }
}

	/*
	 * Constructs a valid date by interpreting the user entered text.
	 * If use enters text which cannot be interpreted into a valid date,
	 * the text is cleared out to let use enter a valid date.
	 */
	function syncDate(f_date){
		if (f_date != null) {
			user_date = constructDateFromInputString(trimStr(f_date.value));
			
			if(!isNaN(user_date) && user_date != null){
				f_date.value = user_date.toString('MM/dd/yyyy');
			}else{
				f_date.value = '';
			}
		}
	}

	/*
	 * Uses the DateJS library to construct Date instance
	 * from the input string.
	 */
	function constructDateFromInputString(inputDate){
		if(inputDate == "last mon")
			inputDate = "last monday";
			
		return Date.parse(inputDate);
	}
		
	// Event triggered on single date calendar close
	//function syncSingleDate(f_date){
	//	syncDate(f_date);
	//}
	
	// Event triggered on single date calendar close
	//function syncSingleFromDate(f_date){
	//	syncDate(f_date);
	//}
	
	// Event triggered on single date calendar close
	//function syncSingleToDate(f_date){
	//	syncDate(f_date);
	//}

	
	
	// if user changed the dates in the text field, 
	// sync them with the calendar control
	function syncFromDate(comp){
		
		
		// prepare array for storing correct dates in textbox
		var processDates = "";
		if (prevFromDate != document.getElementById("fromDate")) {
			prevFromDate = document.getElementById("fromDate");
			// construct tokens for entered dates
			var date_tokens = document.getElementById("fromDate").value.split(',');
			console.log(document.getElementById("fromDate").value);
			if (comp && date_tokens.length == 1 && date_tokens[0].indexOf('->') == -1) {
				//  empty calendar dates, if any
				comp.selection.clear();
			}
			MA_From.length = 0;
			// flag to check if date already processed
			var existAlready = false;
			
			for(indx in date_tokens){
				var val = date_tokens[indx];
				
				var user_date = Date.parse(trimStr(val));
				
				if(!isNaN(user_date) && user_date != null){
					// check if this date already added
					existAlready = false;
					for(var dd in MA_From){
						if(!(user_date > MA_From[dd] || user_date < MA_From[dd] )){
							existAlready = true;
							break;
						}
					}
					
					// if not already added, add it, otherwise ignore it!
					if(!existAlready){
						// push correct date to processDates array
						processDates += user_date.toString("MM/dd/yyyy") +", ";
						
					}
					if (comp && date_tokens.length == 1 && date_tokens[0].indexOf('->') == -1) {
						comp.selection.set(Calendar.dateToInt(user_date));
					}
				}
			}
			if (comp && !comp.selection.isEmpty()) {
				comp.moveTo(comp.selection.getDates()[0]);
			}
			processDates = processDates.substring(0, processDates.lastIndexOf(','));
			document.getElementById("fromDate").value = trimStr(processDates);
		
		}
		return true;
	}

	
	function syncToDate(comp){
		//  empty calendar dates, if any
		MA_To.length = 0;
		
		// prepare array for storing correct dates in textbox
		var processDates = "";
		
		// construct tokens for entered dates
		var date_tokens = document.getElementById("toDate").value.split(',');
		
		// flag to check if date already processed
		var existAlready = false;
		
		for(indx in date_tokens){
			var val = date_tokens[indx];
			
			var user_date = Date.parse(trimStr(val));
			
			if(!isNaN(user_date) && user_date != null){
				// check if this date already added
				existAlready = false;
				for(var dd in MA_To){
					if(!(user_date > MA_To[dd] || user_date < MA_To[dd] )){
						existAlready = true;
						break;
					}
				}
				
				// if not already added, add it, otherwise ignore it!
				if(!existAlready){
					// push correct date to processDates array
					processDates += user_date.toString("MM/dd/yyyy") +", ";
					MA_To[MA_To.length] = user_date;
				}
			}
		}
		
		// refresh date textfield with calendar control dates
		processDates = processDates.substring(0, processDates.lastIndexOf(','));
		document.getElementById("toDate").value = trimStr(processDates);
		return true;
	}
	
	function trimStr(str){
		return str.replace(/^\s*([\S\s]*?)\s*$/, '$1');
	}
	
    function fromClosed(cal) {

      prevFromDate = "";
      // here we'll write the output; this is only for example.  You
      // will normally fill an input field or something with the dates.
	  var d_text = document.getElementById("fromDate");
	  
	  var dates = cal.selection.getDates();

      // Reset the "MA", in case one triggers the calendar again.
      // CAREFUL!  You don't want to do "MA = [];".  We need to modify
      // the value of the current array, instead of creating a new one.
      // Calendar.setup is called only once! :-)  So be careful.
      MA_From.length = 0;
      
      
      // reset initial content.
      d_text.value="";
      
      // walk the calendar's multiple dates selection hash
      for (var i in dates) {
        var d = dates[i];
        // sometimes the date is not actually selected,
        // so let's check
        if (d) {
          // OK, selected.  Fill an input field or something.          
		  d_text.value += Calendar.printDate(d, "%m/%d/%Y, ");
          
		  // and push it in the "MA", in case one triggers the calendar again.
          cal.selection.set(Calendar.dateToInt(d));
        }
      }
	  d_text.value = d_text.value.substring(0, d_text.value.lastIndexOf(','))
	  d_text.value = trimStr(d_text.value);
      
	  // dispatch 'onchange' event for data field
	  fireEvent(dojo.byId(cal.args.inputField), 'change');
	  
	  cal.hide();
      return true;
    };

    function toClosed(cal) {

      // here we'll write the output; this is only for example.  You
      // will normally fill an input field or something with the dates.
	  var d_text = document.getElementById("toDate");
      
      // reset initial content.
      d_text.value="";

      // Reset the "MA", in case one triggers the calendar again.
      // CAREFUL!  You don't want to do "MA = [];".  We need to modify
      // the value of the current array, instead of creating a new one.
      // Calendar.setup is called only once! :-)  So be careful.
      MA_To.length = 0;

      // walk the calendar's multiple dates selection hash
      for (var i in cal.getDates()) {
        var d = cal.getDates()[i];
        // sometimes the date is not actually selected,
        // so let's check
        if (d) {
          // OK, selected.  Fill an input field or something.          
		  d_text.value += Calendar.printDate(d, "%m/%d/%Y, ");
          
		  // and push it in the "MA", in case one triggers the calendar again.
          MA_To[MA_To.length] = d;
        }
      }
	  d_text.value = d_text.value.substring(0, d_text.value.lastIndexOf(','))
	  d_text.value = trimStr(d_text.value);
      cal.hide();
      return true;
    };	

	function compareDates_MMDDYYYY_Format(a, b) {
		var result;
		
		// first compare year
		var a_yr = a.substring(6);
		var b_yr = b.substring(6);
		
		if(a_yr != b_yr){
			result = (a_yr > b_yr) ? 1 : -1;

			// then month	
		}else{
			var a_mnt = a.substring(0,2);
			var b_mnt = b.substring(0,2);

		 	if(a_mnt != b_mnt){
				result = (a_mnt > b_mnt) ? 1 : -1;
			}else{
				var a_day = a.substring(3,5);
				var b_day = b.substring(3,5);
				
				if(a_day == b_day)
					result=0;
				else 
					result = (a_day > b_day) ? 1 : -1;
				
			}
		}
		return result;
		
		//PS: the following is convenient but has poor performance!
	//return Date.parse(a).compareTo(Date.parse(b));					
	}
