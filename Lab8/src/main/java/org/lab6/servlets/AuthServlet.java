package org.lab6.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import org.lab6.entity.UserEntity;
import org.lab6.repositories.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.logging.Logger;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private final UserRepository repo = new UserRepository();
    private static final Logger logger = Logger.getLogger(AuthServlet.class.getName());

    private void sendJsonError(HttpServletResponse resp, int code, String msg) throws IOException {
        resp.setStatus(code);
        resp.setContentType("application/json");
        resp.getWriter().print("{\"error\":\"" + msg.replace("\"","\\\"") + "\"}");
    }

    private String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            JSONObject json = new JSONObject(reader.lines().reduce("", String::concat));
            String login = json.getString("login");
            String password = json.getString("password");

            if (repo.findByLogin(login) != null) {
                sendJsonError(resp, HttpServletResponse.SC_CONFLICT, "User exists");
                return;
            }

            UserEntity user = new UserEntity(login, hash(password), "user");
            repo.create(user);

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("counter", 0);
            logger.info("User " + login + " registered, session: " + session.getId());

            resp.setHeader("X-Session-Id", session.getId());
            resp.setContentType("application/json");
            resp.getWriter().print("{\"status\":\"registered\"}");
        } catch (Exception e) {
            sendJsonError(resp, 500, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            JSONObject json = new JSONObject(reader.lines().reduce("", String::concat));
            String login = json.getString("login");
            String password = json.getString("password");

            UserEntity user = repo.findByLogin(login);
            if (user == null || !user.getPassword().equals(hash(password))) {
                sendJsonError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("counter", 0);
            logger.info("User " + login + " logged in, session: " + session.getId());

            resp.setHeader("X-Session-Id", session.getId());
            resp.setContentType("application/json");
            resp.getWriter().print("{\"status\":\"authorized\"}");
        } catch (Exception e) {
            sendJsonError(resp, 500, e.getMessage());
        }
    }
}
