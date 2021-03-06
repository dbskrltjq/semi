package dao;

import java.sql.SQLException;
import java.util.List;

import helper.DaoHelper;
import vo.Category;
import vo.Product;

public class ProductDao {

	private static ProductDao instance = new ProductDao();
	private ProductDao() {}
	public static ProductDao getInstance() {
		return instance;
	}
	
	private DaoHelper helper = DaoHelper.getInstance();
	
	public Product getProductByNo(int pdNo) throws SQLException {
		
		String sql = "select * "
				   + "from sul_products "
				   + "where pd_no = ? ";
		
		return helper.selectOne(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setCategoryNo(rs.getInt("category_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setStock(rs.getInt("pd_stock"));
			product.setOnSale(rs.getString("pd_onsale"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setReviewCount(rs.getInt("pd_review_count"));
			product.setCompany(rs.getString("pd_company"));
			product.setSaleQuantity(rs.getInt("pd_sale_quantity"));
			product.setRecommended(rs.getString("pd_recommended"));
			product.setFileName(rs.getString("pd_file_name"));

			product.setImageUrl(rs.getString("pd_image_url")); 		// 빠져있어서 추가했습니다! by 유나

			
			return product;
		},pdNo);
	}
	
	public List<Product> getCompanyByKeyword(String keyword) throws SQLException {
		String sql = "select distinct pd_company "
				   + "from sul_products "
				   + "where pd_name like '%' || ? || '%' ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setCompany(rs.getString("pd_company"));
			return product;
			
		}, keyword);
	}
	
	public List<Product> getCompanyByKeywordCategory(String keyword, String categoryName) throws SQLException {
		String sql = "select distinct P.pd_company "
				   + "from sul_products P, sul_category C "
				   + "where pd_name like '%' || ? || '%' "
				   + "and P.category_no = C.category_no "
				   + "and C.category_name = ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setCompany(rs.getString("pd_company"));
			return product;
			
		}, keyword, categoryName);
	}
	
	public List<Product> getCompanyByKeywordCompany(String keyword, String company) throws SQLException {
		String sql = "select distinct P.pd_company "
				   + "from sul_products P, sul_category C "
				   + "where pd_name like '%' || ? || '%' "
				   + "and P.category_no = C.category_no "
				   + "and P.pd_company = ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setCompany(rs.getString("pd_company"));
			return product;
			
		}, keyword, company);
	}
	
	public List<Product> getCompanyByKeyword(String keyword, String categoryName, String company) throws SQLException {
		String sql = "select distinct P.pd_company "
				   + "from sul_products P, sul_category C "
				   + "where pd_name like '%' || ? || '%' "
				   + "and P.category_no = C.category_no "
				   + "and C.category_name = ? "
				   + "and P.pd_company = ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setCompany(rs.getString("pd_company"));
			return product;
			
		}, keyword, categoryName, company);
	}
	
	public int getTotalRows() throws SQLException {
		String sql = "select count(*) cnt from sul_products ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		});
	}
	
	public int getTotalRows(int categoryNo) throws SQLException {
		String sql = "select count(*) cnt from sul_products where category_No = ? ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, categoryNo);
	}
	
	public int getTotalRows(int categoryNo, int period) throws SQLException {
		String sql = "select count(*) cnt "
				   + "from sul_products "
				   + "where category_no = ? "
				   + "and pd_created_date >=  trunc( add_months ( sysdate, ? )) ";
		
		return helper.selectOne(sql, rs-> {
			return rs.getInt("cnt");
		}, categoryNo, period);
	}
	public int getRowsByCompanyKeyword(int categoryNo, int period, String keyword) throws SQLException {
		String sql = "select count(*) cnt "
				   + "from sul_products "
				   + "where category_no = ? "
				   + "and pd_created_date >=  trunc( add_months ( sysdate, ? )) "
				   + "and pd_company like '%' || ? || '%' ";
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, categoryNo, period, keyword);
	}
	
	public int getRowsByNameKeyword(int categoryNo, int period, String keyword) throws SQLException {
		String sql = "select count(*) cnt "
				   + "from sul_products "
				   + "where category_no = ? "
				   + "and pd_created_date >=  trunc( add_months ( sysdate, ? )) "
				   + "and pd_name like '%' || ? || '%' ";
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, categoryNo, period, keyword);
	}
	
	public int getTotalRows(String keyword) throws SQLException {
		String sql = "select count(*) cnt from sul_products where pd_name like '%' || ? || '%' ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, keyword);
	}
	
	public int getTotalRowsByCategory(String keyword, String categoryName) throws SQLException {
		String sql = "select count(*) cnt "
				+ "from sul_products P, sul_category C "
				+ "where pd_name like '%' || ? || '%' "
				+ "and P.category_no = C.category_no "
				+ "and C.category_name = ? ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, keyword, categoryName);
	}
	
	public int getTotalRowsByCompany(String keyword, String company) throws SQLException {
		String sql = "select count(*) cnt "
				+ "from sul_products P, sul_category C "
				+ "where pd_name like '%' || ? || '%' "
				+ "and P.category_no = C.category_no "
				+ "and P.pd_company = ? ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, keyword, company);
	}
	
	public int getTotalRows(String keyword, String categoryName, String company) throws SQLException {
		String sql = "select count(*) cnt "
				+ "from sul_products P, sul_category C "
				+ "where pd_name like '%' || ? || '%' "
				+ "and P.category_no = C.category_no "
				+ "and C.category_name = ? "
				+ "and P.pd_company = ? ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, keyword, categoryName, company);
	}
	
	public int getTotalQunatity(int categoryNo) throws SQLException {
		String sql = "select count(pd_no) cnt "
				+ "from sul_products "
				+ "where category_no = ? ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, categoryNo);
	}
	
	public int getTotalQunatity(String keyword) throws SQLException {
		String sql = "select count(pd_no) cnt "
				+ "from sul_products "
				+ "where pd_name like '%' || ? || '%' ";
		
		return helper.selectOne(sql, rs -> {
			return rs.getInt("cnt");
		}, keyword);
	} 
	
	public List<Product> getAllProducts(int beginIndex, int endIndex) throws SQLException {
		String sql = "select * "
				   + "from(select row_number() over (order by pd_sale_quantity desc) row_number, P.* "
				   + "     from sul_products P) " 
				   + "where row_number >= ?  and row_number <= ? ";
				
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setCategoryNo(rs.getInt("category_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setStock(rs.getInt("pd_stock"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setReviewCount(rs.getInt("pd_review_count"));
			product.setCompany(rs.getString("pd_company"));
			product.setSaleQuantity(rs.getInt("pd_sale_quantity"));
			product.setRecommended(rs.getString("pd_recommended"));
			product.setFileName(rs.getString("pd_file_name"));
			product.setCreatedDate(rs.getDate("pd_created_date"));
			product.setUpdatedDate(rs.getDate("pd_updated_date"));
			
			return product;
		}, beginIndex, endIndex);
	}
	

	/**
	 * 
	 * @param period 조회기간
	 * @param keyword 제조사 키워드
	 * @param categoryNo
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 * @throws SQLException
	 */
	public List<Product> getProductsByCompanyKeyword(int period, String keyword, int categoryNo, int beginIndex, int endIndex) throws SQLException {
		String sql = "select * "
				   + "from (select ROW_NUMBER() OVER (PARTITION BY category_no ORDER BY pd_no desc) row_number, P.* "
				   + "from sul_products P  where P.pd_created_date >= trunc( add_months ( sysdate, ? ))  and p.pd_company like '%'  || ? || '%') "
				   + "where category_no = ? "
				   + "and row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setCategoryNo(rs.getInt("category_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setStock(rs.getInt("pd_stock"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setReviewCount(rs.getInt("pd_review_count"));
			product.setCompany(rs.getString("pd_company"));
			product.setSaleQuantity(rs.getInt("pd_sale_quantity"));
			product.setRecommended(rs.getString("pd_recommended"));
			product.setFileName(rs.getString("pd_file_name"));
			product.setCreatedDate(rs.getDate("pd_created_date"));
			product.setUpdatedDate(rs.getDate("pd_updated_date"));
			
			return product;
		}, period, keyword ,categoryNo, beginIndex, endIndex);
	}
	
	public List<Product> getProductsByName(int period, String keyword, int categoryNo, int beginIndex, int endIndex) throws SQLException {
		String sql = "select * "
				+ "from (select ROW_NUMBER() OVER (PARTITION BY category_no ORDER BY pd_no desc) row_number, P.* "
				+ "from sul_products P  where P.pd_created_date >= trunc( add_months ( sysdate, ? ))  and p.pd_name like '%'  || ? || '%') "
				+ "where category_no = ? "
				+ "and row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setCategoryNo(rs.getInt("category_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setStock(rs.getInt("pd_stock"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setReviewCount(rs.getInt("pd_review_count"));
			product.setCompany(rs.getString("pd_company"));
			product.setSaleQuantity(rs.getInt("pd_sale_quantity"));
			product.setRecommended(rs.getString("pd_recommended"));
			product.setFileName(rs.getString("pd_file_name"));
			product.setCreatedDate(rs.getDate("pd_created_date"));
			product.setUpdatedDate(rs.getDate("pd_updated_date"));
			
			return product;
		}, period, keyword ,categoryNo, beginIndex, endIndex);
	}
	
	public List<Product> getProductsByCategoryNo(int period, int categoryNo, int beginIndex, int endIndex) throws SQLException {
		String sql = "select * "
				+ "from (select ROW_NUMBER() OVER (PARTITION BY category_no ORDER BY pd_no desc) row_number, P.* "
				+ "from sul_products P  where P.pd_created_date >= trunc( add_months ( sysdate, ? ))) "
				+ "where category_no = ? "
				+ "and row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setCategoryNo(rs.getInt("category_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setStock(rs.getInt("pd_stock"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setReviewCount(rs.getInt("pd_review_count"));
			product.setCompany(rs.getString("pd_company"));
			product.setSaleQuantity(rs.getInt("pd_sale_quantity"));
			product.setRecommended(rs.getString("pd_recommended"));
			product.setFileName(rs.getString("pd_file_name"));
			product.setCreatedDate(rs.getDate("pd_created_date"));
			product.setUpdatedDate(rs.getDate("pd_updated_date"));
			
			return product;
		}, period, categoryNo, beginIndex, endIndex);
	}
	
	
	
	
	public List<Product> getProductsByCategoryNo(int categoryNo) throws SQLException {
		String sql = "select * from sul_products where category_no = ? order by pd_no ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setCategoryNo(rs.getInt("category_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setStock(rs.getInt("pd_stock"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setReviewCount(rs.getInt("pd_review_count"));
			product.setCompany(rs.getString("pd_company"));
			product.setSaleQuantity(rs.getInt("pd_sale_quantity"));
			product.setRecommended(rs.getString("pd_recommended"));
			product.setFileName(rs.getString("pd_file_name"));
			product.setCreatedDate(rs.getDate("pd_created_date"));
			product.setUpdatedDate(rs.getDate("pd_updated_date"));
			
			return product;
		}, categoryNo);
	}

	
	public List<Product> getItemBySaleQuantity(int categoryNo, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_sale_quantity desc) row_number, pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where category_no = ?) "
				+ "where row_number >= ? and row_number <= ?";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));

			return product;
		}, categoryNo, beginIndex, endIndex);
	}		
	
	public List<Product> getItemBySaleQuantity(String keyword, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_sale_quantity desc) row_number, pd_no,  pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where pd_name like '%' || ? || '%') "
				+ "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, beginIndex, endIndex);
	}
	
	public List<Product> getItemBySaleQuantityCategory(String keyword, String categoryName, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_quantity desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, beginIndex, endIndex);
	}
	
	public List<Product> getItemBySaleQuantityCompany(String keyword, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_quantity desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemBySaleQuantity(String keyword, String categoryName, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_quantity desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ? "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMinPrice(int categoryNo, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_sale_price asc) row_number, pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where category_no = ? ) "
				+ "where row_number >= ? and row_number <= ?";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, categoryNo, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMinPrice(String keyword, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_sale_price asc) row_number, pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where pd_name like '%' || ? || '%') "
				+ "where row_number >= ? and row_number <= ?";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMinPriceCategory(String keyword, String categoryName, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_price asc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ?) "

				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMinPriceCompany(String keyword, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_price asc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMinPrice(String keyword, String categoryName, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_price asc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ? "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMaxPrice(int categoryNo, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_sale_price desc) row_number, pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where category_no = ? ) "
				+ "where row_number >= ? and row_number <= ?";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, categoryNo, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMaxPrice(String keyword, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_sale_price desc) row_number, pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where pd_name like '%' || ? || '%') "
				+ "where row_number >= ? and row_number <= ?";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMaxPriceCategory(String keyword, String categoryName, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_price desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMaxPriceCompany(String keyword, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_price desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByMaxPrice(String keyword, String categoryName, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_price desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ? "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByDate(int categoryNo, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_created_date desc) row_number, pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where category_no = ? ) "
				+ "where row_number >= ? and row_number <= ?";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, categoryNo, beginIndex, endIndex);
	}
	
	public List<Product> getItemByDate(String keyword, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from(select row_number() over (order by pd_created_date desc) row_number, pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				+ "from sul_products where pd_name like '%' || ? || '%' ) "
				+ "where row_number >= ? and row_number <= ?";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, beginIndex, endIndex);
	}
	
	public List<Product> getItemByDateCategory(String keyword, String categoryName, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_created_date desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ?) "

				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, beginIndex, endIndex);
	}
	
	public List<Product> getItemByDateCompany(String keyword, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_created_date desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByDate(String keyword, String categoryName, String company, int beginIndex, int endIndex) throws SQLException {
		
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_created_date desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ? "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, keyword, categoryName, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByOptionCategory(String keyword, String categoryName, int beginIndex, int endIndex) throws SQLException {
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_quantity desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
			
		}, keyword, categoryName, beginIndex, endIndex);
	}
	
	public List<Product> getItemByOptionCompany(String keyword, String company, int beginIndex, int endIndex) throws SQLException {
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_quantity desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));	
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
			
		}, keyword, company, beginIndex, endIndex);
	}
	
	public List<Product> getItemByOption(String keyword, String categoryName, String company, int beginIndex, int endIndex) throws SQLException {
		String sql = "select pd_no, pd_name, pd_price, pd_sale_price, pd_review_score, pd_image_url "
				   + "from(select row_number() over (order by pd_sale_quantity desc) row_number, P.pd_no, P.pd_name, P.pd_price, P.pd_sale_price, P.pd_review_score, P.pd_image_url "
				   + "     from sul_products P, sul_category C "
			   	   + "     where pd_name like '%' || ? || '%' "
				   + "     and P.category_no = C.category_no "
				   + "     and C.category_name = ? "
				   + "	   and P.pd_company = ?) "
				   + "where row_number >= ? and row_number <= ? ";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
			
		}, keyword, categoryName, company, beginIndex, endIndex);
	}
	
	
	
	/**	시퀀스부분 수정?
	 * 신규상품등록하기, DB에 상품STOCK 디폴트값이 30이므로, 신규상품등록은 PD_STOCK에 입고수량을 넣는다.
	 * @param product
	 * @throws SQLException
	 */
	public void insertNewProduct(Product product) throws SQLException {
		String sql = "insert into sul_products (PD_NO, CATEGORY_NO, PD_NAME, PD_COMPANY, PD_PRICE, PD_SALE_PRICE, PD_STOCK, PD_RECOMMENDED, PD_FILE_NAME)"
				   + "values(sul_products_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		helper.insert(sql, product.getCategoryNo(), product.getName(), product.getCompany(), product.getPrice(), product.getSalePrice(), product.getStock(), product.getRecommended(),product.getFileName());
	}
	
	public void updateProductReviewCount(Product product) throws SQLException {
		String sql = "update sul_products "
				   + "set "
				   + "		pd_review_count = ?"
				   + "where pd_no = ? ";
				   
		helper.update(sql, product.getReviewCount(), product.getNo());
	}
	
	
	/**
	 * modifyProduct.jsp 에서 사용되는 상품수정용 메소드
	 * @param product
	 * @throws SQLException
	 */
	public void modifyProduct(Product product) throws SQLException {
		String sql = "update sul_products "
				   + " set " 
				   + "     category_no = ?, "
				   + "     pd_name = ?, "
				   + "     pd_company = ?, "
				   + "     pd_price = ?, "
				   + "     pd_sale_price = ?, "
				   + "     pd_stock = ?, "
				   + "     pd_recommended = ?, "
				   + "     pd_updated_date = sysdate "
				   + "where pd_no = ? ";
		
		helper.update(sql, product.getCategoryNo(), product.getName(), product.getCompany(), product.getPrice(), product.getSalePrice(), product.getStock(), product.getRecommended(), product.getNo());
	}
	
	/**
	 * deleteProduct.jsp에서 사용되는 상품삭제 메소드, 메소드명은 d
	 * @param productNo
	 * @throws SQLException
	 */
	public void deleteProduct(int productNo) throws SQLException {
		String sql = "delete from sul_products "
				   + "where pd_no = ? ";
		
		helper.delete(sql, productNo);
	}
	
	public Product getUrlByNo(Integer pdNo) throws SQLException {
		String sql = "select pd_no, pd_name, pd_sale_price, pd_image_url "
				   + "from sul_products "
				   + "where pd_no = ? ";
		
		return helper.selectOne(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setName(rs.getString("pd_name"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setImageUrl(rs.getString("pd_image_url"));
			
			return product;
		}, pdNo);
	}
	
	// 220629 민지 추가. 추천 상품 조회하는 데 사용됨 (HOME)
	public List<Product> getPdByRecommend() throws SQLException {
		
		String sql = "select * "
				+ "from sul_products "
				+ "where PD_RECOMMENDED = 'Y'";
		
		return helper.selectList(sql, rs -> {
			Product product = new Product();
			product.setNo(rs.getInt("pd_no"));
			product.setCategoryNo(rs.getInt("category_no"));
			product.setName(rs.getString("pd_name"));
			product.setPrice(rs.getInt("pd_price"));
			product.setSalePrice(rs.getInt("pd_sale_price"));
			product.setStock(rs.getInt("pd_stock"));
			product.setOnSale(rs.getString("pd_onsale"));
			product.setReviewScore(rs.getInt("pd_review_score"));
			product.setReviewCount(rs.getInt("pd_review_count"));
			product.setCompany(rs.getString("pd_company"));
			product.setSaleQuantity(rs.getInt("pd_sale_quantity"));
			product.setRecommended(rs.getString("pd_recommended"));
			product.setFileName(rs.getString("pd_file_name"));
			product.setImageUrl(rs.getString("pd_image_url")); 		// 빠져있어서 추가했습니다! by 유나
			
			return product;
		});
	}
}
