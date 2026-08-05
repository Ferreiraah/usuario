package com.javaproject.business.converter;

import com.javaproject.Infrastructure.entity.Endereco;
import com.javaproject.Infrastructure.entity.Telefone;
import com.javaproject.Infrastructure.entity.Usuario;
import com.javaproject.business.dto.EnderecoDTO;
import com.javaproject.business.dto.TelefoneDTO;
import com.javaproject.business.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefone(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecosDTO) {
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecosDTO) {
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefonesDTO) {
        return telefonesDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }
    //////////////////////////////////////////////////////  DTOS  //////////////////////////////////////////////////////
    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO) {
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefone()))
                .build();
    }

public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecosDTO) {
    List<EnderecoDTO> enderecos = new ArrayList<>();
    for (Endereco enderecoDTO : enderecosDTO) {
        enderecos.add(paraEnderecoDTO(enderecoDTO));
    }
    return enderecos;
}

public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
    return EnderecoDTO.builder()
            .id(endereco.getId())
            .rua(endereco.getRua())
            .cidade(endereco.getCidade())
            .complemento(endereco.getComplemento())
            .cep(endereco.getCep())
            .estado(endereco.getEstado())
            .numero(endereco.getNumero())
            .build();
}

public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefonesDTO) {
    return telefonesDTO.stream().map(this::paraTelefoneDTO).toList();
}

public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
    return TelefoneDTO.builder()
            .id(telefone.getId())
            .numero(telefone.getNumero())
            .ddd(telefone.getDdd())
            .build();
}

public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome()!=null?usuarioDTO.getNome(): entity.getNome())
                .id(usuarioDTO.getId())
                .senha(usuarioDTO.getSenha()!=null ? usuarioDTO.getSenha(): entity.getSenha())
                .email(usuarioDTO.getEmail()!=null ? usuarioDTO.getEmail(): entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefone(entity.getTelefone())
                .build();
}

public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua()!=null?dto.getRua(): entity.getRua())
                .numero(dto.getNumero()!=null? dto.getNumero(): entity.getNumero())
                .cidade(dto.getCidade()!=null? dto.getCidade(): entity.getCidade())
                .cep(dto.getCep()!=null?dto.getCep():entity.getCep())
                .complemento(dto.getComplemento()!=null?dto.getComplemento(): entity.getComplemento())
                .estado(dto.getEstado()!=null?dto.getEstado(): entity.getEstado())
                .build();
}

public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd()!=null?dto.getDdd(): entity.getDdd())
                .numero(dto.getNumero()!=null?dto.getNumero(): entity.getNumero())
                .build();
}

}