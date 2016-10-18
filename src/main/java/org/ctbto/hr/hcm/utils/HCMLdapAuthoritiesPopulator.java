package org.ctbto.hr.hcm.utils;

import java.util.ArrayList;
import java.util.Collection;

import org.ctbto.hr.hcm.hibernate.DBUser;
import org.ctbto.hr.hcm.manager.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.transaction.annotation.Transactional;

public class HCMLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	@Autowired
    private DatabaseManager dbManager;	
	
	@Override
	@Transactional(readOnly = true) 
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
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
		
		return auth;
	}
	
	
	

}
