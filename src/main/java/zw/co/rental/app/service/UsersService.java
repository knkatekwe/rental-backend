package zw.co.rental.app.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import zw.co.rental.app.dto.UsersDTO;
import zw.co.rental.app.entity.User;

public interface UsersService {
	
	public List<User> getUsers();

    public User getUser(Integer userId);
    
    public User saveUser(User usersDTO);
    
    // public int updateProfileImage(String profileImage , int userID);
    
    public int store(MultipartFile file, int userID ,  HttpSession session);

    public long getTotalUsers();
    
    public UsersDTO searchUser(String email);

}
