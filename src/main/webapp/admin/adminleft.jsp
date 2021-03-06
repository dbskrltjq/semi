<%@page import="vo.User" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	User admin = (User)session.getAttribute("ADMIN");
%>
<div id="layoutSidenav_nav" class="h-100">
      <nav class="sb-sidenav accordion sb-sidenav-dark ms-0" id="sidenavAccordion" >
          <div class="sb-sidenav-menu">
              <div class="nav">
                  <div class="sb-sidenav-menu-heading">core</div>
                  <a class="nav-link" href="main.jsp">
                      <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                      main
                  </a>
                  <div class="sb-sidenav-menu-heading">User</div>
                  <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                      <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                      회원관리
                      <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                  </a>
                  <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                      <nav class="sb-sidenav-menu-nested nav">
                          <a class="nav-link" href="/semi/admin/userListForm.jsp?search=all&page=1">전체 회원 조회</a>
                          <a class="nav-link" href="/semi/admin/userListForm.jsp?search=deleted&page=1">탈퇴한 회원 조회</a>
                      </nav>
                  </div>
                 <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                      <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                      문의 및 리뷰 관리
                      <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                  </a>
                  <div class="collapse" id="collapsePages" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                      <nav class="sb-sidenav-menu-nested nav">
                          <a class="nav-link" href="/semi/admin/questionForm.jsp">1:1 문의</a>
                          <a class="nav-link" href="/semi/admin/reviewForm.jsp">등록된 리뷰</a>
                      </nav>
                  </div>
                  <div class="sb-sidenav-menu-heading">Product</div>
                  <a class="nav-link" href="registerPdForm.jsp">
                      <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                      새상품등록
                  </a>
                  <a class="nav-link" href="modifyPdForm.jsp">
                      <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                      상품정보수정
                  </a>
              </div>
          </div>
          <div class="sb-sidenav-footer">
              <div class="small">Logged in as:</div>
              <%=admin.getName() %>
        </div>
    </nav>
</div>
