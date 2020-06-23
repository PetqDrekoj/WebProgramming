package webubb.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import webubb.domain.Question;
import webubb.domain.Response;
import webubb.domain.User;
import webubb.model.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class QuizController extends HttpServlet {

    public QuizController() {
        super();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || session.getAttribute("user") == "") {
            response.getWriter().print("false");
        } else {
            int number_questions = Integer.parseInt(request.getParameter("number_questions"));
            int size = Integer.parseInt(request.getParameter("questions_per_page"));
            session.setAttribute("number_questions", number_questions);
            session.setAttribute("questions_per_page", size);
            response.getWriter().print("true");
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null || session.getAttribute("user") == "") {
            response.setContentType("text");
            PrintWriter p = new PrintWriter(response.getOutputStream());
            p.print("false");
            p.flush();
        } else {
            int nr_question = (int) session.getAttribute("number_questions");
            int questions_per_page = (int) session.getAttribute("questions_per_page");
            DBManager db = new DBManager();
            int score;
            try {
                score = Integer.parseInt(request.getParameter("score"));
            } catch (Exception e) {
                score = -1;
            }
            if (score == -1) {
                int page = Integer.parseInt(request.getParameter("page"));
                try {
                    ArrayList<Question> questions = db.getQuestions(nr_question, page, questions_per_page);
                    if (questions.size() > 0) {
                        response.setContentType("application/json");
                        JSONArray questionJson = new JSONArray();
                        questions.forEach(q -> {
                            JSONObject question = new JSONObject();
                            question.put("question", q.getQuestion());
                            question.put("question_id", "" + q.getId());
                            ArrayList<Response> responseFromQuestion = db.getResponseFromQuestion(q.getId());
                            for (int i = 0; i < responseFromQuestion.size(); i++) {
                                question.put("" + i, responseFromQuestion.get(i).getResponse());
                                if (responseFromQuestion.get(i).isCorrect()) question.put("value", "" + i);
                            }
                            questionJson.add(question);
                        });
                        PrintWriter p = new PrintWriter(response.getOutputStream());
                        p.println(questionJson.toJSONString());
                        p.flush();
                    } else {
                        response.setContentType("text");
                        PrintWriter p = new PrintWriter(response.getOutputStream());
                        p.print("false");
                        p.flush();
                    }
                } catch (Exception e) {
                    response.setContentType("text");
                    PrintWriter p = new PrintWriter(response.getOutputStream());
                    p.print("false");
                    p.flush();
                }
            }
            else{
                response.setContentType("text");
                PrintWriter p = new PrintWriter(response.getOutputStream());
                User user =(User)session.getAttribute("user");
                p.print(""+user.getScore());
                p.print(","+nr_question);
                int oldScore = db.getScore(user.getUsername());
                if(oldScore < score)
                {
                    db.saveScore(user.getUsername(),score);
                    user.setScore(score);
                }
                p.flush();
            }
        }
    }

}
