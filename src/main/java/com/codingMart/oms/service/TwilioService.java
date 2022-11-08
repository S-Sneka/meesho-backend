package com.codingMart.oms.service;

import org.springframework.stereotype.Service;

import com.codingMart.oms.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioService {
	
	public TwilioConfig twilioConfig;
	public TwilioService(TwilioConfig twilioConfig) {
		this.twilioConfig = twilioConfig;
	}
	
	public void sendSMS(String msg,String number) {
//		PhoneNumber to = new PhoneNumber(number);
//		PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
//		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
//        Message.creator(to,from,msg).create();
		System.out.println("\n"+msg+"\n");
	}
}
