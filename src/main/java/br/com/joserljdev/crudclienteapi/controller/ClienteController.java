package br.com.joserljdev.crudclienteapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.joserljdev.crudclienteapi.domain.Cliente;
import br.com.joserljdev.crudclienteapi.domain.Endereco;
import br.com.joserljdev.crudclienteapi.repository.ClienteRepository;
import br.com.joserljdev.crudclienteapi.service.CRUDClienteService;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CRUDClienteService crudClienteService;

	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> listarPeloClienteId(@PathVariable Long clienteId) {
		Cliente cliente = crudClienteService.listarPeloId(clienteId);
		return ResponseEntity.ok(cliente);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return crudClienteService.salvar(cliente);
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {

		if (!clienteRepository.existsById(clienteId))
			return ResponseEntity.notFound().build();

		cliente.setId(clienteId);
		cliente = crudClienteService.salvar(cliente);

		return ResponseEntity.ok().body(cliente);
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		crudClienteService.excluir(clienteId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/endereco/{cep}")
	public ResponseEntity<Endereco> buscarEnderecoPeloCep(@PathVariable String cep) {
		RestTemplate restTemplate = new RestTemplate();

		String uri = "http://viacep.com.br/ws/{cep}/json/";

		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		Endereco endereco = restTemplate.getForObject(uri, Endereco.class, params);

		return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);
	}
}