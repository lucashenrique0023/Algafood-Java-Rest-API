package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	 private static final String MSG_USUARIO_EM_USO = "Usuario de código %d não pode ser removido, pois está em uso";

	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
         
     @Transactional
     public Usuario save(Usuario usuario) {
         
    	 usuarioRepository.detach(usuario);
    	 
    	 Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
    	 
    	 if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
    		 throw new NegocioException(
    				 String.format("Ja existe um usuario cadastrado com o e-mail %s", usuario.getEmail()));
    	 }
    	 
         return usuarioRepository.save(usuario);
     }
     
     @Transactional
     public void remover(Long usuarioId) {
         try {
        	 usuarioRepository.deleteById(usuarioId);
        	 usuarioRepository.flush();
             
         } catch (EmptyResultDataAccessException e) {
             throw new UsuarioNaoEncontradoException(usuarioId);
         
         } catch (DataIntegrityViolationException e) {
             throw new EntidadeEmUsoException (
                 String.format(MSG_USUARIO_EM_USO, usuarioId));
         }
     }
     
     @Transactional
     public void atualizarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
    	 
    	 Usuario usuario = buscarOuFalhar(usuarioId);
    	 
    	 if (usuario.senhaNaoCoincideCom(senhaAtual)) {
    		 throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
    	 }
    	 
    	 usuario.setSenha(novaSenha);
     }
     
     @Transactional
 	public void desassociarGrupo(Long usuarioId, Long grupoId) {	
 		Usuario usuario = buscarOuFalhar(usuarioId);
 		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
 		
 		usuario.desassociarGrupo(grupo);
 	}
 	
 	@Transactional
 	public void associarGrupo(Long usuarioId, Long grupoId) {	
 		Usuario usuario = buscarOuFalhar(usuarioId);
 		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
 		
 		usuario.associarGrupo(grupo);
 	}
     
     public Usuario buscarOuFalhar(Long usuarioId) {
    	 return usuarioRepository.findById(usuarioId)
    			 .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
     }

}
