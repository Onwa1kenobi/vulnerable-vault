<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register</title>
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
	max-width: 500px;
	margin-top: 50px;
}

.error {
	color: red;
}
</style>
</head>
<body>
	<div class="container">
		<h2 class="text-center">Register</h2>
		<form action="${pageContext.request.contextPath}/registerTheUser"
			method="POST" modelAttribute="user">
			<div class="mb-3">
				<label for="email" class="form-label">Email:</label> <input
					path="email" type="email" class="form-control" id="email"
					name="email" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" required />
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">Password:</label> <input
					path="password" type="password" class="form-control" id="password"
					name="password" required />
			</div>
			<div class="mb-3">
				<label for="confirm_password" class="form-label">Confirm
					Password:</label> <input path="confirmPassword" type="password"
					class="form-control" id="confirmPassword" name="confirmPassword"
					required />
			</div>
			<div class="mb-3">
				<label for="fullName" class="form-label">Full Name:</label> <input
					path="fullname" type="text" class="form-control" id="fullname"
					name="fullname" pattern="[a-zA-Z]+" required />
			</div>
			<button type="submit" class="btn btn-primary w-100">Register</button>
		</form>
		<div class="text-center mt-3">
			<p>
				<a href="${pageContext.request.contextPath}/showLogin">Login
					here</a>
			</p>
		</div>
		<!-- Display error messages if any -->
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger mt-3">${errorMessage}</div>
		</c:if>
	</div>
</body>
</html>
