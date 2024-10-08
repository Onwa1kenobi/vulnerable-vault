<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Error Page</title>
<!-- Reference Bootstrap 5 files -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<style>
.error-container {
	max-width: 600px;
	margin: 50px auto;
	padding: 20px;
	border: 1px solid #dc3545;
	border-radius: 5px;
	background-color: #f8d7da;
	color: #721c24;
}

.error-title {
	font-size: 24px;
	font-weight: bold;
}
</style>
</head>
<body>
	<div class="container">
		<div class="error-container">
			<h2 class="error-title">Oops! Something went wrong.</h2>
			<p>We're sorry, but an unexpected error has occurred.</p>
			<c:if test="${not empty errorMessage}">
				<p>
					<strong>Error Message:</strong> ${errorMessage}
				</p>
			</c:if>
			<p>Please try again later or contact support if the issue
				persists.</p>
			<a href="${pageContext.request.contextPath}/" class="btn btn-primary">Return
				to Home</a>
		</div>
	</div>
</body>
</html>
