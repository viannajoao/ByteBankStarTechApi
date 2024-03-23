package com.cafeteria.api.controllers;

import com.cafeteria.api.models.*;
import com.cafeteria.api.repository.Repository;
import com.cafeteria.api.repository.RepositoryCard;
import com.cafeteria.api.repository.RepositoryCompras;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.LimitExceededException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class Controllers {

    @Autowired
    private Repository repository;

    @Autowired
    private ClientService clientService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody @Valid Clients c) {
        if (clientService.userExist(c.getEmail())) {
            return ResponseEntity.badRequest().body("Usuario ja existente");
        } else if(clientService.cpfExist(c.getCpf())){
            return ResponseEntity.badRequest().body("Usuario ja existente");
        }

         repository.save(c);

        return ResponseEntity.ok("Usuario cadastrado");
    }


    @GetMapping("/")
    public Iterable<Clients> PrincipalScreen() {

        return repository.findAll();
    }
    @GetMapping("/cartoes/cadastrarCartao")
    public ResponseEntity<List<Clients>> findClientsNoCard() {
        List<Clients> clientsWithoutCard = clienteService.clientsNoCard();

        if (!clientsWithoutCard.isEmpty()) {
            return ResponseEntity.ok(clientsWithoutCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientById(@PathVariable("id") UUID id) {
        Clients client = repository.findById(id).orElse(null);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> edite(@PathVariable("id") UUID id, @RequestBody @Valid Clients c) {

        if (!clientService.cpfExist(c.getCpf())) {

            return ResponseEntity.badRequest().body("Usuario ja existente");

        }

        repository.save(c);

        return ResponseEntity.ok("Usuario cadastrado");

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        repository.deleteById(id);
    }



    // ============================= FUNCOES CRUD PARA CARTOES ==============================================

    @Autowired
    private Credito card;


    @Autowired
    private RepositoryCard repositoryCard;

    @PostMapping("/cadastrarCartoes")
    public Credito cadastrarCartao(@RequestBody Credito credito) {
        String validade = card.getValidade();
       return repositoryCard.save(credito);

    }

    @GetMapping("/cartoes")
    public Iterable<Credito> Cartoes() {

        return repositoryCard.findAll();
    }

    @GetMapping("/cartoes/{id}")
    public ResponseEntity<Credito> getCreditoById(@PathVariable("id") UUID id) {
        Credito credito = repositoryCard.findById(id).orElse(null);
        if (credito != null) {
            return new ResponseEntity<>(credito, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cartoes/{id}")
    public void deleteCard(@PathVariable UUID id){
        repositoryCard.deleteById(id);
    }

    @PutMapping("/cartoes/{id}")
    public Credito editeCredit(@PathVariable("id") UUID id, @RequestBody Credito c) {
        return repositoryCard.save(c);
    }



    // ================================== CRUD PARA COMPRAS ==================================//


    @Autowired
    RepositoryCompras repositoryCompras;

    @GetMapping("/cartoes/faturas/{cartao}")
    public ResponseEntity<List<Compras>> getComprasPorNumeroCartao(@PathVariable("cartao") String cartao) {
        List<Compras> compras = repositoryCompras.findByCartao(cartao);
        return new ResponseEntity<>(compras, HttpStatus.OK);

    }


    @PostMapping("/compras/cadastrarCompras")
    public ResponseEntity<String> cadastrarCompra(@RequestBody Compras c) {
        if(c.getValor() < c.getCredits_id().getLimity()){

            double limiteAtual = c.getCredits_id().getLimity();
            // Obter todas as compras do cartão no mês atual
            LocalDateTime primeiroDiaMes = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
            LocalDateTime ultimoDiaMes = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
            List<Compras> comprasMesAtual = repositoryCompras.findByCartaoIdAndDataBetween(c.getCredits_id().getId(), primeiroDiaMes, ultimoDiaMes);

            double totalComprasMesAtual = comprasMesAtual.stream().mapToDouble(Compras::getValor).sum();

            totalComprasMesAtual += c.getValor();

            if(totalComprasMesAtual > limiteAtual){
                return ResponseEntity.badRequest().body("Limite excedido para esta compra " + totalComprasMesAtual);
            } else {
                repositoryCompras.save(c);
                return ResponseEntity.ok("Compra realizada com sucesso " + totalComprasMesAtual);
            }
        }else {
            return ResponseEntity.badRequest().body("Valor acima do limite");

        }


    }


    // ============================== RELATORIOS =====================================//

    @Autowired
    private ClientService clienteService;


    @GetMapping("/relatorios")
    public ResponseEntity<List<Map<String, Object>>> getClientesCompras() {
        List<Object[]> results = clienteService.getClientesMaisCompraram();
        List<Map<String, Object>> clientesCompras = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> clienteCompras = new HashMap<>();
            clienteCompras.put("name", result[1]);
            clienteCompras.put("total_compras", result[3]);
            clienteCompras.put("horario", result[2]);
            clienteCompras.put("id", result[0]); // Se houver um quarto elemento como id
            // Se houver mais campos, adicione-os conforme necessário
            clientesCompras.add(clienteCompras);
        }

        return new ResponseEntity<>(clientesCompras, HttpStatus.OK);
    }

    @GetMapping("/compras/relatorioGastos")
    public List<ComprasDTO> findSumValorByCategoria() {
        List<Object[]> resultados = clientService.findSumValorByCategoria();
        List<ComprasDTO> dtos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            String categoria = (String) resultado[0];
            Double valor = (Double) resultado[1];
            LocalDateTime date = (LocalDateTime) resultado[2];
            dtos.add(new ComprasDTO(categoria, valor, date));
        }
        return dtos;
    }




    @GetMapping("/relatorios/maisgastaram")
    public ResponseEntity<List<Map<String, Object>>> getClientesMaisGastaram() {
        List<Object[]> results = clienteService.getClientesMaisGastaram();
        List<Map<String, Object>> clientesCompras = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> clienteCompras = new HashMap<>();
            clienteCompras.put("name", result[1]);
            clienteCompras.put("maior_valor", result[3]);
            clienteCompras.put("horario", result[2]);
            clienteCompras.put("id", result[0]); // Se houver um quarto elemento como id
            // Se houver mais campos, adicione-os conforme necessário
            clientesCompras.add(clienteCompras);
        }

        return new ResponseEntity<>(clientesCompras, HttpStatus.OK);
    }

    @GetMapping("/relatorios/clientsNotBuy")
    public List<ClientBuyDTO> getClientesNaoCompraram() {
        List<Object[]> resultados = clientService.getClientesNaoCompraramNada();
        List<ClientBuyDTO> dtos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            UUID clienteId = (UUID) resultado[0];
            String nomeCliente = (String) resultado[1];
            LocalDateTime date = null;
            if (resultado[2] != null) {
                date = (LocalDateTime) resultado[2];
            }
            dtos.add(new ClientBuyDTO(clienteId, nomeCliente, date));
        }
        return dtos;
    }


}


