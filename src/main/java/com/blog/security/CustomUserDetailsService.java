package com.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import com.blog.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entity.User; //this is our user 
import com.blog.repository.UserRepository;

//to login from the database you will need to create a 
//Custom User Details Service
@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	private UserRepository userRepository;
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	//Override a method loadByUser Always
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		//this will either find the same variable in username or email as we are passing in both the variables
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
		.orElseThrow(() -> 
		new UsernameNotFoundException("User Not Found with username or Email"));
		
		//Spring security only understand spring security User so we need to convert our User to 
		//Spring Security User
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), 
				mapRolesToAuthorities(user.getRoles()));
		}


	//This was showing bug in Eclipse it was resolve by adding <Object> in front of map
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return (Collection<? extends GrantedAuthority>) roles.stream().<Object>map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		//return rolesCollection;
	}
		
	/*
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<com.blog.entity.Role> roles) {
		//return roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()).collect(Collectors.toList()));
		
		roles.stream().map(role->new SimpleGrantedAuthority(role.getName()).collect(Collectors.toList()));
	}*/	
	/*
		private Collection<? extends GrandtedAuthority> mapRoleToAuuthorities(Set<Role> roles);
		{
			return null;
		}
		
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return Arrays.asList(new SimpleGrantedAuthority("TEST"));
			}
		
		//this method will convert the roles to set and give it back to userDetails 
		public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
				return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
			}
		
		private Collection<? extends GrantedAuthority> translate(List<Role> roles) {
			  List<GrantedAuthority> authorities = new ArrayList<>();
			  for (Role role : roles) {
			    String name = role.getName().toUpperCase();
			    if (!name.startsWith("ROLE_")) {
			      name = "ROLE_" + name;
			    }
			    authorities.add(new SimpleGrantedAuthority(name));
			  }
			  return authorities;
			}
		*/
}
