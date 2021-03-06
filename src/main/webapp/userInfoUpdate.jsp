<%@page import="java.net.URLEncoder"%>
<%@page import="util.PasswordUtil"%>
<%@page import="dao.UserDao"%>
<%@page import="vo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error/500.jsp" %>

<%
	String job = request.getParameter("job");
	User user = (User) session.getAttribute("LOGINED_USER");

	if (job == null && user == null) {
		throw new RuntimeException("회원정보 수정은 로그인 후 사용가능한 서비스 입니다.");
	}

	
	UserDao userDao = UserDao.getInstance();
	
	String password = request.getParameter("password");
	
	
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String tel = request.getParameter("tel");
	String postcode = request.getParameter("postcode");
	String addr = request.getParameter("addr");
	String detailAddr = request.getParameter("detailAddr");
	
	
	// resetPassword.jsp에서 새로운비밀번호를 설정하는 경우
	// 이 경우 password 값은 사용자가 변경한 새로운 비밀번호이다.
	if (job != null && "resetPassword".equals(job)) {
		String foundEmail = request.getParameter("foundEmail");
		User savedUser = userDao.getUserByEmail(foundEmail);
		
		savedUser.setPassword(PasswordUtil.generateSecretPassword(savedUser.getId(), password));
		userDao.updateUser(savedUser);
		response.sendRedirect("resetPassword.jsp?name=" + URLEncoder.encode(savedUser.getName(), "utf-8"));
		return;
	}
	
	
	if (password != null) {	// 비밀번호 변경을 하는 사용자
		String secretPassword = PasswordUtil.generateSecretPassword(user.getId(), password);
		user.setPassword(secretPassword);
	}
	
	// 비밀번호 변경을 하지 않는 사용자인 경우에는 기존의 비밀번호를 사용하면 되므로 다시 수정할 필요가 없다.
	user.setName(name);
	user.setEmail(email);
	user.setTel(tel);
	user.setPostCode(postcode);
	user.setAddress(addr);
	user.setDetailAddress(detailAddr);
	
	userDao.updateUser(user);
	
	response.sendRedirect("myPageUpdateForm.jsp?name=" + URLEncoder.encode(name, "utf-8"));
%>