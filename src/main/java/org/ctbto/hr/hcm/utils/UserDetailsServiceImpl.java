package org.ctbto.hr.hcm.utils;

import org.springframework.transaction.annotation.Transactional;
import org.ctbto.hr.hcm.hibernate.DBUser;
import org.ctbto.hr.hcm.manager.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.AclDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.*;

public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
    private DatabaseManager dbManager;	
	
    @Transactional(readOnly = true) 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, AclDataAccessException{	

    	DBUser user = dbManager.getUserByName(username);
		if(user == null){
			throw new UsernameNotFoundException("user not found");
		}
		
		//Collection <GrantedAuthority> authorities = getAuthorities(user.getAuthority());
		Collection <GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority("ROLE_user"));
System.out.println("Role user granted");
		if ("administrator".equalsIgnoreCase(user.getPrivilege_group())) {
			auth.add(new SimpleGrantedAuthority("ROLE_admin"));
System.out.println("Role admin granted");
		} else if (("office_es".equalsIgnoreCase(user.getPrivilege_group()))) {
			auth.add(new SimpleGrantedAuthority("ROLE_office_es"));
System.out.println("Role office_es granted");
		} else if (("chief_hr".equalsIgnoreCase(user.getPrivilege_group()))) {
			auth.add(new SimpleGrantedAuthority("ROLE_chief_hr"));
System.out.println("Role chief_hr granted");
		} else if (("hr_assistant".equalsIgnoreCase(user.getPrivilege_group()))) {
			auth.add(new SimpleGrantedAuthority("ROLE_hr_assistant"));
System.out.println("Role hr_assistant granted");
		} else if (("hr_officer".equalsIgnoreCase(user.getPrivilege_group()))) {
			auth.add(new SimpleGrantedAuthority("ROLE_hr_officer"));
System.out.println("Role hr_officer granted");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auth);
	}
	
	
	private Collection<GrantedAuthority> getAuthorities(String role) {
		
//		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority("ROLE_user"));
		authList.add(new SimpleGrantedAuthority("ROLE_admin"));
		authList.add(new SimpleGrantedAuthority("ROLE_office_es"));
		authList.add(new SimpleGrantedAuthority("ROLE_chief_hr"));
		authList.add(new SimpleGrantedAuthority("ROLE_hr_assistant"));
		authList.add(new SimpleGrantedAuthority("ROLE_hr_officer"));
		
		return authList;
	}
			
	public String getLoggedUserName(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		return auth.getName();
	}
	
}
