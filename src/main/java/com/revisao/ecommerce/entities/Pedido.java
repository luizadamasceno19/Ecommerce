package com.revisao.ecommerce.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant momento;
    private StatusDoPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")  // Relacionamento com o cliente
    private Usuario cliente;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;  // Relacionamento com o pagamento

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemDoPedido> items = new HashSet<>();  // Relacionamento com itens do pedido

    public Pedido() {
    }

    public Pedido(Long id, Instant momento, StatusDoPedido status) {
        this.id = id;
        this.momento = momento;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMomento() {
        return momento;
    }

    public void setMomento(Instant momento) {
        this.momento = momento;
    }

    public StatusDoPedido getStatus() {
        return status;
    }

    public void setStatus(StatusDoPedido status) {
        this.status = status;
    }

    public Set<ItemDoPedido> getItems() {
        return items;
    }

    public List<Produto> getpedido() {
        return items.stream().map(x -> x.getProduto()).toList();
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
