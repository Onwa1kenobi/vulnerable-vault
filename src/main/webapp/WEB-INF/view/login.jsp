<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>

<!-- Reference Bootstrap 5 files -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<style>
.container {
	max-width: 400px;
	margin-top: 50px;
}

.error {
	color: red;
}
</style>
</head>

<body>
	<div class="container">
		<h2 class="text-center">Login</h2>
		<form action="${pageContext.request.contextPath}/authenticateTheUser"
			method="POST">
			<div class="mb-3">
				<label for="username" class="form-label">Username:</label> <input
					type="text" class="form-control" id="username" name="username"
					required />
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">Password:</label> <input
					type="password" class="form-control" id="password" name="password"
					required />
			</div>
			<button type="submit" class="btn btn-primary w-100">Login</button>
		</form>
		<div class="text-center mt-3">
			<p>
				<a href="${pageContext.request.contextPath}/showRegister">Register here</a>
			</p>
		</div>
		<!-- Display error messages if any -->
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger mt-3">${errorMessage}</div>
		</c:if>
	</div>
</body>
</html>

