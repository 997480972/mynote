/**
 * show loading
 */
function show_loading() {
	var body = $(document.body);
	var heightVal = body.height();
	if (!document.getElementById("loading-mask")) {
		var loading = '<div style="left: 1px; top: 0px; width: 1349px; height: 754px;display: none; " id="loading-mask">';
		loading += '<p id="loading_mask_loader" class="loader"><img alt="Loading..." src="';
		loading += '/image/loading.gif"';
		loading += '><br>Please wait...</p>';
		loading += '</div>';
		console.log(loading);
		body.append(loading);
	}
	$('#loading-mask').css({
		"height" : heightVal + 'px',
		"top": "180px",
		"text-align": "center", 
		"color": "red",
		"position": "absolute",
		"z-index": "1",
		"opacity": "0.6", //透明度
		"background": "floralwhite"
	});
	$('#loading-mask').show();
}
/**
 * hide loading
 */
function hide_loading() {
	$('#loading-mask').hide();
}