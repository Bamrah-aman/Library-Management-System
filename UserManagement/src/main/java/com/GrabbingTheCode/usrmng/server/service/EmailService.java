package com.GrabbingTheCode.usrmng.server.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void send(String to, String templateName, String userName, String confirmationUrl)
            throws MessagingException {
        //Let's check is the template is empty or not
        if(!StringUtils.hasLength(templateName)) {
            templateName = "confirm-email";
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", userName);
        properties.put("confirmationUrl", confirmationUrl);

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(properties);

        mimeMessageHelper.setFrom("iamamandeep.singh25@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("Welcome to GRABBING THE CODE");

        String template = templateEngine.process(templateName, thymeleafContext);
        mimeMessageHelper.setText(template, true);
        mailSender.send(mimeMessage);
    }
}
