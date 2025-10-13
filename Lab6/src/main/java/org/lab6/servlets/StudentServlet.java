package org.lab6.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lab6.entity.StudentEntity;
import org.lab6.repositories.StudentRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/students/*")
public class StudentServlet extends HttpServlet {
    private final StudentRepository repo = new StudentRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();

        try (PrintWriter out = resp.getWriter()) {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<StudentEntity> students = repo.findAll();
                JSONArray arr = new JSONArray();
                for (StudentEntity s : students) {
                    arr.put(new JSONObject(s));
                }
                out.print(arr.toString());
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                StudentEntity s = repo.findById(id);
                if (s != null)
                    out.print(new JSONObject(s));
                else
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            JSONObject json = new JSONObject(reader.lines().reduce("", String::concat));
            StudentEntity s = new StudentEntity(json.getString("name"), json.getString("group"), json.getDouble("avgGrade"));
            repo.create(s);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(400, "Missing ID");
            return;
        }

        int id = Integer.parseInt(pathInfo.substring(1));

        try (BufferedReader reader = req.getReader()) {
            StudentEntity existedStudent = repo.findById(id);

            if (existedStudent == null){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            }

            JSONObject json = new JSONObject(reader.lines().reduce("", String::concat));
            StudentEntity s = new StudentEntity(
                    id,
                    json.getString("name"),
                    json.getString("group"),
                    json.getDouble("avgGrade"));
            repo.update(s);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(400, "Missing ID");
            return;
        }

        int id = Integer.parseInt(pathInfo.substring(1));
        try {
            StudentEntity existedStudent = repo.findById(id);

            if (existedStudent == null){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            }

            repo.delete(id);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
