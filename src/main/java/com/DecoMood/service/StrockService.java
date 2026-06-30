package com.DecoMood.service;

import com.DecoMood.entity.Stock;
import com.DecoMood.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StrockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> listarStocks(){
        return stockRepository.findAll();
    }

}
