package com.javaproject.business;


import com.javaproject.Infrastructure.entity.Endereco;
import com.javaproject.Infrastructure.entity.Telefone;
import com.javaproject.Infrastructure.entity.Usuario;
import com.javaproject.Infrastructure.execptions.ConflictException;
import com.javaproject.Infrastructure.execptions.ResourceNotFoundException;
import com.javaproject.Infrastructure.repository.EnderecoRepository;
import com.javaproject.Infrastructure.repository.TelefoneRepository;
import com.javaproject.Infrastructure.repository.UsuarioRepository;
import com.javaproject.Infrastructure.security.JwtUtil;
import com.javaproject.business.converter.UsuarioConverter;
import com.javaproject.business.dto.EnderecoDTO;
import com.javaproject.business.dto.TelefoneDTO;
import com.javaproject.business.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.osgi.resource.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter  usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email nao encontrado" + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email nao encontrado");
        }
    }
    public void deletaUsuarioPorEmail(String email){

        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario (String token, UsuarioDTO dto){
        // Buscamos o email do usuario atraves do Token, assim tirando a obrigatoriedade do email
        String email = jwtUtil.extractEmail(token.substring(7));

        //Cryptografia da senha
        dto.setSenha(dto.getSenha()!=null?passwordEncoder.encode(dto.getSenha()): null);

        //Busca os dados do usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Email nao localizado"));
        //Mesclou os dados que recebemos na requisicao DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Salvamos os dados do usuario convertido e depois pegou o retorno e converteu para UsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));


    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(
                ()-> new ResourceNotFoundException("id nao encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);
        
        return  usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(
                ()-> new ResourceNotFoundException("id nao encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
