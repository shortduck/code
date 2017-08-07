/**
 * Javascript for dynamic page behavior
 */

// used to keep track of form changes
var formModified = false;
var showUnsavedChangesPopup = true;

String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
};


//Check to see if a form was modified
function checkFormState (){
	if (formModified && showUnsavedChangesPopup)
		return 'There are unsaved changes. By leaving this page, you will lose all unsaved data.';
	showUnsavedChangesPopup = true;
}

/**
 * Does nothing other than making an Ajax call to extend the HTTP session
 * @return
 */
function keepSessionAlive(){
	dojo.xhrPost({
		url: 'FindAppointments.action',
		handleAs: "json",
		handle: function(data,args){
			// do nothing
			}
	});
}






