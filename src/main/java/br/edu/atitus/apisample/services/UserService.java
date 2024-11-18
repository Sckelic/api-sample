package br.edu.atitus.apisample.services;

import java.util.regex.Pattern;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;
	
	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}


	public UserEntity save(UserEntity newUser) throws Exception {
		if(newUser == null)
			throw new Exception("Objeto null");
		if(newUser.getName() == null || newUser.getName().isEmpty()) 
			throw new Exception ("Nome Invalido");
		newUser.setName(newUser.getName().trim());
		if(newUser.getEmail() == null || newUser.getEmail().isEmpty())
			throw new Exception ("Email Invalido");
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher Matcher = pattern.matcher(newUser.getEmail());
		if (!Matcher.matches()) 
			throw new Exception("Email Inalido");
		this.repository.save(newUser);

		newUser.setEmail(newUser.getEmail().trim());
		
		if(repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Ja existe usuario com este email.");
		//TODO invocar metodo camada repository
		return newUser;
	}
	
	public List<UserEntity> findAll() throws Exception{
		return repository.findAll();
	}
}