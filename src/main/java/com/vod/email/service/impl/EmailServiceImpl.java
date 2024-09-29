package com.vod.email.service.impl;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.vod.email.exception.EmailServiceException;
import com.vod.email.service.EmailService;
import com.vod.oauth.model.UserVerifyToken;
import com.vod.util.HelperBean;

@Service
public class EmailServiceImpl implements EmailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private HelperBean helper;
	

	@Override
	public void sendEmailActivationMessage(UserVerifyToken uvt) throws EmailServiceException {
		
try {

	
	        String activationLink = helper.getEmailConfirmationLink(uvt.getVerifyToken(), uvt.getId());
	        String appLogoUrl = helper.getAppLogoUrl();
	        String subject = helper.getEmailActivationEmailSubject();
	        String emailFrom = helper.getActivationEmailFrom();
	        
	        if( null == activationLink)
	        {
	        	throw new EmailServiceException("EmailActivation Link can not be null");
	        }
	        
	        MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper msgHelper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
	    
	        VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("name", uvt.getFirstName());
			velocityContext.put("activationLink", activationLink);
			velocityContext.put("appLogoUrl", appLogoUrl);
			
			StringWriter stringWriter = new StringWriter();
			velocityEngine.mergeTemplate("templates/activation-template.vm", "UTF-8", velocityContext, stringWriter);
	        
	        String html = stringWriter.toString();
	        
	       // FileUtils.writeStringToFile(new File("activationEmail.html"), html, StandardCharsets.UTF_8);
	        msgHelper.setTo(uvt.getEmailAddress());
	        msgHelper.setText(html, true);
	        msgHelper.setSubject(subject);
	        msgHelper.setFrom(emailFrom);
	        emailSender.send(message);
	        
	        
		}
		catch(Exception e)
		{
			logger.error("Error in Sending Email Service:"+e.getMessage(), e);
			throw new EmailServiceException(e.getMessage(), e);
		}

		
	}

}
