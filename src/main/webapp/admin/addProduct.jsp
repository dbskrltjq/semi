<%@page import="dao.ProductDao"%>
<%@page import="vo.Product"%>
<%@page import="util.StringUtil"%>
<%@page import="util.MultipartRequest"%>
<%@page import="vo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	/* User admin = (User)session.getAttribute("ADMIN");
	if(admin == null) {
		throw new RuntimeException("신규상품등록은 관리자 로그인 후에 사용가능합니다.");
	} errorPage="../error/500.jsp" */
	
	// multipart/form-data요청을 처리하는 MultipartRequest객체 생성하기
	MultipartRequest mr = new MultipartRequest(request, "C:\\eclipse\\workspace-web\\productImages");
	
	// 요청파라미터값을 조회
	int categoryNo = StringUtil.stringToInt(mr.getParameter("categoryNo"));
	String name = mr.getParameter("name");
	String company = mr.getParameter("company");
	
	int price = StringUtil.stringToInt(mr.getParameter("price"));
	int salePrice = StringUtil.stringToInt(mr.getParameter("salePrice"));
	int quantity = StringUtil.stringToInt(mr.getParameter("quantity"));
	String recommended = mr.getParameter("recommended");		// on & null? 체크박스는 체크를 하지 않으면 요청파라미터 값 자체가 넘어가지 않는다. 그래서 null이 나온다.
																// 라디오박스도 사용가능
	String fileName = mr.getParameter("upfile");
	
	Product newProduct = new Product();
	newProduct.setCategoryNo(categoryNo);
	newProduct.setName(name);
	newProduct.setCompany(company);
	newProduct.setPrice(price);
	newProduct.setSalePrice(salePrice);
	newProduct.setStock(quantity); 		// DB에 상품STOCK 디폴트값이 30이므로, 신규상품등록은 PD_STOCK에 입고수량을 넣는다.
	newProduct.setRecommended(recommended);
	
	if(fileName != null) {
		newProduct.setFileName(fileName);
	} else {
		newProduct.setFileName("이미지 없음");
	}
	
	ProductDao productDao = ProductDao.getInstance();
	productDao.insertNewProduct(newProduct);
	
	switch (categoryNo) {
	case 100 :
		response.sendRedirect("registerPdForm.jsp?categoryNo=100");
		break;
	case 200 :
		response.sendRedirect("registerPdForm.jsp?categoryNo=200");
		break;
	case 300 :
		response.sendRedirect("registerPdForm.jsp?categoryNo=300");
		break;
	case 400 :
		response.sendRedirect("registerPdForm.jsp?categoryNo=400");
		break;
	case 500 :
		response.sendRedirect("registerPdForm.jsp?categoryNo=500");
		break;
	}
	



%>