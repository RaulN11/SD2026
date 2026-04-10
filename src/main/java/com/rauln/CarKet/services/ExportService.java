package com.rauln.CarKet.services;

import com.rauln.CarKet.export.ExportStrategyInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExportService {
    private final Map<String, ExportStrategyInterface> strategies;

    public ExportStrategyInterface getStrategy(String format){
        ExportStrategyInterface strategy = strategies.get(format.toLowerCase()+"Export");
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid export format!");
        }
        return strategy;
    }
}
