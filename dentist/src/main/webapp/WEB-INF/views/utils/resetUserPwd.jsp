<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="/imgs/common/logo_img_ori.png">
<link rel="stylesheet" href="css/common/layout.css">
<link rel="stylesheet" href="css/bootstrap.resetpassword.css">
<title>비밀번호 재설정 ::: 스마투스코리아</title>
<style type="text/css">
html{
	font-family: AppleSDGothicNeoR;
	background-color: #333333;
}
</style>
</head>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	// input 태그 값 추출
	var strPwd1 = $('#userPwd1').val();
	var strPwd2 = $('#userPwd2').val();
	
	function pwdChk(){
		var userId = $('#userId').val();
		var regExpPw = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
		var strPwd1 = $('#userPwd1').val();
		var strPwd2 = $('#userPwd2').val();
		if(strPwd1 != strPwd2){
			alert("The passwords entered do not match.");
			return false;
		}else{
			if(!regExpPw.test(strPwd1)){
				alert("8-16 characters including English/special characters/numbers");
				return false;
			}else{
				$.ajax({
					type:'POST',   //post 방식으로 전송
					url:'/dentist/user/updateUserPwd.do',   //데이터를 주고받을 파일 주소
					data:JSON.stringify ({
						"userId" : userId
						,"userPwd" : strPwd1
					}),   //위의 변수에 담긴 데이터를 전송해준다.
					dataType:'JSON',   //json 파일 형식으로 값을 담아온다.
					contentType : "application/json; charset=UTF-8",
					success : function(data){   //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
						alert("비밀번호가 변경되었습니다.");
		 				window.close();
					},
					error:function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"
								+"\n"
								+"\n"
								+"비밀번호 변경 중 오류가 발생하였습니다."+"\n"+"관리자에게 문의해주시기 바랍니다."
								+"\n"
								+"\n"+"smartooth.system@gmail.com"
								+"\n");
					}
				});
			}
		}
	}
</script>
<body>
	
	<div class="jumbotron vertical-center">
		<input type="hidden" id="userId" name="userId" value="${userId}">
		<div class="container">
			<div id="login_logo_background">
				<img id="logo_img" src="/imgs/login/login_logo_background.png" alt="㈜스마투스코리아 로고" style="width: 400px;" />
			</div>
			<div class="commonHeight30"></div>
			<h3 style="text-align: center; color: white; font-weight: bold;">비밀번호 변경 페이지</h3>
			<div class="commonHeight40"></div>
			<div class="form-group">
				<input type="password" class="form-control" placeholder="비밀번호" id="userPwd1" name="userPwd1" maxlength="20">
			</div>
			<div class="commonHeight10"></div>
			<div class="form-group">
				<input type="password" class="form-control" placeholder="비밀번호 재입력" id="userPwd2" name="userPwd2" maxlength="20">
			</div>
			<div class="commonHeight20"></div>
			<div class="form-group">
				<input type="button" id="login_btn" style="width: 50%;height: 40px;font-weight: bold; font-size: 20px;line-height: 0;" value="비밀번호 재설정" onclick="pwdChk();" class="btn btn-primary form-control" value="로그인">
			</div>
		</div>
	</div>
	
</body>
</html>