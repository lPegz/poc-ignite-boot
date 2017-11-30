package br.pegz.pocigniteboot.model;

import lombok.Value;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Value
public class AlertConfig implements Serializable {

    @QuerySqlField(index = true)
    @NotNull
    String serviceCode;

    @QuerySqlField(index = true)
    @NotNull
    String errorCode;

    List<String> emails;
    int maxCount;
    String mailTemplate;


}
