<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>메일 인증 실패 :: 스마투스코리아</title>
<!-- Custom fonts for this template-->
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<!-- Custom styles for this template-->
<link rel="stylesheet" href="css/common/layout.css">
<link rel="stylesheet" href="css/error/mailAuth.css">
<script type="text/javascript">

	setTimeout(function() {
		alert("비밀번호 재설정 화면에서 인증 메일을 다시 요청하시기 바랍니다.\n해당 화면은 종료됩니다.");
		window.close();
		
	}, 3000);
	
	

</script>
</head>

<body id="page-top">
	<div class="commonHeight250"></div>
	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- 500 Error Text -->
					<div class="text-center">
						<div class="error" style="font-size: 40px;">
							메일 인증에 실패하였습니다.
							<br/>
							메일의 유효기간이 지난 것으로 확인이 됩니다.
						</div>
						<br/>
						<p class="lead text-gray-800 mb-5">비밀번호 재설정에서 인증메일을 다시 요청하시기 바랍니다.</p>
					</div>

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->

			<!-- Footer -->
			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; 스마투스코리아</span>
					</div>
				</div>
			</footer>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->
</body>
</html>