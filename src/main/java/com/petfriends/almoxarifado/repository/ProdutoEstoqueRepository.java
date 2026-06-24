package com.petfriends.almoxarifado.repository;

import com.petfriends.almoxarifado.domain.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoque, String> {

}
