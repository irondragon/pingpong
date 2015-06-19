<%@ page import="com.glumobile.moscow.pingpong.commands.CommandType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> PingPong game! </title>
</head>
<body>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#getPingCount').click(function () {
            var request = new Object();
            request.commandType = '<%=CommandType.PING%>';
            request.sessionId = '<%=request.getSession().getId()%>';
            $.ajax({
                url: 'handler',
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(request),
                contentType: 'application/json',
                success: function (response) {
                    $('#Pong').text(response.commandType + ' ' + response.pingCount);
                },
                error: function (response, status, er) {
                    alert("error: " + response + " status: " + status + " er:" + er);
                }
            });
        });
    });
</script>
<input type="button" id="getPingCount" value="<%=CommandType.PING%>"/>

<p id="Pong"></p>
</body>
</html>
