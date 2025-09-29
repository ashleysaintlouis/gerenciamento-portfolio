//package io.github.ashleysaintlouis.gerenciamentoportfolio.security;
//
//import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Usuario;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//
//public class CustomUserDetails implements UserDetails {
//
//    private final Usuario usuario;
//
//    public CustomUserDetails(Usuario usuario) {
//        this.usuario = usuario;
//    }
//
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getPassword() {
//        return usuario.getSenha();
//    }
//
//    @Override
//    public String getUsername() {
//        return usuario.getEmail();
//    }
//
//    @Override public boolean isAccountNonExpired() { return true; }
//    @Override public boolean isAccountNonLocked() { return true; }
//    @Override public boolean isCredentialsNonExpired() { return true; }
//
//}
