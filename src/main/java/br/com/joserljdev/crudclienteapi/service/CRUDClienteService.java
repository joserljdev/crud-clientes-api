package br.com.joserljdev.crudclienteapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joserljdev.crudclienteapi.domain.Cliente;
import br.com.joserljdev.crudclienteapi.exceptionhandler.ClienteException;
import br.com.joserljdev.crudclienteapi.repository.ClienteRepository;

@Service
public class CRUDClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente listarPeloId(Long clienteId) {
		return clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteException("Cliente não encontrado."));
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public void excluir(Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);

		cliente.map(e -> new Cliente()).orElseThrow(() -> new ClienteException("Cliente não encontrado."));

		clienteRepository.deleteById(clienteId);
	}
}
