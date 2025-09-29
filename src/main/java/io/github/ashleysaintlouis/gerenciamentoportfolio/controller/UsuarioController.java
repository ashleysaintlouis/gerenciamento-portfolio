//package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;
//
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario.UsuarioDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario.UsuarioResponseDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.service.UsuarioService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/usuario")
//public class UsuarioController {
//    @Autowired
//    private UsuarioService usuarioService;
//
//
//    @PostMapping("/register")
//    public ResponseEntity<UsuarioResponseDto> register(@RequestBody UsuarioDto usuarioDto){
//        UsuarioResponseDto usuarioResponse = usuarioService.criarUsuario(usuarioDto);
//        return new ResponseEntity<>(usuarioResponse,HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<UsuarioResponseDto> login(@RequestBody UsuarioDto usuarioDto){
//        UsuarioResponseDto usuarioResponse = usuarioService.logar(usuarioDto);
//        return new ResponseEntity<>(usuarioResponse,HttpStatus.OK);
//    }
//}
