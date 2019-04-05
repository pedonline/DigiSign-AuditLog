/**
* @author Weerayut Wichaidit
* @version 2018-09-19
*/
//Create WebSocket connection.
const websocket = new WebSocket('ws://127.0.0.1:9000/');
websocket.onopen = function(evt) { onOpen(evt) };
websocket.onclose = function(evt) { onClose(evt) };
websocket.onmessage = function(evt) { onMessage(evt) };
websocket.onerror = function(evt) { onError(evt) };
var signcomplete = false;

function onloadPdfSign(){
	var bt_ok = document.getElementById('pdfSignDialogForm:bt_ok');
	bt_ok.disabled = true;
	
	var sign_status = document.getElementById('pdfSignDialogForm:sign_status');
	sign_status.innerHTML = 'ยังไม่ลงนาม';
}

function onloadDigiSign(){
	var bt_ok = document.getElementById('digiSignForm:bt_ok');
	bt_ok.disabled = true;
	
	var sign_status = document.getElementById('digiSignForm:sign_status');
	sign_status.innerHTML = 'ยังไม่ลงนาม';
}

function isSigned(){
	return signcomplete;
}

function onOpen(evt)
{
	console.log('CONNECTED');
	getLocation();
	if (typeof(Storage) !== "undefined") {
	    // Code for localStorage/sessionStorage.
		localStorage.setItem("lastname", "Smith");
	} else {
	    // Sorry! No Web Storage support..
		console.log('Sorry! No Web Storage support..');
	}
}

function onClose(evt)
{
	console.log('DISCONNECTED');
}

function onMessage(evt)
{
	console.log('onMessage: Start ');
	var myJSON = JSON.parse(evt.data);
	console.log('evt: ' + evt.data);
	console.log('RESPONSE Method: ' + myJSON.responseMethod);
	console.log('RESPONSE: ' + myJSON.content);
	if(myJSON.responseMethod == 'ENCRYPT_OTK'){
		console.log('RESPONSE: ' + myJSON.digisignOTK);
		var digisignAlias = document.getElementById('digiSignForm:digisignAlias');
		digisignAlias.value = myJSON.digisignAlias;
		var digisignOTK = document.getElementById('digiSignForm:digisignOTK');
		var description = document.getElementById('digiSignForm:description');
		digisignOTK.value = myJSON.digisignOTK;
		description.value = myJSON.digisignOTK;
		var digisignCertificate = document.getElementById('digiSignForm:digisignCertificate');
		digisignCertificate.value = myJSON.digisignCertificate;
		var sign_status = document.getElementById('digiSignForm:sign_status');
		sign_status.innerHTML = 'ลงนามสำเร็จ';
		var img_status = document.getElementById("digiSignForm:img_status");
		img_status.src = '../resources/images/certificate.png';
		var bt_ok = document.getElementById('digiSignForm:bt_ok');
		bt_ok.disabled = false;
	}
	
}

function onError(evt)
{
	console.log('ERROR:' + evt.data);
	var sign_status = document.getElementById('digiSignForm:sign_status');
	sign_status.innerHTML = 'ลงนามไม่สำเร็จ';
	var img_status = document.getElementById("digiSignForm:img_status");
	img_status.src = '../resources/images/alarm.gif';
}

function doSend(message)
{
	console.log("SENT: " + message);
	console.log("SENT length : " + message.length);
	websocket.send(message);
	console.log("SENT: END");
}

function Sign() {
	var filep12 = document.getElementById("digiSignForm:filep12");
	var keystorepassword = document.getElementById("digiSignForm:keystorepassword");

	var userName = document.getElementById("digiSignForm:username");
	var password = document.getElementById("digiSignForm:password");
	var content = document.getElementById("digiSignForm:content");
	console.log(filep12.files[0].name);
	var file  = filep12.files[0];
	var reader  = new FileReader();
	reader.addEventListener("load", function () {		
    	var src = reader.result;
    	console.log(src);
    	console.log(reader.target);
    	var myObj = { "keysotreType":"PKCS12","option":"ENCRYPT_OTK", "keysotreFile":src,"passwordKeysotre":keystorepassword.value, "userName":userName.value, "userPassword":password.value , "content":"","formFieldName":"","ownerSignatureCode":"", "ownerSignatureName":"","workPositionCode":"", "workPositionName":"", "workOrganizationCode":"" , "workOrganizationName":"" , "location":"", "reason":"", "licen":""};
    	var myJSON = JSON.stringify(myObj);
      doSend(myJSON);

    }, false);
	if (file) {
	   reader.readAsDataURL(file);
	}
}

function PdfSignStart() {
	var img_status = document.getElementById("pdfSignDialogForm:img_status");
	img_status.src = '../resources/images/ajax-loader.gif';
}

function DigiSignStart() {
	var img_status = document.getElementById("digiSignForm:img_status");
	img_status.src = '../resources/images/ajax-loader.gif';
}


function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else { 
    	//locationL = "Geolocation is not supported by this browser.";
    }
}

function showPosition(position) {
	var location = document.getElementById("pdfSignDialogForm:location");
	location.value = "Latitude: " + position.coords.latitude + ",Longitude: " + position.coords.longitude;
}

function getBuffer(resolve) {
    var reader = new FileReader();
    reader.readAsArrayBuffer(fileData);
    reader.onload = function() {
      var arrayBuffer = reader.result
      var bytes = new Uint8Array(arrayBuffer);
      resolve(bytes);
    }
}


