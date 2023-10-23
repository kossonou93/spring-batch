package com.ntica.springbatch.writer;

import com.ntica.springbatch.model.Tache;
import com.ntica.springbatch.repository.TacheRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TacheItemWriter implements ItemWriter<Tache> {

    @Autowired
    private TacheRepository tacheRepository;

    @Override
    public void write(List<? extends Tache> list) throws Exception {
        tacheRepository.saveAll(list);
    }
}
