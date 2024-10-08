<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard</title>
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
	max-width: 1000px;
	margin-top: 50px;
	margin-bottom: 50px;
}

.error {
	color: red;
}

.card {
	margin-bottom: 20px;
}

.status-pending {
	color: orange;
}

.status-approved {
	color: green;
}

.status-denied {
	color: red;
}
</style>
</head>
<body>
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4>Admin Dashboard</h4>
			</div>
			<div class="card-body">
				<!-- Display success or error messages -->
				<c:if test="${not empty successMessage}">
					<div class="alert alert-success">${successMessage}</div>
				</c:if>
				<c:if test="${not empty errorMessage}">
					<div class="alert alert-danger">${errorMessage}</div>
				</c:if>

				<!-- Transactions Table -->
				<table class="table table-striped">
					<thead>
						<tr>
							<th>ID</th>
							<th>Source Account</th>
							<th>Destination Account</th>
							<th>Type</th>
							<th>Amount</th>
							<th>Receipt</th>
							<th>Status</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="transaction" items="${transactions}">
							<tr>
								<td>${transaction.id}</td>
								<td>${transaction.sourceAccountId != null ? transaction.sourceAccountNumber : 'N/A'}</td>
								<td>${transaction.destinationAccountId != null ? transaction.destinationAccountNumber : 'N/A'}</td>
								<td>${transaction.transactionType}</td>
								<td>$${transaction.amount}</td>
								<td><c:if
										test="${not empty transaction.transactionReceiptPath}">
										<a
											href="${pageContext.request.contextPath}/uploads${transaction.transactionReceiptPath}"
											target="_blank">View Receipt</a>
									</c:if></td>
								<td
									class="${transaction.transactionStatus == 'Pending' ? 'status-pending' : (transaction.transactionStatus == 'Approved' ? 'status-approved' : 'status-denied')}">
									${transaction.transactionStatus}</td>
								<td>
									<form action="${pageContext.request.contextPath}/updateTransactionStatus" method="post"
										class="d-inline">
										<input type="hidden" name="csrfToken" value="${csrfToken}" />
										<input type="hidden" name="transactionId"
											value="${transaction.id}"> <select name="status"
											class="form-select" required>
											<option value="Pending"
												${transaction.transactionStatus == 'Pending' ? 'selected' : ''}>Pending</option>
											<option value="Approved"
												${transaction.transactionStatus == 'Approved' ? 'selected' : ''}>Approved</option>
											<option value="Denied"
												${transaction.transactionStatus == 'Denied' ? 'selected' : ''}>Denied</option>
										</select>
										<button type="submit" class="btn btn-primary mt-2">Update
											Status</button>
									</form> <!-- Delete Button -->
									<form action="${pageContext.request.contextPath}/deleteTransaction" method="post"
										class="d-inline mt-2">
										<input type="hidden" name="csrfToken" value="${csrfToken}" />
										<input type="hidden" name="transactionId"
											value="${transaction.id}">
										<button type="submit" class="btn btn-danger">Delete</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/logout">
			<div class="d-grid gap-2 col-12 mx-auto">
				<button type="submit" class="btn btn-outline-danger">Logout</button>
			</div>
		</form>
	</div>
</body>
</html>
