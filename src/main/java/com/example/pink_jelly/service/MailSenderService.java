package com.example.pink_jelly.service;

public interface MailSenderService {
    boolean sendMailByAddMember(String mailTo) throws Exception;
    String getConfirmKey();
}
