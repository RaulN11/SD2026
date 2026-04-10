package com.rauln.CarKet.export;

import com.rauln.CarKet.model.Advertisement;

import java.util.List;

public interface ExportStrategyInterface {
    String export(List<Advertisement> ads) throws Exception;
    String getContentType();
    String getFileExtension();


}
