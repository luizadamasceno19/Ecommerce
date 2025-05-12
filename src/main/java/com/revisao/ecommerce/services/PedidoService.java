package com.revisao.ecommerce.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revisao.ecommerce.dto.ItemDoPedidoDTO;
import com.revisao.ecommerce.dto.PedidoDTO;
import com.revisao.ecommerce.entities.ItemDoPedido;
import com.revisao.ecommerce.entities.Pedido;
import com.revisao.ecommerce.entities.Produto;
import com.revisao.ecommerce.entities.StatusDoPedido;
import com.revisao.ecommerce.entities.Usuario;
import com.revisao.ecommerce.repositories.ItemDoPedidoRepository;
import com.revisao.ecommerce.repositories.PedidoRepository;
import com.revisao.ecommerce.repositories.ProdutoRepository;
import com.revisao.ecommerce.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {
	
	@Autowired
    PedidoRepository pedidoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ItemDoPedidoRepository itemDoPedidoRepository;
    
	@Transactional
    public PedidoDTO inserir(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setMomento(Instant.now());
        pedido.setCliente(usuarioRepository.findById(dto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
 
        for (ItemDoPedidoDTO itemDto : dto.getItems()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
 
            ItemDoPedido item = new ItemDoPedido(pedido, produto, itemDto.getQuantidade(), itemDto.getPreco());
            pedido.getItems().add(item); 
        }
 
        pedido = pedidoRepository.save(pedido); 
        itemDoPedidoRepository.saveAll(pedido.getItems());
        return new PedidoDTO(pedido);
    }
    
    public List<Pedido> listarTodos(){
		return pedidoRepository.findAll();
	}
    
    public String deletarPedido(Long id) {
    	pedidoRepository.deleteById(id);
		return "Pedido deletado!";
	}
    
    public Pedido atualizarPedido(Long id, Pedido atualizado) {
		Pedido pedido = pedidoRepository.findById(id).get();   
		pedido.setCliente(atualizado.getCliente());
		pedido.setMomento(atualizado.getMomento());;
		pedido.setStatus(atualizado.getStatus());
		return pedidoRepository.save(pedido);
	}
}