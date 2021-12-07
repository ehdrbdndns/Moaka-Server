package com.moaka.service;

import com.moaka.dto.Mail;
import com.moaka.dto.MailCode;
import com.moaka.dto.User;
import com.moaka.mapper.AuthMapper;
import com.moaka.mapper.MailMapper;
import com.moaka.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
@AllArgsConstructor
@Transactional
public class MailService {
    @Autowired
    MailMapper mailMapper;
    @Autowired
    UserMapper userMapper;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "moaka.team@gmail.com";

    /**
     * setTo: 받는 사람 주소
     * setFrom: 보내는 사람 주소
     * setSubject: 제목
     * setText: 메세지 내용
     */
    public JSONObject mailSendOfRegister(Mail mail) {
        JSONObject result = new JSONObject();
        User user = userMapper.retrieveUserById(mail.getAddress());
        if (user == null) {
            MailCode mailCode = new MailCode();

            int code = (int) (Math.random() * 10000) + 10000;
            mail.setMessage(code + "");
            mailCode.setCode(code);
            mailCode.setRegdate(getToday());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail.getAddress());
            message.setFrom(MailService.FROM_ADDRESS);
            message.setSubject("모아카 회원가입 인증번호입니다.");
            message.setText(mail.getMessage());

            mailSender.send(message);

            mailMapper.insertMailCode(mailCode);

            result.put("isSuccess", true);
            result.put("no", mailCode.getNo());
        } else {
            result.put("isSuccess", false);
            result.put("no", 0);
        }

        return result;
    }

    /**
     * setTo: 받는 사람 주소
     * setFrom: 보내는 사람 주소
     * setSubject: 제목
     * setText: 메세지 내용
     */
    public JSONObject mailSend(Mail mail, String subject) {
        JSONObject result = new JSONObject();

        MailCode mailCode = new MailCode();

        int code = (int) (Math.random() * 10000) + 10000;
        mail.setMessage(code + "");
        mailCode.setCode(code);
        mailCode.setRegdate(getToday());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(subject);
        message.setText(mail.getMessage());

        mailSender.send(message);

        mailMapper.insertMailCode(mailCode);

        result.put("isSuccess", true);
        result.put("no", mailCode.getNo());

        return result;
    }

    public JSONObject isExistCode(int no, int code) {
        JSONObject result = new JSONObject();
        boolean isExistCode = mailMapper.isExistCode(no, code);
        if (isExistCode) {
            // 코드 존재
            mailMapper.expireValidOfMailCode(no);
            result.put("isSuccess", true);
        } else {
            // 코드가 존재하지 않음
            result.put("isSuccess", false);
        }

        return result;
    }

    public JSONObject expireMailCode(int no) {
        JSONObject result = new JSONObject();
        mailMapper.expireValidOfMailCode(no);
        result.put("isSuccess", true);
        return result;
    }

    @Scheduled(cron = "0 0 23 * * *")
    public void resetMailCode() {
        String today = getToday();
        System.out.println("메일 코드 초기화: " + today);
        mailMapper.resetMailCode(today);
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}
