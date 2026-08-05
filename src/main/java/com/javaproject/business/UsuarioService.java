package com.javaproject.business;


import com.javaproject.Infrastructure.entity.Usuario;
import com.javaproject.Infrastructure.execptions.ConflictException;
import com.javaproject.Infrastructure.execptions.ResourceNotFoundException;
import com.javaproject.Infrastructure.repository.UsuarioRepository;
import com.javaproject.business.converter.UsuarioConverter;
import com.javaproject.business.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter  usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));

    }

    public void emailExiste(String email) {
        try{
            boolean existe = usuarioRepository.existsByEmail(email);
            if(existe){
                throw new ConflictException("Email ja cadastrado" + email);
            }
        }catch(ConflictException e){
            throw new ConflictException("Email ja cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);}

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email nao encontrado"));
    }
    public void deletaUsuarioPorEmail(String email){

        usuarioRepository.deleteByEmail(email);
    }
}
