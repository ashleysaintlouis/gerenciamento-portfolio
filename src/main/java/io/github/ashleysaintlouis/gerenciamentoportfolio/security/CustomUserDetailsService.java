//package io.github.ashleysaintlouis.gerenciamentoportfolio.security;
//
//import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Usuario;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService   implements UserDetailsService {
//
//    @Autowired
//    UsuarioRepository usuarioRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
//    {
//        Usuario usuario = usuarioRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
//
//        return new CustomUserDetails(usuario);
//    }
//
//
//}