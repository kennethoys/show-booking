package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Phone;
import repository.PhoneRepository;

@Service
public class PhoneService {
	
	@Autowired
	PhoneRepository phoneRepository;
	
	public Phone createPhone(String phoneNumber) {
		Phone phone = new Phone(phoneNumber);
		phoneRepository.save(phone);
		return phone;
	}
	
	public Phone getPhoneByPhoneNumber(String phoneNumber) {
		return phoneRepository
				.findAll()
				.stream()
				.filter(phone -> phone.getPhoneNumber().equals(phoneNumber))
				.findFirst()
				.orElse(null);
	}
	
	public Phone getOrCreatePhone(String phoneNumber) {
		Phone phone = getPhoneByPhoneNumber(phoneNumber);
		
		if(phone == null) {
			return createPhone(phoneNumber);
		}
		
		System.out.println("Phone id: " + phone.getPhoneId());
		System.out.println("Phone number: " + phone.getPhoneNumber());
		
		return phone;
	}
}
