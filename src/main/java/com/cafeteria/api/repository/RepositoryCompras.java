package com.cafeteria.api.repository;

import com.cafeteria.api.models.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RepositoryCompras extends JpaRepository<Compras, UUID> {

    List<Compras> findByCartao(String cartao);

    @Query("SELECT c FROM Compras c WHERE c.credits_id.id = :cartaoId AND c.date BETWEEN :primeiroDiaMes AND :ultimoDiaMes")
    List<Compras> findByCartaoIdAndDataBetween(@Param("cartaoId") UUID cartaoId, @Param("primeiroDiaMes") LocalDateTime primeiroDiaMes, @Param("ultimoDiaMes") LocalDateTime ultimoDiaMes);


}
