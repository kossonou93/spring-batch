package com.ntica.springbatch.config;

import com.ntica.springbatch.listener.JobCompletionListener;
import com.ntica.springbatch.model.Tache;
import com.ntica.springbatch.step.Processor;
import com.ntica.springbatch.step.Reader;
import com.ntica.springbatch.step.Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemReader<Tache> tacheItemReader;

    @Autowired
    private ItemWriter<Tache> tacheItemWriter;

    @Autowired
    private ItemProcessor<Tache, Tache> tacheItemProcessor;

    @Bean
    public Job tacheJob(){
        Step step1 = stepBuilderFactory.get("step-load-data")
                .<Tache, Tache>chunk(100)
                .reader(tacheItemReader)
                .processor(tacheItemProcessor)
                .writer(tacheItemWriter)
                .build();

        return jobBuilderFactory.get("tache-data-loader-job")
                .start(step1)
                .build();
    }

    @Bean
    public FlatFileItemReader<Tache> flatFileItemReader(@Value("${inputFile}") Resource inputFile){
        FlatFileItemReader<Tache> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("FFIR1");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(lineMappe());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Tache> lineMappe(){
        DefaultLineMapper<Tache> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id","nom","priorite","etat","dateDebut","dateEcheance","pourcentage","note");
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper();
        beanWrapperFieldSetMapper.setTargetType(Tache.class);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return lineMapper;
    }

    /*@Bean
    public Job processJob() {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer()).listener(listener())
                .flow(orderStep1()).end().build();
    }

    @Bean
    public Step orderStep1() {
        return stepBuilderFactory.get("orderStep1").<String, String> chunk(1)
                .reader(new Reader()).processor(new Processor())
                .writer(new Writer()).build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionListener();
    }*/
}
