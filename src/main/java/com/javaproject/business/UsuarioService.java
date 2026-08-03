package com.javaproject.business;


import com.javaproject.Infrastructure.entity.Usuario;
import com.javaproject.Infrastructure.repository.UsuarioRepository;
import com.javaproject.business.converter.UsuarioConverter;
import com.javaproject.business.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter  usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));

    }
}
