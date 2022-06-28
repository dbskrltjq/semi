package dao;

import java.sql.SQLException;
import java.util.List;

import dto.QuestionDto;
import helper.DaoHelper;
import vo.Question;

public class ProductQuestionDao {

	private static ProductQuestionDao instance = new ProductQuestionDao();
	private ProductQuestionDao() {}
	public static ProductQuestionDao getInstance() {
		return instance;
	}
	
	private DaoHelper helper = DaoHelper.getInstance();
	
	public void insertQuestion(Question question) throws SQLException {
		String sql = "insert into sul_questions "
			     + "(q_no, user_no, pd_no, q_title, q_content) "
			     + "values "
			     + "(sul_questions_seq.nextval, ?, ?, ?, ?) ";
		
		helper.insert(sql, question.getUserNo(), question.getPdNo(), question.getTitle(), question.getContent());
	}
	
	public void addAnswer(QuestionDto questionDto) throws SQLException {
		String sql = "update sul_questions "
				   + " set "
				   + "   a_content = ?, "
				   + "   a_created_date = sysdate,  "
				   + "   a_answered = ? "
				   + "where q_no = ? ";
		
		helper.update(sql, questionDto.getAnswerContent(), questionDto.getAnswered(), questionDto.getNo());
	}
	
	public QuestionDto getProductQuestion(int questionNo) throws SQLException {
		String sql = "select * "
				   + "from sul_questions "
				   + "where q_no = ? ";
		return helper.selectOne(sql, rs -> {
			QuestionDto question = new QuestionDto();
			question.setNo(rs.getInt("q_no"));
			question.setUserNo(rs.getInt("user_no"));
			question.setPdNo(rs.getInt("pd_no"));
			question.setTitle(rs.getString("q_title"));
			question.setContent(rs.getString("q_content"));
			question.setAnswerContent(rs.getString("a_content"));
			question.setDeleted(rs.getString("q_deleted"));
			question.setCreatedDate(rs.getDate("q_created_date"));
			question.setUpdatedDate(rs.getDate("q_updated_date"));
			question.setAnswerCreatedDate(rs.getDate("a_created_date"));
			question.setAnswered(rs.getString("a_answered"));
			
			return question;
		}, questionNo);
	}
	
	public List<QuestionDto> getProductQuestions(int productNo) throws SQLException {
		String sql = "select P.category_no, Q.q_no, U.user_no, U.user_name, Q.pd_no, Q.q_title, Q.q_content, "
				   + "Q.a_content, Q.q_deleted, Q.q_created_date, Q.q_updated_date, Q.a_created_date, Q.a_answered "
				   + "from sul_questions Q, sul_products P, sul_users U "
				   + "where Q.user_no = U.user_no "
				   + "and Q.pd_no = P.pd_no "
				   + "and Q.pd_no = ? ";
		
		return helper.selectList(sql, rs -> {
			QuestionDto question = new QuestionDto();
			question.setCategoryNo(rs.getInt("category_no"));
			question.setNo(rs.getInt("q_no"));
			question.setUserNo(rs.getInt("user_no"));
			question.setUserName(rs.getString("user_name"));
			question.setPdNo(rs.getInt("pd_no"));
			question.setTitle(rs.getString("q_title"));
			question.setContent(rs.getString("q_content"));
			question.setAnswerContent(rs.getString("a_content"));
			question.setDeleted(rs.getString("q_deleted"));
			question.setCreatedDate(rs.getDate("q_created_date"));
			question.setUpdatedDate(rs.getDate("q_updated_date"));
			question.setAnswerCreatedDate(rs.getDate("a_created_date"));
			question.setAnswered(rs.getString("a_answered"));
			
			return question;
			
		},productNo);
	}
}
