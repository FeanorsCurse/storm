function displaymessage()
{
alert("Hello World!");
}

$('myForm').addEvent('submit', function(e) {
	/**
	 * Prevent the submit event
	 */
	new Event(e).stop();
 
	/**
	 * This empties the log and shows the spinning indicator
	 */
	var log = $('log_res').empty().addClass('ajax-loading');
 
	/**
	 * send takes care of encoding and returns the Ajax instance.
	 * onComplete removes the spinner from the log.
	 */
	this.send({
		update: log,
		onComplete: function() {
			log.removeClass('ajax-loading');
		}
	});
});
