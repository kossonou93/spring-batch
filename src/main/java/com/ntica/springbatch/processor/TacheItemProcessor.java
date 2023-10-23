package com.ntica.springbatch.processor;

import com.ntica.springbatch.model.Tache;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class TacheItemProcessor implements ItemProcessor<Tache, Tache> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Tache process(Tache tache) throws Exception {
        //tache.setDateDebut(dateFormat.parse(String.valueOf(tache.getDateDebut())));
        //tache.setDateEcheance(dateFormat.parse(String.valueOf(tache.getDateEcheance())));
        return tache;
    }
}
