<%-- 
    Document   : index
    Created on : Jun 19, 2013, 12:12:37 AM
    Author     : val
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" type="text/css" href="css/style.css"/>
        <script type="text/javascript">
            var timeoutID;
            function getXMLObject() { //XML OBJECT
                var xmlHttp = false;
                try {
                    xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")  // For Old Microsoft Browsers
                } catch (e) {
                    try {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")  // For Microsoft IE 6.0+
                    } catch (e2) {
                        xmlHttp = false   // No Browser accepts the XMLHTTP Object then false
                    }
                }
                if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
                    xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
                }
                return xmlHttp;  // Mandatory Statement returning the ajax object created
            }
            var xmlhttp = new getXMLObject();	//xmlhttp holds the ajax object
            function ajaxFunction() {
                if(xmlhttp) {
                    xmlhttp.open("GET","./DigitalClock.do",true); //gettime will be the servlet name
                    xmlhttp.onreadystatechange  = handleServerResponse;
                    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                    xmlhttp.send(null);
                }
                timeoutID = setTimeout("ajaxFunction()",1000);
            }

            function handleServerResponse() {
                if (xmlhttp.readyState == 4) {
                    if(xmlhttp.status == 200) {
                        var tableDataDiv = document.getElementById("tableData");
                        tableDataDiv.innerHTML = xmlhttp.responseText;
                    }
                }
            }
            function setAutoLoad() {
                setTimeout("ajaxFunction();",0);
            }
        </script>        
    </head>
    <body onload="setAutoLoad();">
        <h1>Me game</h1>
         <form name="mainForm">
            <fieldset>
                <div id="tableData">

                </div>
            </fieldset>
        </form>      
    </body>
</html>
