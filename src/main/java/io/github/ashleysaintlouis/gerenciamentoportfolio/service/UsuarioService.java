//package io.github.ashleysaintlouis.gerenciamentoportfolio.service;
//
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario.UsuarioDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario.UsuarioResponseDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.UsuarioMapper;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Usuario;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.UsuarioRepository;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.security.utils.TokenService;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UsuarioService {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//    @Autowired
//    private UsuarioMapper usuarioMapper;
//    @Autowired
//    TokenService tokenService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    public UsuarioResponseDto criarUsuario(UsuarioDto usuarioDto){
//       Usuario usuario = usuarioMapper.toEntity(usuarioDto);
//       Usuario salvo = usuarioRepository.save(usuario);
//       return usuarioMapper.toResponseDto(salvo);
//    }
//
//    public UsuarioResponseDto logar(UsuarioDto usuarioDto){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(usuarioDto.email(), usuarioDto.senha())
//        );
//        Usuario usuario = usuarioRepository.findByEmail(usuarioDto.email())
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
//
//        String token = tokenService.gerarToken(usuario);
//
//        UsuarioResponseDto usuarioResponseDto = usuarioMapper.toResponseDto(usuario);
//        return usuarioResponseDto;
//    }
//
//}
