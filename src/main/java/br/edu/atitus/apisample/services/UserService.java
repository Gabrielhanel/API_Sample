package br.edu.atitus.apisample.services;

import org.springframework.stereotype.Service;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service // Gerenciado pelo Spring
public class UserService {
	
	//Essa classe possui uma dependencia de um objeto repository
	private final UserRepository repository;

	//no metodo construtor existe a injeção de dependencia
	 public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + 
             "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

	public UserEntity save(UserEntity newUser) throws Exception {
		// TODO validar regras de negocios
		if (newUser == null)
				throw new Exception("Objeto nulo!");
		
		if (newUser.getName() == null || newUser.getName().isEmpty())
				throw new Exception("Nome invalido!");
		
		if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
            throw new Exception("E-mail inválido!");

		// REGEX
        if (!validarEmail(newUser.getEmail()))
            throw new Exception("E-mail inválido!");
            
        if(newUser.getPassword() == null || newUser.getPassword().isEmpty())
        	throw new Exception("Senha inválida!");
	
	if (repository.existsByEmail(newUser.getEmail()))
		throw new Exception ("Já existe um usuario com esse email!");
	
	newUser.setName(newUser.getName().trim());
		// invocar metodo camada repository
	this.repository.save(newUser);
	
		return newUser;
       }
	
	public List<UserEntity> findAll() throws Exception {
		return repository.findAll();
	}
	private boolean validarEmail(String email) {
        // Cria o padrão baseado na regex
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        // Cria um matcher para comparar o e-mail
        Matcher matcher = pattern.matcher(email);
        // Retorna verdadeiro se o e-mail for válido, falso caso contrário
        return matcher.matches();
        
    }
}