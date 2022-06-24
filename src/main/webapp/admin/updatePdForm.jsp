<!DOCTYPE html>
<%@page import="util.StringUtil"%>
<%@page import="vo.Category"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryDao"%>
<%@page import="vo.User" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="../error/500.jsp"%>
<html lang="ko">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>관리자 페이지</title>
	<link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
	<link href="css/styles.css" rel="stylesheet" />
	<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
</style>
</head>
<%
	//세션에서 로그인된 관리자정보를 조회한다.
	User admin = (User) session.getAttribute("ADMIN");
	if (admin == null) {
		throw new RuntimeException("해당 서비스는 관리자만 이용할 수 있습니다.");
	}  
	CategoryDao categoryDao = CategoryDao.getInstance();
	List<Category> categories = categoryDao.getCategories();
	int rows = StringUtil.stringToInt(request.getParameter("rows"), 5);
%>
<body>
<jsp:include page="admintop.jsp"></jsp:include>
	<div class="container-fluid ">
		<div class="row">
			<div class="col-2 p-0">
				<jsp:include page="adminleft.jsp"></jsp:include>
			</div>
			<div class="col-10">
				<div id="layoutSidenav_content">
					<main>
						<div class="container-fluid px-4">
							<h1 class="mt-4">상품정보수정</h1>

							<div class="card my-4 ">
								<div class="card-header">
									<div class="d-flex justify-content-between">
										<strong class="me-3"><i class="fas fa-table me-1"></i>상품정보수정</strong>
										<button type="button" class="btn btn-primary " id="update-btn" data-bs-toggle="modal" data-bs-target="#updateModal">상품수정하기</button>
									</div>
									<div class="form-div ">
										<form id="update-form" class="row g-3" method="post">
												<div class="col-3 input-group ">
												<input type="hidden" name="page" />
												<select class="form-select form-select-sm" name="category" onchange="﻿loadProducts();" style="width: auto;">
													<option disabled selected >카테고리 선택</option>
											<%
												for(Category category : categories) {
											%>	
													<option value="<%=category.getNo() %>"><%=category.getName() %></option>
											<%
												}
											%>
												</select>
												<select class="form-select form-select-sm float-end" name="search" onchange="﻿loadProducts();" style="width: auto;">
													<option value="" selected disabled>검색조건</option>
													<option value="company">제조사</option>
													<option value="name">상품명</option>
												</select>
												<input type="text" class="form-control" name="keyword" placeholder="키워드 입력" style="width: auto;"/>
												<select class="form-select form-select-sm" name="period" onchange="﻿loadProducts();" style="width: auto;">
													<option value="-9999">전체보기</option>
													<option value="-1">1개월</option>											
													<option value="-3">3개월</option>											
													<option value="-6">6개월</option>	
												</select>
												</div>
												<button type="button" class="btn btn-outline-secondary" onclick="searchByKeyword();">검색</button>
												<select class="form-select form-select-sm float-end" name="rows" onchange="﻿loadProducts();" style="width: auto;">
													<option value="5" <%=rows == 5 ? "selected" : ""%>>5개씩 보기</option>
													<option value="10" <%=rows == 10 ? "selected" : ""%>>10개씩 보기</option>
													<option value="15" <%=rows == 15 ? "selected" : ""%>>15개씩 보기</option>
												</select>
										</form>
									</div>
								</div>
								<div class="card-body">
									<table class="table table-hover text-center" id="product-table">
										<colgroup>
											<col width="5%">
											<col width="8%">
											<col width="15%">
											<col width="18%">
											<col width="15%">
											<col width="15%">
											<col width="*">
											<col width="10%">
										</colgroup>
										<thead class="table-light">
											<tr>
												<th id=all-toggle><input type="checkbox" id="all-toggle-checkbox" onchange="toggleCheckbox();"/></th>
												<th>상품분류</th>
												<th>상품명</th>
												<th>제조사</th>
												<th>정가</th>
												<th>판매가</th>
												<th>재고량</th>
												<th>추천상품</th>
											</tr>
										</thead>
										<tbody>
									
										</tbody>
										<tfoot>
											<tr>

											</tr>
										</tfoot>
									</table>
									<nav>
										<ul class="pagination justify-content-center" id="pagination">
										
										</ul>
									</nav>
								</div>
							</div>
						</div>

					</main>
					<jsp:include page="adminbottom.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	
	
<!-- 모달만들기 -->
<div class="modal fade" id="product-modify-modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">상품수정하기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      <form id="newProduct-form" class="form-horizontal border bg-light p-3" method="post" enctype="multipart/form-data" action="addProduct.jsp">
				<div class="row mb-3">
					<label for="category" class="col-sm-2 col-form-label">상품 분류</label>
					<div class="col-sm-10">
						<select class="form-select" name="categoryNo">
							<option value="" disabled selected >카테고리 선택</option>
					<%
						for(Category category : categories) {
					%>	
							<option value="<%=category.getNo() %>"><%=category.getName() %></option>
					<%
						}
					%>
						</select>
					</div>
				</div>
				<div class="row mb-3">
					<label for="name" class="col-sm-3 col-form-label">상품명</label>
					<div class="col-sm-9">
						<input type="text" name="name"  class="form-control">
					</div>
				</div>
				<div class="row mb-3">
					<label for="company" class="col-sm-3 col-form-label">제조사</label>
					<div class="col-sm-9">
						<input type="text" name="company"  class="form-control">
					</div>
				</div>
				<div class="row mb-3">
					<label for="price" class="col-sm-3 col-form-label">정가</label>
					<div class="col-sm-9">
						<input type="text" name="price" class="form-control">
					</div>
				</div>
				<div class="row mb-3">
					<label for="salePrice" class="col-sm-3 col-form-label">판매가</label>
					<div class="col-sm-9">
						<input type="text" name="salePrice" class="form-control">
					</div>
				</div>
				<div class="row mb-3">
					<label for="quantity" class="col-sm-3 col-form-label">입고량</label>
					<div class="col-sm-9">
						<input type="number" name="quantity" class="form-control" min="1">
					</div>
				</div>
				<fieldset class="row mb-3">
					<legend class="col-form-label col-sm-3 pt-0">추천상품</legend>
					<div class="col-sm-9">
						<div class="form-check form-check-inline">
  							<input class="form-check-input" type="radio" name="recommended" value="Y" >
  							<label class="form-check-label" for="inlineRadio1">예</label>
						</div>
						<div class="form-check form-check-inline">
	  						<input class="form-check-input" type="radio" name="recommended" id="inlineRadio2" value="N" checked>
	  						<label class="form-check-label" for="inlineRadio2">아니오</label>
						</div>
					</div>
				</fieldset>
				
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="btn-form-close">닫기</button>
        <button type="button" id="registerBtn" class="btn btn-primary" onclick="submitForm();">등록하기</button>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">

	function loadProducts(page) {
		document.getElementById("all-toggle").innerHTML ='<input type="checkbox" id="all-toggle-checkbox" onchange="toggleCheckbox();"/>' // 페이지를 넘길 때 체크박스 초기화되도록? 다른 방법?
		let categoryNo = document.querySelector("select[name=category]").value;
		let period = document.querySelector("select[name=period]").value;
		let rows = document.querySelector("select[name=rows]").value;
		let pageNo = page || 1; 
		
		let search = document.querySelector("select[name=search]").value;
		let keyword = document.querySelector("input[name=keyword]").value;
		
		let tbody = document.querySelector("#product-table tbody");
		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState === 4 && xhr.status === 200) {
				let jsonText = xhr.responseText;
				let result= JSON.parse(jsonText);
				let pagination = result.pagination;
				let products = result.products;
				
				
				let rows ="";
				for(let index = 0; index < products.length; index++) {
					let product = products[index];
					let pdNo = product.no;
					let categoryNo = product.categoryNo;
					let name = product.name;
					let company = product.company;
					let price = product.price;
					let salePrice = product.salePrice;
					let quantity = product.stock;
					let createdDate= product.createdDate;
					let recommended = product.recommended;
					
					//let modalInfo = {categoryNo: product.categoryNo, name: name, company: company, price: price, salePrice: salePrice, quantity: quantity, recommended: recommended};
					// 위와 같이 객체를 생성해서 전달하는 것은 불가능! 맨마지막 값만 들어가기 때문에
					
					rows += "<tr id='row-" + pdNo +"'>";
					rows += '<td><input type="checkbox" name="pdCheckbox" value="' + pdNo + '" onchange="changeCheckboxChecked();"/></td>'; // 수정
					rows += "<td data-product-category-no='"+ categoryNo + "'>" + categoryNo + "</td>";
					rows += "<td><a href='javascript:openModal(" + pdNo +")'>" + name + "</a></td>"; //  data-bs-toggle='modal' data-bs-target='#product-modify-modal' 삭제
					rows += "<td>" + company + "</td>";
					rows += "<td>" + price + "</td>";
					rows += "<td>" + salePrice + "</td>";
					rows += "<td>" + quantity + "(" + createdDate + ")" + "</td>";
					rows += "<td>" + recommended + "</td>";
					rows += "</tr>";
				}
				tbody.innerHTML = rows;
				
				let paginationContent = "";
				
				paginationContent += '<li class="page-item">';
				if (pagination.currentPage > 1) {
					paginationContent += '<a class="page-link" href="javascript:loadProducts('+(pagination.currentPage - 1)+')">이전</a>';
				} else {
					paginationContent += '<a class="page-link disabled" href="">이전</a>';
				}
				paginationContent += '</li>';
				
				
				for (let num = pagination.beginPage; num <= pagination.endPage; num++) {
					paginationContent += '<li class="page-item">'
					if (pagination.currentPage === num) {
						paginationContent += '<a class="page-link active" href="javascript:loadProducts('+num+')">'+num+'</a>'
					} else {
						paginationContent += '<a class="page-link" href="javascript:loadProducts('+num+')">'+num+'</a>'
					}
					paginationContent += '</li>';
				}
				
				paginationContent += '<li class="page-item">'
				if (pagination.currentPage < pagination.totalPages) {
					paginationContent += '<a class="page-link"  href="javascript:loadProducts('+(pagination.currentPage + 1)+')">다음</a>';
				} else {
					paginationContent += '<a class="page-link disabled" href="">다음</a>';
				}
				paginationContent += '</li>';
				
				
				document.getElementById("pagination").innerHTML = paginationContent;
			}
		}
		
		xhr.open("GET", "productList.jsp?categoryNo=" + categoryNo + "&period=" + period + "&rows=" + rows + "&pageNo=" + pageNo + "&search=" + search + "&keyword=" + keyword);
		xhr.send();
		
	}
	
	﻿
	let productModifyModal= new bootstrap.Modal(document.getElementById('product-modify-modal'), {

	keyboard: false

	});
	function openModal(productNo) {
		let tds = document.querySelectorAll("#row-" + productNo);
		
		//alert("상품명: " + tds[2].textContent + "회사명: " + tds[3].textContent );
		document.querySelector("#product-modify-modal input[name=name]").value = tds[2]﻿.querySelector("a").textContent;
		document.querySelector("#product-modify-modal input[name=company]").value = tds[3].textContent;
		document.querySelector("#product-modify-modal input[name=price]").value = tds[4].textContent;
		document.querySelector("#product-modify-modal input[name=salePrice]").value = tds[5].textContent;
		document.querySelector("#product-modify-modal input[name=quantity]").value = tds[6].textContent;
		
		productModifyModal.show(); 
	}
		﻿
	
	function searchByKeyword() {
		
		// input박스 안에 키워드를 입력했지만 검색조건(제조자 or 상품명)를 설정하지 않은 경우 alert창을 출력한다.
		let search = document.querySelector("select[name=search]").value;
		let keyword = document.querySelector("input[name=keyword]").value.trim();
		if(search === "" && keyword !== "") {
			alert("검색조건을 선택해주세요!");
			false;
		}
		
		
		let form = document.getElementById("update-form");
		let formData = new FormData(form);
		
		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4 && xhr.status === 200) {
				loadProducts(1);
			}
		}
		xhr.open("POST", "productList.jsp");			
		xhr.send(formData);		 
	
		
	}
	
	
	
	
	
	
	function toggleCheckbox() {
        let allToggleChecboxCheckedStatus = document.getElementById("all-toggle-checkbox").checked;
        let pdCheckboxNodeList = document.querySelectorAll("input[name='pdCheckbox']");
        for (let index = 0; index < pdCheckboxNodeList.length; index++) {
            let pdCheckbox = pdCheckboxNodeList[index];
            pdCheckbox.checked = allToggleChecboxCheckedStatus;
        }
    }
	
	function changeCheckboxChecked() {
        let checkboxCount = document.querySelectorAll('input[name="pdCheckbox"]').length;
        let checkedCheckboxCount = document.querySelectorAll('input[name="pdCheckbox"]:checked').length;

        document.getElementById("all-toggle-checkbox").checked = (checkboxCount === checkedCheckboxCount);
    }
	
	
</script>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
</html>