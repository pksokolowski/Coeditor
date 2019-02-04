package com.github.pksokolowski.coeditor;

import com.github.pksokolowski.coeditor.model.Invitation;
import com.github.pksokolowski.coeditor.model.User;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import com.github.pksokolowski.coeditor.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CoeditorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CoeditorApplication.class, args);
	}

	@Autowired
	private InvitationsRepository invitationsRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public void run(String... args) throws Exception {
		var usersCount = usersRepository.count();
		var invitationsCount = invitationsRepository.count();
		if(usersCount == 0 && invitationsCount == 0){
			// creates the first invitation so admin can register their account.
			// invitation code is equal to 0.
			// usage: go to /register?invitationCode=0
			var adminInvitation = new Invitation(1L, 0L);
			invitationsRepository.save(adminInvitation);
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

