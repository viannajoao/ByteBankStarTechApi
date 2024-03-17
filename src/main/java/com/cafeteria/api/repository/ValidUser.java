package com.cafeteria.api.repository;

import com.cafeteria.api.models.ClientBuyDTO;
import com.cafeteria.api.models.Clients;
import com.cafeteria.api.models.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ValidUser extends JpaRepository<Clients, UUID> {
    Clients findByEmail(String email);
    Clients findByCpf(String cpf);

    @Query("SELECT c.categoria, SUM(c.valor), c.date FROM Compras c GROUP BY c.categoria, c.date ")
    List<Object[]> findSumValorByCategoria();

    @Query("SELECT c FROM Clients c WHERE NOT EXISTS (SELECT 1 FROM Credito cc WHERE cc.clients_id = c)")
    List<Clients> findClientesSemCartao();


    @Query("SELECT c.id AS cliente_id, c.name AS nome_cliente, " +
            "DATE_FORMAT(co.date, '%d/%m/%Y') AS data_compra, " +
            "COUNT(co) AS total_compras " +
            "FROM Clients c " +
            "JOIN Credito ca ON c.id = ca.clients_id.id " +
            "JOIN Compras co ON ca.id = co.credits_id.id " +
            "GROUP BY c.id, c.name, data_compra " +
            "ORDER BY data_compra DESC")
    List<Object[]> findClientesMaisCompraram();


    @Query("SELECT c.id AS cliente_id, c.name AS nome_cliente, " +
            "DATE_FORMAT(co.date, '%d/%m/%Y') AS data_compra, " +
            "MAX(co.valor) AS maior_valor " +
            "FROM Clients c " +
            "JOIN Credito ca ON c.id = ca.clients_id.id " +
            "JOIN Compras co ON ca.id = co.credits_id.id " +
            "GROUP BY c.id, c.name, data_compra " +
            "ORDER BY maior_valor DESC")
    List<Object[]> findClientesMaisGastaram();

    @Query("SELECT c.id AS cliente_id, c.name AS nome_cliente, " +
            "DATE_FORMAT(co.date, '%d/%m/%Y') AS data_compra " +
            "FROM Clients c " +
            "LEFT JOIN Credito ca ON c.id = ca.clients_id.id " +
            "LEFT JOIN Compras co ON ca.id = co.credits_id.id " +
            "WHERE co.id IS NULL")
    List<Object[]> findNaoCompraramNada();

}
