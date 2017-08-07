/**
 * 
 */

dojo.addOnLoad(function() {
	if (dojo.byId('moveForward').value == 'true' && window.name != location.href){
		// This will move forward one page in history in case history exists. A page refresh
		// will not forward. 
			if (window.name != 'forwarded') {
				history.forward();
				window.name = 'forwarded';
			} else {
				window.name = '';
			}
	} 
		if (window.name == "" || window.name != 'forwarded'){
			window.name = location.href;
		}
		
		window.onbeforeunload = checkFormState;
		startTimer();
		dojo.connect(window, "onresize", resizeGrid);
	});
	
	
	var timerVar;
	var logout;

	if( document.captureEvents && Event.KEYUP ) 
	{
	  document.captureEvents( Event.KEYUP );
	}

	document.onkeyup = resetTimer;
	document.onclick = resetTimer;

	function dialogSessionContinueLogout()
	{
		dijit.byId('dialogSessionContinueLogout').show();
		dojo.byId('btnContinue').focus();
		window.onbeforeunload = null;
		// Set continue timeout to 5 minutes. User will be logged out after 5 minutes if Continue has not been
		// clicked
		logout = setTimeout('logoutClick();',300000);
	}

	function startTimer()
	{
		// Set timeout to display the popup after 24 minutes.
		timerVar=setInterval('dialogSessionContinueLogout();', 1440000);
	}

	function resetTimer()
	{
		clearInterval(timerVar);
		clearTimeout(logout);
		startTimer();
	}

	function continueClick()
	{
		dijit.byId('dialogSessionContinueLogout').hide();
		keepSessionAlive();
		window.onbeforeunload = checkFormState;
		resetTimer();
	}

	function logoutClick()
	{
		window.location.href=dojo.byId('contextPath').value + '/jsp/logout.jsp';
		dijit.byId('dialogSessionContinueLogout').hide();
	}