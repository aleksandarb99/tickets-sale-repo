package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.Comment;
import model.CommentState;
import model.Customer;
import model.Manifestation;


public class CommentDAO {
	private Map<Integer, Comment> comments = new HashMap<>();	
	
	public CommentDAO() {}
		
	public CommentDAO(String contextPath, ManifestationDAO dao, UserDAO userDao) {
		loadData(contextPath, dao, userDao);
	}
	
	public Comment find(int id) {
		return comments.containsKey(id) ? comments.get(id) : null;
	}
	
	public Collection<Comment> findAll() {
		return comments.values();
	}
	
	public Comment addComment(Comment comment) {
		Integer newId = comments.size() + 1;
		comment.setId(newId);
		comments.put(newId, comment);
		return comment;
	}
	
	private void loadData(String contextPath, ManifestationDAO dao, UserDAO userDao) {
		BufferedReader in = null;
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" +separator+ "comments.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					Integer id = Integer.parseInt(st.nextToken().trim());
					Customer customer = (Customer)userDao.find(st.nextToken().trim());
					Manifestation manifestation = dao.find(st.nextToken().trim());
					String text = st.nextToken().trim();
					Integer grade = Integer.parseInt(st.nextToken().trim());
					CommentState state = CommentState.valueOf(st.nextToken().trim());
					comments.put(id, 
					new Comment(id, customer, manifestation, text, grade, state));
				}		
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	public void saveData(String contextPath) {
		StringBuilder builder = new StringBuilder();
		for(Comment c : comments.values()) {
			builder.append(c.getId() + ";");
			builder.append(c.getCustomer().getUsername() + ";");
			builder.append(c.getManifestation().getName() + ";");
			builder.append(c.getText() + ";");
			builder.append(c.getGrade() + ";");
			builder.append(c.getState() + "\n");
		}
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" +separator+ "comments.txt");
			PrintWriter myWriter = new PrintWriter(file);
			myWriter.write(builder.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
