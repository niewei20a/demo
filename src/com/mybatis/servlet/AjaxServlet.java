package com.mybatis.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mybatis.pojo.User;
import com.mybatis.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AjaxServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    private void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");

        int id = Integer.valueOf(req.getParameter("provCode"));
        SqlSession sqlSession = MyBatisUtil.getSession();
        List<User> user = sqlSession.selectList("wzujb.findUserById", id);
        JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        String jsonStr = JSON.toJSONString(user, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        sqlSession.close();
        resp.getWriter().write(jsonStr);
        System.out.println("DATE == " + jsonStr);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        SqlSession sqlSession = MyBatisUtil.getSession();
        List<User> user = sqlSession.selectList("wzujb.findAllUsers");
        JSONObject.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        String jsonStr = JSON.toJSONString(user, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        sqlSession.close();
        resp.getWriter().write(jsonStr);
        System.out.println(jsonStr);
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        String method = request.getParameter("method");
        System.out.println("method = " + method);
        if (null != method && !"".equals(method)) {
            if ("findById".equals(method)) {
                findById(request, response);
            } else if ("FindAll".equals(method)) {
                findAll(request, response);
            } else if ("addUser".equals(method)) {
                addUser(request, response);
            } else if ("Update".equals(method)) {
                update(request, response);
            } else if ("delete".equals(method)) {
                delete(request, response);
            }
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        rep.setContentType("text/html; charset=UTF-8");
        rep.setCharacterEncoding("utf-8");
        SqlSession sqlSession = MyBatisUtil.getSession();
        System.out.println("id = " + req.getParameter("id") );
        int id = Integer.parseInt(req.getParameter("id").trim());
        sqlSession.delete("wzujb.deleteUserById", id);
        sqlSession.commit();
        sqlSession.close();
        String jsonStr = JSON.toJSONString("删除成功");
        rep.getWriter().write(jsonStr);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");
        String name = req.getParameter("username");
        String sex = req.getParameter("sex");
        String address = req.getParameter("address");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date day = null;
        try {
            day = df.parse(req.getParameter("birthday"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int id = Integer.parseInt(req.getParameter("id"));
        User user = new User(id, name, sex, day, address);
        System.out.println("用户 = " + user);
        SqlSession sqlSession = MyBatisUtil.getSession();
        sqlSession.update("wzujb.updateUser", user);
        sqlSession.commit();
        sqlSession.close();
        String jsonStr = JSON.toJSONString("更新成功");
        resp.getWriter().write(jsonStr);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");

        System.out.println(" day is " + req.getParameter("username"));
        String name = req.getParameter("username");
        String sex = req.getParameter("sex");
        String address = req.getParameter("address");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date day = null;
        try {
            day = df.parse(req.getParameter("birthday"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = new User(name, sex, day, address);
        System.out.println("User = " + user);
        SqlSession sqlSession = MyBatisUtil.getSession();
        sqlSession.insert("wzujb.insertUser", user);
        sqlSession.commit();
        sqlSession.close();
        String jsonStr = JSON.toJSONString("添加成功");
        resp.getWriter().write(jsonStr);
    }
}
