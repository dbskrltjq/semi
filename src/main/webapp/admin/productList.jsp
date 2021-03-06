<%@page import="vo.Pagination"%>
<%@page import="vo.Product"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDao"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="util.StringUtil"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%
	ProductDao productDao = ProductDao.getInstance();

	int categoryNo = StringUtil.stringToInt(request.getParameter("categoryNo"));
	int period = StringUtil.stringToInt(request.getParameter("period"));
	int rows = StringUtil.stringToInt(request.getParameter("rows"));
	int currentPage = StringUtil.stringToInt(request.getParameter("pageNo"));
	
	
	String search = request.getParameter("search");
	String keyword = request.getParameter("keyword");
	//System.out.println("categoryNo: " + categoryNo + ", period: " + period + ", rows: " + rows +", currentPage: " + ", search: " + search + ", keyword: " + keyword);
	
	
	Map<String, Object> result = new HashMap<>();
	
	int totalRows = 0;
	if(search == null || search.isEmpty()) {						// registerPdForm.jsp에서 상품목록을 출력할 때는 search와 keyword값이 null(input설정 안함)
		totalRows = productDao.getTotalRows(categoryNo, period);	// modifyProductForm.jsp에서는 검색조건을 설정하지 않을 경우 search와 keyword값이 없으므로 
	} else if("company".equals(search)) {
		totalRows = productDao.getRowsByCompanyKeyword(categoryNo, period, keyword);
	} else if("name".equals(search)) {
		totalRows = productDao.getRowsByNameKeyword(categoryNo, period, keyword);
	}
	//System.out.println("totalRows: " + totalRows);
	
	Pagination pagination = new Pagination(rows, totalRows, currentPage);
	
	List<Product> products = null;
	if(search == null || search.isEmpty()) {
		products = productDao.getProductsByCategoryNo(period, categoryNo, pagination.getBeginIndex(), pagination.getEndIndex());
	} else if("company".equals(search)) {
		products = productDao.getProductsByCompanyKeyword(period, keyword, categoryNo, pagination.getBeginIndex(), pagination.getEndIndex());
	} else if("name".equals(search)) {
		products = productDao.getProductsByName(period, keyword, categoryNo, pagination.getBeginIndex(), pagination.getEndIndex());
	}

	result.put("pagination", pagination);
	result.put("products", products);

	Gson gson = new Gson();
	String jsonText = gson.toJson(result);
 	out.write(jsonText);


%>