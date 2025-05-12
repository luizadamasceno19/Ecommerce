package com.revisao.ecommerce.dto;

import java.time.format.DateTimeFormatter;

import com.revisao.ecommerce.entities.Pedido;

public class RelatorioPedidoDTO {

	private Long pedido;
	private String cliente;
	private String status;
	private String momento;
	
	public RelatorioPedidoDTO(Pedido entity) {
		this.pedido = entity.getId();
		this.cliente = entity.getCliente().getNome();
		this.status = entity.getStatus().toString();
		this.momento = entity.getMomento().atZone(java.time.ZoneId.systemDefault())
		.format(DateTimeFormatter.ofPattern("ad/MM/yyyy HH: mn"));
	}

	public Long getPedido() {
		return pedido;
	}

	public String getCliente() {
		return cliente;
	}

	public String getStatus() {
		return status;
	}

	public String getMomento() {
		return momento;
	}
	
	
}
