<%@page import="dao.ProductDao"%>
<%@page import="vo.Pagination"%>
<%@page import="util.StringUtil"%>
<%@page import="vo.Category"%>
<%@page import="vo.Product"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap demo</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="common/nav.jsp">
	<jsp:param name="menu" value="list"/>
</jsp:include>
<div class="container">
	<div class="text-center">
		<img src="images/cate_01.jpg">
	</div>
	
<%
	int categoryNo = Integer.parseInt(request.getParameter("categoryNo")) ;
	String sort = request.getParameter("sort");
	
	CategoryDao categoryDao = CategoryDao.getInstance();
	ProductDao productDao = ProductDao.getInstance();
	
	int currentPage = StringUtil.stringToInt(request.getParameter("page"), 1);			// 현재페이지 지정
	int rows = StringUtil.stringToInt(request.getParameter("rows"), 5);					// 페이지에 표시될 행 개수
	int totalRows = productDao.getTotalRows(categoryNo);											// 전체 행 개수
	
	Pagination pagination = new Pagination(rows, totalRows, currentPage);

	String categoryName = categoryDao.getCategoryNameByNo(categoryNo);		// 카테고리 이름 
	int pdQuantity = categoryDao.getTotalQunatity(categoryNo);				// 카테고리별 총상품수량
	
	List<Product> productList = null;
	if ("sell".equals(sort)) {
		productList = categoryDao.getItemBySaleQuantity(categoryNo, pagination.getBeginIndex(), pagination.getEndIndex());		
	} else if ("low".equals(sort)) {
		productList = categoryDao.getItemByMinPrice(categoryNo, pagination.getBeginIndex(), pagination.getEndIndex());
	} else if ("high".equals(sort)) {
		productList = categoryDao.getItemByMaxPrice(categoryNo, pagination.getBeginIndex(), pagination.getEndIndex());
	} else {
		productList = categoryDao.getItemByDate(categoryNo, pagination.getBeginIndex(), pagination.getEndIndex());
	}
	
%>
	<p><%=categoryName %></p>
	<p>전체상품<strong><%=pdQuantity %></strong>개</p>
	<div>
		<form>
		 <div class="row mb-3 border-top border-bottom border-1 p3">
	 		<div class="col p-2">
	 			<a href="list.jsp?categoryNo=<%=categoryNo %>&sort=sell&rows=<%=rows %>" class="btn btn-outline-primary">판매량순</a>	 
	 			<a href="list.jsp?categoryNo=<%=categoryNo %>&sort=low&rows=<%=rows %>" class="btn btn-outline-primary">낮은가격순</a>	 	
	 			<a href="list.jsp?categoryNo=<%=categoryNo %>&sort=high&rows=<%=rows %>" class="btn btn-outline-primary">높은가격순</a>	 	
	 			<a href="list.jsp?categoryNo=<%=categoryNo %>&sort=date&rows=<%=rows %>" class="btn btn-outline-primary">등록일순</a>
	 		</div>
	 		<select class="form-control form-control-sm w-25 float-end" name="rows" onchange="changeRows();">
	 			<option value="5" <%=rows == 5 ? "selected" : "" %>> 5개씩 보기</option>
	 			<option value="10" <%=rows == 10 ? "selected" : "" %>> 10개씩 보기</option>
	 			<option value="20" <%=rows == 20 ? "selected" : "" %>> 20개씩 보기</option>
	 		</select>
		 </div>
		</form>
	</div>
	<div class="row">
	<%
		for (Product product : productList) {
	%>
            <div class="col-lg-3 col-md-6 mb-4">
               <div class="card h-100">
                   <a href="product/detail.jsp?no=<%=product.getNo() %>" class="text-dark text-decoration-none"><img class="card-img-top" src="images/cate_01.jpg" alt="..." />
                   	 <div class="card-body">
                       <h5 class="card-title fs-6 text-bold"><%=product.getName() %></h5>
                       <p class="mb-1"><del><%=product.getPrice() %></del> 원</p>
                       <p><strong class="text-danger"><%=product.getSalePrice() %></strong> 원</p>
                       <p class="card-text">상품설명(생략가능)</p>
                     </div>
                     <div class="card-footer"><small class="text-muted"><%=product.getReviewScore() %></small></div>
                   	</a>
               </div>
            </div>  
    <%
		}
    %>             
	<nav>
			<ul class="pagination justify-content-center">
				<li class="page-item">
					<a class="page-link <%=pagination.getCurrentPage() == 1 ? "disabled" : "" %>" href="javascript:clickPageNo(<%=pagination.getCurrentPage() - 1 %>)">이전</a>
				</li>
		<%
			for (int num = pagination.getBeginPage(); num <= pagination.getEndPage(); num++) {
		%>
				<li class="page-item">
					<a class="page-link <%=pagination.getCurrentPage() == num ? "active" : "" %>" href="javascript:clickPageNo(<%=num %>)"><%=num %></a>
				</li>
		<%
			}
		%>
				<li class="page-item">
					<a class="page-link <%=pagination.getCurrentPage() == pagination.getTotalPages() ? "disabled" : "" %>" href="javascript:clickPageNo(<%=pagination.getCurrentPage() + 1 %>)">다음</a>
				</li>
			</ul>
	</nav>
		<form id="search-form" class="row g-3" method="get" action="list.jsp">
			<input type="hidden" name="categoryNo" value=<%=categoryNo %> />
			<input type="hidden" name="sort" value=<%=sort %> />	
			<input type="hidden" name="page" />
			<input type="hidden" name="rows" />	
		</form>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
<script>
	function changeRows() {
		document.querySelector("input[name=page]").value = 1;
		document.querySelector("input[name=rows]").value = document.querySelector("select[name=rows]").value;
		document.getElementById("search-form").submit();
	}
	function clickPageNo(pageNo) {
		document.querySelector("input[name=page]").value = pageNo;
		document.querySelector("input[name=rows]").value = document.querySelector("select[name=rows]").value;
		console.log('$'+document.querySelector("input[name=sort]").value+'$');
		document.getElementById("search-form").submit();
	}

</script>
</body>
</html>