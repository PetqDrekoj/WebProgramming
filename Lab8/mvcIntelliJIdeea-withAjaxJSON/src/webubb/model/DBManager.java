package webubb.model;

import webubb.domain.Question;
import webubb.domain.Response;
import webubb.domain.User;

import java.sql.*;
import java.util.ArrayList;


public class DBManager {
    private Statement stmt;

    public DBManager() {
        connect();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab8", "root", "");
            stmt = con.createStatement();
        } catch(Exception ex) {
            System.out.println("eroare la connect:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        ResultSet rs;
        User u = null;
        System.out.println(username+" "+password);
        try {
            rs = stmt.executeQuery("select * from users where user='"+username+"' and password='"+password+"'");
            if (rs.next()) {
                u = new User(rs.getInt("id"), rs.getString("user"), rs.getString("password"),rs.getInt("score"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public void saveScore(String username, int score){

        try {
            stmt.executeUpdate("update users set score="+score+" where user='" + username+ "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getScore(String username){
        ResultSet rs;
        int score = 0;
        try {
            rs = stmt.executeQuery("select * from users where user='"+username+"'");
            if (rs.next()) {
                score = rs.getInt("score");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public ArrayList<Question> getQuestions(int numberOfQuestions, int page, int size) {
        ArrayList<Question> questions = new ArrayList<Question>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from questions");
            while (rs.next()) {
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("question")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Question> paginatedQuestions = new ArrayList<Question>();
        int startingQuestionIndex = (page-1)*size;
        int endingQuestionIndex = page*size;
        while(startingQuestionIndex < numberOfQuestions && startingQuestionIndex<endingQuestionIndex && startingQuestionIndex<questions.size()){
            paginatedQuestions.add(questions.get(startingQuestionIndex));
            startingQuestionIndex += 1;
        }
        return paginatedQuestions;
    }

    public ArrayList<Response> getResponseFromQuestion(int question_id) {
        ArrayList<Response> responses = new ArrayList<Response>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from responses where question_id =" + question_id);
            while (rs.next()) {
                responses.add(new Response(
                        rs.getInt("id"),
                        rs.getString("response"),
                        rs.getInt("question_id"),
                        rs.getBoolean("correct")
                        ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responses;
    }

}