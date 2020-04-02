package com.example.algamoney.api.repository.lancamento;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LancamentoRepositoryQuery{

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
