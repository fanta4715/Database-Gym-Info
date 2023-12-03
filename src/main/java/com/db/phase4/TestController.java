//package com.db.phase4;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Controller
//@RequiredArgsConstructor
//public class TestController {
//    private final UserDao userDao;
//
//    @GetMapping("/")
//    public String index() throws SQLException {
//        ResultSet rs = userDao.selectAllUsers();
//        while(rs.next()){
//            System.out.println(rs.getString(1));
//        }
//        return "success!!";
//    }
//
//    @PostMapping("/test")
//    public String test(Model model) throws SQLException {
//        //서비스에서
//        return "review";
//    }
//}
