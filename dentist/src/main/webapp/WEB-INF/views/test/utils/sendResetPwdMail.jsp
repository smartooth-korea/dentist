<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 재설정 메일 발송</title>
</head>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$('#submit').click(function(){
		
		var userId = $('#userId').val();
		var userEmail = $('#userEmail').val();

		$.ajax({
			type:'POST',   //post 방식으로 전송
			url:'/dentist/utils/sendResetPwdMail.do',   //데이터를 주고받을 파일 주소
			data:JSON.stringify ({ //변수에 담긴 데이터를 전송해준다 (JSON 방식)
				"userId" : userId
				,"userEmail" : userEmail
			}),
			dataType:'JSON', //데이터 타입 JSON
			contentType : "application/json; charset=UTF-8",
			success : function(data){   //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
				alert("성공");
			},
			error:function(request,status,error){
				alert("실패");
			}
		});
	});
	
});
</script>
<body>
		<input type="text" id="userId" name="userId"/>
		<input type="text" id="userEmail" name="userEmail"/>
		<input type="button" id="submit" value="버튼"/>
		
</body>
</html>