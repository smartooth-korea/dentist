<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Smartooth 관리자 페이지 :: 로그인 실패</title>
<link rel="shortcut icon" type="image/x-icon" href="/imgs/common/logo_origin.png">
</head>
<body>
	<script type="text/javascript">
		var message = "${msg}";
		var loginUrl = "${loginUrl}";
		alert(message);
		document.location.href = "http://"+loginUrl;
	</script>
</body>
</html>