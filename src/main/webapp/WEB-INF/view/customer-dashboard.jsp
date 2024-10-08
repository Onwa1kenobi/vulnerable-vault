<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Dashboard</title>
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
	max-width: 800px;
	margin-top: 50px;
	margin-bottom: 50px;
}

.error {
	color: red;
}

.card {
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<div class="container">
		<!-- Display user details -->
		<div class="card">
			<div class="card-header">
				<h4>User Dashboard</h4>
			</div>
			<div class="card-body">
				<h5 class="card-title">Welcome, ${user.fullname}</h5>
				<p class="card-text">
					<strong>Account Number:</strong> ${account.accountNumber}
				</p>
				<p class="card-text">
					<strong>Balance:</strong> $${account.balance}
				</p>
			</div>
		</div>

		<!-- Tab navigation -->
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item" role="presentation">
				<a class="nav-link active" id="perform-transaction-tab" data-bs-toggle="tab" href="#performTransaction" role="tab" aria-controls="performTransaction" aria-selected="true">Perform Transaction</a>
			</li>
			<li class="nav-item" role="presentation">
				<a class="nav-link" id="view-transactions-tab" data-bs-toggle="tab" href="#viewTransactions" role="tab" aria-controls="viewTransactions" aria-selected="false">View Transactions</a>
			</li>
		</ul>

		<div class="tab-content" id="myTabContent">
			<!-- Perform Transaction Tab -->
			<div class="tab-pane fade show active" id="performTransaction" role="tabpanel" aria-labelledby="perform-transaction-tab">
				<div class="card mt-3">
					<div class="card-header">
						<h4>Perform a Transaction</h4>
					</div>
					<div class="card-body">
						<form action="${pageContext.request.contextPath}/performTransaction" method="POST" enctype="multipart/form-data">
						<input type="hidden" name="csrfToken" value="${csrfToken}" />
							<div class="mb-3">
								<label for="transactionType" class="form-label">Transaction Type</label>
								<select id="transactionType" name="transactionType" class="form-select" required>
									<option value="" disabled selected>Select a transaction type</option>
									<option value="deposit">Deposit</option>
									<option value="withdrawal">Withdrawal</option>
									<option value="transfer">Transfer</option>
								</select>
							</div>
							<div class="mb-3">
								<label for="amount" class="form-label">Amount</label>
								<input type="number" class="form-control" id="amount" name="amount" step="0.01" required>
							</div>
							<div class="mb-3" id="transferFields" style="display: none;">
								<label for="destinationAccount" class="form-label">Destination Account Number</label>
								<input type="text" class="form-control" id="destinationAccount" name="destinationAccount">
							</div>
							<div class="mb-3" id="uploadImageField" style="display: none;">
								<label for="receipt" class="form-label">Upload Receipt Image</label>
								<input type="file" class="form-control" id="receipt" name="receipt" accept="image/*">
							</div>
							<button type="submit" class="btn btn-primary">Submit</button>
						</form>
					</div>
				</div>
			</div>

			<!-- View Transactions Tab -->
			<div class="tab-pane fade" id="viewTransactions" role="tabpanel" aria-labelledby="view-transactions-tab">
				<div class="card mt-3">
					<div class="card-header">
						<h4>Your Transactions</h4>
					</div>
					<div class="card-body">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>ID</th>
									<th>Type</th>
									<th>Amount</th>
									<th>Date</th>
									<th>Status</th>
									<th>Receipt</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="transaction" items="${transactions}">
									<tr>
										<td>${transaction.id}</td>
										<td>${transaction.transactionType}</td>
										<td>$${transaction.amount}</td>
										<td>${transaction.transactionDate}</td>
										<td>${transaction.transactionStatus}</td>
										<td><c:if
										test="${not empty transaction.transactionReceiptPath}">
										<a
											href="${pageContext.request.contextPath}/uploads${transaction.transactionReceiptPath}"
											target="_blank">View Receipt</a>
									</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

		<!-- Display success or error messages -->
		<c:if test="${not empty successMessage}">
			<div class="alert alert-success mt-3">${successMessage}</div>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<div class="alert alert-danger mt-3">${errorMessage}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/logout">
			<div class="d-grid gap-2 col-12 mx-auto">
				<button type="submit" class="btn btn-outline-danger">Logout</button>
			</div>
		</form>
	</div>

	<!-- JavaScript to show/hide transfer fields based on transaction type -->
	<script>
		$(document).ready(function() {
			$('#transactionType').change(function() {
				if ($(this).val() === 'transfer') {
					$('#transferFields').show();
					$('#uploadImageField').hide();
				} else if ($(this).val() === 'deposit') {
					$('#uploadImageField').show();
					$('#transferFields').hide();
				} else {
					$('#transferFields').hide();
					$('#uploadImageField').hide();
				}
			});
		});
	</script>
</body>
</html>
